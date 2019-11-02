import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import java.rmi.RemoteException;
import java.util.Scanner;

import Controllers.*;
import Service.*;

public class Client {
    private JavaSpace space;
    private String clientName;

    public Client(){
        initSpace();
    }

    private void initSpace(){
        Lookup finder = new Lookup(JavaSpace.class);
        space = (JavaSpace) finder.getService();
        clientName = UserController.registerUser(space);
    }

    private void printMenu(){
        System.out.println("--------------Menu--------------");
        System.out.print("------Client: ");
        System.out.print(clientName);
        System.out.println("------------");
        System.out.println("1 - Enviar Mensagem");
        System.out.println("2 - Ler Mensagens");
        System.out.println("3 - Imprimir lista de usuários");
        System.out.println("4 - Criar ambiente");
    }

    private void menuOptions(String option){
        Scanner scanner = new Scanner(System.in);
        switch (option){
            case "1":
                System.out.println("Message: ");
                String message = scanner.nextLine();
                MessageController.sendMessage(message, space);
                break;

            case "2":
                try {
                    System.out.println(MessageController.readMessage(space).content);
                } catch (TransactionException | UnusableEntryException | RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }

            case "3":
                UserListController.getUsersList(space);

            case "4":
                String newEnvironment = EnvironmentController.registerEnvironment(space);
                System.out.print("Novo ambiente criado: ");
                System.out.println(newEnvironment);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            client.printMenu();
            String option = scanner.nextLine();
            client.menuOptions(option);
        }
    }
}