package DBmanager;

import eventdata.TemperatureData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by anders on 11/13/16.
 */
public class DBLog extends ConnectDB {

    static Logger iLog = LogManager.getLogger(DBLog.class);

    private static DBLog ourInstance = new DBLog();
    public static DBLog getInstance() {
        if (ourInstance == null){
            ourInstance = new DBLog();
        }
        return ourInstance;
    }

    private DBLog() {

    }

    public static void saveTemperature(HashMap<Double,DateTime> tempData){

        Iterator it = tempData.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry values = (Map.Entry)it.next();
            //Filtrerar bort rappakaljavärden
            if (Double.parseDouble(values.getKey().toString()) > -25.0){
                System.out.println("Temperature: " + values.getKey());
                DBManager.saveTemperature(Double.parseDouble(values.getKey().toString()), DateTime.parse(values.getValue().toString()));
            }



        }

        //System.out.println("Saved temperature.");
    }

    public static void saveTemperatureValues(TemperatureData tempData){

        if (tempData == null)
            return;

        if ( (tempData.getHumidity() > -25.0) && tempData.getTemperature() > -30) {
            System.out.println("Temperature: " + tempData.getTemperature() + " humidity: " + tempData.getHumidity());
            DBManager.saveTemperatureValues(tempData);
        }
    }
}
