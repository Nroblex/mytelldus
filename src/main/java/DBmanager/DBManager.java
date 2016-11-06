package DBmanager;

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
public class DBManager implements ConnectionManager{

    Logger iLog = LogManager.getLogger(DBManager.class);
    Connection connection=null;

    public DBManager(){
        XMLParser.readXMLDeviceConfig();
    }

    public void connect() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/configdb.db");
        } catch (ClassNotFoundException e) {
            iLog.error(e);
        } catch (SQLException e) {
            iLog.error(e);
        }

    }

    public Map<Integer, Device> getScheduledDevicesLaterThanNowXML() {
        return XMLParser.xmlFilesToObject();
    }

    private void sortDevices(Map<Integer, SchemaDevice> deviceMap) {

        Comparer comp = new Comparer(deviceMap);

        int x = 1;

    }



    public void UpdateConfiguration(SchemaDevice device) {
        String sql = "UPDATE timeschema SET updatedAt = ? Where ID = ?";

        if (connection==null)
            connect();

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp((new Date().getTime())));
            stmt.setInt(2, device.getID());

            stmt.execute();

            //getAllScheduledDevices();

        } catch (SQLException e) {

            iLog.error(e);
        }

    }
}
