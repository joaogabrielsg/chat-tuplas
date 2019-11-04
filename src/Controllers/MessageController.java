package Controllers;

import net.jini.core.entry.Entry;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import Models.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MessageController implements Entry {
    public static void sendMessage(String message, JavaSpace space, String clientNameFrom, String clientNameTo){
        Message msg = new Message();
        msg.userFrom = clientNameFrom;
        msg.userTo = clientNameTo;
        try {
            msg = (Message) space.read(msg, null, JavaSpace.NO_WAIT);
            if (msg == null) {
                msg = new Message();
                msg.userFrom = clientNameFrom;
                msg.userTo = clientNameTo;
            }
            if (msg.content == null) {
                msg.content = new ArrayList<>();
            }
            msg.content.add(message);
            space.write(msg, null, 60 * 1000);
        } catch (TransactionException | RemoteException | UnusableEntryException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void readMessage(JavaSpace space, String clientNameFrom, String clientNameTo) {
        Message msg = new Message();
        msg.userFrom = clientNameFrom;
        msg.userTo = clientNameTo;
        try {
            System.out.println("======== Mensagens enviadas =========");
            msg = (Message) space.read(msg, null, JavaSpace.NO_WAIT);
            if (msg == null || msg.content == null || msg.content.size() == 0) {
                System.out.println("Não existem mensagens");
            } else {
                msg.content.forEach(System.out::println);
            }
            msg = new Message();
            msg.userTo = clientNameFrom;
            msg.userFrom = clientNameTo;

            System.out.println("======== Mensagens recebidas =========");
            msg = (Message) space.read(msg, null, JavaSpace.NO_WAIT);
            if (msg == null || msg.content == null || msg.content.size() == 0) {
                System.out.println("Não existem mensagens");
            } else {
                msg.content.forEach(System.out::println);
            }
        } catch (TransactionException | RemoteException | UnusableEntryException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
