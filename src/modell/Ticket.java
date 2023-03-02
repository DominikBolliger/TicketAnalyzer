package modell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String url;
    private String checkWith;
    private String info;
    private String software;
    private static ObservableList ticketList = FXCollections.observableArrayList();

    public Ticket(String url, String checkWith, String info, String software) {
        this.url = url;
        this.checkWith = checkWith;
        this.info = info;
        this.software = software;
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
                '}';
    }
}
