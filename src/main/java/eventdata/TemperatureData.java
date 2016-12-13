package eventdata;

import org.joda.time.DateTime;

/**
 * Created by anders on 12/5/16.
 */
public class TemperatureData {
    private DateTime dateTime;
    private double temperature;
    private Integer humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
