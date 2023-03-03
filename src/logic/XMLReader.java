package logic;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableRow;
import modell.Ticket;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {

    private Document doc;
    private NodeList pageList;
    private File file;
    private String path;

    public XMLReader() throws TransformerException {
        createDocument();
        //addTicket();
    }

    private void createDocument() {
        try {
            path = "C:\\TicketAnalyzer\\sections.xml";
            file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.normalize();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void readNodes() {
        pageList = doc.getElementsByTagName("page");
    }

    public String[] getPageNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < pageList.getLength(); i++) {
            names.add(pageList.item(i).getAttributes().getNamedItem("region").getNodeValue());
        }
        return names.toArray(new String[0]);
    }

    public void getTicketsForRegion(String pageName) {
        Ticket.getTicketList().clear();
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName) {
                NodeList list = pageList.item(i).getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    if (Node.ELEMENT_NODE == list.item(j).getNodeType()) {
                        NodeList ticketNodes = list.item(j).getChildNodes();
                        int ticketID = Integer.parseInt(list.item(j).getAttributes().getNamedItem("id").getNodeValue());
                        String ticketData = "";
                        for (int k = 0; k < ticketNodes.getLength(); k++) {
                            if (Node.ELEMENT_NODE == ticketNodes.item(k).getNodeType()) {
                                Node node = ticketNodes.item(k).getFirstChild();
                                if (ticketData == "") {
                                    ticketData = node.getNodeValue();
                                } else {
                                    ticketData += ";" + node.getNodeValue();
                                }
                            }
                        }
                        createTicket(ticketData, ticketID);
                    }
                }
            }
        }
    }

    public void deleteTicketInRegion(String pageName, TableRow<Object> row) {
        int ticketID = ((Ticket) row.getItem()).getId();
        Ticket.getTicketList().clear();
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName) {
                NodeList list = pageList.item(i).getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    if (Node.ELEMENT_NODE == list.item(j).getNodeType()) {
                        if (Integer.parseInt(list.item(j).getAttributes().getNamedItem("id").getNodeValue()) == ticketID) {
                            pageList.item(i).removeChild(list.item(j));
                        }
                    }
                }
            }
        }
        saveXML();
        getTicketsForRegion(pageName);
    }

    private void createTicket(String ticketData, int ticketID) {
        String[] values = ticketData.split(";");
        CheckBox cbIsDone = new CheckBox();
        cbIsDone.setDisable(true);
        if (values[5].equals("true")) {
            cbIsDone.selectedProperty().set(true);
        }
        new Ticket(values[0], values[1], values[2], values[3], values[4], cbIsDone, ticketID);
    }

    public void saveXML() {
        try {
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", "2");
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(path);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRegion(String regionName) {
        Node root = doc.getDocumentElement();
        Node newNode = doc.createElement("page");
        ((Element) newNode).setAttribute("region", regionName);
        root.appendChild(newNode);
        saveXML();
    }

    public void addTicket(String pageName, String URL, String checkwith, String info, String software, String
            prio, String isDone, String ticketID) {
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName) {
                Node pageNode = pageList.item(i);
                Node newTicketNode = doc.createElement("ticket");
                ((Element)newTicketNode).setAttribute("id", ticketID);
                Node urlNode = doc.createElement("url");
                urlNode.setTextContent(URL);
                Node checkWithNode = doc.createElement("checkwith");
                checkWithNode.setTextContent(checkwith);
                Node infoNode = doc.createElement("info");
                infoNode.setTextContent(info);
                Node softwareNode = doc.createElement("software");
                softwareNode.setTextContent(software);
                Node prioNode = doc.createElement("prio");
                prioNode.setTextContent(prio);
                Node isDoneNode = doc.createElement("isdone");
                isDoneNode.setTextContent(isDone);
                newTicketNode.appendChild(urlNode);
                newTicketNode.appendChild(checkWithNode);
                newTicketNode.appendChild(infoNode);
                newTicketNode.appendChild(softwareNode);
                newTicketNode.appendChild(prioNode);
                newTicketNode.appendChild(isDoneNode);
                pageNode.appendChild(newTicketNode);
                saveXML();
                break;
            }
        }
        getTicketsForRegion(pageName);
    }
}
