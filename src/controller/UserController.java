package controller;

import dao.UserDao;
import model.User;

public class UserController {
    private UserDao userDao = new UserDao();

    public boolean register(String username, String phone, String password, String email) {
        User user = new User(username, phone, password, email);
        return userDao.register(user);
    }

    public User login(String phone, String password) {
        return userDao.login(phone, password);
    }

    public boolean changeUsername(int userId, String newUsername) {
        return userDao.updateUsername(userId, newUsername);
    }

    public boolean changePassword(int userId, String newPassword) {
        return userDao.updatePassword(userId, newPassword);
    }

    public boolean changeEmail(int userId, String newEmail) {
        return userDao.updateEmail(userId, newEmail);
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
}