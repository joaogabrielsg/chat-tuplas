package Models;

import net.jini.core.entry.Entry;
import java.util.List;

public class DeviceList  implements Entry {
    public List<String> devices;
    public DeviceList() {}

    public String getDeviceName(){
        if (devices == null || devices.size() == 0){
            return "disp1";
        }
        return String.format("disp%s", devices.size() + 1);
    }
}
