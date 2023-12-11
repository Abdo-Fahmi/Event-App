package core;

import java.sql.*;
import java.util.ArrayList;

import controllers.Observer;
import proxyFlyweight.event;
import proxyFlyweight.proxyEvent;
import proxyFlyweight.proxyFactory;

public class dbUtils implements Observable {
    private static dbUtils dbInstance;
    private static Connection conn;
    private static ArrayList<Observer> controllers;
    private PreparedStatement stmt;
    private ResultSet res;
    private dbUtils() {
        try {
            String url = "jdbc:mysql://localhost:3306/eventgui";
            String usr = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, usr, pass);
            controllers = new ArrayList<>();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static dbUtils getInstance() {
        if(dbInstance == null) 
            dbInstance = new dbUtils();
        return dbInstance;
    }

    public boolean validateLogIn(String usr, String pass) {
        try {
            stmt = conn.prepareStatement("SELECT username, password FROM user WHERE username = ?");
            stmt.setString(1, usr);

            res = stmt.executeQuery();
            if(res.next()) {
                return pass.equals(res.getString("password"));
            } else {
                return false;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getUserID(String usr) {
        try {
            stmt = conn.prepareStatement("SELECT id FROM user WHERE username = ?");
            stmt.setString(1, usr);

            res = stmt.executeQuery();
            if(res.next()) {
               return res.getInt(1); 
            } else return -1;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public String getUsername(int id) {
        try {
            stmt = conn.prepareStatement("SELECT username FROM user WHERE id = ?");
            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if(res.next()) {
               return res.getString(1); 
            } else return null;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean checkDupEmail(String email) {
        try {
            stmt = conn.prepareStatement("SELECT username FROM user WHERE email = ?");
            stmt.setString(1, email);

            res = stmt.executeQuery();
            return res.next();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void signInUser(currUser user, int id) {
        try {
            stmt = conn.prepareStatement("SELECT username, email FROM user WHERE id = ?");
            stmt.setInt(1, id);

            res = stmt.executeQuery();
            if(res.next()) {
                user.setEmail(res.getString("email"));
                user.setUsername((res.getString("username")));
            }
            notifyControllers();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean signUpNewUser(String usr, String email, String pass) {
        String sql = "INSERT INTO `user`(`username`, `email`, `password`) VALUES ( ?, ?, ?)";
        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, usr);
            stmt.setString(2, email);
            stmt.setString(3, pass);

            stmt.execute();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } return true;
    }

    public ArrayList<proxyEvent> getEvents() {
        ArrayList<proxyEvent> allEvents = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT event_id, name, location, date FROM events");

            res = stmt.executeQuery();
            while(res.next()) {
                proxyEvent proxy = proxyFactory.getProxyEvent(res.getInt("event_id"),res.getString("name"),res.getString("location"),res.getDate("date"));
                allEvents.add(proxy);
            } 
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }   return allEvents;
    }

    public ArrayList<proxyEvent> getMyEvents(int id) {
        ArrayList<proxyEvent> allEvents = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT event_id, name, location, date FROM events WHERE user_id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            while(res.next()) {
                proxyEvent proxy = proxyFactory.getProxyEvent(res.getInt("event_id"),res.getString("name"),res.getString("location"),res.getDate("date"));
                allEvents.add(proxy);
            } 
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } return allEvents;
    }

    public event getEvent(int id) {
        event Event;
        try {
            stmt = conn.prepareStatement("SELECT user_id, name, location, date, description, date FROM events WHERE event_id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            res.next();
            int creator_id = res.getInt("user_id");
            Event = new event(id,res.getString("name"),res.getString("location"),res.getDate("date"),res.getString("description"));

            stmt = conn.prepareStatement("SELECT username FROM user WHERE id = ?");
            stmt.setInt(1, creator_id);
            res = stmt.executeQuery();
            res.next();
            Event.setCreatorID(res.getString("username"));
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return Event;
    }

    public boolean createEvent(String name, String elocation, String description, Date date) {
        String sql = "INSERT INTO `events` (`user_id`, `name`, `location`, `description`, `date`) VALUES ( ?, ?, ?, ?, ?)";
        currUser user = currUser.getInstance();
        int i = user.getUserID();
        System.out.println(" "+i);
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, i);
            stmt.setString(2, name);
            stmt.setString(3, elocation);
            stmt.setString(4, description);
            stmt.setDate(5, date);

            stmt.execute();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } 
        notifyControllers();
        return true;
    }

    public ArrayList<Integer> getResIDs(int userID) {
        ArrayList<Integer> reservations = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT event_id FROM reservations WHERE user_id = ?");
            stmt.setInt(1, userID);
            res = stmt.executeQuery();
            while(res.next()) {
                reservations.add(res.getInt(1));
            } 
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } return reservations;
    }

    public proxyEvent getReservedEvent(int id) {
        try {
            stmt = conn.prepareStatement("SELECT name, location, date FROM events WHERE event_id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            if(res.next()) {
                return proxyFactory.getProxyEvent(id,res.getString("name"),res.getString("location"),res.getDate("date"));
            } else return null;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    public ArrayList<Integer> getReservedUsers(int id) {
        ArrayList<Integer> reservations = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT user_id FROM reservations WHERE event_id = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            while(res.next()) {
                reservations.add(res.getInt(1));
            }
            return reservations; 
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    public void changeUsername(String newUsr, int id) {
        try {
            stmt = conn.prepareStatement("UPDATE user SET username = ? WHERE id = ?");
            stmt.setString(1, newUsr);
            stmt.setInt(2, id);

            stmt.execute();
            notifyControllers();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePassword(String newPass, int id) {
        try {
            stmt = conn.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
            stmt.setString(1, newPass);
            stmt.setInt(2, id);

            stmt.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeEmail(String newEmail, int id) {
        try {
            stmt = conn.prepareStatement("UPDATE user SET email = ? WHERE id = ?");
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);

            stmt.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addReservation(int eventID, int userID) {
        String sql = "INSERT INTO `reservations`(`event_id`, `user_id`) VALUES ( ?, ?)";
        try {
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, eventID);
            stmt.setInt(2, userID);

            stmt.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            
            return false;
        } 
        notifyControllers();
        return true;
    }

    public void deleteAccount(int userID) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            stmt.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } 
        notifyControllers();
    }

    @Override
    public void notifyControllers() {
        for(Observer i: controllers) {
            i.updateEventCollection();
        }
    }

    @Override
    public void subscribe(Observer obs) {
        if(!controllers.contains(obs)) controllers.add(obs);
    } 
}
