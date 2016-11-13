package DBmanager;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by anders on 11/13/16.
 */
public abstract class ConnectDB {

    private static Logger iLog = Logger.getLogger(ConnectDB.class);

    private static Connection connection;

    public static Connection dbConnect() {

        if (connection == null){
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:db/configdb.db");
            } catch (ClassNotFoundException e) {
                iLog.error(e);
            } catch (SQLException e) {
                iLog.error(e);
            }
        }
        return connection;
    }

}
