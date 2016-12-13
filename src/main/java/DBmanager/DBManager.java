package DBmanager;

import eventdata.TemperatureData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import utils.Comparer;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by ueh093 on 10/22/15.
 */
public class DBManager extends ConnectDB {

    static Logger iLog = LogManager.getLogger(DBManager.class);
    private static DBManager instance = null;

    public static DBManager getInstance() {
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager(){
        //XMLParser.readXMLDeviceConfig();
    }


    public static Map<Integer, Device> getScheduledDevicesLaterThanNowXML() {
        return XMLParser.xmlFilesToObject();
    }

    private void sortDevices(Map<Integer, SchemaDevice> deviceMap) {

        Comparer comp = new Comparer(deviceMap);

        int x = 1;

    }



    public static void UpdateConfiguration(SchemaDevice device) {
        String sql = "UPDATE timeschema SET updatedAt = ? Where ID = ?";

        try {



            PreparedStatement stmt = dbConnect().prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp((new Date().getTime())));
            stmt.setInt(2, device.getID());

            stmt.execute();

            //getAllScheduledDevices();

        } catch (SQLException e) {

            iLog.error(e);
        }

    }

    public static void saveTemperature(double temperature, DateTime timepoint){
        String sql = "INSERT INTO temperature (temp, tidpunkt) VALUES (?, ?)";

        try {

            PreparedStatement stmt = dbConnect().prepareStatement(sql);

            stmt.setDouble(1, temperature);
            stmt.setTimestamp(2, new Timestamp(timepoint.getMillis()));

            stmt.execute();

        } catch (SQLException e) {
            iLog.error(e);
        }
    }


    public static void saveTemperatureValues(TemperatureData tempData) {
        String sql = "INSERT INTO temperature (temp, humidity, tidpunkt) VALUES(?,?,?)";
        try{

            PreparedStatement stmt = dbConnect().prepareStatement(sql);
            stmt.setDouble(1, tempData.getTemperature());
            stmt.setInt(2, tempData.getHumidity());
            stmt.setTimestamp(3, new Timestamp(tempData.getDateTime().getMillis()));

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
