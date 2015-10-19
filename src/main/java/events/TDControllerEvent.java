package events;

import communication.Protocol;
import messages.Message;

/**
 * Created by anders on 10/19/15.
 */

public class TDControllerEvent extends TelldusEvent {

    private int controllerId;
    private Protocol.ChangeEvent changeEvent;
    private Protocol.ChangeType changeType;
    private String newValue;

    public TDControllerEvent(Message msg) {
        super("TDControllerEvent");
        controllerId = msg.takeInt();
        changeEvent = Protocol.ChangeEvent.fromCode(msg.takeInt());
        changeType = Protocol.ChangeType.fromCode(msg.takeInt());
        newValue = msg.takeString();
    }

    public int getControllerId() {
        return controllerId;
    }

    public Protocol.ChangeEvent getChangeEvent() {
        return changeEvent;
    }

    public Protocol.ChangeType getChangeType() {
        return changeType;
    }

    public String getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+
                "[controllerId="+controllerId+", changeEvent="+changeEvent+
                ", changeType="+changeType+", newValue="+newValue+"]";
    }

    public interface Listener extends TelldusEvent.Listener {
        void onTDControllerEvent(TDControllerEvent event);
    }
}
