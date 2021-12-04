package model.dao;

import model.to.AccountRequestTO;
import model.to.CustomerTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AccountRequestDAO extends DAO {

    public AccountRequestDAO() throws Exception {
        setConnection();
    }

    public AccountRequestDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //===================
    public void insert(AccountRequestTO accountRequestTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO ACCOUNT_REQUEST" +
                "(CUSTOMER_ID,ACCOUNT_TYPE,CHECKED) " +
                "VALUES (?,?,?");
        this.statement.setLong(1, accountRequestTO.getCustomerId());
        this.statement.setString(2, accountRequestTO.getAccountType());
        this.statement.setInt(3, accountRequestTO.getChecked());
        this.statement.executeUpdate();

    }

    public ArrayList selectAll() throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM ACCOUNT_REQUEST");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            AccountRequestTO accountRequestTO = new AccountRequestTO();
            accountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            accountRequestTO.setAccountType(resultSet.getString("ACCOUNT_TYPE"));
            accountRequestTO.setChecked(resultSet.getInt("CHECKED"));
            arrayList.add(accountRequestTO);
        }

        return arrayList;
    }

    public ArrayList selectByCustomerId(Long customerId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM ACCOUNT_REQUEST " +
                "WHERE CUSTOMER_ID = ?");
        this.statement.setLong(1, customerId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList=new ArrayList();
        while (resultSet.next()) {
            AccountRequestTO accountRequestTO = new AccountRequestTO();
            accountRequestTO.setId(resultSet.getLong("ID"));
            accountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            accountRequestTO.setAccountType(resultSet.getString("ACCOUNT_TYPE"));
            accountRequestTO.setChecked(resultSet.getInt("CHECKED"));
            arrayList.add(accountRequestTO);
        }
        return arrayList;
    }

    public AccountRequestTO update(AccountRequestTO accountRequestTO, AccountRequestTO oldAccountRequestTO) throws Exception {

        return new AccountRequestTO();
    }
}
