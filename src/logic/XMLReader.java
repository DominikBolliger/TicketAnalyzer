package logic;

import modell.Ticket;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {

    private Document doc;
    private NodeList pageList;

    public XMLReader(){
        createDocument();
    }
    private void createDocument(){
        try {
            File file = new File("C:\\TicketAnalyzer\\sections.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void readNodes(){
        pageList = doc.getElementsByTagName("page");
    }

    public String[] returnPageNames(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < pageList.getLength(); i++) {
            names.add(pageList.item(i).getAttributes().getNamedItem("region").getNodeValue());
        }
        return names.toArray(new String[0]);
    }

    public String[] getColumnNames(String pageName) {
        boolean columnsReady = false;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName){
                NodeList list = pageList.item(i).getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    if (Node.ELEMENT_NODE == list.item(j).getNodeType()){
                        NodeList ticketNodes = list.item(j).getChildNodes();
                        String ticketData = "";
                        for (int k = 0; k < ticketNodes.getLength(); k++) {
                            if (Node.ELEMENT_NODE == ticketNodes.item(k).getNodeType()) {
                                if (!columnsReady) {
                                    names.add(ticketNodes.item(k).getNodeName());
                                }
                                Node node = ticketNodes.item(k).getFirstChild();
                                if (ticketData == ""){
                                    ticketData = node.getNodeValue();
                                } else {
                                    ticketData += "; " + node.getNodeValue();
                                }
                            }
                        }
                        createTicket(ticketData);
                        columnsReady = true;
                    }
                }
            }
        }
        return names.toArray(new String[0]);
    }

    private void createTicket(String ticketData) {
        String[] values = ticketData.split(";");
        new Ticket(values[0], values[1], values[2], values[3]);
    }

    public void getTickets(){

    }

}
