package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by ueh093 on 10/22/15.
 */
public class Util {


    public static String getSetting(String key, String defaultSetting){

        String retSetting = "";

        initSettings();

        if (defaultSetting == null || defaultSetting.length()==0)
            retSetting = parseSetting(key);
        else
            retSetting=defaultSetting;

        return retSetting;

    }

    public static String getSetting(String key){
        return getSetting(key, "");
    }

    public static void printMessage( String msg ){
        System.out.println(msg);
    }

    public static Integer getIntSetting(String key){
        return Integer.parseInt(getSetting(key, ""));
    }

    private static void initSettings(){

        Properties properties = new Properties();
        try {

            File f = new File("config/settings.properties");
            if (!f.exists()){
                f.createNewFile();
            }

            InputStream fsIn = new FileInputStream("config/settings.properties");
            properties.load(fsIn);

            if (properties.getProperty("logSettingsPath")== null) {
                properties.setProperty("logSettingsPath", "config/log4j.xml");
            }

            if (properties.getProperty("mode") == null){
                properties.setProperty("mode", "emulation");
            }

            if (properties.getProperty("clientPort") == null){
                properties.setProperty("clientPort", "9998");
            }

            if (properties.getProperty("eventPort") == null){
                properties.setProperty("eventPort", "9999");
            }

            if (properties.getProperty("hostname") == null ){
                properties.setProperty("hostname", "10.0.1.48");
            }

            if (properties.getProperty("dbfile") == null){
                properties.setProperty("dbfile", "config/configdb.db");
            }


            OutputStream os = new FileOutputStream("config/settings.properties");
            properties.store(os, "Automatic created configuration file.");



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String parseSetting(String setting) {

        Properties prop = new Properties();
        try {
            InputStream in = new FileInputStream("config/settings.properties");
            prop.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return prop.getProperty(setting);
    }
}
