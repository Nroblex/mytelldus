package events;

/**
 * Created by anders on 10/19/15.
 */
public abstract class TelldusDeviceEvent extends TelldusEvent {
    protected int deviceId;

    public TelldusDeviceEvent(String type, int deviceId) {
        super(type);
        this.deviceId = deviceId;
    }

    public int getDeviceId() {
        return deviceId;
    }
}
