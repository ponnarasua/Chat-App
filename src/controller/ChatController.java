package controller;

import dao.ChatDao;
import model.Message;

import java.util.List;

public class ChatController {
    private ChatDao chatDao = new ChatDao();

    public boolean sendMessage(int senderId, int receiverId, String message) {
        Message msg = new Message(senderId, receiverId, message);
        return chatDao.saveMessage(msg);
    }

    public List<Message> getChatHistory(int userId1, int userId2) {
        return chatDao.getChatBetweenUsers(userId1, userId2);
    }
}