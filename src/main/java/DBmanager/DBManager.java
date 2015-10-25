package DBmanager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by ueh093 on 10/22/15.
 */
public class DBManager implements ConnectionManager{

    Logger iLog = LogManager.getLogger(DBManager.class);
    Connection connection=null;

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

    public List<Map<Integer, SchemaDevice>> getScheduledDevices(){

        Map<Integer, SchemaDevice> deviceMap = new HashMap<Integer, SchemaDevice>();
        List<Map<Integer, SchemaDevice>> mapList = new ArrayList<Map<Integer, SchemaDevice>>();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-mm-dd HH:mm:ss");
        if (connection == null)
            connect();
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM timeschema");
            while (rs.next()){
                deviceMap.put
                        (
                                rs.getInt("deviceID"),
                                new SchemaDevice(rs.getInt("deviceID"), formatter.parseDateTime(rs.getString("timePoint")), rs.getInt("action"))
                        );
                mapList.add(deviceMap);
            }

        } catch (SQLException e) {
            iLog.error(e);
        }

        return mapList;
    }


    public void UpdateConfiguration(SchemaDevice device) {
        String sql = "UPDATE timeschema SET updatedAt = ? Where deviceID = ?";

        if (connection==null)
            connect();

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp((new Date().getTime())));
            stmt.setInt(2, device.getDeviceID());

            stmt.execute();

            getScheduledDevices();

        } catch (SQLException e) {

            iLog.error(e);
        }

    }
}
