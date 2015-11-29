package communication;

import DBmanager.SchemaDevice;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import utils.Util;

/**
 * Created by anders on 10/24/15.
 */
public abstract class CommandHandler {

    Logger iLog = LogManager.getLogger(CommandHandler.class);

    private DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-mm-dd HH:mm:ss");

    private static Telldus telldus;

    private void ExecuteCommand(SchemaDevice device){
        if (telldus == null)
            telldus = Initialize.getTelldus();

        if (device.getAction().toUpperCase().compareTo("OFF") == 0){
            iLog.info("Action was 0, trying to turn off device + " + device.getDeviceID());
            telldus.tdTurnOff(device.getDeviceID());
        }
        if (device.getAction().toUpperCase().compareTo("ON")== 0){
            iLog.info("Action was 1, trying to turn on device + " + device.getDeviceID());
            telldus.tdTurnOn(device.getDeviceID());
        }


        if (Util.getSetting("dbfile").endsWith(".db"))
            updateConfiguration(device);



    }

    protected abstract void updateConfiguration(SchemaDevice device);



    public void HandleConfiguredDevice(SchemaDevice device){

        if (device.getUpdatedAt() != null) {
            System.out.println("Last UpdatedTime is : " + device.getUpdatedAt().toLocalTime());
        } else {
            System.out.println("The deviceConfig was never updated!");
        }

        ExecuteCommand(device);


    }
}
