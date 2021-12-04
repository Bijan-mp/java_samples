package model.dao;

import model.to.CheckingAccountRequestTO;
import model.to.DepositAccountRequestTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DepositAccountRequestDAO extends DAO {

    public DepositAccountRequestDAO()throws Exception{
        setConnection();
    }

    public DepositAccountRequestDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public DepositAccountRequestTO insert(DepositAccountRequestTO depositAccountRequestTO)throws Exception{
        this.statement = this.connection.prepareStatement("INSERT INTO DEPOSIT_ACCOUNT_REQUEST (" +
                "REQUEST_DATE," +
                "CUSTOMER_ID," +
                "DEPOSIT_ACCOUNT_TYPE_ID," +
                "SUPPORT_CHECKING_ACCOUNT_ID," +
                "MONEY," +
                "CHECK_FLAG) " +
                "VALUES (?,?,?,?,?,?)");
        this.statement.setLong(1,depositAccountRequestTO.getRequestDate());
        this.statement.setLong(2,depositAccountRequestTO.getCustomerId());
        this.statement.setInt(3,depositAccountRequestTO.getDepositAccountTypeId());
        this.statement.setLong(4,depositAccountRequestTO.getSupportCheckingAccountId());
        this.statement.setLong(5,depositAccountRequestTO.getMoney());
        this.statement.setLong(6,-1);
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("select DEPOSIT_ACCOUNT_REQUEST_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        depositAccountRequestTO.setId(lastInsertedRowId);
        return depositAccountRequestTO;
    }

    public DepositAccountRequestTO selectById(long depositAccountRequestId)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM DEPOSIT_ACCOUNT_REQUEST" +
                " WHERE id = ? " );
        this.statement.setLong(1,depositAccountRequestId);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        DepositAccountRequestTO depositAccountRequestTO = new DepositAccountRequestTO();
        depositAccountRequestTO.setId(resultSet.getLong("ID"));
        depositAccountRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
        depositAccountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        depositAccountRequestTO.setDepositAccountTypeId(resultSet.getInt("DEPOSIT_ACCOUNT_TYPE_ID"));
        depositAccountRequestTO.setSupportCheckingAccountId(resultSet.getLong("SUPPORT_CHECKING_ACCOUNT_ID"));
        depositAccountRequestTO.setMoney(resultSet.getLong("MONEY"));
        depositAccountRequestTO.setCheck_flag(resultSet.getLong("CHECK_FLAG"));

        return depositAccountRequestTO;
    }

    public ArrayList<DepositAccountRequestTO> selectByCustomerId(long customerId)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM DEPOSIT_ACCOUNT_REQUEST" +
                " WHERE CUSTOMER_ID = ? " );
        this.statement.setLong(1,customerId);
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList<DepositAccountRequestTO> arrayList =new ArrayList<DepositAccountRequestTO>();
        while (resultSet.next()){
            DepositAccountRequestTO depositAccountRequestTO = new DepositAccountRequestTO();
            depositAccountRequestTO.setId(resultSet.getLong("ID"));
            depositAccountRequestTO.setRequestDate(resultSet.getLong("REQUEST_DATE"));
            depositAccountRequestTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            depositAccountRequestTO.setDepositAccountTypeId(resultSet.getInt("DEPOSIT_ACCOUNT_TYPE_ID"));
            depositAccountRequestTO.setSupportCheckingAccountId(resultSet.getLong("SUPPORT_CHECKING_ACCOUNT_ID"));
            depositAccountRequestTO.setMoney(resultSet.getLong("MONEY"));
            depositAccountRequestTO.setCheck_flag(resultSet.getLong("CHECK_FLAG"));
            arrayList.add(depositAccountRequestTO);
        }
        return arrayList;
    }

}
