package events;

/**
 * Created by anders on 10/19/15.
 */
public abstract class TelldusEvent  {

    //protected String EventType;

    private EventType eventType;

    public TelldusEvent(EventType typeOfEvent) {
        this.eventType = typeOfEvent;
    }

    public interface Listener {
		/* Dummy base for all listeners */
    }

    public EventType getEventType() { return this.eventType; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[]";
    }
}
