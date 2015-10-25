package scheduler;

import DBmanager.DBManager;
import DBmanager.SchemaDevice;
import communication.CommandHandler;
import communication.Initialize;
import communication.TelldusInterface;
import devices.ConfiguredDevice;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ueh093 on 10/22/15.
 * This reads the ConfigurationDatabase to Search for configuredDevices.
 */
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

        initTimeMap();
        getConfiguredDevices();

        if (configuredDevicesList.size() == 0){
            iLog.warn("There are no configured devices. Ending...!");
            return;
        }


        /*
        if (ConfiguredDevice.getConfiguredDevicesMOCK() == null) {
            try {
                throw new Exception("There are no configured Devices!!!");
            } catch (Exception e) {
                iLog.error(e);
                return;
            }
        }
        */

        while(doRun){

            for (ConfiguredDevice device : configuredDevicesList){

                for (int n =0; n<=timeMapList.size() -1; n++){

                    Map<Integer, SchemaDevice> confDevice = timeMapList.get(n);
                    if ( confDevice.containsKey(device.getDeviceId()) ){

                        SchemaDevice schemaDevice = confDevice.get(device.getDeviceId());
                        HandleConfiguredDevice(schemaDevice);

                    }

                }

            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                iLog.error(e);
            }

        }
    }

    private void getConfiguredDevices() {
        configuredDevicesList = new ArrayList<ConfiguredDevice>(telldusInterface.tdGetNumberOfDevices());

        for (int n = 0; n<=telldusInterface.tdGetNumberOfDevices(); n++){

            ConfiguredDevice configuredDevice = new ConfiguredDevice(
                    telldusInterface.tdGetDeviceId(n),
                    telldusInterface.tdGetName(n)
            );

            configuredDevicesList.add(configuredDevice);
        }
    }

    private void initTimeMap(){
        timeMapList=dbManager.getScheduledDevices();
    }

    @Override
    protected void updateConfiguration(SchemaDevice device) {
        dbManager.UpdateConfiguration(device);
    }
}
