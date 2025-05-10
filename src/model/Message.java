package model;

import java.sql.Timestamp;

public class Chat {
    private String senderPhone;
    private String receiverPhone;
    private String message;
    private Timestamp timestamp;

    public Chat(String senderPhone, String receiverPhone, String message) {
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.message = message;
    }

    // Getters
    public String getSenderPhone() { return senderPhone; }
    public String getReceiverPhone() { return receiverPhone; }
    public String getMessage() { return message; }
}