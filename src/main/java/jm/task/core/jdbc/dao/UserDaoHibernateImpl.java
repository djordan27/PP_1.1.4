package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {

    }

    public void saveUser(User user) {
    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS`user` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastname` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`));
                """;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            System.out.println("Create a user table");
            transaction.commit();
        } catch (HibernateException e) {
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
//            Query query = session.createSQLQuery(sql).addEntity(User.class);
            System.out.println("you delete your's table");
            transaction.commit();
        } catch (HibernateException e) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
//        User user = new User(name, lastName, age);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            session.persist(new User(name, lastName, age));
            transaction.commit();
            System.out.println("You save your student: \n name: " + name + "\n lastname: " + lastName + "\n age: " + age);
        } catch (Exception e) {
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        }

    }

    public List<User> getAllUsers() {
        List<User> resultSet = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            resultSet = session.createQuery("from User", User.class).list();
            return resultSet;
        } catch (HibernateException h) {
        }
        return resultSet;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from User ").executeUpdate();
            transaction.commit();
        } catch (HibernateException h) {
        }
    }
}

