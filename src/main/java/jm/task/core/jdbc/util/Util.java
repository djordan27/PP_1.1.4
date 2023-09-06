package jm.task.core.jdbc.util;


import java.sql.*;

public  class Util {
    private  static volatile Util instance;
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    //private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection connection;
    private static Driver driver;
    public static Util getInstance(){
        if(instance == null){
            synchronized (Util.class){
                if(instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }
    public Util() {

        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.getConnection();
//            System.out.println(connection.isClosed());
        }
        catch (SQLException e1) {
            System.out.println("Драйвер не зарегистрировался" + e1.getErrorCode() + e1.getSQLState() + e1.getLocalizedMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
