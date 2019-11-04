package Controllers;

import Models.Device;
import Models.DeviceList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DeviceController {
    public static String registerDevice(JavaSpace space){
        DeviceList deviceList = new DeviceList();
        String deviceName = "";
        try {
            deviceList = (DeviceList) space.take(deviceList, null, 60);
            if (deviceList == null){
                deviceList = new DeviceList();
                deviceList.devices = new ArrayList<>();
                deviceName = "disp1";
                deviceList.devices.add(deviceName);
                space.write(deviceList, null, 60 * 1000);
            } else {
                deviceName = deviceList.getDeviceName();
                deviceList.devices.add(deviceName);
                space.write(deviceList, null, 60*1000);
            }
            Device device = new Device();
            device.name = deviceName;
            space.write(device, null, 60 * 1000);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return deviceName;
    }

    static void addEnvironment(JavaSpace space, String deviceName, String environmentName) {
        Device device = new Device();
        device.name = deviceName;

        try {
            device = (Device) space.take(device, null, JavaSpace.NO_WAIT);
            device.environment = environmentName;
            space.write(device, null, 60 * 1000);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
