package events;

/**
 * Created by anders on 10/19/15.
 */

import messages.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 *
 * @author johan
 */
public class EventFactory {

    private static final Logger log = LogManager.getLogger(EventFactory.class);

    public static TelldusEvent createEvent(Message msg) {
        try {
            String type = msg.takeString();
            TelldusEvent event = null;

            if ("TDDeviceChangeEvent".equals(type)) {
                event = new TDDeviceChangeEvent(msg);
            } else if ("TDDeviceEvent".equals(type)) {
                event = new TDDeviceEvent(msg);
            } else if ("TDRawDeviceEvent".equals(type)) {
                event = new TDRawDeviceEvent(msg);
            } else if ("TDSensorEvent".equals(type)) {
                event = new TDSensorEvent(msg);
            } else if ("TDControllerEvent".equals(type)) {
                event = new TDControllerEvent(msg);
            }

            if(event == null) {
                log.warn("Unhandled event of EventType "+type);
                return null;
            }

            return event;
        }catch(Exception e) {
            log.error("Failed to parse event", e);
            return null;
        }
    }

}
