package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class TicketAnalyzerApplication extends Application {

    private static Stage secondaryStage;
    private static Scene addTicketScene;
    private static Scene changeTicketScene;
    private static Scene ticketScene;
    private static FXMLLoader changeTicketSceneLoader;
    private static FXMLLoader addTicketSceneLoader;
    private static FXMLLoader ticketSceneLoader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        ticketSceneLoader = new FXMLLoader(TicketAnalyzerApplication.class.getResource("/view/Tickets.fxml"));
        ticketScene = new Scene(ticketSceneLoader.load());
        primaryStage.setScene(ticketScene);
        primaryStage.setTitle("Ticket Analyzer");
        primaryStage.show();
        createSecondaryStageAndScenes(primaryStage);
    }

    private void createSecondaryStageAndScenes(Stage primaryStage) throws IOException {
        addTicketSceneLoader = new FXMLLoader(TicketAnalyzerApplication.class.getResource("/view/AddTicket.fxml"));
        addTicketScene = new Scene(addTicketSceneLoader.load());
        changeTicketSceneLoader = new FXMLLoader(TicketAnalyzerApplication.class.getResource("/view/ChangeTicket.fxml"));
        changeTicketScene = new Scene(changeTicketSceneLoader.load());
        secondaryStage = new Stage();
        secondaryStage.initModality(Modality.WINDOW_MODAL);
        secondaryStage.initOwner(primaryStage);
    }

    public static Stage getSecondaryStage() {
        return secondaryStage;
    }

    public static Scene getAddTicketScene() {
        return addTicketScene;
    }

    public static Scene getChangeTicketScene() {
        return changeTicketScene;
    }

    public static FXMLLoader getChangeTicketSceneLoader() {
        return changeTicketSceneLoader;
    }

    public static FXMLLoader getAddTicketSceneLoader() {
        return addTicketSceneLoader;
    }

    public static Scene getTicketScene() {
        return ticketScene;
    }

    public static FXMLLoader getTicketSceneLoader() {
        return ticketSceneLoader;
    }
}
