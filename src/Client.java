import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Models.*;
import Service.*;

public class Client {
    public JavaSpace space;
    public String clientName;

    public Client(){
        initSpace();
        registerUser();
    }

    public void initSpace(){
        Lookup finder = new Lookup(JavaSpace.class);
        space = (JavaSpace) finder.getService();
    }

    public void getUsersList(){

        UserList userList = new UserList();
        try {
            UserList usersList = (UserList) space.read(userList, null, 60 * 1000);
            System.out.println(usersList.usersName.size());
            usersList.usersName.forEach(user -> {
                System.out.println(user);
            });
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(){
        UserList userList = new UserList();
        try {
            UserList usersList = (UserList) space.readIfExists(userList, null, 60 * 1000);
            if (usersList == null){
                userList.usersName = new ArrayList<>();
                clientName = "user1";
                userList.usersName.add(clientName);
                space.write(userList, null, 60 * 1000);
            } else {
                usersList = (UserList) space.take(userList, null, 60 * 1000);
                clientName = usersList.getUserName();
                usersList.usersName.add(clientName);
                space.write(usersList, null, 60*1000);
            }
            User user = new User();
            user.name = clientName;
            space.write(user, null, 60 * 1000);
        } catch (UnusableEntryException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        Message msg = new Message();
        msg.content = message;
        try {
            space.write(msg, null, 60 * 1000);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Message readMessage() throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        Message template = new Message();
        return (Message) space.take(template, null, 60 * 1000);

    }

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------------Menu--------------");
            System.out.print("------Client: ");
            System.out.print(client.clientName);
            System.out.println("------------");
            System.out.println("1 - Write Message");
            System.out.println("2 - Read Message");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.println("Message: ");
                String message = scanner.nextLine();
                client.sendMessage(message);
            }
            if (option.equals("2")) {
                try {
                    System.out.println(client.readMessage().content);
                } catch (TransactionException e) {
                    e.printStackTrace();
                } catch (UnusableEntryException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (option.equals("3")) {
                client.getUsersList();
            }
        }
    }
}