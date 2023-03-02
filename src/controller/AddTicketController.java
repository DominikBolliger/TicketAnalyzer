package controller;

import application.TicketAnalyzerApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.XMLReader;
import modell.Ticket;

public class AddTicketController {

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

    private XMLReader reader;

    public void createTicket(){
        String page = ((TicketController) TicketAnalyzerApplication.getTicketSceneLoader().getController()).getPage();
        reader.addTicket(page, tfUrl.getText(), tfCheckWith.getText(), tfInfo.getText(), tfSoftware.getText());
        closeStage();
    }

    public void setReader(XMLReader reader){
        this.reader = reader;
    }

    private void closeStage(){
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

}
