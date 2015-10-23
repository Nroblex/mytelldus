package devices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anders on 10/20/15.
 */
public class ConfiguredDevices {

    private Integer deviceId;
    private String deviceName;

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




    public static List<ConfiguredDevices> getConfiguredDevices() {
        //Implement this to Telldus Later...
        return mockGetConfiguredDevices();
    }

    private static List<ConfiguredDevices> mockGetConfiguredDevices(){
        List<ConfiguredDevices> configuredDevices = new ArrayList<ConfiguredDevices>();
        ConfiguredDevices conf = new ConfiguredDevices();
        conf.setDeviceId(1);
        conf.setDeviceName("Lampa 1");

        ConfiguredDevices conf1 = new ConfiguredDevices();
        conf.setDeviceId(2);
        conf.setDeviceName("Lampa 2");

        configuredDevices.add(conf);
        configuredDevices.add(conf1);

        return configuredDevices;

    }



}
