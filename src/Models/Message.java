package Models;

import net.jini.core.entry.Entry;

import java.util.List;

public class Message implements Entry {
    public String userFrom;
    public String userTo;
    public List<String> content;
    public Message() {
    }
}