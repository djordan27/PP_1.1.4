package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
        boolean haveTable = false;
        String sql = """
                CREATE TABLE `user` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastname` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`));
                """;
        try (Connection connection = new Util().getInstance().getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables("mydbtest", null, null, new String[]{});
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                if (name.equals("user")) {
                    System.out.println("you just have this table");
                    haveTable = true;
                }
            }
            if (haveTable == false) {
                Statement stmt = connection.createStatement();
                connection.setAutoCommit(false);
                stmt.executeUpdate(sql);
                System.out.println("create table");
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //удаление базы даных
    public void dropUsersTable() {
        String sql = "DROP TABLE user";
        try (Connection connection = new Util().getInstance().getConnection()) {
//            connection = new Util().getConnection();
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            stmt.executeUpdate(sql);
            connection.commit();
            connection.close();
            System.out.println("you delate your table");
        } catch (SQLException e) {
            System.out.println("You can't delete this table");
        }
    }

    //сохранение пользователя
    public void saveUser(String name, String lastName, byte age) {

        String sql = "Insert into user (age, lastName, name) values(?,?,?)";
        try (Connection connection = new Util().getInstance().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, age);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, name);
            preparedStatement.execute();
            connection.commit();
            connection.close();
            System.out.println("save user is successfull");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("you can't save this user");
        }
    }

    //удаление пользователя по id
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = " + id;
        try (Connection connection = new Util().getInstance().getConnection()) {
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
        try (Connection connection = new Util().getInstance().getConnection()) {
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
        try (Connection connection = new Util().getInstance().getConnection()) {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.commit();
            connection.close();
            System.out.println("you clean your table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
