package model.dao;

import model.to.BankCheckRequestTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BankCheckRequestDAO extends DAO{

    public BankCheckRequestDAO() throws Exception{
        setConnection();
    }

    public BankCheckRequestDAO(Connection connection,PreparedStatement statement) {
        this.connection=connection;
        this.statement=statement;
    }

    //======================
    public BankCheckRequestTO insert(BankCheckRequestTO bankCheckRequestTO)throws Exception{
        this.statement = this.connection.prepareStatement("INSERT INTO BANK_CHECK_REQUEST" +
                "(REQUEST_DATE,CUSTOMER_ID,CHECKING_ACCOUNT_ID,CHECKED_FLAG) " +
                "VALUES (?,?,?,?)");
        this.statement.setLong(1,bankCheckRequestTO.getRequestDate());
        this.statement.setLong(2,bankCheckRequestTO.getCustomerId());
        this.statement.setLong(3,bankCheckRequestTO.getCheckingAccountId());
        this.statement.setInt(4,bankCheckRequestTO.getCheckedFlag());
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("SELECT BANK_CHECK_REQUEST_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankCheckRequestTO.setId(lastInsertedRowId) ;
        return bankCheckRequestTO;
    }

    public ArrayList selectAll()throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while(resultSet.next()){
            BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
            bankCheckRequestTO.setId(resultSet.getLong("ID"));
            bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

            arrayList.add(bankCheckRequestTO);
        }
        return arrayList;
    }

    public ArrayList selectByRequestDate(long requestDate)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST " +
                "WHERE REQUEST_DATE = ?");
        this.statement.setLong(1,requestDate);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while(resultSet.next()){
            BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
            bankCheckRequestTO.setId(resultSet.getLong("ID"));
            bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

            arrayList.add(bankCheckRequestTO);
        }
        return arrayList;
    }

    public ArrayList selectByCheckingAccountID(long checkingAccountID)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST " +
                "WHERE CHECKING_ACCOUNT_ID = ?");
        this.statement.setLong(1,checkingAccountID);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while(resultSet.next()){
            BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
            bankCheckRequestTO.setId(resultSet.getLong("ID"));
            bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

            arrayList.add(bankCheckRequestTO);
        }
        return arrayList;
    }

    public ArrayList selectByCustomerID(long customerID)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST " +
                "WHERE CUSTOMER_ID = ?");
        this.statement.setLong(1,customerID);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while(resultSet.next()){
            BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
            bankCheckRequestTO.setId(resultSet.getLong("ID"));
            bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

            arrayList.add(bankCheckRequestTO);
        }
        return arrayList;
    }

    public ArrayList selectByCheckedFlag(int checkedFlag)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST " +
                "WHERE CHECKED_FLAG = ?");
        this.statement.setInt(1,checkedFlag);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while(resultSet.next()){
            BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
            bankCheckRequestTO.setId(resultSet.getLong("ID"));
            bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
            bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

            arrayList.add(bankCheckRequestTO);
        }
        return arrayList;
    }

    public BankCheckRequestTO updateCheckingAccountID(long  recordID,long newCheckingAccountID)throws Exception{

        this.statement = this.connection.prepareStatement("UPDATE BANK_CHECK_REQUEST " +
                "SET CHECKING_ACCOUNT_ID = ?  " +
                "WHERE ID = ?");
        this.statement.setLong(1,newCheckingAccountID);
        this.statement.setLong(2,recordID);
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST" +
                "WHERE ID = ?");
        this.statement.setLong(1,recordID);
        ResultSet resultSet = this.statement.executeQuery();
        BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
        bankCheckRequestTO.setId(resultSet.getLong("ID"));
        bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
        bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
        bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

        return bankCheckRequestTO;
    }

    public BankCheckRequestTO updateCheckedFlag(long  recordID,int newCheckedFlag)throws Exception{

        this.statement = this.connection.prepareStatement("UPDATE BANK_CHECK_REQUEST " +
                "SET CHECKED_FLAG = ?  " +
                "WHERE ID = ?");
        this.statement.setLong(1,newCheckedFlag);
        this.statement.setLong(2,recordID);
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_CHECK_REQUEST" +
                "WHERE ID = ?");
        this.statement.setLong(1,recordID);
        ResultSet resultSet = this.statement.executeQuery();
        BankCheckRequestTO bankCheckRequestTO=new BankCheckRequestTO();
        bankCheckRequestTO.setId(resultSet.getLong("ID"));
        bankCheckRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
        bankCheckRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        bankCheckRequestTO.setCheckingAccountId(resultSet.getLong("CHECKING_ACCOUNT_ID"));
        bankCheckRequestTO.setCheckedFlag(resultSet.getInt("CHECKED_FLAG"));

        return bankCheckRequestTO;
    }
}
