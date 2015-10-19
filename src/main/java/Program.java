import communication.Protocol;
import communication.TelldusInterface;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.IOException;


/**
 * Created by anders on 10/16/15.
 */
public class Program {

    static Logger iLog = LogManager.getLogger(Program.class);

    public static void main(String[] args) throws IOException {

        System.out.println("Starting");

        String lgFile = "/home/anders/IdeaProjects/mytelldus/config/log4j.xml";

        setupLog4JLogging(lgFile);

        iLog.info("Starting...");


        //initializeTest();

        startADevice(2);




    }

    private static void startADevice(int i) throws IOException{

        TelldusInterface iface = new TelldusInterface("10.0.1.48", 9998, 9999);
        String name = iface.tdGetName(i);

        iface.tdTurnOn(i);

        System.out.println(name);


    }

    private static void initializeTest() {
        try {

            TelldusInterface iface = new TelldusInterface("10.0.1.48", 9998, 9999);

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
