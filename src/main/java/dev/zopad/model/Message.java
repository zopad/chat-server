package dev.zopad.model;

import java.util.Date;

public class Message {

    private final String sender;
    private final String message;
    private final Date dateTime;

    public Message(String sender, String message, Date dateTime) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
