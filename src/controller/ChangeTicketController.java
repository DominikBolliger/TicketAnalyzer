package controller;

import application.TicketAnalyzerApplication;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modell.Ticket;

public class ChangeTicketController {

    @FXML
    protected TextField tfUrl;
    @FXML
    protected TextField tfCheckWith;
    @FXML
    protected TextField tfInfo;
    @FXML
    protected TextField tfSoftware;
    @FXML
    protected TextField tfPrio;
    @FXML
    protected CheckBox cbIsDone;
    @FXML
    protected Button btnAdd;
    private Ticket ticket;

    @FXML
    public void initialize() {
        btnAdd.disableProperty().bind(Bindings.isEmpty(tfUrl.textProperty())
                .and(Bindings.isEmpty(tfInfo.textProperty()))
                .and(Bindings.isEmpty(tfCheckWith.textProperty()))
                .and(Bindings.isEmpty(tfSoftware.textProperty()))
                .and(Bindings.isEmpty(tfPrio.textProperty()))
        );
    }

    public void setValuesToTextFields(){
        tfUrl.setText(ticket.getUrl());
        tfCheckWith.setText(ticket.getCheckWith());
        tfInfo.setText(ticket.getInfo());
        tfSoftware.setText(ticket.getSoftware());
        tfPrio.setText(ticket.getPrio());
        cbIsDone.setSelected(ticket.getIsDone().isSelected());
    }

    public void updateTicket(){
        ticket.setUrl(tfUrl.getText());
        ticket.setCheckWith(tfCheckWith.getText());
        ticket.setInfo(tfInfo.getText());
        ticket.setSoftware(tfSoftware.getText());
        ticket.setPrio(tfPrio.getText());
        ticket.setIsDone(cbIsDone);
        ((TicketController)TicketAnalyzerApplication.getTicketSceneLoader().getController()).updateTableView();
        closeStage();
    }
    public void setTicketData(Ticket ticket){
        this.ticket = ticket;
    }

    private void closeStage(){
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

}
