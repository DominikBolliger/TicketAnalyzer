package controller;

import application.TicketAnalyzerApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    protected Button btnAdd;
    private Ticket ticket;

    public void setValuesToTextFields(){
        tfUrl.setText(ticket.getUrl());
        tfCheckWith.setText(ticket.getCheckWith());
        tfInfo.setText(ticket.getInfo());
        tfSoftware.setText(ticket.getSoftware());
    }

    public void updateTicket(){
        ticket.setUrl(tfUrl.getText());
        ticket.setCheckWith(tfCheckWith.getText());
        ticket.setInfo(tfInfo.getText());
        ticket.setSoftware(tfSoftware.getText());
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
