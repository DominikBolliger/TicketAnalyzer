package logic;

import application.TicketAnalyzerApplication;
import controller.TicketController;
import modell.Ticket;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
    private void createDocument(){
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

    public void readNodes(){
        pageList = doc.getElementsByTagName("page");
    }

    public String[] getPageNames(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < pageList.getLength(); i++) {
            names.add(pageList.item(i).getAttributes().getNamedItem("region").getNodeValue());
        }
        return names.toArray(new String[0]);
    }

    public void getTicketsForRegion(String pageName) {
        Ticket.getTicketList().clear();
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName){
                NodeList list = pageList.item(i).getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    if (Node.ELEMENT_NODE == list.item(j).getNodeType()){
                        NodeList ticketNodes = list.item(j).getChildNodes();
                        String ticketData = "";
                        for (int k = 0; k < ticketNodes.getLength(); k++) {
                            if (Node.ELEMENT_NODE == ticketNodes.item(k).getNodeType()) {
                                Node node = ticketNodes.item(k).getFirstChild();
                                if (ticketData == ""){
                                    ticketData = node.getNodeValue();
                                } else {
                                    ticketData += "; " + node.getNodeValue();
                                }
                            }
                        }
                        createTicket(ticketData);
                    }
                }
            }
        }
    }

    private void createTicket(String ticketData) {
        String[] values = ticketData.split(";");
        new Ticket(values[0], values[1], values[2], values[3]);
    }

    public void saveXML(){
        try {
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", "2");
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(path);
            transformer.transform(source, result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addRegion(String regionName) {
        Node root = doc.getDocumentElement();
        Node newNode = doc.createElement("page");
        ((Element)newNode).setAttribute("region", regionName);
        root.appendChild(newNode);
        saveXML();
    }

    public void addTicket(String pageName, String URL, String checkwith, String info, String software){
        for (int i = 0; i < pageList.getLength(); i++) {
            if (pageList.item(i).getAttributes().getNamedItem("region").getNodeValue() == pageName){
                Node pageNode = pageList.item(i);
                Node newTicketNode = doc.createElement("ticket");
                Node urlNode = doc.createElement("url");
                urlNode.setTextContent(URL);
                Node checkWithNode = doc.createElement("checkwith");
                checkWithNode.setTextContent(checkwith);
                Node infoNode = doc.createElement("info");
                infoNode.setTextContent(info);
                Node softwareNode = doc.createElement("software");
                softwareNode.setTextContent(software);
                newTicketNode.appendChild(urlNode);
                newTicketNode.appendChild(checkWithNode);
                newTicketNode.appendChild(infoNode);
                newTicketNode.appendChild(softwareNode);
                pageNode.appendChild(newTicketNode);
                saveXML();
                break;
            }
        }
        getTicketsForRegion(pageName);
    }
}
