package view;

import controller.ChatController;
import controller.ContactController;
import controller.UserController;
import java.util.List;
import java.util.Scanner;
import model.Contact;
import model.Message;
import model.User;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final UserController userController = new UserController();
    private final ContactController contactController = new ContactController();
    private final ChatController chatController = new ChatController();

    private User currentUser = null;

    public void start() {
        while (true) {
            showEntryPage();
        }
    }

    private void showEntryPage() {
        System.out.println("\n==== RealTime Chat Application ====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter Option: ");
        String input = scanner.nextLine();
        if (input.equals("000")) return;

        switch (input) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> {
                System.out.println("Exiting. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private void login() {
        System.out.print("Phone Number (or 000 to go back): ");
        String phone = scanner.nextLine();
        if (phone.equals("000")) return;
        System.out.print("Password (or 000 to go back): ");
        String password = scanner.nextLine();
        if (password.equals("000")) return;

        User user = userController.login(phone, password);
        if (user != null) {
            currentUser = user;
            showOptions();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void register() {
        System.out.print("Username (or 000 to go back): ");
        String username = scanner.nextLine();
        if (username.equals("000")) return;
        System.out.print("Phone Number (or 000 to go back): ");
        String phone = scanner.nextLine();
        if (phone.equals("000")) return;
        System.out.print("Password (or 000 to go back): ");
        String password = scanner.nextLine();
        if (password.equals("000")) return;
        System.out.print("Email (or 000 to go back): ");
        String email = scanner.nextLine();
        if (email.equals("000")) return;

        boolean success = userController.register(username, phone, password, email);
        System.out.println(success ? "Registered successfully. Please login." : "Registration failed.");
    }

    private void showOptions() {
        while (true) {
            System.out.println("\n==== Options ====");
            System.out.println("1. Contacts");
            System.out.println("2. Chats");
            System.out.println("3. Settings");
            System.out.println("4. Logout");
            System.out.print("Option (or 000 to logout): ");
            String input = scanner.nextLine();
            if (input.equals("000") || input.equals("4")) {
                currentUser = null;
                System.out.println("Logged out.");
                return;
            }
            switch (input) {
                case "1" -> contactsPage();
                case "2" -> chatsPage();
                case "3" -> settingsPage();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void contactsPage() {
        while (true) {
            System.out.println("\n==== Contacts ====");
            System.out.println("1. View Contacts");
            System.out.println("2. Save Contact");
            System.out.println("3. Back");
            System.out.print("Option (or 000 to go back): ");
            String input = scanner.nextLine();
            if (input.equals("000") || input.equals("3")) return;
            switch (input) {
                case "1" -> viewContacts();
                case "2" -> saveContact();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewContacts() {
        List<Contact> contacts = contactController.getContactsByUserId(currentUser.getId());
        if (contacts.isEmpty()) {
            System.out.println("No contacts.");
        } else {
            for (Contact c : contacts) {
                System.out.printf("Name: %s, Phone: %s%n", c.getContactName(), c.getContactPhone());
            }
        }
    }

    private void saveContact() {
        System.out.print("Contact Name (or 000 to go back): ");
        String name = scanner.nextLine();
        if (name.equals("000")) return;
        System.out.print("Contact Phone (or 000 to go back): ");
        String phone = scanner.nextLine();
        if (phone.equals("000")) return;
        boolean success = contactController.saveContact(currentUser.getId(), name, phone);
        System.out.println(success ? "Saved." : "Failed.");
    }

    private void chatsPage() {
        while (true) {
            System.out.println("\n==== Chats ====");
            System.out.println("1. Search Contact by Name");
            System.out.println("2. Recent Chats");
            System.out.println("3. Back");
            System.out.print("Option (or 000 to go back): ");
            String input = scanner.nextLine();
            if (input.equals("000") || input.equals("3")) return;
            switch (input) {
                case "1" -> searchContactChat();
                case "2" -> recentChats();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void searchContactChat() {
        System.out.print("Enter Contact Name (or 000 to go back): ");
        String name = scanner.nextLine();
        if (name.equals("000")) return;
        List<Contact> contacts = contactController.getContactsByUserId(currentUser.getId());
        Contact contact = null;
        for (Contact c : contacts) {
            if (c.getContactName().equalsIgnoreCase(name)) {
                contact = c;
                break;
            }
        }
        if (contact != null) chatWithContact(contact);
        else System.out.println("Contact not found.");
    }

    private void recentChats() {
        List<Contact> contacts = contactController.getContactsByUserId(currentUser.getId());
        boolean any = false;
        for (Contact c : contacts) {
            User udp = userController.getUserByPhone(c.getContactPhone());
            if (udp != null) {
                List<Message> messages = chatController.getChatHistory(currentUser.getId(), udp.getId());
                if (!messages.isEmpty()) {
                    any = true;
                    System.out.printf("Chat with %s (%s)%n", c.getContactName(), c.getContactPhone());
                }
            }
        }
        if (!any) {
            System.out.println("No recent chats.");
            return;
        }
        System.out.print("Enter Contact Name to open chat (or 000): ");
        String name = scanner.nextLine();
        if (name.equals("000")) return;
        Contact contact = null;
        for (Contact c : contacts) {
            if (c.getContactName().equalsIgnoreCase(name)) {
                contact = c;
                break;
            }
        }
        if (contact != null) chatWithContact(contact);
        else System.out.println("Contact not found.");
    }

    private void chatWithContact(Contact contact) {
        User receiver = userController.getUserByPhone(contact.getContactPhone());
        if (receiver == null) {
            System.out.println("Contact user not registered.");
            return;
        }
        System.out.println("\n==== Chat with " + contact.getContactName() + " ====");
        List<Message> messages = chatController.getChatHistory(currentUser.getId(), receiver.getId());
        if (messages.isEmpty()) System.out.println("No previous chat.");
        else {
            for (Message m : messages) {
                String who = (m.getSenderId() == currentUser.getId()) ? "You" : contact.getContactName();
                System.out.printf("[%s] %s: %s%n", m.getTimestamp(), who, m.getMessage());
            }
        }
        System.out.println("Type messages, '000' to exit chat.");
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("000")) {
                System.out.println("Exiting chat.");
                break;
            }
            boolean sent = chatController.sendMessage(currentUser.getId(), receiver.getId(), msg);
            if (!sent) System.out.println("Failed to send message.");
        }
    }

    private void settingsPage() {
        while (true) {
            System.out.println("\n==== Settings ====");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Change Email");
            System.out.println("4. Back");
            System.out.print("Option (or 000 to go back): ");
            String input = scanner.nextLine();
            if (input.equals("000") || input.equals("4")) return;
            switch (input) {
                case "1" -> changeUsername();
                case "2" -> changePassword();
                case "3" -> changeEmail();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void changeUsername() {
        System.out.print("Old Username (or 000 to cancel): ");
        String oldU = scanner.nextLine();
        if (oldU.equals("000")) return;
        if (!oldU.equals(currentUser.getUsername())) {
            System.out.println("Old username does not match.");
            return;
        }
        System.out.print("New Username (or 000 to cancel): ");
        String newU = scanner.nextLine();
        if (newU.equals("000")) return;
        System.out.print("Confirm New Username (or 000 to cancel): ");
        String conf = scanner.nextLine();
        if (!newU.equals(conf)) {
            System.out.println("Confirmation mismatch.");
            return;
        }
        boolean changed = userController.changeUsername(currentUser.getId(), newU);
        if (changed) {
            System.out.println("Username updated.");
            currentUser.setUsername(newU);
        } else {
            System.out.println("Update failed.");
        }
    }

    private void changePassword() {
        System.out.print("Old Password (or 000 to cancel): ");
        String oldP = scanner.nextLine();
        if (oldP.equals("000")) return;
        if (!oldP.equals(currentUser.getPassword())) {
            System.out.println("Old password does not match.");
            return;
        }
        System.out.print("New Password (or 000 to cancel): ");
        String newP = scanner.nextLine();
        if (newP.equals("000")) return;
        System.out.print("Confirm New Password (or 000 to cancel): ");
        String conf = scanner.nextLine();
        if (!newP.equals(conf)) {
            System.out.println("Confirmation mismatch.");
            return;
        }
        boolean changed = userController.changePassword(currentUser.getId(), newP);
        if (changed) {
            System.out.println("Password updated.");
            currentUser.setPassword(newP);
        } else {
            System.out.println("Update failed.");
        }
    }

    private void changeEmail() {
        System.out.print("Old Email (or 000 to cancel): ");
        String oldE = scanner.nextLine();
        if (oldE.equals("000")) return;
        if (!oldE.equals(currentUser.getEmail())) {
            System.out.println("Old email does not match.");
            return;
        }
        System.out.print("New Email (or 000 to cancel): ");
        String newE = scanner.nextLine();
        if (newE.equals("000")) return;
        System.out.print("Confirm New Email (or 000 to cancel): ");
        String conf = scanner.nextLine();
        if (!newE.equals(conf)) {
            System.out.println("Confirmation mismatch.");
            return;
        }
        boolean changed = userController.changeEmail(currentUser.getId(), newE);
        if (changed) {
            System.out.println("Email updated.");
            currentUser.setEmail(newE);
        } else {
            System.out.println("Update failed.");
        }
    }
}
