package communication;

import messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anders on 10/19/15.
 */
public class Sensor {

    private String protocol;
    private String model;
    private int id;
    private List<Protocol.SensorValueType> dataTypes;

    /**
     * Construct a Sensor from message. Assumes message has fields, in order:
     *
     *	String protocol
     *	String model
     *	Int id
     *	Int dataTypes
     *
     * This holds true for both tdSensor call and TDSensorEvent.
     *
     * @param src Message with fields according to above.
     */
    public Sensor(Message src) {
        protocol = src.takeString();
        model = src.takeString();
        id = src.takeInt();
        dataTypes = new ArrayList();
        int dt = src.takeInt();
        for (Protocol.SensorValueType svt : Protocol.SensorValueType.values()) {
            if ((dt & svt.code()) != 0) {
                dataTypes.add(svt);
            }
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }

    /**
     * Return a list of all datatypes available.
     * Normally only used when received from {@see TelldusInterface.tdSensor()}.
     */
    public List<Protocol.SensorValueType> getDataTypes() {
        return dataTypes;
    }

    /**
     * Return the datatype of this sensor.
     * Normally only used when received as a sensor event.
     */
    public Protocol.SensorValueType getDataType() {
        if(dataTypes != null && dataTypes.size() > 0)
            return dataTypes.get(0);

        return null;
    }

}
