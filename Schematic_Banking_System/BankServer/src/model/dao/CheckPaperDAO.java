package model.dao;

import model.to.CheckPaperTO;
import model.to.CheckPaperTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CheckPaperDAO extends DAO {

    public CheckPaperDAO() throws Exception {
        setConnection();
    }

    public CheckPaperDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //=====================
    public void insert(CheckPaperTO checkPaperTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO CHECK_PAPER " +
                "(BANK_CHECK_ID," +
                "CHECK_PAPER_ID," +
                "MONEY," +
                "RECEIVE_DATE," +
                "PASSED_FLAG," +
                "RETURNED_FLAG," +
                "FOR_PERSON," +
                "RECEIVER_NAME," +
                "RECEIVER_FAMILY," +
                "RECEIVER_NATIONAL_ID," +
                "BARRED_FLAG," +
                "REAL_RECEIVE_DATE) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
        this.statement.setLong(1, checkPaperTO.getBankCheckId());
        this.statement.setInt(2, checkPaperTO.getCheckPaperId());
        this.statement.setInt(3, checkPaperTO.getMoney());
        this.statement.setLong(4, checkPaperTO.getReceiveDate());
        this.statement.setInt(5, checkPaperTO.getPassedFlag());
        this.statement.setInt(6, checkPaperTO.getPassedFlag());
        this.statement.setString(7, checkPaperTO.getForPerson());
        this.statement.setString(8, checkPaperTO.getReceiverName());
        this.statement.setString(9, checkPaperTO.getReceiverFamily());
        this.statement.setLong(10, checkPaperTO.getReceiverNationalId());
        this.statement.setInt(11, checkPaperTO.getBarredFlag());
        this.statement.setLong(12, checkPaperTO.getRealReceiveDate());


        this.statement.executeUpdate();

    }

    public ArrayList selectByBankCheckId(long bankCheckId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM CHECK_PAPER " +
                "WHERE BANK_CHECK_ID = ?");
        this.statement.setLong(1, bankCheckId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            CheckPaperTO checkPaperTO = new CheckPaperTO();
            checkPaperTO.setBankCheckId(resultSet.getLong("BANK_CHECK_ID"));
            checkPaperTO.setCheckPaperId(resultSet.getInt("CHECK_PAPER_ID"));
            checkPaperTO.setMoney(resultSet.getInt("MONEY"));
            checkPaperTO.setReceiveDate(resultSet.getLong("RECEIVE_DATE"));
            checkPaperTO.setPassedFlag(resultSet.getInt("PASSED_FLAG"));
            checkPaperTO.setReturnFlag(resultSet.getInt("RETURNED_FLAG"));
            checkPaperTO.setForPerson(resultSet.getString("FOR_PERSON"));
            checkPaperTO.setReceiverName(resultSet.getString("RECEIVER_NAME"));
            checkPaperTO.setReceiverFamily(resultSet.getString("RECEIVER_FAMILY"));
            checkPaperTO.setReceiverNationalId(resultSet.getLong("RECEIVER_NATIONAL_ID"));
            checkPaperTO.setBarredFlag(resultSet.getInt("BARRED_FLAG"));
            checkPaperTO.setRealReceiveDate(resultSet.getLong("REAL_RECEIVE_DATE"));
            arrayList.add(checkPaperTO);
        }

        return arrayList;
    }

    public CheckPaperTO selectByBankCheckIdAndCheckPaperId(long bankCheckId, int checkPaperId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM CHECK_PAPER " +
                "WHERE BANK_CHECK_ID = ? AND CHECK_PAPER_ID = ?");
        this.statement.setLong(1, bankCheckId);
        this.statement.setInt(2, checkPaperId);
        ResultSet resultSet = this.statement.executeQuery();

        resultSet.next();
        CheckPaperTO checkPaperTO = new CheckPaperTO();
        checkPaperTO.setBankCheckId(resultSet.getLong("BANK_CHECK_ID"));
        checkPaperTO.setCheckPaperId(resultSet.getInt("CHECK_PAPER_ID"));
        checkPaperTO.setMoney(resultSet.getInt("MONEY"));
        checkPaperTO.setReceiveDate(resultSet.getLong("RECEIVE_DATE"));
        checkPaperTO.setPassedFlag(resultSet.getInt("PASSED_FLAG"));
        checkPaperTO.setReturnFlag(resultSet.getInt("RETURNED_FLAG"));
        checkPaperTO.setForPerson(resultSet.getString("FOR_PERSON"));
        checkPaperTO.setReceiverName(resultSet.getString("RECEIVER_NAME"));
        checkPaperTO.setReceiverFamily(resultSet.getString("RECEIVER_FAMILY"));
        checkPaperTO.setReceiverNationalId(resultSet.getLong("RECEIVER_NATIONAL_ID"));
        checkPaperTO.setBarredFlag(resultSet.getInt("BARRED_FLAG"));
        checkPaperTO.setRealReceiveDate(resultSet.getLong("REAL_RECEIVE_DATE"));

        return checkPaperTO;
    }


    public ArrayList selectNotPassedCheckPaper(long bankCheckId)throws Exception{
        this.statement = this.connection.prepareStatement("SELECT * FROM CHECK_PAPER " +
                "WHERE BANK_CHECK_ID = ? AND PASSED_FLAG <> 1");
        this.statement.setLong(1, bankCheckId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            CheckPaperTO checkPaperTO = new CheckPaperTO();
            checkPaperTO.setBankCheckId(resultSet.getLong("BANK_CHECK_ID"));
            checkPaperTO.setCheckPaperId(resultSet.getInt("CHECK_PAPER_ID"));
            checkPaperTO.setMoney(resultSet.getInt("MONEY"));
            checkPaperTO.setReceiveDate(resultSet.getLong("RECEIVE_DATE"));
            checkPaperTO.setPassedFlag(resultSet.getInt("PASSED_FLAG"));
            checkPaperTO.setReturnFlag(resultSet.getInt("RETURNED_FLAG"));
            checkPaperTO.setForPerson(resultSet.getString("FOR_PERSON"));
            checkPaperTO.setReceiverName(resultSet.getString("RECEIVER_NAME"));
            checkPaperTO.setReceiverFamily(resultSet.getString("RECEIVER_FAMILY"));
            checkPaperTO.setReceiverNationalId(resultSet.getLong("RECEIVER_NATIONAL_ID"));
            checkPaperTO.setBarredFlag(resultSet.getInt("BARRED_FLAG"));
            checkPaperTO.setRealReceiveDate(resultSet.getLong("REAL_RECEIVE_DATE"));
            arrayList.add(checkPaperTO);
        }

        return arrayList;
    }

    public CheckPaperTO updateBarredFlag(long bankCheckId,int checkPaperId,int barredFlag) throws Exception {

        this.statement = this.connection.prepareStatement("UPDATE CHECK_PAPER " +
                "SET BARRED_FLAG = ? " +
                "WHERE BANK_CHECK_ID = ? AND CHECK_PAPER_ID = ?");
        this.statement.setInt(1,barredFlag);
        this.statement.setLong(2,bankCheckId);
        this.statement.setInt(3,checkPaperId);
        this.statement.executeUpdate();

        return selectByBankCheckIdAndCheckPaperId(bankCheckId,checkPaperId);


    }
}
