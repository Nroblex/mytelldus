package DBmanager;

import java.util.Map;

/**
 * Created by ueh093 on 10/22/15.
 */
public interface ConnectionManager {
    void connect();

    //Map<Integer, SchemaDevice> getScheduledDevicesLaterThanNow();
    Map<Integer,Device> getScheduledDevicesLaterThanNowXML();
}
