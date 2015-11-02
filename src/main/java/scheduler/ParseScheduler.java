package scheduler;

import DBmanager.DBManager;
import DBmanager.SchemaDevice;
import communication.CommandHandler;
import communication.Initialize;
import communication.TelldusInterface;
import devices.ConfiguredDevice;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ueh093 on 10/22/15.
 * This reads the ConfigurationDatabase to Search for configuredDevices.
 */
@Deprecated
public class ParseScheduler extends CommandHandler implements Runnable{

    Logger iLog = LogManager.getLogger(ParseScheduler.class);
    private List<Map<Integer, SchemaDevice>> timeMapList = new ArrayList<Map<Integer, SchemaDevice>>();
    private DBManager dbManager;
    public Boolean doRun = true;

    private List<ConfiguredDevice> configuredDevicesList ;

    static TelldusInterface telldusInterface = Initialize.getTelldusInterface();

    public ParseScheduler() {
        dbManager = new DBManager();
    }

    public void run() {

        getConfiguredDevices();

        if (configuredDevicesList.size() == 0){
            iLog.warn("There are no configured devices. Ending...!");
            return;
        }


        while(doRun){

            initTimeMap(); //reparsing database...

            for (ConfiguredDevice device : configuredDevicesList){

                for (int n =0; n<=timeMapList.size() -1; n++){

                    Map<Integer, SchemaDevice> confDevice = timeMapList.get(n);

                    if ( confDevice.containsKey(device.getDeviceId()) ){
                        SchemaDevice schemaDevice = confDevice.get(device.getDeviceId());

                        //If On Same Second...
                        if (schemaDevice.getTimePoint().getSecondOfDay() == DateTime.now().getSecondOfDay()) {
                            HandleConfiguredDevice(schemaDevice);
                        }
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                iLog.error(e);
            }

        }
    }

    private boolean timeHasPassed(DateTime timePoint) {
        Boolean elapsed = DateTime.now().getSecondOfDay()> timePoint.getSecondOfDay();
        return DateTime.now().getSecondOfDay()> timePoint.getSecondOfDay();

    }

    private void getConfiguredDevices() {

        if (Util.getSetting("emulation", "true") == "true"){
            iLog.info("OBS === Running in emulation mode ===");
            configuredDevicesList = ConfiguredDevice.getConfiguredDevicesMOCK();
            return;
        }

        configuredDevicesList = new ArrayList<ConfiguredDevice>(telldusInterface.tdGetNumberOfDevices());
        for (int n = 0; n<=telldusInterface.tdGetNumberOfDevices(); n++){

            ConfiguredDevice configuredDevice = new ConfiguredDevice(
                    telldusInterface.tdGetDeviceId(n),
                    telldusInterface.tdGetName(n)
            );

            configuredDevicesList.add(configuredDevice);
        }
    }

    //Getting future configured devices...not passed!
    private void initTimeMap(){
        //timeMapList=dbManager.getScheduledDevicesLaterThanNow();
    }

    @Override
    protected void updateConfiguration(SchemaDevice device) {
        dbManager.UpdateConfiguration(device);
    }
}
