package Controllers;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;

public class UserListController {

    public static void getUsersList(JavaSpace space){
        Models.UserList userList = new Models.UserList();
        try {
            Models.UserList usersList = (Models.UserList) space.read(userList, null, 60 * 1000);
            System.out.println(usersList.users.size());
            usersList.users.forEach(user -> {
                System.out.println(user);
            });
        } catch (UnusableEntryException | RemoteException | InterruptedException | TransactionException e) {
            e.printStackTrace();
        }
    }
}
