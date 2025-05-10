package view;
import dao.ContactDAO;

import java.util.Scanner;

public class Contact {
    private ContactDAO contactDAO = new ContactDAO();
    private Scanner scanner = new Scanner(System.in);

    public void saveContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        model.Contact contact = new model.Contact(name, phone);
        if (contactDAO.saveContact(contact)) {
            System.out.println("Contact Saved!");
        } else {
            System.out.println("Failed to Save Contact.");
        }
    }
}
