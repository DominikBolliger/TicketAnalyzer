package controller;

import application.TicketAnalyzerApplication;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import logic.XMLReader;
import modell.Ticket;

import javax.xml.transform.TransformerException;
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
    public void initialize() throws TransformerException {
        tblvTickets.setOnContextMenuRequested(event -> TicketAnalyzerApplication.getContextMenu().show(tblvTickets, event.getX(), event.getY()));
        EventHandler<javafx.scene.input.MouseEvent> onTableViewClick = this::handleTableRowMouseClick;
        tblvTickets.setRowFactory(param -> {
            TableRow<Ticket> row = new TableRow<>();
            row.setOnMouseClicked(onTableViewClick);
            return row;
        });
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

    private void handleTableRowMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
            TableRow<Object> row = (TableRow<Object>) mouseEvent.getSource();
            if (!row.isEmpty() && row.getItem() != null) {
                TicketAnalyzerApplication.getSecondaryStage().setScene(TicketAnalyzerApplication.getChangeTicketScene());
                TicketAnalyzerApplication.getSecondaryStage().setTitle("Change Ticket");
                TicketAnalyzerApplication.getSecondaryStage().show();
                ((ChangeTicketController) TicketAnalyzerApplication.getChangeTicketSceneLoader().getController()).setTicketData((Ticket) row.getItem());
                ((ChangeTicketController) TicketAnalyzerApplication.getChangeTicketSceneLoader().getController()).setValuesToTextFields();
                mouseEvent.consume();
            }
        } else if(mouseEvent.getButton() == MouseButton.SECONDARY){

        }
    }

    private void createButtons() {
        hBoxPages.getChildren().clear();
        buttonsList.clear();
        for (String pageName : reader.getPageNames()) {
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
    private void addRegion() {
        TextInputDialog tid = new TextInputDialog("Enter new Region name");
        tid.showAndWait();
        reader.addRegion(tid.getEditor().getText());
        createButtons();
    }

    public void clickedOnTableView(javafx.scene.input.MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            TicketAnalyzerApplication.getSecondaryStage().setScene(TicketAnalyzerApplication.getAddTicketScene());
            TicketAnalyzerApplication.getSecondaryStage().setTitle("Add Ticket");
            ((AddTicketController)TicketAnalyzerApplication.getAddTicketSceneLoader().getController()).setReader(reader);
            TicketAnalyzerApplication.getSecondaryStage().show();
        }
    }

    public void updateTableView(){
        tblvTickets.refresh();
    }

    public String getPage() {
        return page;
    }
}
