package events;

import communication.Protocol;
import messages.Message;

/**
 * Created by anders on 10/19/15.
 */
public class TDDeviceEvent extends TelldusDeviceEvent {

    private Protocol.DeviceMethod method; // or state..
    private String stateValue;

    public TDDeviceEvent(Message msg) {
        super("TDDeviceEvent", msg.takeInt());
		/* In callback code the method is called 'deviceState',
		 * In service code it is called eventState, which is always
		 * set to one of our DeviceMethod.* enums.
		 * Thus, using DeviceMethod here.
		 */ method = Protocol.DeviceMethod.fromCode(msg.takeInt());
        stateValue = msg.takeString();
    }

    public Protocol.DeviceMethod getMethod() {
        return method;
    }

    public String getStateValue() {
        return stateValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[method="+method+", stateValue="+stateValue+"]";
    }

    public interface Listener extends TelldusEvent.Listener {
        void onTDDeviceEvent(TDDeviceEvent event);
    }
}
