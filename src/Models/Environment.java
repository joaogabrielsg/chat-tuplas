package Models;

import java.util.ArrayList;
import java.util.List;
import net.jini.core.entry.Entry;
public class Environment  implements Entry {
    public String name;
    public List<String> users;
    public List<String> devices;
    public Environment() {
    }
}
