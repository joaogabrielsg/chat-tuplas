package Controllers;

import Models.*;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;

public class EnvironmentListController {
    public static void getEnvironmentList(JavaSpace space){
        EnvironmentList environmentList = new EnvironmentList();
        try {
            environmentList = (EnvironmentList) space.read(environmentList, null, 60 * 1000);
            System.out.println(environmentList.environments.size());
            environmentList.environments.forEach(environment -> {
                System.out.println(environment);
            });
        } catch (UnusableEntryException | RemoteException | InterruptedException | TransactionException e) {
            e.printStackTrace();
        }
    }
}
