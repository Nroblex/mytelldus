import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;



/**
 * Created by anders on 10/16/15.
 */
public class Program {

    static Logger iLog = LogManager.getLogger(Program.class);

    public static void main(String[] args) {

        System.out.println("Starting");

        String lgFile = "/home/anders/IdeaProjects/mytelldus/config/log4j.xml";

        setupLog4JLogging(lgFile);

        iLog.info("Starting...");


    }

    private static void setupLog4JLogging(String logFile) {

        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();
        root.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),
                ConsoleAppender.SYSTEM_ERR));

        LogManager.getRootLogger().setLevel(Level.INFO);

        DOMConfigurator.configure(logFile);


    }

}
