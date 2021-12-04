package model.dao;

import model.to.DepositAccountTypeTO;
import model.to.DepositAccountTypeTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DepositAccountTypeDAO extends DAO {

    public DepositAccountTypeDAO() throws Exception {
        setConnection();
    }

    public DepositAccountTypeDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public void insert(DepositAccountTypeTO depositAccountTypeTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO DEPOSIT_ACCOUNT_TYPE " +
                "(NAME,DURATION,ANNUAL_INTEREST_PERCENT) " +
                "VALUES (?,?,?)");
        this.statement.setString(1, depositAccountTypeTO.getName());
        this.statement.setInt(2, depositAccountTypeTO.getDuration());
        this.statement.setFloat(3, depositAccountTypeTO.getAnnualInterestPercent());
        this.statement.executeUpdate();

    }

    public ArrayList selectAll() throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM DEPOSIT_ACCOUNT_TYPE");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {
            DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
            depositAccountTypeTO.setId(resultSet.getInt("ID"));
            depositAccountTypeTO.setName(resultSet.getString("NAME"));
            depositAccountTypeTO.setDuration(resultSet.getInt("DURATION"));
            depositAccountTypeTO.setAnnualInterestPercent(resultSet.getInt("ANNUAL_INTEREST_PERCENT"));
            arrayList.add(depositAccountTypeTO);
        }

        return arrayList;
    }

    public DepositAccountTypeTO selectByID(int id) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM DEPOSIT_ACCOUNT_TYPE " +
                "WHERE ID = ?");
        this.statement.setInt(1, id);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        DepositAccountTypeTO depositAccountTypeTO = new DepositAccountTypeTO();
        depositAccountTypeTO.setId(resultSet.getInt("ID"));
        depositAccountTypeTO.setName(resultSet.getString("NAME"));
        depositAccountTypeTO.setDuration(resultSet.getInt("DURATION"));
        depositAccountTypeTO.setAnnualInterestPercent(resultSet.getInt("ANNUAL_INTEREST_PERCENT"));
        return depositAccountTypeTO;
    }


    public DepositAccountTypeTO update(int id, DepositAccountTypeTO newDepositAccountTypeTO) throws Exception {
        this.statement = this.connection.prepareStatement("UPDATE DEPOSIT_ACCOUNT_TYPE " +
                "SET NAME = ? , DURATION = ? , ANNUAL_INTEREST_PERCENT = ? " +
                "WHERE ID = ");
        this.statement.setString(1,newDepositAccountTypeTO.getName());
        this.statement.setInt(2,newDepositAccountTypeTO.getDuration());
        this.statement.setFloat(3,newDepositAccountTypeTO.getAnnualInterestPercent());
        this.statement.executeUpdate();
        return selectByID(id);
    }
}
