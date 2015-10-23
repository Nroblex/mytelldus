package DBmanager;

/**
 * Created by ueh093 on 10/23/15.
 */
public class SchemaDevices {

    private String timePoint;
    private Integer action;

    public SchemaDevices(String deviceName, Integer action){
        this.timePoint=deviceName; this.action=action;
    }

    public String getDeviceName() {
        return timePoint;
    }

    public void setDeviceName(String deviceName) {
        this.timePoint = deviceName;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
