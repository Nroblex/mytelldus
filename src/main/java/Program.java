import communication.Protocol;
import communication.Telldus;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
import scheduler.TimeParser;
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
            parseCommand(args);
        } else {
            iLog.info("Starting timeparser!");
            new TimeParser();
        }

        //new ParseScheduler().run();

        //initializeTest();

        //startADevice(2);




    }

    private static void parseCommand(String[] args) {
        for (String s : args){
            if (s.toLowerCase().compareTo("-t")==0){
                letUserTest();
                return;
            }
        }

    }

    private static void letUserTest()  {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Util.print("Enter device to Test (number) : ");
        Util.printMessage("");

        String device = null;
        try {

            device = reader.readLine();
            Integer idDevice = Integer.parseInt(device);

            boolean run = true;

            while (run) {
                Util.print("Select [ON] or [OFF] : ");
                String choise = reader.readLine();

                if (choise.toUpperCase().compareTo("ON") == 0 || choise.toUpperCase().compareTo("OFF") == 0){
                    if (choise.toUpperCase().compareTo("ON") == 0){
                        startADevice(idDevice, true);
                    } else {
                        startADevice(idDevice, false);
                    }
                    run=false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void startADevice(int i, Boolean turnOn) throws IOException{

        String host = Util.getSetting("hostname");
        Integer clientPort = Integer.parseInt(Util.getSetting("clientPort"));
        Integer eventPort = Integer.parseInt(Util.getSetting("eventPort"));

        Telldus iface = new Telldus(host, clientPort, eventPort);
        String name = iface.tdGetName(i);


        String proto = iface.tdGetProtocol(i);
        Protocol.ControllerType tp = iface.tdController().get(0).getType();

        String namse = iface.tdController().get(0).getName();
        String model = iface.tdGetModel(i);

        if (turnOn)
            iface.tdTurnOn(i);
        else
            iface.tdTurnOff(i);
        //iface.tdDim(i, 100);


        System.out.println(name);

        iface.close();
        System.exit(1);

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
