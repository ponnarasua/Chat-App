package controller;

import dao.UserDAO;
import model.User;

public class UsersController {
    private UserDAO userDAO;

    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerUser (String name, String phone, String password, String email, String dob, String role) {
        User user = new User(name, phone, password, email, dob, role);
        return userDAO.registerUser (user);
    }

    public User loginUser (String phone, String password) {
        return userDAO.loginUser (phone, password);
    }
}