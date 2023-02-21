package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TicketAnalyzerApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene ticketScene = new Scene(new FXMLLoader(TicketAnalyzerApplication.class.getResource("/view/Tickets.fxml")).load());
        primaryStage.setScene(ticketScene);
        primaryStage.show();
    }
}
