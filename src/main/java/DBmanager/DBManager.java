package DBmanager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<Integer, SchemaDevices>> getScheduledDevices(){

        Map<Integer,SchemaDevices> deviceMap = new HashMap<Integer, SchemaDevices>();
        List<Map<Integer,SchemaDevices>> mapList = new ArrayList<Map<Integer, SchemaDevices>>();

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
                                new SchemaDevices(rs.getString("timePoint"), rs.getInt("action"))
                        );
                mapList.add(deviceMap);
            }

        } catch (SQLException e) {
            iLog.error(e);
        }

        return mapList;
    }
}
