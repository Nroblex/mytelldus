package events;

import communication.Protocol;
import messages.Message;

/**
 * Created by anders on 10/19/15.
 */
public class TDDeviceChangeEvent extends TelldusDeviceEvent {

    private Protocol.ChangeEvent changeEvent;
    private Protocol.ChangeType changeType;

    public TDDeviceChangeEvent(Message msg) {
        super(EventType.DeviceChangeEvent, msg.takeInt());
		/* In service code, changeEvent is called eventDeviceChanges, which would
		 * imply multiple changes (which doesn't make sense though, since values are
		 * not bit-weighted).
		 * However, in telldusServer callback code (CallbackDispatcher.h) it is called
		 * changeEvent, so thats what we use here.
		 */
        changeEvent = Protocol.ChangeEvent.fromCode(msg.takeInt());
        changeType = Protocol.ChangeType.fromCode(msg.takeInt());
    }

    public Protocol.ChangeEvent getChangeEvent() {
        return changeEvent;
    }

    public Protocol.ChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[changeEvent="+changeEvent+", changeType="+changeType+"]";
    }

    public interface Listener extends TelldusEvent.Listener {
        void onTDDeviceChangeEvent(TDDeviceChangeEvent event);
    }
}