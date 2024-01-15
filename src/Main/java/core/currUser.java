package core;

import models.dbUtils;

public class currUser {
    private static currUser userInstance;
    private static dbUtils db;
    private static String username;
    private static String email;
    private static int userID;

    private currUser() { } 

    public static currUser getInstance() {
        if(userInstance == null) 
            userInstance = new currUser();
        return userInstance;
    }

    public boolean validateUser(String user, String pass) {
        db = dbUtils.getInstance();
        return db.validateLogIn(user, pass);
    }

    public void setUser(String user) {
        userID = db.getUserID(user);
        db.signInUser(currUser.getInstance(), userID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUser) {
        username = newUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public int getUserID() {
        return userID;
    }
    
}
