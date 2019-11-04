package Controllers;

import Models.*;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;

public class EnvironmentListController {
    public static void getEnvironmentList(JavaSpace space, String userName){
        EnvironmentList environmentList = new EnvironmentList();
        User user = new User();
        String userEnvironmentName = "";
        try {
            if (userName != null) {
                user.name = userName;
                user = (User) space.read(user, null, JavaSpace.NO_WAIT);
                userEnvironmentName = user.environment;
            }
            environmentList = (EnvironmentList) space.read(environmentList, null, 60 * 1000);
            System.out.println(environmentList.environments.size());
            String finalUserEnvironmentName = userEnvironmentName;
            environmentList.environments.forEach(environment -> {
                if (!environment.equals(finalUserEnvironmentName)) {
                    System.out.println(environment);
                }
            });
        } catch (UnusableEntryException | RemoteException | InterruptedException | TransactionException e) {
            e.printStackTrace();
        }
    }
}
