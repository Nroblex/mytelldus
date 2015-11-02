package DBmanager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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



    public List<Map<Integer, SchemaDevice>> getAllScheduledDevices(){

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
                                new SchemaDevice
                                        (
                                            rs.getInt("ID"),
                                            rs.getInt("deviceID"),
                                            formatter.parseDateTime(rs.getString("timePoint")),
                                            rs.getInt("action")
                                        )
                        );
                mapList.add(deviceMap);
            }

        } catch (SQLException e) {
            iLog.error(e);
        }

        return mapList;
    }

    public Map<Integer, SchemaDevice> getScheduledDevicesLaterThanNow(){

        Map<Integer, SchemaDevice> deviceMap = new HashMap<Integer, SchemaDevice>();


        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-mm-dd HH:mm:ss");
        DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("HH:mm");
        if (connection == null)
            connect();
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM timeschema ts INNER JOIN devices dv ON dv.deviceId = ts.deviceId WHERE time(ts.timePoint) > time('now')");
            while (rs.next()){
                deviceMap.put
                        (
                                rs.getInt("ID"),
                                new SchemaDevice
                                        (
                                                rs.getInt("ID"),
                                                rs.getInt("deviceID"),
                                                dtFormatter.parseDateTime(rs.getString("timePoint")),
                                                rs.getInt("action")
                                        )
                        );


            }

        } catch (SQLException e) {
            iLog.error(e);
        }

        return deviceMap;
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

            getAllScheduledDevices();

        } catch (SQLException e) {

            iLog.error(e);
        }

    }
}
