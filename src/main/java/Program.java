import communication.Protocol;
import communication.Telldus;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.joda.time.DateTime;
import scheduler.TimeParser;
import utils.ConfigTimes;
import utils.Util;

import java.io.*;


/**
 * Created by anders on 10/16/15.
 */
public class Program {

    static Logger iLog = LogManager.getLogger(Program.class);



    public static void main(String[] args) throws IOException {

        System.out.println("Starting");


        setupLog4JLogging(Util.getSetting("logSettingsPath", ""));


        if (args.length>0){
            iLog.info("Starting command mode");
            handleCommand(args);
        } else {
            iLog.info("Starting timeparser!");
            new TimeParser();
        }

        //new ParseScheduler().run();

        //initializeTest();

        //startADevice(2);




    }

    private static void handleCommand(String[] args) {

        for (String st : args){
            if (st.compareTo("-C") == 0 || st.compareTo("-c") == 0){
                runConfiguration();
                break;
            } else{
                Util.printMessage("Commands to use -c [configurationMode]");
            }
        }

    }

    private static void runConfiguration() {

        ConfigTimes.runConfiguration();


    }


    private static void startADevice(int i) throws IOException{

        Telldus iface = new Telldus("10.0.1.48", 9998, 9999);
        String name = iface.tdGetName(i);


        String proto = iface.tdGetProtocol(i);
        Protocol.ControllerType tp = iface.tdController().get(0).getType();

        String namse = iface.tdController().get(0).getName();
        String model = iface.tdGetModel(i);


        iface.tdTurnOn(i);
        //iface.tdDim(i, 100);


        System.out.println(name);


    }

    private static void initializeTest() {
        try {

            Telldus iface = new Telldus("10.0.1.48", 9998, 9999);

            int devices = iface.tdGetNumberOfDevices();

            System.out.println(String.format("There are %s devices configurerd", devices));

            for(int i = 0; i < devices; i++) {
                int id = iface.tdGetDeviceId(i);
                String name  = iface.tdGetName(id);
                Protocol.DeviceType t = iface.tdGetDeviceType(id);
                System.out.println(id+" : "+ name +" of type "+ t);

            }

            System.out.println("Start listening for events...");


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static void setupLog4JLogging(String logFile) {

        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();
        root.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),
                ConsoleAppender.SYSTEM_ERR));

        LogManager.getRootLogger().setLevel(Level.INFO);
        LogManager.getRootLogger().setLevel(Level.TRACE);
        LogManager.getRootLogger().setLevel(Level.DEBUG);

        DOMConfigurator.configure(logFile);


    }

}
