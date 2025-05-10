package model;

import java.sql.Timestamp;

public class Message extends AbstractEntity {
    private int senderId;
    private int receiverId;
    private String message;
    private Timestamp timestamp;

    public Message() { super(); }

    public Message(int id, int senderId, int receiverId, String message, Timestamp timestamp) {
        super(id);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Message(int senderId, int receiverId, String message) {
        super();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    @Override
    public void display() {
        System.out.println("Message ID: " + id + " | From: " + senderId + " | To: " + receiverId + " | Time: " + timestamp);
        System.out.println("Message: " + message);
    }

    @Override
    public void setId(int int1) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
