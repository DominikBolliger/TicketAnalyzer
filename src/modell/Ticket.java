package modell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.util.List;

public class Ticket {
    private String url;
    private String checkWith;
    private String info;
    private String software;
    private String prio;
    private CheckBox isDone;
    private int id;
    private static ObservableList ticketList = FXCollections.observableArrayList();

    public Ticket(String url, String checkWith, String info, String software, String prio, CheckBox isDone, int id) {
        this.url = url;
        this.checkWith = checkWith;
        this.info = info;
        this.software = software;
        this.prio = prio;
        this.isDone = isDone;
        this.id = id;
        ticketList.add(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCheckWith() {
        return checkWith;
    }

    public void setCheckWith(String checkWith) {
        this.checkWith = checkWith;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    public CheckBox getIsDone() {
        return isDone;
    }

    public void setIsDone(CheckBox isDone) {
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<Ticket> getTicketList() {
        return ticketList;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "url='" + url + '\'' +
                ", checkWith='" + checkWith + '\'' +
                ", info='" + info + '\'' +
                ", software='" + software + '\'' +
                ", prio='" + prio + '\'' +
                ", isDone=" + isDone.toString() +
                '}';
    }
}
