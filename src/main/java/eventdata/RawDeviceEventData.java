package eventdata;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by anders on 11/12/16.
 */
public class RawDeviceEventData {
    //TDRawDeviceEvent[controller=1, data=class:sensor;protocol:mandolyn;id:11;model:temperaturehumidity;temp:-1.7;humidity:24;]
    private String rawDeviceEventString;

    private int controller ;
    private String protocolName ;
    private double temperatureValue ;
    private DateTime logCreatedAt;
    private Integer humidity = 0;

    private HashMap<Double,DateTime> temperatureMap = new HashMap<>();
    private TemperatureData temperatureData=null;


    public RawDeviceEventData(String rawDeviceEvent){
        this.rawDeviceEventString=rawDeviceEvent;

        String[] arrData = rawDeviceEvent.split(";");

        for (String data : arrData){
            String[] cont = data.split(":");
            for (String val : cont){
                if (val.compareTo("temp") == 0){

                    temperatureValue = Double.valueOf(cont[1]);
                    logCreatedAt = DateTime.now();

                    temperatureMap.put(temperatureValue, logCreatedAt);
                }
                if (val.compareTo("humidity")==0){
                    humidity = Integer.valueOf(cont[1]);
                }
            }


            if (temperatureValue != 0.0 && humidity != 0) {

                temperatureData = new TemperatureData();
                temperatureData.setHumidity(humidity);
                temperatureData.setTemperature(temperatureValue);
                temperatureData.setDateTime(logCreatedAt);

            }

        }
    }

    public HashMap<Double,DateTime> getTemperatureMap() {
        return temperatureMap;
    }

    public TemperatureData getTemperatureData(){
        return  temperatureData;
    }

}
