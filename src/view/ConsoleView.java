package view;

import controller.UsersController;
import controller.*;
import model.Chat;
import model.Contact;
import model.User;

import java.util.*;


public class Menu {
    private UsersController usersController;
    private ChatController chatController;
    private ContactController contactController;
    private Scanner scanner;

    public Menu(UsersController usersController, ChatController chatController, ContactController contactController) {
        this.usersController = usersController;
        this.chatController = chatController;
        this.contactController = contactController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        String phone = null;
        while (true) {
            System.out.println("\n--- Chat App ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("00. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": register(); break;
                case "2": phone = login(); break;
                case "00": return;
                default: System.out.println("Invalid Option.");
            }

            if (phone != null) {
                while (true) {
                    System.out.print("1. View Contacts\n2. Start Chat\n00. Logout\nEnter choice: ");
                    choice = scanner.nextLine();
                    switch (choice) {
                        case "1": viewContacts(); break;
                        case "2": startChat(phone); break;
                        case "00": phone = null; break;
                        default: System.out.println("Invalid Option.");
                    }
                }
            }
        }
    }
    private void register() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter DOB (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        String role = "user";

        if (usersController.registerUser(name, phone, password, email, dob, role)) {
            System.out.println("Registration Successful!");
        } else {
            System.out.println("Registration Failed!");
        }
    }

    private String login() {
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = usersController.loginUser (phone, password);
        if (user != null) {
            System.out.println("Login Successful! Welcome " + user.getName());
            return user.getPhone();
        } else {
            System.out.println("Invalid Credentials!");
            return null;
        }
    }
    private void viewContacts() {
        // Fetch and display contacts
        List<Contact> contacts = contactController.getAllContacts(); // Implement this method in ContactController
        System.out.println("--- Contacts ---");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName() + " (" + contacts.get(i).getPhone() + ")");
        }
        System.out.println("Enter the number to chat or '00' to go back:");
        String choice = scanner.nextLine();
        if (choice.equals("00")) {
            return; // Go back
        }
        try {
            int index = Integer.parseInt(choice) - 1;
            if (index >= 0 && index < contacts.size()) {
                startChatWithContact(contacts.get(index).getPhone());
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void startChat(String phone) {
        // This method can be used to start a chat with a known number or saved contact
        System.out.print("Enter the phone number or '00' to go back: ");
        String input = scanner.nextLine();
        if (input.equals("00")) {
            return; // Go back
        }
        startChatWithContact(input);
    }

    private void startChatWithContact(String contactPhone) {
        while (true) {
            System.out.println("--- Chat with " + contactPhone + " ---");
            List<Chat> messages = chatController.getRecentChats(contactPhone); // Fetch recent chats
            for (Chat chat : messages) {
                System.out.println(chat.getSenderPhone() + ": " + chat.getMessage());
            }
            System.out.println("Enter your message (or '00' to go back): ");
            String message = scanner.nextLine();
            if (message.equals("00")) {
                return; // Go back to the previous menu
            }
            chatController.sendMessage(contactPhone, message); // Send message
            System.out.println("Message sent!");
        }
    }
}