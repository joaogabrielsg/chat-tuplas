package Models;

import java.util.*;
import net.jini.core.entry.Entry;
public class UserList implements Entry  {
    public List<String> usersName;
    public UserList() {}

    public String getUserName(){
        if (usersName == null || usersName.size() == 0){
            return "user1";
        }
        return String.format("user%s", usersName.size() + 1);
    }
}
