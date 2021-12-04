package model.dao;


import model.to.BankTransactionTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BankTransactionDAO extends DAO {


    public BankTransactionDAO() throws Exception {
        setConnection();
    }

    public BankTransactionDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //===================
    public BankTransactionTO fullInsert(BankTransactionTO bankTransactionTO) throws Exception {


        this.statement = this.connection.prepareStatement("INSERT INTO BANK_TRANSACTION(" +
                "DESCRIPTION," +
                "TITLE," +
                "TRANSACTION_DATE," +
                "CUSTOMER_ID," +
                "SRC_ACCOUNT_ID," +
                "DES_ACCOUNT_ID," +
                "SRC_BANK_CARD_ID," +
                "DES_BANK_CARD_ID) " +
                "VALUES (?,?,?,?,?,?,?,?)");

        this.statement.setString(1, bankTransactionTO.getDescription());
        this.statement.setString(2, bankTransactionTO.getTitle());
        this.statement.setLong(3, bankTransactionTO.getDate());
        this.statement.setLong(4, bankTransactionTO.getCustomerId());
        this.statement.setLong(5, bankTransactionTO.getSourceCheckingAccountId());
        this.statement.setLong(6, bankTransactionTO.getDestinationCheckingAccountId());
        this.statement.setLong(7, bankTransactionTO.getSourceBankCardId());
        this.statement.setLong(8, bankTransactionTO.getDestinationBankCardId());
        this.statement.executeUpdate();

        //get last inserted row id.
        this.statement = this.connection.prepareStatement("SELECT BANK_TRANSACTION_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankTransactionTO.setId(lastInsertedRowId);
        return bankTransactionTO;
    }

    public BankTransactionTO insertCheckingAccount(BankTransactionTO bankTransactionTO) throws Exception {
        return null;
    }

    public BankTransactionTO insertCheckingAccountToCheckingAccount(BankTransactionTO bankTransactionTO) throws Exception {


        this.statement = this.connection.prepareStatement("INSERT INTO BANK_TRANSACTION(" +
                "DESCRIPTION," +
                "TITLE," +
                "TRANSACTION_DATE," +
                "CUSTOMER_ID," +
                "SRC_ACCOUNT_ID," +
                "DES_ACCOUNT_ID," +
                "MONEY) " +
                "VALUES (?,?,?,?,?,?,?)");

        this.statement.setString(1, bankTransactionTO.getDescription());
        this.statement.setString(2, bankTransactionTO.getTitle());
        this.statement.setLong(3, bankTransactionTO.getDate());
        this.statement.setLong(4, bankTransactionTO.getCustomerId());
        this.statement.setLong(5, bankTransactionTO.getSourceCheckingAccountId());
        this.statement.setLong(6, bankTransactionTO.getDestinationCheckingAccountId());
        this.statement.setLong(7, bankTransactionTO.getMoney());
        this.statement.executeUpdate();

        //get last inserted row id.
        this.statement = this.connection.prepareStatement("SELECT BANK_TRANSACTION_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankTransactionTO.setId(lastInsertedRowId);
        return bankTransactionTO;
    }

    public BankTransactionTO insertBankCardToCheckingAccount(BankTransactionTO bankTransactionTO) throws Exception {


        this.statement = this.connection.prepareStatement("INSERT INTO BANK_TRANSACTION(" +
                "DESCRIPTION," +
                "TITLE," +
                "TRANSACTION_DATE," +
                "CUSTOMER_ID," +
                "SRC_ACCOUNT_ID," +
                "SRC_BANK_CARD_ID," +
                "DES_ACCOUNT_ID," +
                "MONEY) " +
                "VALUES (?,?,?,?,?,?,?,?)");

        this.statement.setString(1, bankTransactionTO.getDescription());
        this.statement.setString(2, bankTransactionTO.getTitle());
        this.statement.setLong(3, bankTransactionTO.getDate());
        this.statement.setLong(4, bankTransactionTO.getCustomerId());
        this.statement.setLong(5, bankTransactionTO.getSourceCheckingAccountId());
        this.statement.setLong(6, bankTransactionTO.getSourceBankCardId());
        this.statement.setLong(7, bankTransactionTO.getDestinationCheckingAccountId());
        this.statement.setLong(8, bankTransactionTO.getMoney());
        this.statement.executeUpdate();

        //get last inserted row id.
        this.statement = this.connection.prepareStatement("SELECT BANK_TRANSACTION_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankTransactionTO.setId(lastInsertedRowId);
        return bankTransactionTO;
    }

    public BankTransactionTO insertPayBillByCheckingAccountTransaction(BankTransactionTO bankTransactionTO)throws Exception{
        this.statement = this.connection.prepareStatement("INSERT INTO BANK_TRANSACTION(" +
                "DESCRIPTION," +
                "TITLE," +
                "TRANSACTION_DATE," +
                "CUSTOMER_ID," +
                "SRC_ACCOUNT_ID," +
                "MONEY) " +
                "VALUES (?,?,?,?,?,?)");

        this.statement.setString(1, bankTransactionTO.getDescription());
        this.statement.setString(2, bankTransactionTO.getTitle());
        this.statement.setLong(3, bankTransactionTO.getDate());
        this.statement.setLong(4, bankTransactionTO.getCustomerId());
        this.statement.setLong(5, bankTransactionTO.getSourceCheckingAccountId());
        this.statement.setLong(6, bankTransactionTO.getMoney());
        this.statement.executeUpdate();

        //get last inserted row id.
        this.statement = this.connection.prepareStatement("SELECT BANK_TRANSACTION_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankTransactionTO.setId(lastInsertedRowId);
        return bankTransactionTO;
    }


    public BankTransactionTO insertPayBillByBankCardTransaction(BankTransactionTO bankTransactionTO)throws Exception{
        this.statement = this.connection.prepareStatement("INSERT INTO BANK_TRANSACTION(" +
                "DESCRIPTION," +
                "TITLE," +
                "TRANSACTION_DATE," +
                "CUSTOMER_ID," +
                "SRC_ACCOUNT_ID," +
                "SRC_BANK_CARD_ID,"+
                "MONEY) " +
                "VALUES (?,?,?,?,?,?,?)");

        this.statement.setString(1, bankTransactionTO.getDescription());
        this.statement.setString(2, bankTransactionTO.getTitle());
        this.statement.setLong(3, bankTransactionTO.getDate());
        this.statement.setLong(4, bankTransactionTO.getCustomerId());
        this.statement.setLong(5, bankTransactionTO.getSourceCheckingAccountId());
        this.statement.setLong(6,bankTransactionTO.getSourceBankCardId());
        this.statement.setLong(7, bankTransactionTO.getMoney());
        this.statement.executeUpdate();

        //get last inserted row id.
        this.statement = this.connection.prepareStatement("SELECT BANK_TRANSACTION_SEQ.CURRVAL FROM DUAL");
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        long lastInsertedRowId = resultSet.getLong(1);
        bankTransactionTO.setId(lastInsertedRowId);
        return bankTransactionTO;
    }

    public ArrayList selectAll() throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_TRANSACTION ");
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList arrayList = new ArrayList();
        while (resultSet.next()) {

            BankTransactionTO bankTransactionTO = new BankTransactionTO();
            bankTransactionTO.setId(resultSet.getLong("ID"));
            bankTransactionTO.setTitle(resultSet.getString("TITLE"));
            bankTransactionTO.setDescription(resultSet.getString("DESCRIPTION"));
            bankTransactionTO.setDate(resultSet.getLong("TRANSACTION_DATE"));
            bankTransactionTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankTransactionTO.setSourceCheckingAccountId(resultSet.getLong("SRC_ACCOUNT_ID"));
            bankTransactionTO.setDestinationCheckingAccountId(resultSet.getLong("DES_ACCOUNT_ID"));
            bankTransactionTO.setSourceBankCardId(resultSet.getLong("SRC_BANK_CARD_ID"));
            bankTransactionTO.setDestinationBankCardId(resultSet.getLong("DES_BANK_CARD_ID"));
            arrayList.add(bankTransactionTO);
        }
        return arrayList;
    }

    public BankTransactionTO selectByTransactionId(long id) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_TRANSACTION " +
                "WHERE ID = ?");

        this.statement.setLong(1, id);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setId(resultSet.getLong("ID"));
        bankTransactionTO.setTitle(resultSet.getString("TITLE"));
        bankTransactionTO.setDescription(resultSet.getString("DESCRIPTION"));
        bankTransactionTO.setDate(resultSet.getLong("TRANSACTION_DATE"));
        bankTransactionTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
        bankTransactionTO.setSourceCheckingAccountId(resultSet.getLong("SRC_ACCOUNT_ID"));
        bankTransactionTO.setDestinationCheckingAccountId(resultSet.getLong("DES_ACCOUNT_ID"));
        bankTransactionTO.setSourceBankCardId(resultSet.getLong("SRC_BANK_CARD_ID"));
        bankTransactionTO.setDestinationBankCardId(resultSet.getLong("DES_BANK_CARD_ID"));
        return bankTransactionTO;

    }

    public ArrayList<BankTransactionTO> selectByCheckingAccountId(long checkingAccountId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_TRANSACTION " +
                "WHERE SRC_ACCOUNT_ID = ? or DES_ACCOUNT_ID = ?");
        this.statement.setLong(1, checkingAccountId);
        this.statement.setLong(2, checkingAccountId);
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList<BankTransactionTO> bankTransactionTOArrayList = new ArrayList<BankTransactionTO>();
        while (resultSet.next()) {
            BankTransactionTO bankTransactionTO = new BankTransactionTO();
            bankTransactionTO.setId(resultSet.getLong("ID"));
            bankTransactionTO.setTitle(resultSet.getString("TITLE"));
            bankTransactionTO.setDescription(resultSet.getString("DESCRIPTION"));
            bankTransactionTO.setDate(resultSet.getLong("TRANSACTION_DATE"));
            bankTransactionTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankTransactionTO.setSourceCheckingAccountId(resultSet.getLong("SRC_ACCOUNT_ID"));
            bankTransactionTO.setDestinationCheckingAccountId(resultSet.getLong("DES_ACCOUNT_ID"));
            bankTransactionTO.setSourceBankCardId(resultSet.getLong("SRC_BANK_CARD_ID"));
            bankTransactionTO.setDestinationBankCardId(resultSet.getLong("DES_BANK_CARD_ID"));
            bankTransactionTOArrayList.add(bankTransactionTO);
        }
        return bankTransactionTOArrayList;
    }

    public ArrayList<BankTransactionTO> selectByBankCardId(long bankCardId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_TRANSACTION " +
                "WHERE SRC_BANK_CARD_ID = ? or DES_BANK_CARD_ID = ?");
        this.statement.setLong(1, bankCardId);
        this.statement.setLong(2, bankCardId);
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList<BankTransactionTO> bankTransactionTOArrayList = new ArrayList<BankTransactionTO>();
        while (resultSet.next()) {
            BankTransactionTO bankTransactionTO = new BankTransactionTO();
            bankTransactionTO.setId(resultSet.getLong("ID"));
            bankTransactionTO.setTitle(resultSet.getString("TITLE"));
            bankTransactionTO.setDescription(resultSet.getString("DESCRIPTION"));
            bankTransactionTO.setDate(resultSet.getLong("TRANSACTION_DATE"));
            bankTransactionTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankTransactionTO.setSourceCheckingAccountId(resultSet.getLong("SRC_ACCOUNT_ID"));
            bankTransactionTO.setDestinationCheckingAccountId(resultSet.getLong("DES_ACCOUNT_ID"));
            bankTransactionTO.setSourceBankCardId(resultSet.getLong("SRC_BANK_CARD_ID"));
            bankTransactionTO.setDestinationBankCardId(resultSet.getLong("DES_BANK_CARD_ID"));
            bankTransactionTOArrayList.add(bankTransactionTO);
        }
        return bankTransactionTOArrayList;
    }

    public ArrayList<BankTransactionTO> selectByCustomerId(long customerId) throws Exception {
        this.statement = this.connection.prepareStatement("SELECT * FROM BANK_TRANSACTION " +
                "WHERE CUSTOMER_ID = ? ");
        this.statement.setLong(1, customerId);
        ResultSet resultSet = this.statement.executeQuery();
        ArrayList<BankTransactionTO> bankTransactionTOArrayList = new ArrayList<BankTransactionTO>();
        while (resultSet.next()) {
            BankTransactionTO bankTransactionTO = new BankTransactionTO();
            bankTransactionTO.setId(resultSet.getLong("ID"));
            bankTransactionTO.setTitle(resultSet.getString("TITLE"));
            bankTransactionTO.setDescription(resultSet.getString("DESCRIPTION"));
            bankTransactionTO.setDate(resultSet.getLong("TRANSACTION_DATE"));
            bankTransactionTO.setCustomerId(resultSet.getLong("CUSTOMER_ID"));
            bankTransactionTO.setSourceCheckingAccountId(resultSet.getLong("SRC_ACCOUNT_ID"));
            bankTransactionTO.setDestinationCheckingAccountId(resultSet.getLong("DES_ACCOUNT_ID"));
            bankTransactionTO.setSourceBankCardId(resultSet.getLong("SRC_BANK_CARD_ID"));
            bankTransactionTO.setDestinationBankCardId(resultSet.getLong("DES_BANK_CARD_ID"));
            bankTransactionTOArrayList.add(bankTransactionTO);
        }
        return bankTransactionTOArrayList;
    }

    //select by date
}
