package controller;

import application.TicketAnalyzerApplication;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    protected TextField tfPrio;
    @FXML
    protected CheckBox cbIsDone;
    @FXML
    protected Button btnAdd;
    private XMLReader reader;

    @FXML
    public void initialize() {
        btnAdd.disableProperty().bind(Bindings.isEmpty(tfUrl.textProperty())
                .and(Bindings.isEmpty(tfInfo.textProperty()))
                .and(Bindings.isEmpty(tfCheckWith.textProperty()))
                .and(Bindings.isEmpty(tfSoftware.textProperty()))
                .and(Bindings.isEmpty(tfPrio.textProperty()))
        );
    }

    public void createTicket() {
        String page = ((TicketController) TicketAnalyzerApplication.getTicketSceneLoader().getController()).getPage();
        String[] id = tfUrl.getText().toString().split("-");
        String ticketID = id[1];
        reader.addTicket(page, tfUrl.getText(), tfCheckWith.getText(), tfInfo.getText(), tfSoftware.getText(), tfPrio.getText(), String.valueOf(cbIsDone.isSelected()), ticketID);
        resetStageValues();
        closeStage();
    }

    private void resetStageValues() {
        tfUrl.setText("");
        tfCheckWith.setText("");
        tfInfo.setText("");
        tfSoftware.setText("");
        tfPrio.setText("");
        cbIsDone.setSelected(false);
    }

    public void setReader(XMLReader reader) {
        this.reader = reader;
    }

    private void closeStage() {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

}
