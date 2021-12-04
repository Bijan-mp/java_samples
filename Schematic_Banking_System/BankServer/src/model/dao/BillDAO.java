package model.dao;

import model.to.BillTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BillDAO extends DAO {

    public BillDAO() throws Exception {

        setConnection();

    }

    public BillDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //==================
    public void insert(BillTO billTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO BILL" +
                "(PAY_ID,PRICE,TYPE,PAYED_FLAG) " +
                "VALUES (?,?,?,?)");
        this.statement.setLong(1, billTO.getPayId());
        this.statement.setLong(2, billTO.getPrice());
        this.statement.setString(3, billTO.getType());
        this.statement.setInt(4, billTO.getPayedFlag());
        this.statement.executeUpdate();

    }

    public BillTO select(long id, long payId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BILL " +
                "WHERE ID = ? AND PAY_ID = ?");
        this.statement.setLong(1, id);
        this.statement.setLong(2, payId);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        BillTO billTO = new BillTO();
        billTO.setId(resultSet.getLong("ID"));
        billTO.setPayId(resultSet.getLong("PAY_ID"));
        billTO.setPrice(resultSet.getLong("PRICE"));
        billTO.setType(resultSet.getString("TYPE"));
        billTO.setPayedFlag(resultSet.getInt("PAYED_FLAG"));
        return billTO;
    }

    public BillTO update(BillTO oldBillTO, int payedFlag) throws Exception {

        this.statement = this.connection.prepareStatement("UPDATE BILL " +
                "SET PAYED_FLAG = ?" +
                "WHERE ID = ? AND PAY_ID = ?");
        this.statement.setInt(1, payedFlag);
        this.statement.setLong(2, oldBillTO.getId());
        this.statement.setLong(3, oldBillTO.getPayId());
        this.statement.executeUpdate();

        this.statement = this.connection.prepareStatement("SELECT * FROM BILL WHERE ID = ? AND PAY_ID = ?");
        this.statement.setLong(1, oldBillTO.getId());
        this.statement.setLong(2, oldBillTO.getPayId());
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        BillTO billTO = new BillTO();
        billTO.setId(resultSet.getLong("ID"));
        billTO.setPayId(resultSet.getLong("PAY_ID"));
        billTO.setType(resultSet.getString("TYPE"));
        billTO.setPrice(resultSet.getInt("PRICE"));
        billTO.setPayedFlag(resultSet.getInt("PAYED_FLAG"));

        return billTO;
    }
}
