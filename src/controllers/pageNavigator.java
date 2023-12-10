package controllers;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Objects;

public class pageNavigator {

    public static final String DASHBOARD    = "/resources/DASHBOARD_PAGE.fxml";
    public static final String LOG_IN = "/resources/LOGIN_PAGE.fxml";
    public static final String SIGN_UP = "/resources/SIGNUP_PAGE.fxml";
    public static final String EVENT_BROWSER = "/resources/BROWSING_PAGE.fxml";
    public static final String CURRENT_EVENT = "/resources/VIEW_EVENT_PAGE.fxml";
    public static final String MY_EVENTS = "/resources/MY_EVENTS_PAGE.fxml";
    public static final String PROFILE_PAGE = "/resources/MY_PROFILE_PAGE.fxml";
    public static final String CREATE_EVENT = "/resources/CREATE_EVENT_PAGE.fxml";

    /** The main application layout controller. */
    private static mainController mController;

    public static void setMainController(mainController mainController) {
        pageNavigator.mController = mainController;
    }
    /**
     * Loads the nodes specified by the fxml file into the
     * pageHolder pane of the main application layout.
     *
     * Previously loaded page for the same fxml file are not cached.
     * The fxml is loaded anew and a new page node hierarchy generated
     * every time this method is invoked.
     */
    public static void loadPage(String fxml) {
        try {
            mController.setPage(FXMLLoader.load(Objects.requireNonNull(pageNavigator.class.getResource(fxml))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
