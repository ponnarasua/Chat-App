package model;

public class Contact extends AbstractEntity {
    private int userId;
    private String contactName;
    private String contactPhone;

    public Contact() { super(); }

    public Contact(int id, int userId, String contactName, String contactPhone) {
        super(id);
        this.userId = userId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Contact(int userId, String contactName, String contactPhone) {
        super();
        this.userId = userId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    @Override
    public void display() {
        System.out.println("Contact ID: " + id + " | Name: " + contactName + " | Phone: " + contactPhone);
    }
}
