package model;

public interface IUser {
    String getUsername();
    String getPhone();
    default void greet() {
        System.out.println("Hello, " + getUsername() + "!");
    }
}
