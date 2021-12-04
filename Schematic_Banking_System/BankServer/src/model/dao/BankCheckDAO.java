package model.dao;

import model.to.BankCardTO;
import model.to.BankCheckTO;
import model.to.BankCheckTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BankCheckDAO extends DAO {

    public BankCheckDAO() throws Exception {
        setConnection();
    }

    public BankCheckDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //==================
    public void insert(BankCheckTO bankCheckTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO BANK_CHECK" +
                "(PAPER_MAX_NUMBER,CHECKING_ACCOUNT_ID) " +
                "VALUES (?,?)");
        this.statement.setLong(1, bankCheckTO.getPaperMaxNumber());
        this.statement.setLong(2, bankCheckTO.getCheckingAccountId());
        this.statement.executeUpdate();
    }

    public ArrayList selectAll() throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            BankCheckTO bankCheckTO = new BankCheckTO();
            bankCheckTO.setPaperMaxNumber(resultSet.getInt("PAPER_MAX_NUMBER"));
            bankCheckTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            arrayList.add(bankCheckTO);
        }

        return arrayList;
    }

    public BankCheckTO selectByID(long checkID) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK WHERE ID = ?");
        this.statement.setLong(1, checkID);
        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        BankCheckTO bankCheckTO = new BankCheckTO();
        bankCheckTO.setPaperMaxNumber(resultSet.getInt("PAPER_MAX_NUMBER"));
        bankCheckTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
        return bankCheckTO;
    }

    public ArrayList selectByCheckingAccountID(long checkingAccountID) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK WHERE CHECKING_ACCOUNT_ID = ?");
        this.statement.setLong(1, checkingAccountID);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            BankCheckTO bankCheckTO = new BankCheckTO();
            bankCheckTO.setPaperMaxNumber(resultSet.getInt("PAPER_MAX_NUMBER"));
            bankCheckTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            arrayList.add(bankCheckTO);
        }
        return arrayList;
    }

}
