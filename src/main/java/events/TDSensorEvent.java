package events;

import communication.Sensor;
import communication.SensorValue;
import messages.Message;

/**
 * Created by anders on 10/19/15.
 */
public class TDSensorEvent extends TelldusEvent {

    private Sensor sensor;
    private SensorValue value;

    public TDSensorEvent(Message msg) {
        super("TDSensorEvent");
        sensor = new Sensor(msg);
        value = new SensorValue(msg);
    }

    public Sensor getSensor() {
        return sensor;
    }

    public SensorValue getSensorValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[sensor="+sensor+", value="+value+"]";
    }

    public interface Listener extends TelldusEvent.Listener {
        void onTDSensorEvent(TDSensorEvent event);
    }
}

