package model.dao;

import model.to.BankCardTO;
import model.to.BranchTO;
import model.to.BranchTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BranchDAO extends DAO {


    public BranchDAO() throws Exception {
        setConnection();
    }

    public BranchDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public void insert(BranchTO branchTO) throws Exception {
        this.statement = this.connection.prepareStatement("INSERT INTO BRANCH " +
                "(CITY,ADDRESS,BRANCH_NAME) " +
                "VALUES (?,?,?)");
        this.statement.setString(1, branchTO.getCity());
        this.statement.setString(2, branchTO.getAddress());
        this.statement.setString(3, branchTO.getBranchName());
        this.statement.executeUpdate();
    }

    public ArrayList selectAll() throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BRANCH");
        ResultSet resultSet = this.statement.executeQuery();

        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {

            BranchTO branchTO = new BranchTO();
            branchTO.setId(resultSet.getInt("ID"));
            branchTO.setBranchName(resultSet.getString("BRANCH_NAME"));
            branchTO.setAddress(resultSet.getString("ADDRESS"));
            branchTO.setCity(resultSet.getString("CITY"));

            arrayList.add(branchTO);
        }
        return arrayList;
    }

    public BranchTO selectById(int id) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BRANCH " +
                "WHERE ID = ?");
        this.statement.setInt(1, id);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        BranchTO branchTO = new BranchTO();
        branchTO.setId(resultSet.getInt("ID"));
        branchTO.setBranchName(resultSet.getString("BRANCH_NAME"));
        branchTO.setAddress(resultSet.getString("ADDRESS"));
        branchTO.setCity(resultSet.getString("CITY"));

        return branchTO;
    }

    public BranchTO update(int id,BranchTO newBranchTO) throws Exception {

        this.statement=this.connection.prepareStatement("UPDATE BRANCH " +
                "SET BRANCH_NAME = ? , ADDRESS = ? " +
                "WHERE ID = ?");
        this.statement.setString(1,newBranchTO.getBranchName());
        this.statement.setString(2,newBranchTO.getAddress());
        this.statement.setInt(3,id);
        this.statement.executeUpdate();

        BranchTO branchTO = selectById(id);

        return branchTO;
    }
}
