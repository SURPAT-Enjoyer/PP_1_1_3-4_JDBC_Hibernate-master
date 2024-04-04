package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {



    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user(id MEDIUMINT NOT NULL AUTO_INCREMENT, name TEXT, lastName TEXT, age TINYINT, PRIMARY KEY (id));");
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user;");
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user(name, lastName, age) VALUES(?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user;");
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE user;");
        } catch (Exception e) {
            System.out.println("Ошибка работы с БД - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
