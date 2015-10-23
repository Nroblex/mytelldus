package scheduler;

import DBmanager.DBManager;
import devices.ConfiguredDevices;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ueh093 on 10/22/15.
 * This reads the ConfigurationDatabase to Search for configuredDevices.
 */
public class ParseScheduler implements Runnable {

    Logger iLog = LogManager.getLogger(ParseScheduler.class);
    private List<Map<Integer, DateTime>> timeMapList = new ArrayList<Map<Integer, DateTime>>();
    private DBManager dbManager;
    public Boolean doRun = true;


    public ParseScheduler() {
        dbManager = new DBManager();
    }

    public void run() {

        initTimeMap();

        if (ConfiguredDevices.getConfiguredDevices() == null) {
            try {
                throw new Exception("There are no configured Devices!!!");
            } catch (Exception e) {
                iLog.error(e);
                return;
            }
        }



        while(doRun){
            System.out.println("Checking devices..");

            if (timeMapList.size()>0){
                for (int n = 0; n<=timeMapList.size(); n++){
                    timeMapList.get(n).
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                iLog.error(e);
            }

        }
    }

    private void initTimeMap(){
        timeMapList=dbManager.getScheduledDevices();
    }

}
