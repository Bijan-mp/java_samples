package model.dao;

import model.to.CheckingAccountTO;
import model.to.CheckingAccountTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckingAccountDAO extends DAO {

    public CheckingAccountDAO() throws Exception {
        setConnection();
    }

    public CheckingAccountDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //========================
    public void insert(CheckingAccountTO checkingAccountTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO CHECKING_ACCOUNT " +
                "(MONEY,OPENING_DATE,CUSTOMER_ID,BRANCH_ID) " +
                "VALUES (?,?,?,?)");
        this.statement.setLong(1, checkingAccountTO.getMoney());
        this.statement.setLong(2, checkingAccountTO.getOpeningDate());
        this.statement.setLong(3, checkingAccountTO.getCustomerId());
        this.statement.setInt(4, checkingAccountTO.getBranchId());
        this.statement.executeUpdate();

    }

    public CheckingAccountTO selectById(long checkingAccountId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT " +
                " WHERE ID = ?");
        this.statement.setLong(1, checkingAccountId);
        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        CheckingAccountTO checkingAccountTO = new CheckingAccountTO();
        checkingAccountTO.setId(resultSet.getLong("ID"));
        checkingAccountTO.setMoney(resultSet.getInt("MONEY"));
        checkingAccountTO.setOpeningDate(resultSet.getLong("OPENING_DATE"));
        checkingAccountTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        checkingAccountTO.setBranchId(resultSet.getInt("BRANCH_ID"));
        return checkingAccountTO;
    }

    public ArrayList selectByCustomerId(long customerId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT " +
                "WHERE CUSTOMER_ID = ? ORDER BY ID");
        this.statement.setLong(1, customerId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            CheckingAccountTO checkingAccountTO = new CheckingAccountTO();
            checkingAccountTO.setId(resultSet.getLong("ID"));
            checkingAccountTO.setMoney(resultSet.getInt("MONEY"));
            checkingAccountTO.setOpeningDate(resultSet.getLong("OPENING_DATE"));
            checkingAccountTO.setBranchId(resultSet.getInt("BRANCH_ID"));
            arrayList.add(checkingAccountTO);
        }
        return arrayList;
    }

    public CheckingAccountTO selectByCustomerIDAndAccountId(long customerId, long accountId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT " +
                " WHERE ID = ? AND CUSTOMER_ID = ?");
        this.statement.setLong(1, accountId);
        this.statement.setLong(2, customerId);
        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        CheckingAccountTO checkingAccountTO = new CheckingAccountTO();
        checkingAccountTO.setId(resultSet.getLong("ID"));
        checkingAccountTO.setMoney(resultSet.getInt("MONEY"));
        checkingAccountTO.setOpeningDate(resultSet.getLong("OPENING_DATE"));
        checkingAccountTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        checkingAccountTO.setBranchId(resultSet.getInt("BRANCH_ID"));

        return checkingAccountTO;
    }

    public ArrayList selectByCustomerIdAndAccountTypeId(long customerId, int accountTypeId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT " +
                "WHERE CUSTOMER_ID = ? AND ACCOUNT_TYPE_ID = ?");
        this.statement.setLong(1, customerId);
        this.statement.setInt(2, accountTypeId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            CheckingAccountTO checkingAccountTO = new CheckingAccountTO();
            checkingAccountTO.setId(resultSet.getLong("ID"));
            checkingAccountTO.setMoney(resultSet.getInt("MONEY"));
            checkingAccountTO.setOpeningDate(resultSet.getLong("OPENING_DATE"));
            checkingAccountTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            checkingAccountTO.setBranchId(resultSet.getInt("BRANCH_ID"));
            arrayList.add(checkingAccountTO);
        }
        return arrayList;
    }

    public ArrayList selectByCustomerIdAndBranchId(long customerId, int branchId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT " +
                "WHERE CUSTOMER_ID = ? AND BRANCH_ID = ?");
        this.statement.setLong(1, customerId);
        this.statement.setInt(2, branchId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            CheckingAccountTO checkingAccountTO = new CheckingAccountTO();
            checkingAccountTO.setId(resultSet.getLong("ID"));
            checkingAccountTO.setMoney(resultSet.getInt("MONEY"));
            checkingAccountTO.setOpeningDate(resultSet.getLong("OPENING_DATE"));
            checkingAccountTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            checkingAccountTO.setBranchId(resultSet.getInt("BRANCH_ID"));
            arrayList.add(checkingAccountTO);
        }
        return arrayList;
    }

    public CheckingAccountTO updateMoney(long id, long money) throws Exception {
        this.statement = this.connection.prepareStatement("UPDATE CHECKING_ACCOUNT " +
                "SET MONEY = ? " +
                "WHERE ID = ?");
        this.statement.setLong(1, money);
        this.statement.setLong(2, id);
        this.statement.executeUpdate();
        return selectById(id);
    }

    public CheckingAccountTO updateAccountTypeId(long id, int accountTypeId) throws Exception {
        this.statement = this.connection.prepareStatement("UPDATE CHECKING_ACCOUNT " +
                "SET ACCOUNT_TYPE_ID = ? " +
                "WHERE ID = ?");
        this.statement.setInt(1, accountTypeId);
        this.statement.setLong(2, id);
        this.statement.executeUpdate();
        return selectById(id);
    }
}
