package Main;

import java.io.IOException;
import java.util.Objects;

import controllers.mainController;
import controllers.pageNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
    private static Stage stg;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        //storing the main stage in an aux stage to change scenes later
        mainStage.setResizable(false);
        stg = mainStage;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/LOGIN_PAGE.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void startDashboard() throws IOException {
        //!temp proj title, find a good one :)
        stg.setTitle("Event Organizer");
        //changing the scene to the dashboard page using the aux stage
        stg.setScene(createScene(loadMainPane()));
        stg.show();
    }
    /**
     * Loads the main fxml layout.
     * Sets up the page switching pageNavigator.
     * Loads the first page into the fxml layout.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = loader.load(getClass().getResourceAsStream(pageNavigator.DASHBOARD));

        mainController MainController = loader.getController();

        pageNavigator.setMainController(MainController);
        pageNavigator.loadPage(pageNavigator.PROFILE_PAGE);
        return mainPane;
    }

    //Creates the main application scene.
    private Scene createScene(Pane mainPane) {
        return new Scene(mainPane);
    }
    
    //function will be used by controllers to switch between scenes
    public void changeScenes(String newScene) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newScene)));
        stg.getScene().setRoot(root);
    }
}