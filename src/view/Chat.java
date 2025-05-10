package view;
import dao.ChatDAO;
import model.Chat;
import java.util.List;
import java.util.Scanner;

public class ChatController {
    private ChatDAO chatDAO = new ChatDAO();
    private Scanner scanner = new Scanner(System.in);

    public void sendMessage(String senderPhone) {
        System.out.print("Enter Receiver's Phone: ");
        String receiverPhone = scanner.nextLine();
        System.out.print("Enter Message: ");
        String message = scanner.nextLine();

        Chat chat = new Chat(senderPhone, receiverPhone, message);
        if (chatDAO.sendMessage(chat)) {
            System.out.println("Message Sent!");
        } else {
            System.out.println("Failed to Send Message.");
        }
    }

    public void viewRecentChats(String phone) {
        List<Chat> chats = chatDAO.getRecentChats(phone);
        if (chats.isEmpty()) {
            System.out.println("No recent chats.");
        } else {
            System.out.println("--- Recent Chats ---");
            int i = 1;
            for (Chat chat : chats) {
                System.out.println(i + ". " + chat.getSenderPhone() + " -> " + chat.getReceiverPhone() + ": " + chat.getMessage());
                i++;
            }
        }
    }
}
