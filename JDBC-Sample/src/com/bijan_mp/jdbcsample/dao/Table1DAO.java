package com.bijan_mp.jdbcsample.dao;

import java.sql.*;
import java.util.Properties;

public class Table1DAO {

    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public Table1DAO() throws SQLException {

        //Step 1 : Register JDBC driver Abroach-1 : with Class.for naem
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            System.out.println("Error : Unable to Load jdbc driver");
        }

        /** Register JDBC driver Abroach-1 : with Class.forName wit getInstance
         * Use to work around noncompliant JVMs,but then you'll have to code
         * for two extra Exceptions as follows :
         * Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
         * exceptions : ClassNotFoundException , InstantiationException  ,IllegalAccessException
         **/

        /** Register JDBC driver Abroach-2 : with DriverManager.registerDriver() wit getInstance
         * You should use the registerDriver() method if you are using a non-JDK compliant JVM,
         * such as the one provided by Microsoft.
         *      Driver myDriver = new oracle.jdbc.driver.OracleDriver();
         *      DriverManager.registerDriver(myDriver);
         *    exceptions :   SQLException
         **/


        //step 2 : create connection object.
            //jdbc:oracle:thin:@localhost:1521:xe
        conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:db_name",
                "username",
                "password");

        //With Property object
        /*Properties info = new Properties( );
        info.put( "user", "username" );
        info.put( "password", "password" );
        Connection conn2 = DriverManager.getConnection(URL, info);
        */

        //Step 3:
        statement = conn.createStatement();

    }

    public void insert(String name, String family) throws Exception {
        statement.executeUpdate("INSERT INTO T1 (NAME,FAMILY) VALUES ('" + name + "','" + family + "')");
    }

    public ResultSet select() throws Exception {
        return statement.executeQuery("SELECT * FROM T1");
    }

    public ResultSet selectByName(String name) throws SQLException {
        preparedStatement = conn.prepareStatement("SELECT * FROM T1 WHERE NAME = ?");
        preparedStatement.setString(1, name);
        return preparedStatement.executeQuery();
    }

    public void update(String name, String family) throws Exception {
        statement.executeUpdate("UPDATE T1 SET NAME='" + name + "' WHERE FAMILY = '" + family + "'");
    }

    public void doTransaction() throws SQLException {

        // 1. set autocommit false.
        conn.setAutoCommit(false);

        // 2. Create batch and execute it.
        statement.addBatch("INSERT INTO T1 (NAME,FAMILY) VALUES ('Bijan','MP')");
        statement.addBatch("INSERT INTO T1 (NAME,FAMILY) VALUES ('Bijan','MP')");
        //...
        statement.executeBatch();

        // 3. Commit by connection.
        conn.commit();

        // Rollback : You Can rollback if you want.
        // conn.rollback( );

    }

    public void close() throws Exception {
        statement.close();
        if (preparedStatement != null) preparedStatement.close();
        conn.close();
    }


}
