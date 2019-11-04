package Controllers;

import Models.DeviceList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;

public class DeviceListController {
    public static void getDeviceList(JavaSpace space){
        DeviceList deviceList = new DeviceList();
        try {
            deviceList = (DeviceList) space.read(deviceList, null, 60 * 1000);
            System.out.println("================");
            deviceList.devices.forEach(device -> {
                System.out.println(device);
            });
            System.out.println("================");
        } catch (UnusableEntryException | RemoteException | InterruptedException | TransactionException e) {
            e.printStackTrace();
        }
    }
}
