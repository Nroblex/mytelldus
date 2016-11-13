package events;

/**
 * Created by anders on 10/19/15.
 */
public abstract class TelldusDeviceEvent extends TelldusEvent {
    protected int deviceId;

    public TelldusDeviceEvent(EventType eventType, int deviceId) {
        super(eventType);
        this.deviceId = deviceId;
    }

    public int getDeviceId() {
        return deviceId;
    }
}
