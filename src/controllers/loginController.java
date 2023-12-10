package controllers;

import java.io.IOException;

import Main.App;
import Main.currUser;
import Main.inputFilter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class loginController {
    private final App app = new App();
    @FXML private Button logIn;
    @FXML private Button signUp;
    @FXML private Label wrg;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML
    void switchAuth() throws IOException {
        app.changeScenes("/resources/SIGNUP_PAGE.fxml");
    }

    public void initialize() { }

    public void authLogIn() throws IOException {
        checkLogIn();
    }

    private void checkLogIn() throws IOException {
        currUser newUser = currUser.getInstance();
        String usr = username.getText().trim();
        String pass = password.getText().trim();
        pass = inputFilter.passwordHash(pass);
        if (newUser.validateUser(usr, pass)) {
            newUser.setUser(usr);
            app.startDashboard();
        } else {
            wrg.setText("Incorrect username or password");
        }
    }
}