package model.dao;

import model.to.BankCardTO;
import model.to.BankCardTO;
import tools.Encryption;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TimerTask;

public class BankCardDAO extends DAO {

    public BankCardDAO() throws Exception {
        setConnection();
    }

    public BankCardDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public void insert(BankCardTO bankCardTO) throws Exception {

        String password = bankCardTO.getPassword();
        String internetPassword = bankCardTO.getInternetPassword();

        this.statement = this.connection.prepareStatement("INSERT INTO BANK_CARD" +
                "(PASSWORD,INTERNET_PASSWORD,CVV2,EXPIRE_DATE,BRANCH_ID,CHECKING_ACCOUNT_ID,BLOCKED_FLAG) " +
                "VALUES (?,?,?,?,?,?,?");
        this.statement.setString(1, password);
        this.statement.setString(2, internetPassword);
        this.statement.setInt(3, bankCardTO.getCVV2());
        this.statement.setLong(4, bankCardTO.getExpireDate());
        this.statement.setLong(5, bankCardTO.getCheckingAccountId());
        this.statement.setInt(6, bankCardTO.getBlockedFlag());
        this.statement.executeUpdate();
    }

    public ArrayList selectAll() throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CARD");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(resultSet.getLong("ID"));
            bankCardTO.setCVV2(resultSet.getInt("CVV2"));
            bankCardTO.setExpireDate(resultSet.getInt("EXPIRE_DATE"));
            bankCardTO.setBranchId(resultSet.getInt("BRANCH_ID"));
            bankCardTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCardTO.setBlockedFlag(resultSet.getInt("BLOCKED_FLAG"));
            arrayList.add(bankCardTO);
        }

        return arrayList;
    }

    public BankCardTO selectUnBlockedBankCard(BankCardTO bankCardTO) throws Exception {

        String internetPassword = Encryption.getMD5(bankCardTO.getInternetPassword());

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CARD " +
                "WHERE  " +
                //"BLOCKED_FLAG != 1 " +
                "AND ID = ? " +
                "AND INTERNET_PASSWORD = ? " +
                "AND CVV2 = ? " +
                "AND EXPIRE_DATE = ? ");

        this.statement.setLong(1, bankCardTO.getId());
        this.statement.setString(2, internetPassword);
        this.statement.setInt(3, bankCardTO.getCVV2());
        this.statement.setInt(4, bankCardTO.getExpireDate());
        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        BankCardTO bankCardTO2 = new BankCardTO();
        bankCardTO2.setId(resultSet.getLong("ID"));
        bankCardTO2.setExpireDate(resultSet.getInt("EXPIRE_DATE"));
        bankCardTO2.setCVV2(resultSet.getInt("CVV2"));
        bankCardTO2.setBranchId(resultSet.getInt("BRANCH_ID"));
        bankCardTO2.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
        bankCardTO2.setBlockedFlag(resultSet.getInt("BLOCKED_FLAG"));
        return bankCardTO2;
    }

    public BankCardTO selectById(long bankCardId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CARD " +
                "WHERE ID = ? ");

        this.statement.setLong(1, bankCardId);

        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        BankCardTO bankCardTO = new BankCardTO();
        bankCardTO.setId(resultSet.getLong("ID"));
        bankCardTO.setExpireDate(resultSet.getInt("EXPIRE_DATE"));
        bankCardTO.setCVV2(resultSet.getInt("CVV2"));
        bankCardTO.setBranchId(resultSet.getInt("BRANCH_ID"));
        bankCardTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
        bankCardTO.setBlockedFlag(resultSet.getInt("BLOCKED_FLAG"));
        return bankCardTO;
    }

    public void updateInternetPassword(BankCardTO oldBankCardTO, String newInternetPassword) throws Exception {

        //String newPassword = Encryption.getMD5(bankCardTO.getPassword());
        String newEncryptedInternetPassword = Encryption.getMD5(newInternetPassword);
        String oldInternetPassword = Encryption.getMD5(oldBankCardTO.getInternetPassword());

        this.statement = this.connection.prepareStatement("UPDATE BANK_CARD " +
                "SET INTERNET_PASSWORD = ?  " +
                "WHERE ID = ? AND INTERNET_PASSWORD = ? AND CVV2 = ? AND EXPIRE_DATE = ?");

        this.statement.setString(1, newEncryptedInternetPassword);
        this.statement.setLong(2, oldBankCardTO.getId());
        this.statement.setString(3, oldInternetPassword);
        this.statement.setInt(4, oldBankCardTO.getCVV2());
        this.statement.setLong(5, oldBankCardTO.getExpireDate());
        this.statement.executeUpdate();

        //can return BankCardTO object contains new bank card details

    }

    public void updatePassword(BankCardTO oldBankCardTO, String newPassword) throws Exception {

        String newEncryptedPassword = Encryption.getMD5(newPassword);
        String oldPassword = Encryption.getMD5(oldBankCardTO.getPassword());

        this.statement = this.connection.prepareStatement("UPDATE BANK_CARD " +
                "SET PASSWORD = ?  " +
                "WHERE ID = ? AND PASSWORD = ? AND CVV2 = ? AND EXPIRE_DATE = ?");

        this.statement.setString(1, newEncryptedPassword);
        this.statement.setLong(2, oldBankCardTO.getId());
        this.statement.setString(3, oldPassword);
        this.statement.setInt(4, oldBankCardTO.getCVV2());
        this.statement.setLong(5, oldBankCardTO.getExpireDate());
        this.statement.executeUpdate();

        //can return BankCardTO object contains new bank card details
    }

    public void updateBlockFlag(BankCardTO bankCardTO) throws Exception {
        String internetPassword = Encryption.getMD5(bankCardTO.getInternetPassword());
        this.statement = this.connection.prepareStatement("UPDATE BANK_CARD " +
                " SET BLOCKED_FLAG = ?  " +
                " WHERE ID = ? AND PASSWORD = ? AND CVV2 = ? AND EXPIRE_DATE = ?");

        this.statement.setInt(1, bankCardTO.getBlockedFlag());
        this.statement.setLong(2, bankCardTO.getId());
        this.statement.setString(3, internetPassword);
        this.statement.setInt(4, bankCardTO.getCVV2());
        this.statement.setInt(5, bankCardTO.getExpireDate());
        this.statement.executeUpdate();

        System.out.println("ok");
        System.out.println(bankCardTO.getBlockedFlag());
        System.out.println(bankCardTO.getId());
        System.out.println(internetPassword);
        System.out.println(bankCardTO.getCVV2());
        System.out.println(bankCardTO.getExpireDate());


    }




}
