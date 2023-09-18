package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    //Создание базы данных
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS`user` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastname` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`));
                """;
        try (Connection connection = Util.getInstance().getConnection()) {
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            stmt.executeUpdate(sql);
            System.out.println("create table");

        } catch (SQLException e) {
        }
    }


    //удаление базы даных
    public void dropUsersTable() {
        String sql = "DROP TABLE user";
        try (Connection connection = Util.getInstance().getConnection()) {
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            stmt.executeUpdate(sql);
            connection.commit();
            System.out.println("you delate your table");
        } catch (SQLException e) {
            System.out.println("You can't delete this table");
        }
    }

    //сохранение пользователя
    public void saveUser(String name, String lastName, byte age) {

        String sql = "Insert into user (age, lastName, name) values(?,?,?)";
        try (Connection connection = Util.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, age);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, name);
            preparedStatement.execute();
            connection.commit();
            System.out.println("save user is successfull");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("you can't save this user");
        }
    }

    //удаление пользователя по id
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = " + id;
        try (Connection connection = Util.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //получения листа пользователей
    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        Statement stmt;
        String sql = "SELECT age, lastname, name from user";
        try (Connection connection = Util.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                user.add(new User(result.getString("name"), result.getString("lastname"), result.getByte("age")));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //очистка таблицы
    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        Statement stmt;
        try (Connection connection = Util.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.commit();
            System.out.println("you clean your table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
