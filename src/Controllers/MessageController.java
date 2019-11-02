package Controllers;

import net.jini.core.entry.Entry;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import Models.*;

import java.rmi.RemoteException;

public class MessageController implements Entry {
    public static void sendMessage(String message, JavaSpace space){
        Models.Message msg = new Models.Message();
        msg.content = message;
        try {
            space.write(msg, null, 60 * 1000);
        } catch (TransactionException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public static Message readMessage(JavaSpace space) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
        Models.Message template = new Models.Message();
        return (Models.Message) space.take(template, null, 60 * 1000);

    }

}
