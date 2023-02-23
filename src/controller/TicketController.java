package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import logic.XMLReader;
import modell.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketController {
    private XMLReader reader;
    private List<Button> buttonsList;
    private List<TableColumn> columnList;
    @FXML
    protected HBox hBoxPages;
    @FXML
    protected TableView tblvTickets;
    @FXML
    public void initialize(){
        buttonsList = new ArrayList<>();
        reader = new XMLReader();
        reader.readNodes();
        createButtons();
        buttonsList.get(0).fire();
        populateTableView();
    }

    private void setUpPage(String pageName) {
        tblvTickets.getColumns().clear();
        for (String columnName:reader.getColumnNames(pageName)){
            columnList.add(new TableColumn(columnName));
        }
        tblvTickets.getColumns().addAll(columnList);
    }

    private void createButtons(){
        for (String pageName:reader.returnPageNames()){
            Button button = new Button(pageName);
            buttonsList.add(button);
            button.setOnAction(event -> {
                setUpPage(button.getText());
            });
            hBoxPages.getChildren().add(button);
        }
    }

    private void populateTableView() {
        for (TableColumn column: columnList){
            column.setCellValueFactory(new PropertyValueFactory<>(column.getText()));
        }
        tblvTickets.setItems(ticketModels);
    }

    private ObservableList<Ticket> ticketModels = FXCollections.observableList(Ticket.getTicketList());

}
