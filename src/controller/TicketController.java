package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import logic.XMLReader;
import modell.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketController {
    private XMLReader reader;
    private List<Button> buttonsList;
    @FXML
    protected HBox hBoxPages;
    @FXML
    protected TableView tblvTickets;
    @FXML
    public void initialize(){
        buttonsList = new ArrayList<>();
        reader = new XMLReader();
        reader.readNodes();
        for (String pageName:reader.returnPageNames()){
            Button button = new Button(pageName);
            buttonsList.add(button);
            button.setOnAction(event -> {
                setUpPage(button.getText());
            });
            hBoxPages.getChildren().add(button);
        }
        buttonsList.get(0).fire();
        for (Ticket ticket:Ticket.getTicketList()){
            System.out.println(ticket);
        }
    }

    private void setUpPage(String pageName) {
        tblvTickets.getColumns().clear();
        for (String columnName:reader.getColumnNames(pageName)){
            tblvTickets.getColumns().add(new TableColumn(columnName));
        }
    }
}
