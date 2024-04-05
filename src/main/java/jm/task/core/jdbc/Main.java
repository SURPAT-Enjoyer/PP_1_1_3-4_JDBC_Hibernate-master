package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vasya", "Pupkin", (byte)20);
        userService.saveUser("Ivan", "Ivanov", (byte)25);
        userService.saveUser("Sidor", "Sidorov", (byte)26);
        userService.saveUser("Petr", "Petrov", (byte)19);
        userService.getAllUsers().stream()
                .peek(System.out::println)
                .map(User::getId)
                .forEach(userService::removeUserById);
        userService.dropUsersTable();
    }
}
