package events;

/**
 * Created by anders on 10/19/15.
 */
public abstract class TelldusEvent {

    protected String EventType;

    public TelldusEvent(String type) {
        this.EventType = type;
    }

    public interface Listener {
		/* Dummy base for all listeners */
    }

    public String getEventType() { return EventType; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[]";
    }
}
