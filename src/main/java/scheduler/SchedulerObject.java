package scheduler;

import org.joda.time.DateTime;

/**
 * Created by anders on 10/19/15.
 */
public class SchedulerObject {

    private String deviceName;
    private Integer deviceId;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public DateTime getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(DateTime timePoint) {
        this.timePoint = timePoint;
    }

    private DateTime timePoint;

    public SchedulerObject(String deviceName, Integer deviceId, DateTime timePoint) {
        this.deviceId=deviceId; this.deviceName=deviceName; this.timePoint=timePoint;
    }

}
