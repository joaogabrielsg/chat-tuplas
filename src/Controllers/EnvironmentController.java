package Controllers;

import Models.Environment;
import Models.EnvironmentList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EnvironmentController {
    public static String registerEnvironment(JavaSpace space){
        EnvironmentList environmentList = new EnvironmentList();
        String environmentName = "";
        try {
            EnvironmentList environmentsList = (EnvironmentList) space.take(environmentList, null, 60);
            if (environmentsList == null){
                environmentList.environments = new ArrayList<>();
                environmentName = "amb1";
                environmentList.environments.add(environmentName);
                space.write(environmentList, null, 60 * 1000);
            } else {
                environmentName = environmentsList.getEnvironmentName();
                environmentsList.environments.add(environmentName);
                space.write(environmentsList, null, 60*1000);
            }
            Environment environment = new Environment();
            environment.name = environmentName;
            space.write(environment, null, 60 * 1000);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return environmentName;
    }
}
