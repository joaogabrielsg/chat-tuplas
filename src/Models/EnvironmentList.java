package Models;

import net.jini.core.entry.Entry;

import java.util.List;

public class EnvironmentList  implements Entry {
    public List<String> environments;
    public EnvironmentList() {}

    public String getEnvironmentName(){
        if (environments == null || environments.size() == 0){
            return "amb1";
        }
        return String.format("amb%s", environments.size() + 1);
    }
}
