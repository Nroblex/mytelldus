package communication;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.Util;

import java.io.IOException;

/**
 * Created by anders on 10/23/15.
 */
public class Initialize{

    private static TelldusInterface telldusInterface = null;
    private static Logger iLog = LogManager.getLogger(Initialize.class);


    public static TelldusInterface getTelldusInterface(){
        if (telldusInterface == null) {
            try {
                telldusInterface = new TelldusInterface(
                        Util.getSetting("hostname", ""),
                        Util.getIntSetting("clientPort"),
                        Util.getIntSetting("eventPort"));

                iLog.info("TelldusInterface started, listening for communication!");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return telldusInterface;
    }

}
