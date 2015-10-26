package devices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anders on 10/20/15.
 */
public class ConfiguredDevice {

    private Integer deviceId;
    private String deviceName;

    public ConfiguredDevice(Integer deviceId, String deviceName){
        this.deviceId=deviceId; this.deviceName=deviceName;
    }

    public ConfiguredDevice() {}

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




    public static List<ConfiguredDevice> getConfiguredDevicesMOCK() {
        //Implement this to Telldus Later...
        return mockGetConfiguredDevices();
    }

    private static List<ConfiguredDevice> mockGetConfiguredDevices(){
        List<ConfiguredDevice> configuredDevices = new ArrayList<ConfiguredDevice>();

        ConfiguredDevice conf = new ConfiguredDevice();
        conf.setDeviceId(1);
        conf.setDeviceName("Lampa 1");

        ConfiguredDevice conf1 = new ConfiguredDevice();
        conf1.setDeviceId(2);
        conf1.setDeviceName("Lampa 2");

        configuredDevices.add(conf);
        configuredDevices.add(conf1);

        return configuredDevices;

    }



}
