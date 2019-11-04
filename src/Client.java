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
        System.out.print("-------Client: ");
        System.out.print(clientName);
        System.out.println("-------------");
        System.out.println("1 - Enviar Mensagem");
        System.out.println("2 - Ler Mensagens");
        System.out.println("3 - Imprimir lista de usuários");
        System.out.println("4 - Criar ambiente");
        System.out.println("5 - Entrar em um ambiente");
        System.out.println("6 - Trocar de ambiente");
        System.out.println("7 - Listar usuários de um ambiente");
        System.out.println("8 - Criar dispositivo");
        System.out.println("9 - Imprimir lista de dispositivos");
        System.out.println("10 - Adicionar dispositivo a um ambiente");
        System.out.println("11 - Listar dispositivos de um ambiente");
    }

    private void menuOptions(String option){
        Scanner scanner = new Scanner(System.in);
        switch (option){
            case "1":
                System.out.print("Usuário para enviar a mensagem: ");
                String clientNameTo = scanner.nextLine();
                System.out.print("Message: ");
                String message = scanner.nextLine();
                MessageController.sendMessage(message, space, clientName, clientNameTo);
                break;

            case "2":
                System.out.print("Usuário para ler mensagem: ");
                String clientNameFrom = scanner.nextLine();
                MessageController.readMessage(space, clientName, clientNameFrom);
                break;

            case "3":
                UserListController.getUsersList(space);
                break;

            case "4":
                String newEnvironment = EnvironmentController.registerEnvironment(space);
                System.out.print("Novo ambiente criado: ");
                System.out.println(newEnvironment);
                break;

            case "5":
                System.out.println("---- Ambientes disponiveis ----");
                EnvironmentListController.getEnvironmentList(space, clientName);
                System.out.println("-------------------------------");
                System.out.println("Ambiente: ");
                String environmentName = scanner.nextLine();
                EnvironmentController.addUser(space, clientName, environmentName);
                break;

            case "6":
                System.out.println("---- Ambientes disponiveis ----");
                EnvironmentListController.getEnvironmentList(space, clientName);
                System.out.println("-------------------------------");
                System.out.println("Novo Ambiente: ");
                environmentName = scanner.nextLine();
                EnvironmentController.editUser(space, clientName, environmentName);
                break;

            case "7":
                System.out.println("---- Ambientes disponiveis ----");
                EnvironmentListController.getEnvironmentList(space, null);
                System.out.println("-------------------------------");
                System.out.println("Ambiente: ");
                environmentName = scanner.nextLine();
                EnvironmentController.getEnvironmentUsers(space, environmentName);
                break;

            case "8":
                String newDevice = DeviceController.registerDevice(space);
                System.out.print("Novo dispositivo criado: ");
                System.out.println(newDevice);
                break;

            case "9":
                DeviceListController.getDeviceList(space);
                break;

            case "10":
                System.out.println("---- Dispositivos ----");
                DeviceListController.getDeviceList(space);
                System.out.println("-------------------------------");
                System.out.println("Dispositivo: ");
                String deviceName = scanner.nextLine();
                System.out.println("---- Ambientes disponiveis ----");
                EnvironmentListController.getEnvironmentList(space, null);
                System.out.println("-------------------------------");
                System.out.println("Ambiente: ");
                environmentName = scanner.nextLine();
                EnvironmentController.addDevice(space, deviceName, environmentName);
                break;

            case "11":
                System.out.println("---- Ambientes disponiveis ----");
                EnvironmentListController.getEnvironmentList(space, null);
                System.out.println("-------------------------------");
                System.out.println("Ambiente: ");
                environmentName = scanner.nextLine();
                EnvironmentController.getEnvironmentDevice(space, environmentName);
                break;
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