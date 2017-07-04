package scheduler;

import DBmanager.DBManager;
import DBmanager.Device;
import DBmanager.SchemaDevice;
import communication.*;
import communication.TelldusInterface.Controller;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import utils.Util;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

/**
 * Created by ueh093 on 11/2/15.
 */
public class TimeParser extends CommandHandler {

    Logger iLog = LogManager.getLogger(TimeParser.class);

    private Timer timerCheckCurrentTime = new Timer("Actual time");
    private Timer timerCheckdatabaseConfig = new Timer("Read database config");
    private String schemadevice = null;
    private Map<Integer, Device> dbConfiguredDevices = new HashMap<Integer, Device>();
    private Integer checkDBTimeinterval ;
    private static DBManager dbManager;

    private Integer dummyCounter = 0;

    private static Telldus telldusServer;

    public TimeParser() {


        checkDBTimeinterval=Util.getIntSetting("dbchecktimeinterval");

        //Util.printMessage("Reading every: " + checkDBTimeinterval/1000 + " second");
        iLog.info("[Reading every]: " + checkDBTimeinterval/1000 + " second.");

        iLog.info(String.format("[CONFIG FILE : %s]", Util.getConfigFileName()));


        timerCheckCurrentTime.scheduleAtFixedRate(timerTaskCheckCurrentTime, 1000, 1000); //startar om en sekund, kör varje sekund.
        timerCheckdatabaseConfig.scheduleAtFixedRate(timerTaskReadDatabaseConfig, 0, checkDBTimeinterval); //startar direkt, kör enl. konf.

        dbManager = DBManager.getInstance();

        initDBConfiguration();

        initTelldusServer();

    }

    private void initTelldusServer() {
        telldusServer = Initialize.getTelldus();
        List<Controller> ctls = telldusServer.tdController();

        for (Controller ctl : ctls){
            System.out.println(String.format("TelldusController name = %s, TypeCode = %s" , ctl.getName(), ctl.getType().code()));
        }

        String model = telldusServer.tdGetModel(2);
        System.out.print(model);

        //int supported = telldusServer.tdMethods(2, Protocol.DeviceMethod.LEARN);
        //System.out.print(supported);

        //Protocol.DeviceMethod method = telldusServer.tdLastSentCommand(2, 0);
        //String name = method.toString();
        //System.out.print(name);

    }

    private void initDBConfiguration() {
        schemadevice = Util.getSetting("schemadevice");

        if (schemadevice.endsWith(".db")) {
            iLog.info("Application is running with SQLite database");
            Util.printMessage("Reading from SQLite database.");
        }
        else {
            iLog.info("Application is running with XML database: " + Util.getSetting("schemadevice"));
            Util.printMessage("Reading from XML datafile : " + Util.getSetting("schemadevice"));
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


                        dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNowXML();


                        /*
                        if (schemadevice.endsWith(".db"))
                            dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNow();
                        else
                            dbConfiguredDevices = dbManager.getScheduledDevicesLaterThanNowXML();
                        */


                        if (dbConfiguredDevices.size() > 0 && dbConfiguredDevices != null) {
                            logInformation();
                        } else {
                            if (dummyCounter==0){
                                Util.printMessage("No more activites for today...");
                            }
                            dummyCounter++;
                            if (dummyCounter >= 1000) {
                                iLog.info("\n\nLogging runtime information!!, app is alive and running");
                                iLog.info("\n\n");
                                dummyCounter=0;
                            }
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

            //Map.Entry<Integer, SchemaDevice> actualDevice = (Map.Entry) iterator.next();
            Map.Entry<Integer, Device> actualDevice = (Map.Entry)iterator.next();

            for (SchemaDevice schemaDevice : actualDevice.getValue().getSchemaDevices()) {

                if (schemaDevice.getTimePoint().getSecondOfDay() == DateTime.now().getSecondOfDay()){

                    iLog.info("====EXECUTING==== for device = > " + actualDevice.getValue().getName());
                    if (schemaDevice.getTimePoint().getSecondOfDay() == DateTime.now().getSecondOfDay()) {
                        HandleConfiguredDevice(schemaDevice);
                    }

                    iLog.info("====END EXECUTING====");
                }


            }

            //if (actualDevice.getValue().getTimePoint().getSecondOfDay() == DateTime.now().getSecondOfDay()){

                //iLog.info("====EXECUTING==== for device = > " + actualDevice.getValue().getDeviceName());





            }

    }

    private void logInformation()  {


        //Util.printMessage(String.format("Det finns %s konfigurerade tider.", String.valueOf(dbConfiguredDevices.size())));

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

            Map.Entry<Integer, Device> actualDevice = (Map.Entry) iterator.next();

            for (SchemaDevice schemaDevice : actualDevice.getValue().getSchemaDevices()){

                Integer secDiff = schemaDevice.getTimePoint().getSecondOfDay() - DateTime.now().getSecondOfDay();

                if (secDiff < 60) {

                    Util.printMessage(String.format("Device {%s} to be executed {%s} in %s secs",
                            String.valueOf(actualDevice.getValue().getName()),
                            schemaDevice.getAction(),
                            String.valueOf(secDiff)));
                } else {

                    Integer mins = schemaDevice.getTimePoint().getMinuteOfDay() - DateTime.now().getMinuteOfDay();


                    Util.printMessage(String.format("Device {%s} to be executed {%s} in  %s mins timepoint will be = {%s}" ,
                            String.valueOf(actualDevice.getValue().getName()),
                            schemaDevice.getAction(),
                            //String.valueOf(hours),
                            String.valueOf(mins),
                            schemaDevice.getTimePoint().toLocalTime().toString()));
                }

            }


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




