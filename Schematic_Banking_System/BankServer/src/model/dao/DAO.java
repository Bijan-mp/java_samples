package model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class DAO {

    protected Connection connection;
    protected PreparedStatement statement;
    protected Properties prop;

    protected void setConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        prop = new Properties();
        prop.load(new FileInputStream("config.properties"));
        String database = prop.getProperty("database");
        String databaseUser= prop.getProperty("dbuser");
        String databasePassword  = prop.getProperty("dbpassword");

        this.connection = DriverManager.getConnection(database,databaseUser,databasePassword);

    }

    public void close() throws Exception {
        this.statement.close();
        this.connection.close();
    }
}
