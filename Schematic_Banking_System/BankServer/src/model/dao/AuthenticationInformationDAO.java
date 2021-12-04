package model.dao;

import model.to.AuthenticationInformationTO;
import tools.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthenticationInformationDAO extends DAO {

    public AuthenticationInformationDAO() throws Exception {
        setConnection();
    }

    public AuthenticationInformationDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //================
    public void insert(AuthenticationInformationTO authenticationInformationTO)throws Exception{

        String encryptedPassword = Encryption.getMD5(authenticationInformationTO.getPassword());
        this.statement = this.connection.prepareStatement("INSERT INTO AUTHENTICATION_INFORMATION " +
                "(CUSTOMER_ID,USERNAME,PASSWORD) " +
                "VALUES (?,?,?)");
        this.statement.setLong(1,authenticationInformationTO.getCustomerId());
        this.statement.setString(2,authenticationInformationTO.getUserName());
        this.statement.setString(3,encryptedPassword);
        this.statement.executeUpdate();

    }

    public AuthenticationInformationTO select(String userName,String password)throws Exception{


        String encryptedPassword = Encryption.getMD5(password);

        this.statement = this.connection.prepareStatement("SELECT * FROM AUTHENTICATION_INFORMATION " +
                "WHERE USERNAME = ? AND PASSWORD = ?");
        this.statement.setString(1,userName);
        this.statement.setString(2,encryptedPassword);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        AuthenticationInformationTO authenticationInformationTO = new AuthenticationInformationTO();
        authenticationInformationTO.setId(resultSet.getLong("ID"));
        authenticationInformationTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        authenticationInformationTO.setUserName(resultSet.getString("USERNAME"));
        //authenticationInformationTO.setPassword(); It must not set

        return authenticationInformationTO;
    }

    public void updatePassword(String userName,String oldPassword,String newPassword)throws Exception{

       String encryptedPassword = Encryption.getMD5(oldPassword);
       String newEncryptedPassword = Encryption.getMD5(newPassword);

        this.statement = this.connection.prepareStatement("UPDATE AUTHENTICATION_INFORMATION" +
                " SET PASSWORD = ?" +
                " WHERE USERNAME = ? AND PASSWORD = ?");
        this.statement.setString(1,newEncryptedPassword);
        this.statement.setString(2,userName);
        this.statement.setString(3,encryptedPassword);
        this.statement.executeUpdate();

    }
}
