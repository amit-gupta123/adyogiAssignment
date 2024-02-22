package com.example;
import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message message1, Message message2) {
        return Long.compare(message1.getVisibleFrom() , message2.getVisibleFrom());
    }
}
