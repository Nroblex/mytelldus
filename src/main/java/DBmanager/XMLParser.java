package DBmanager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.Util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ueh093 on 11/19/15.
 * Läser xml-konfig istället (för att köra på raspberry pi)
 */
public class XMLParser {

    private static String DEVICE_PATH="/devices/device";
    static Logger iLog = LogManager.getLogger(XMLParser.class);


    public static List<Devices> xmlDeviceToObject(){
        List<Devices> devices = new ArrayList<Devices>();

        File xFile = new File(Util.getSetting("device"));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xFile);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList deviceList = (NodeList) xPath.compile(DEVICE_PATH).evaluate(doc, XPathConstants.NODESET);

            for (int nCount = 0; nCount<deviceList.getLength(); nCount++){

                Node deviceNode = deviceList.item(nCount);

                if (deviceNode.getNodeType() == Node.ELEMENT_NODE){

                    Integer id =  Integer.parseInt(xPath.compile("./id").evaluate(deviceNode));
                    String name = xPath.compile("./name").evaluate(deviceNode);
                    String comment = xPath.compile("./comment").evaluate(deviceNode);

                    Devices d = new Devices();
                    d.setId(id);
                    d.setName(name);
                    d.setComment(comment);

                    devices.add(d);

                }

            }

        } catch (ParserConfigurationException e) {
            iLog.error(e);
        } catch (SAXException e) {
            iLog.error(e);
        } catch (IOException e) {
            iLog.error(e);
        } catch (XPathExpressionException e) {
            iLog.error(e);
        }


        return devices;
    }


    public static Map<Integer, SchemaDevice> xmlSchemaDeviceToObject(){


        File xFile = new File(Util.getSetting("schemadevice"));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        DateTime timePoint=null;
        String action =null;
        String weekDay = null;
        Integer dayOfWeek = DateTime.now().getDayOfWeek();
        String deviceId=null;
        String id = null;
        SchemaDevice dbSchemaDevice=null;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");

        Map<Integer, SchemaDevice> deviceMap = new HashMap<Integer, SchemaDevice>();

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xFile);

            XPath xPath = XPathFactory.newInstance().newXPath();

            ///*/item[id/@isInStock='true']/category/text()
           // String sql = "/devices[device/@weekday='torsdag']";

            NodeList deviceList = (NodeList) xPath.compile(DEVICE_PATH).evaluate(doc, XPathConstants.NODESET);

            for (int nCount = 0; nCount<deviceList.getLength(); nCount++){

                Node deviceNode = deviceList.item(nCount);

                if (deviceNode.getNodeType() == Node.ELEMENT_NODE){
                    if (deviceNode.getNodeName().compareTo("device")==0) {

                        String dtTimePoint =  xPath.compile("./timepoint").evaluate(deviceNode);
                        if (dtTimePoint.length() == 5)
                            dtTimePoint = dtTimePoint.concat(":00");

                        timePoint = formatter.parseDateTime(dtTimePoint);
                        action = xPath.compile("./action").evaluate(deviceNode);
                        id = xPath.compile("./id").evaluate(deviceNode);
                        //id =String.valueOf(new Random().nextInt());
                    }
                }

                if (deviceNode.hasAttributes()){
                    NamedNodeMap namedNodeMap=deviceNode.getAttributes();
                    for (int n = 0; n<namedNodeMap.getLength(); n++){
                        if (namedNodeMap.item(n).getNodeName().compareTo("deviceId") == 0)
                            deviceId = namedNodeMap.item(n).getNodeValue(); //
                        else
                            weekDay =  namedNodeMap.item(n).getNodeValue().toLowerCase();
                    }
                }

                if (weekDay.isEmpty() || weekDay==null)
                    continue;

                //Är det rätt dag idag?

                if (WeekDays.valueOf(weekDay).getWeekDay() == dayOfWeek){
                    //Är tiden senare än nu ?
                    if (timePoint.toLocalTime().getMillisOfDay() > DateTime.now().getMillisOfDay()) {
                        dbSchemaDevice = new SchemaDevice(Integer.parseInt(deviceId), timePoint, action);
                        deviceMap.put(Integer.parseInt(id), dbSchemaDevice);
                    }
                }


            }



        } catch (ParserConfigurationException e) {
            iLog.error(e);
        } catch (SAXException e) {
            iLog.error(e);
        } catch (IOException e) {
            iLog.error(e);
        } catch (XPathExpressionException e) {
            iLog.error(e);
        }


        return deviceMap;

    }

}
