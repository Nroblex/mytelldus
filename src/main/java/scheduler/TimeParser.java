package scheduler;

import DBmanager.DBManager;
import DBmanager.SchemaDevice;
import communication.CommandHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import utils.Util;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ueh093 on 11/2/15.
 */
public class TimeParser extends CommandHandler {

    Logger iLog = LogManager.getLogger(TimeParser.class);

    private Timer timerCheckCurrentTime = new Timer("Actual time");
    private Timer timerCheckdatabaseConfig = new Timer("Read database config");
    private String schemadevice = null;
    private Map<Integer, SchemaDevice> dbConfiguredDevices = new HashMap<Integer, SchemaDevice>();
    private Integer checkDBTimeinterval ;
    private DBManager dbManager;

    public TimeParser() {

        checkDBTimeinterval=Util.getIntSetting("dbchecktimeinterval");
        Util.printMessage("Reading every: " + checkDBTimeinterval/1000 + " second");

        timerCheckCurrentTime.scheduleAtFixedRate(timerTaskCheckCurrentTime, 1000, 1000); //startar om en sekund, kör varje sekund.
        timerCheckdatabaseConfig.scheduleAtFixedRate(timerTaskReadDatabaseConfig, 0, checkDBTimeinterval);

        dbManager = new DBManager();

        initDBConfiguration();

    }

    private void initDBConfiguration() {
        schemadevice = Util.getSetting("schemadevice");

        if (schemadevice.endsWith(".db")) {
            iLog.info("Application is running with SQLite database");
            Util.printMessage("Reading from SQLite database.");
        }
        else {
            iLog.info("Application is running with XML database");
            Util.printMessage("Reading from XML datafile");
        }
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

                        if (schemadevice.endsWith(".db"))
                            dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNow();
                        else
                            dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNowXML();

                        if (dbConfiguredDevices.size() > 0 && dbConfiguredDevices != null) {
                            logInformation();
                        } else {
                            Util.printMessage("No more activities for today!");
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

                        //DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        //Calendar calendar = Calendar.getInstance();
                        //calendar.getTime();
                        //String time = timeFormat.format(calendar.getTimeInMillis());
                       // System.out.println("Time is now: " + timeFormat.format(calendar.getTimeInMillis()));


                        executeIfOnTime();





                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }


    };

    //Om tiden är på sekunden så kör vi kommandot
    private void executeIfOnTime() {

        Iterator iterator = dbConfiguredDevices.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, SchemaDevice> actualDevice = (Map.Entry) iterator.next();

            if (actualDevice.getValue().getTimePoint().getSecondOfDay() == DateTime.now().getSecondOfDay()){

                iLog.info("====EXECUTING====");

                HandleConfiguredDevice(actualDevice.getValue());

                iLog.info("====END EXECUTING====");

            }

        }

    }


    private void logInformation()  {


        Util.printMessage(String.format("Det finns %s konfigurerade tider.", String.valueOf(dbConfiguredDevices.size())));

        //dbConfiguredDevices.forEach((k, v)->System.out.println("Key = " + k + " Value = " + v);
        try {
            final String osName = System.getProperty("os.name");
            if (osName.toLowerCase().contains("linux")){
                Runtime.getRuntime().exec("clear");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iterator = dbConfiguredDevices.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Integer, SchemaDevice> actualDevice = (Map.Entry) iterator.next();
            
            Integer secDiff = actualDevice.getValue().getTimePoint().getSecondOfDay() - DateTime.now().getSecondOfDay();

            Util.printMessage(String.format("Counting seconds, : device {%s} sec = [%s] action -> [%s] ==TID== > [%s]",
                    String.valueOf(actualDevice.getValue().getDeviceID()),
                    String.valueOf(secDiff),
                    String.valueOf(actualDevice.getValue().getAction() ),
                    actualDevice.getValue().getTimePoint().toLocalTime()));

        }


        Util.printMessage("\n");

    }

    @Override
    protected void updateConfiguration(SchemaDevice device) {
        dbManager.UpdateConfiguration(device);
    }




            /*
            for (Map.Entry<Integer, SchemaDevice> actualDevice : confDevice.entrySet() ){
                Integer secDiff = actualDevice.getValue().getTimePoint().getSecondOfDay() - DateTime.now().getSecondOfDay();
                        Util.printMessage(String.format("Following schema exist: %s time up : %s seconds...", actualDevice.getValue().getTimePoint().toLocalTime(),
                        String.valueOf(secDiff)));
            }

            */


}




