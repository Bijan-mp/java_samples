package model.dao;

import model.to.CheckingAccountRequestTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CheckingAccountRequestDAO extends DAO{

    public CheckingAccountRequestDAO()throws Exception{
        setConnection();
    }

    public CheckingAccountRequestDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public CheckingAccountRequestTO insert(CheckingAccountRequestTO checkingAccountRequestTO)throws Exception{
        this.statement = this.connection.prepareStatement("INSERT INTO CHECKING_ACCOUNT_REQUEST (" +
                "REQUEST_DATE," +
                "CUSTOMER_ID," +
                "SUPPORT_CHECKING_ACCOUNT_ID," +
                "MONEY," +
                "CHECK_FLAG) " +
                "VALUES (?,?,?,?,?)");
        this.statement.setLong(1,checkingAccountRequestTO.getRequestDate());
        this.statement.setLong(2,checkingAccountRequestTO.getCustomerId());
        this.statement.setLong(3,checkingAccountRequestTO.getSupportCheckingAccountId());
        this.statement.setLong(4,checkingAccountRequestTO.getMoney());
        this.statement.setLong(5,-1);
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("select CHECKING_ACCOUNT_REQUEST_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        checkingAccountRequestTO.setId(lastInsertedRowId);
        return checkingAccountRequestTO;
    }

    public CheckingAccountRequestTO selectById(long checkingAccountRequestId)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT_REQUEST" +
                " WHERE id = ? " );
        this.statement.setLong(1,checkingAccountRequestId);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        CheckingAccountRequestTO checkingAccountRequestTO = new CheckingAccountRequestTO();
        checkingAccountRequestTO.setId(resultSet.getLong("ID"));
        checkingAccountRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
        checkingAccountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        checkingAccountRequestTO.setSupportCheckingAccountId(resultSet.getLong("SUPPORT_CHECKING_ACCOUNT_ID"));
        checkingAccountRequestTO.setMoney(resultSet.getLong("MONEY"));
        checkingAccountRequestTO.setCheck_flag(resultSet.getLong("CHECK_FLAG"));

        return checkingAccountRequestTO;
    }

    public ArrayList<CheckingAccountRequestTO> selectByCustomerId(long customerId)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM CHECKING_ACCOUNT_REQUEST" +
                " WHERE CUSTOMER_ID = ? " );
        this.statement.setLong(1,customerId);
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList<CheckingAccountRequestTO> arrayList =new ArrayList<CheckingAccountRequestTO>();
        while (resultSet.next()){
            CheckingAccountRequestTO checkingAccountRequestTO = new CheckingAccountRequestTO();
            checkingAccountRequestTO.setId(resultSet.getLong("ID"));
            checkingAccountRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            checkingAccountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            checkingAccountRequestTO.setSupportCheckingAccountId(resultSet.getLong("SUPPORT_CHECKING_ACCOUNT_ID"));
            checkingAccountRequestTO.setMoney(resultSet.getLong("MONEY"));
            checkingAccountRequestTO.setCheck_flag(resultSet.getLong("CHECK_FLAG"));
            arrayList.add(checkingAccountRequestTO);
        }
        return arrayList;
    }

}
