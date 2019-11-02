package Models;

import java.util.*;
import net.jini.core.entry.Entry;
public class UserList implements Entry  {
    public List<String> users;
    public UserList() {}

    public String getUserName(){
        if (users == null || users.size() == 0){
            return "user1";
        }
        return String.format("user%s", users.size() + 1);
    }
}
