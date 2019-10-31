package Models;

import net.jini.core.entry.Entry;
public class Message implements Entry {
    public String userFrom;
    public String userTo;
    public String content;
    public Message() {
    }
}