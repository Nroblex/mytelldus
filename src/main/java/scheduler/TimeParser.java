package scheduler;

import DBmanager.DBManager;
import DBmanager.SchemaDevice;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import utils.Util;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ueh093 on 11/2/15.
 */
public class TimeParser extends Timer {

    Logger iLog = LogManager.getLogger(TimeParser.class);

    private Timer timerCheckCurrentTime = new Timer("Actual time");
    private Timer timerCheckdatabaseConfig = new Timer("Read database config");

    private List<Map<Integer,SchemaDevice>> dbConfiguredDevices = new ArrayList<Map<Integer, SchemaDevice>>();


    private DBManager dbManager;
    public TimeParser() {

        timerCheckCurrentTime.scheduleAtFixedRate(timerTaskCheckCurrentTime, 1000, 1000);
        timerCheckdatabaseConfig.scheduleAtFixedRate(timerTaskReadDatabaseConfig, 0, 10000);

        dbManager = new DBManager();

    }

    /*
        reading database configuration ...
     */
    TimerTask timerTaskReadDatabaseConfig = new TimerTask() {
        @Override
        public void run() {

            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {



                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Calendar calendar = Calendar.getInstance();
                        calendar.getTime();

                        String time = timeFormat.format(calendar.getTimeInMillis());

                        System.out.println("DATABASE CHECKING AT : " + timeFormat.format(calendar.getTimeInMillis()));


                        dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNow();
                        if (dbConfiguredDevices.size()> 0){
                            logInformation();
                        }

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    };

    /*
        Kollar av aktuell tid. Skall jämföras mot konfigurerad tid.
     */
    TimerTask timerTaskCheckCurrentTime = new TimerTask() {
        @Override
        public void run() {
            //Execute this shit every second!
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {

                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Calendar calendar = Calendar.getInstance();
                        calendar.getTime();

                        String time = timeFormat.format(calendar.getTimeInMillis());

                        System.out.println("Time is now: " + timeFormat.format(calendar.getTimeInMillis()));

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    };

    private void logInformation() {


        Util.printMessage(String.format("Threre are %s configured devices", String.valueOf(dbConfiguredDevices.size())));
        

        for (Map<Integer, SchemaDevice> confDevice : dbConfiguredDevices) {

            for (Map.Entry<Integer, SchemaDevice> actualDevice : confDevice.entrySet() ){

                Integer minDiff = actualDevice.getValue().getTimePoint().getMinuteOfDay() - DateTime.now().getMinuteOfDay();
                        Util.printMessage(String.format("Following schema exist: %s time up : %s", actualDevice.getValue().getTimePoint().toLocalTime(),
                        String.valueOf(minDiff)));
            }

            /*
            Iterator iterator = confDevice.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Integer, SchemaDevice> configuredDBDevice = confDevice.entrySet().iterator().next();
                Util.printMessage(String.valueOf(configuredDBDevice.getValue().getDeviceID()));

                confDevice.entrySet().iterator().remove();

            }
            */
        }
    }


}