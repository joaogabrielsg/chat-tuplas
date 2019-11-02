package Controllers;

import Models.User;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserController {

    public static String registerUser(JavaSpace space){
        Models.UserList userList = new Models.UserList();
        String clientName = "";
        try {
            Models.UserList usersList = (Models.UserList) space.take(userList, null, 60);
            if (usersList == null){
                userList.users = new ArrayList<>();
                clientName = "user1";
                userList.users.add(clientName);
                space.write(userList, null, 60 * 1000);
            } else {
                clientName = usersList.getUserName();
                usersList.users.add(clientName);
                space.write(usersList, null, 60*1000);
            }
            User user = new User();
            user.name = clientName;
            space.write(user, null, 60 * 1000);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return clientName;
    }
}
