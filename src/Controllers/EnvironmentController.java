package Controllers;

import Models.Environment;
import Models.User;
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

    public static void addUser(JavaSpace space, String userName, String environmentName){
        Environment environment = new Environment();
        environment.name = environmentName;
        try {
            environment = (Environment) space.take(environment, null, JavaSpace.NO_WAIT);
            if (environment.users == null) {
                environment.users = new ArrayList<>();
            }
            environment.users.add(userName);
            space.write(environment, null, 60 * 1000);
            UserController.addEnvironment(space, userName, environmentName);
            System.out.print("Usuário ");
            System.out.print(userName);
            System.out.print(" adicionado ao ambiente ");
            System.out.println(environmentName);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(JavaSpace space, String userName, String environmentName){
        Environment environment = new Environment();
        User user = new User();
        try {
            user = (User) space.read(user, null, JavaSpace.NO_WAIT);
            environment.name = user.environment;
            environment = (Environment) space.take(environment, null, 60);
            if (environment.users != null) {
                environment.users.remove(userName);
                if (environment.users.size() == 0) {
                    environment.users = null;
                }
            }
            space.write(environment, null, 60);
            EnvironmentController.addUser(space, userName, environmentName);
            UserController.editEnvironment(space, userName, environmentName);
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void getEnvironmentUsers(JavaSpace space, String environmentName){
        Environment environment = new Environment();
        environment.name = environmentName;
        try {
            environment = (Environment) space.read(environment, null, JavaSpace.NO_WAIT);
            System.out.print("=====");
            System.out.print(environment.name);
            System.out.println("=====");
            if (environment.users == null) {
                System.out.println("Não tem ninguem nesse ambiente");
            } else {
                environment.users.forEach(user -> {
                    System.out.println(user);
                });
            }
        } catch (UnusableEntryException | TransactionException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
