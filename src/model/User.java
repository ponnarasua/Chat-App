package model;

public class User extends AbstractEntity implements IUser {
    private String username;
    private String phone;
    private String password;
    private String email;

    public User() { super(); }

    public User(int id, String username, String phone, String password, String email) {
        super(id);
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public User(String username, String phone, String password, String email) {
        super();
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public void display() {
        System.out.println("User ID: " + id + " | Username: " + username + " | Phone: " + phone + " | Email: " + email);
    }
}
