package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import logic.XMLReader;
import modell.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketController {

    private String page;
    private XMLReader reader;
    private List<Button> buttonsList;
    @FXML
    protected HBox hBoxPages;
    @FXML
    protected TableView tblvTickets;
    @FXML
    protected Button btnAddTicket;
    @FXML
    TableColumn<Ticket, String> tcURL;
    @FXML
    TableColumn<Ticket, String> tcInfo;
    @FXML
    TableColumn<Ticket, String> tcCheckWith;
    @FXML
    TableColumn<Ticket, String> tcSoftware;
    @FXML
    public void initialize(){
        buttonsList = new ArrayList<>();
        reader = new XMLReader();
        reader.readNodes();
        createButtons();
        buttonsList.get(0).fire();
        tcURL.setCellValueFactory(new PropertyValueFactory<Ticket, String>("Url"));
        tcInfo.setCellValueFactory(new PropertyValueFactory<Ticket, String>("Info"));
        tcCheckWith.setCellValueFactory(new PropertyValueFactory<Ticket, String>("CheckWith"));
        tcSoftware.setCellValueFactory(new PropertyValueFactory<Ticket, String>("Software"));
    }

    private void createButtons(){
        for (String pageName:reader.getPageNames()){
            Button button = new Button(pageName);
            buttonsList.add(button);
            button.setOnAction(event -> {
                page = button.getText();
                tblvTickets.getItems().clear();
                reader.getTicketsForRegion(page);
                tblvTickets.setItems((ObservableList) Ticket.getTicketList());
            });
            hBoxPages.getChildren().add(button);
        }
    }
    @FXML
    private void addTicket(){
        TextInputDialog tid = new TextInputDialog("Enter new Ticket Data");
        tid.show();
        //reader.addTicketToRegion();
    }
}
