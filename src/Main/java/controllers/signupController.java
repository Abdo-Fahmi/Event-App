package controllers;

import java.io.IOException;

import core.App;
import core.dbUtils;
import core.inputFilter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class signupController {
    private final App app = new App();
    /* 
        Regular expressions used in validating the username and email inputs
        username regex
            check that length is 7-20, "_" is the only allowed symbol and the first character isn't a symbol/number

        email regex 
            checks for at least one dot in the domain name and after the dot, it consists only the letters. The top-level domain should have only two to six letters which is also checked by this regex.
    */
    private static final dbUtils db = dbUtils.getInstance();
    private static final String usrRegex = "^[a-zA-Z][a-zA-Z0-9_]{6,19}$";
    private static final String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    @FXML private Button signUp;
    @FXML private Label wrg;
    @FXML private TextField tfUsername;
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfRepeatPassword;
    @FXML
    void switchAuth() throws IOException {
        app.changeScenes("/LOGIN_PAGE.fxml");
    }
    /*
     * setting up filter on the input field to reduce faulty input
     * and having to do unnecessary checks
     */
    public void initialize() {
        inputFilter.setTextFieldLengthFormatter(pfPassword, 20);
        inputFilter.setTextFieldLengthFormatter(tfUsername, 20);
        inputFilter.setTextFieldLengthFormatter(pfRepeatPassword, 20);
    }
    public void signUp() throws IOException {
        String usr = tfUsername.getText().trim();
        String pass = pfPassword.getText().trim();
        String repeatPass = pfRepeatPassword.getText().trim();
        String email = tfEmail.getText().trim();

        if(validateSignUpInfo(wrg, usr, email, pass, repeatPass)) {
            pass = inputFilter.passwordHash(pass);
            if(signUpNewUser(usr, email, pass)) {
                switchAuth();
            } else wrg.setText("something went wrong please try again later");
        }
    }  

    //validating the input entered by the user before proceeding with registration
    public static boolean validateSignUpInfo(Label wrg, String usr, String email, String pass, String rpass) {
        if(db.getUserID(usr) != -1) {
            wrg.setText("Username already exists!");
            return false;
        }

        if(db.checkDupEmail(email)) {
            wrg.setText("Email already exists!");
            return false;
        }
        
        if(!usr.matches(usrRegex)) {
            wrg.setText("Invalid username!");
            return false;
        }

        if(!email.matches(emailRegex)) {
            wrg.setText("Invalid email!");
            return false;
        }

        if(!(pass.length() > 7)) {
            wrg.setText("Password too short!");
            return false;
        }

        if(!(pass.equals(rpass))) {
            wrg.setText("Password are not identical!");
            return false;
        }

        return true;
    }

    public static boolean signUpNewUser(String usr, String email, String pass) {
        return db.signUpNewUser(usr, email, pass);
    }
}
