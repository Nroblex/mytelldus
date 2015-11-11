package communication;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.Util;

import java.io.IOException;

/**
 * Created by anders on 10/23/15.
 */
public class Initialize{

    private static Telldus telldus = null;
    private static Logger iLog = LogManager.getLogger(Initialize.class);


    public static Telldus getTelldus(){
        if (telldus == null) {
            try {
                iLog.info("Starting up telldusinterface, listening on port " + String.valueOf(Util.getIntSetting("clientPort")));
                telldus = new Telldus(
                        Util.getSetting("hostname", ""),
                        Util.getIntSetting("clientPort"),
                        Util.getIntSetting("eventPort"));

                iLog.info("Telldus started, listening for communication!");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return telldus;
    }

}
