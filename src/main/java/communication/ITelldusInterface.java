package communication;

import events.TelldusEvent;
import messages.Message;

import java.util.List;

/**
 * Created by ueh093 on 11/10/15.
 */
public interface ITelldusInterface {
    void close();

    void registerListener(TelldusEvent.Listener listener);

    void unregisterListener(TelldusEvent.Listener listener);

    int tdTurnOn(int deviceId);

    int tdTurnOff(int deviceId);

    int tdBell(int deviceId);

    int tdDim(int deviceId, int level);

    int tdExecute(int deviceId);

    int tdUp(int deviceId);

    int tdDown(int deviceId);

    int tdStop(int deviceId);

    int tdLearn(int deviceId);

    Protocol.DeviceMethod tdLastSentCommand(int deviceId, int methodsSupported);

    String tdLastSentValue(int deviceId);

    int tdGetNumberOfDevices();

    int tdGetDeviceId(int intDeviceIndex);

    Protocol.DeviceType tdGetDeviceType(int intDeviceId);

    String tdGetName(int intDeviceId);

    boolean tdSetName(int intDeviceId, String strNewName);

    String tdGetProtocol(int intDeviceId);

    boolean tdSetProtocol(int intDeviceId, String strProtocol);

    String tdGetModel(int intDeviceId);

    boolean tdSetModel(int intDeviceId, String strModel);

    boolean tdSetDeviceParameter(int intDeviceId, String strName, String strValue);

    String tdGetDeviceParameter(int intDeviceId, String strName, String defaultValue);

    int tdAddDevice();

    boolean tdRemoveDevice(int intDeviceId);

    int tdMethods(int id, Protocol.DeviceMethod... methodsSupported);

    String tdGetErrorString(int intErrorNo);

    Protocol.ErrorCode tdSendRawCommand(String command, int reserved);

    void tdConnectTellStickController(int vid, int pid, String serial);

    void tdDisconnectTellStickController(int vid, int pid, String serial);

    List<Sensor> tdSensor();

    Protocol.ErrorCode tdSensorValue(String protocol, String model, int id, int dataType, SensorValue value);

    List<Controller> tdController();

    String tdControllerValue(int controllerId, String name);

    Protocol.ErrorCode tdSetControllerValue(int controllerId, String name, String value);

    Protocol.ErrorCode tdRemoveController(int controllerId);

    public static class Controller {
        private int id;
        private Protocol.ControllerType type;
        private String name;
        private boolean available;

        public Controller(Message src) {
            id = src.takeInt();
            type = Protocol.ControllerType.fromCode(src.takeInt());
            name = src.takeString();
            available = (src.takeInt() == 1);
        }

        public int getId() {
            return id;
        }

        public Protocol.ControllerType getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public boolean isAvailable() {
            return available;
        }
    }
}
