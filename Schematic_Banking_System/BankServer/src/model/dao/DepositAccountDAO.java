package model.dao;

import model.to.DepositAccountTO;
import model.to.DepositAccountTO;
import model.to.DepositAccountTypeTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DepositAccountDAO extends DAO {

    public DepositAccountDAO() throws Exception {
        setConnection();
    }

    public DepositAccountDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //===============
    public void insert(DepositAccountTO depositAccountTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO DEPOSIT_ACCOUNT " +
                "(MONEY," +
                "OPENING_DATE," +
                "DEPOSIT_ACCOUNT_TYPE_ID," +
                "CHECKING_ACCOUNT_ID," +
                "CUSTOMER_ID," +
                "BRANCH_ID) " +
                "VALUES (?,?,?,?,?,?)");
        this.statement.setLong(1, depositAccountTO.getMoney());
        this.statement.setLong(2, depositAccountTO.getOpeningDate());
        this.statement.setInt(3, depositAccountTO.getDepositAccountTypeTO().getId());
        this.statement.setLong(4, depositAccountTO.getCheckingAccountId());
        this.statement.setLong(5, depositAccountTO.getCustomerId());
        this.statement.setInt(6, depositAccountTO.getBranchId());
        this.statement.executeUpdate();

    }

    public ArrayList selectByCustomerId(long customerId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT " +
                "DEPOSIT_ACCOUNT.ID DA_ID," +
                "DEPOSIT_ACCOUNT.MONEY A_MONEY, " +
                "DEPOSIT_ACCOUNT.OPENING_DATE A_OPENING_DATE," +
                "DEPOSIT_ACCOUNT.CUSTOMER_ID A_CUSTOMER_ID," +
                "DEPOSIT_ACCOUNT.DEPOSIT_ACCOUNT_TYPE_ID DAT_FK_ID," +
                "DEPOSIT_ACCOUNT.BRANCH_ID A_BRANCH_ID," +
                "DEPOSIT_ACCOUNT.CHECKING_ACCOUNT_ID A_CHECKING_ACCOUNT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.ID DAT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.NAME T_NAME," +
                "DEPOSIT_ACCOUNT_TYPE.DURATION T_DURATION," +
                "DEPOSIT_ACCOUNT_TYPE.ANNUAL_INTEREST_PERCENT T_ANNUAL_INTEREST_PERCENT" +
                " FROM DEPOSIT_ACCOUNT,DEPOSIT_ACCOUNT_TYPE " +
                " WHERE DEPOSIT_ACCOUNT.CUSTOMER_ID = ? AND DEPOSIT_ACCOUNT.DEPOSIT_ACCOUNT_TYPE_ID = DEPOSIT_ACCOUNT_TYPE.ID " +
                " ORDER BY DEPOSIT_ACCOUNT.ID");
        this.statement.setLong(1, customerId);
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList<DepositAccountTO> arrayList = new ArrayList();
        while (resultSet.next()) {
            DepositAccountTO depositAccountTO = new DepositAccountTO();
            depositAccountTO.setId(resultSet.getLong("DA_ID"));
            depositAccountTO.setMoney(resultSet.getLong("A_MONEY"));
            depositAccountTO.setOpeningDate(resultSet.getLong("A_OPENING_DATE"));
            depositAccountTO.setCustomerId(resultSet.getLong("A_CUSTOMER_ID"));
            depositAccountTO.setBranchId(resultSet.getInt("A_BRANCH_ID"));
            depositAccountTO.setCheckingAccountId(resultSet.getLong("A_CHECKING_ACCOUNT_ID"));
            DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
            depositAccountTypeTO.setId(resultSet.getInt("DAT_ID"));
            depositAccountTypeTO.setName(resultSet.getString("T_NAME"));
            depositAccountTypeTO.setDuration(resultSet.getInt("T_DURATION"));
            depositAccountTypeTO.setAnnualInterestPercent(resultSet.getFloat("T_ANNUAL_INTEREST_PERCENT"));
            depositAccountTO.setDepositAccountTypeTO(depositAccountTypeTO);

            arrayList.add(depositAccountTO);
        }
        return arrayList;
    }

    public DepositAccountTO selectByAccountId(long depositAccountId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT " +
                "DEPOSIT_ACCOUNT.ID DA_ID," +
                "DEPOSIT_ACCOUNT.MONEY A_MONEY, " +
                "DEPOSIT_ACCOUNT.OPENING_DATE A_OPENING_DATE," +
                "DEPOSIT_ACCOUNT.CUSTOMER_ID A_CUSTOMER_ID," +
                "DEPOSIT_ACCOUNT.DEPOSIT_ACCOUNT_TYPE_ID DAT_FK_ID," +
                "DEPOSIT_ACCOUNT.BRANCH_ID A_BRANCH_ID," +
                "DEPOSIT_ACCOUNT.CHECKING_ACCOUNT_ID A_CHECKING_ACCOUNT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.ID DAT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.NAME T_NAME," +
                "DEPOSIT_ACCOUNT_TYPE.DURATION T_DURATION," +
                "DEPOSIT_ACCOUNT_TYPE.ANNUAL_INTEREST_PERCENT T_ANNUAL_INTEREST_PERCENT" +
                " FROM DEPOSIT_ACCOUNT,DEPOSIT_ACCOUNT_TYPE " +
                "WHERE ID = ? AND DAT_FK_ID = DAT_ID");
        this.statement.setLong(1, depositAccountId);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        DepositAccountTO depositAccountTO = new DepositAccountTO();
        depositAccountTO.setId(resultSet.getLong("DA_ID"));
        depositAccountTO.setMoney(resultSet.getLong("A_MONEY"));
        depositAccountTO.setOpeningDate(resultSet.getLong("A_OPENING_DATE"));
        depositAccountTO.setCustomerId(resultSet.getLong("A_CUSTOMER_ID"));
        // depositAccountTO.setDepositAccountTypeId(resultSet.getInt("DAT_FK_ID"));
        depositAccountTO.setBranchId(resultSet.getInt("A_BRANCH_ID"));
        depositAccountTO.setCheckingAccountId(resultSet.getLong("A_CHECKING_ACCOUNT_ID"));

        DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
        depositAccountTypeTO.setId(resultSet.getInt("DAT_ID"));
        depositAccountTypeTO.setName(resultSet.getString("T_NAME"));
        depositAccountTypeTO.setDuration(resultSet.getInt("T_DURATION"));
        depositAccountTypeTO.setAnnualInterestPercent(resultSet.getFloat("T_ANNUAL_INTEREST_PERCENT"));
        depositAccountTO.setDepositAccountTypeTO(depositAccountTypeTO);

        return depositAccountTO;
    }

    public DepositAccountTO selectByCustomerIdAndAccountTypeId(long customerId, int depositAccountType) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT " +
                "DEPOSIT_ACCOUNT.ID DA_ID," +
                "DEPOSIT_ACCOUNT.MONEY A_MONEY, " +
                "DEPOSIT_ACCOUNT.OPENING_DATE A_OPENING_DATE," +
                "DEPOSIT_ACCOUNT.CUSTOMER_ID A_CUSTOMER_ID," +
                "DEPOSIT_ACCOUNT.DEPOSIT_ACCOUNT_TYPE_ID DAT_FK_ID," +
                "DEPOSIT_ACCOUNT.BRANCH_ID A_BRANCH_ID," +
                "DEPOSIT_ACCOUNT.CHECKING_ACCOUNT_ID A_CHECKING_ACCOUNT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.ID DAT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.NAME T_NAME," +
                "DEPOSIT_ACCOUNT_TYPE.DURATION T_DURATION," +
                "DEPOSIT_ACCOUNT_TYPE.ANNUAL_INTEREST_PERCENT T_ANNUAL_INTEREST_PERCENT" +
                " FROM DEPOSIT_ACCOUNT,DEPOSIT_ACCOUNT_TYPE " +
                "WHERE CUSTOMER_ID = ? AND DEPOSIT_ACCOUNT_TYPE_ID = ? AND DAT_FK_ID = DAT_ID");
        this.statement.setLong(1, customerId);
        this.statement.setInt(2, depositAccountType);

        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        DepositAccountTO depositAccountTO = new DepositAccountTO();
        depositAccountTO.setId(resultSet.getLong("DA_ID"));
        depositAccountTO.setMoney(resultSet.getLong("A_MONEY"));
        depositAccountTO.setOpeningDate(resultSet.getLong("A_OPENING_DATE"));
        depositAccountTO.setCustomerId(resultSet.getLong("A_CUSTOMER_ID"));
        // depositAccountTO.setDepositAccountTypeId(resultSet.getInt("DAT_FK_ID"));
        depositAccountTO.setBranchId(resultSet.getInt("A_BRANCH_ID"));
        depositAccountTO.setCheckingAccountId(resultSet.getLong("A_CHECKING_ACCOUNT_ID"));

        DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
        depositAccountTypeTO.setId(resultSet.getInt("DAT_ID"));
        depositAccountTypeTO.setName(resultSet.getString("T_NAME"));
        depositAccountTypeTO.setDuration(resultSet.getInt("T_DURATION"));
        depositAccountTypeTO.setAnnualInterestPercent(resultSet.getFloat("T_ANNUAL_INTEREST_PERCENT"));
        depositAccountTO.setDepositAccountTypeTO(depositAccountTypeTO);

        return depositAccountTO;
    }

    public ArrayList selectByCustomerIdAndAccountId(long customerId, long accountId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT " +
                "DEPOSIT_ACCOUNT.ID DA_ID," +
                "DEPOSIT_ACCOUNT.MONEY A_MONEY, " +
                "DEPOSIT_ACCOUNT.OPENING_DATE A_OPENING_DATE," +
                "DEPOSIT_ACCOUNT.CUSTOMER_ID A_CUSTOMER_ID," +
                "DEPOSIT_ACCOUNT.DEPOSIT_ACCOUNT_TYPE_ID DAT_FK_ID," +
                "DEPOSIT_ACCOUNT.BRANCH_ID A_BRANCH_ID," +
                "DEPOSIT_ACCOUNT.CHECKING_ACCOUNT_ID A_CHECKING_ACCOUNT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.ID DAT_ID," +
                "DEPOSIT_ACCOUNT_TYPE.NAME T_NAME," +
                "DEPOSIT_ACCOUNT_TYPE.DURATION T_DURATION," +
                "DEPOSIT_ACCOUNT_TYPE.ANNUAL_INTEREST_PERCENT T_ANNUAL_INTEREST_PERCENT" +
                " FROM DEPOSIT_ACCOUNT,DEPOSIT_ACCOUNT_TYPE " +
                "WHERE CUSTOMER_ID = ? AND ID = ? AND DAT_FK_ID = DAT_ID");
        this.statement.setLong(1, customerId);
        this.statement.setLong(2, accountId);

        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {

            DepositAccountTO depositAccountTO = new DepositAccountTO();
            depositAccountTO.setId(resultSet.getLong("DA_ID"));
            depositAccountTO.setMoney(resultSet.getLong("A_MONEY"));
            depositAccountTO.setOpeningDate(resultSet.getLong("A_OPENING_DATE"));
            depositAccountTO.setCustomerId(resultSet.getLong("A_CUSTOMER_ID"));
            // depositAccountTO.setDepositAccountTypeId(resultSet.getInt("DAT_FK_ID"));
            depositAccountTO.setBranchId(resultSet.getInt("A_BRANCH_ID"));
            depositAccountTO.setCheckingAccountId(resultSet.getLong("A_CHECKING_ACCOUNT_ID"));

            DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
            depositAccountTypeTO.setId(resultSet.getInt("DAT_ID"));
            depositAccountTypeTO.setName(resultSet.getString("T_NAME"));
            depositAccountTypeTO.setDuration(resultSet.getInt("T_DURATION"));
            depositAccountTypeTO.setAnnualInterestPercent(resultSet.getFloat("T_ANNUAL_INTEREST_PERCENT"));
            depositAccountTO.setDepositAccountTypeTO(depositAccountTypeTO);

            arrayList.add(depositAccountTO);
        }

        return arrayList;
    }

    public DepositAccountTO updateMoney(Long id, Long money) throws Exception {

        this.statement = this.connection.prepareStatement("UPDATE DEPOSIT_ACCOUNT " +
                "SET MONEY = ? " +
                "WHERE ID = ?");
        this.statement.setLong(1, money);
        this.statement.setLong(2, id);
        this.statement.executeUpdate();

        return  selectByAccountId(id);
    }

    public DepositAccountTO updateDepositAccountType(Long id, int depositAccountTypeId) throws Exception {

        this.statement = this.connection.prepareStatement("UPDATE DEPOSIT_ACCOUNT " +
                "SET DEPOSIT_ACCOUNT_TYPE_ID = ? " +
                "WHERE ID = ?");
        this.statement.setInt(1, depositAccountTypeId);
        this.statement.setLong(2, id);
        this.statement.executeUpdate();

        return selectByAccountId(id);
    }
}
