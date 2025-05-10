package dao;

import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    public boolean saveContact(Contact contact) {
        String sql = "INSERT INTO contacts (user_id, contact_name, contact_phone) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, contact.getUserId());
            ps.setString(2, contact.getContactName());
            ps.setString(3, contact.getContactPhone());
            int row = ps.executeUpdate();
            if (row > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()) {
                        contact.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Saving contact error: " + e.getMessage());
        }
        return false;
    }

    public List<Contact> getContactsByUserId(int userId) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    contacts.add(new Contact(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("contact_name"),
                            rs.getString("contact_phone")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Getting contacts error: " + e.getMessage());
        }
        return contacts;
    }
}