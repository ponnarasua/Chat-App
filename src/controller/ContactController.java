package controller;

import dao.ContactDao;
import model.Contact;

import java.util.List;

public class ContactController {
    private ContactDao contactDao = new ContactDao();

    public boolean saveContact(int userId, String contactName, String contactPhone) {
        Contact contact = new Contact(userId, contactName, contactPhone);
        return contactDao.saveContact(contact);
    }

    public List<Contact> getContactsByUserId(int userId) {
        return contactDao.getContactsByUserId(userId);
    }
}
