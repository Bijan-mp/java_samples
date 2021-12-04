package model.transactionsobjects;

import model.dao.BankTransactionDAO;
import model.to.BankTransactionTO;
import tools.PersianDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TransactionObject {

    protected Connection connection;
    protected PreparedStatement statement;

    protected long customerId;

    public TransactionObject()throws Exception{
        setConnection();

    }

    public TransactionObject(long customerId)throws Exception{
        this.customerId = customerId;
        setConnection();

    }

    //It need a method that get date as long number

    protected void setConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PARSIAN_BANK", "myjava123");
        this.connection.setAutoCommit(false);

    }

    public BankTransactionTO setPayBillByCheckingAccountTransaction(String title , String description,long sourceCheckingAccountId,long  money)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        bankTransactionTO.setDate(PersianDate.getDateAsNumber());
        bankTransactionTO.setCustomerId(this.customerId);
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setMoney(money);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);
        bankTransactionTO = bankTransactionDAO.insertPayBillByCheckingAccountTransaction(bankTransactionTO);
        return bankTransactionTO;
    }

    public BankTransactionTO setPayBillByBankCardTransaction(String title , String description,long sourceCheckingAccountId,long bankCardId,long  money)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setCustomerId(this.customerId);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);

        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        bankTransactionTO.setDate(PersianDate.getDateAsNumber());
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setSourceBankCardId(bankCardId);
        bankTransactionTO.setMoney(money);
        bankTransactionTO = bankTransactionDAO.insertPayBillByBankCardTransaction(bankTransactionTO);
        return bankTransactionTO;
    }


    public BankTransactionTO setCheckingAccountTransaction(String title , String description,long sourceCheckingAccountId,long destinationCheckingAccount,long money)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        bankTransactionTO.setDate(PersianDate.getDateAsNumber());
        bankTransactionTO.setCustomerId(this.customerId);
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setDestinationCheckingAccountId(destinationCheckingAccount);
        bankTransactionTO.setMoney(money);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);
        bankTransactionTO = bankTransactionDAO.insertCheckingAccountToCheckingAccount(bankTransactionTO);
        return bankTransactionTO;
    }

    public BankTransactionTO setBankCardTransaction(String title , String description,long sourceCheckingAccountId,long sourceBankCardId)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        bankTransactionTO.setDate(PersianDate.getDateAsNumber());
        bankTransactionTO.setCustomerId(this.customerId);
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setSourceBankCardId(sourceBankCardId);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);
     //   bankTransactionTO = bankTransactionDAO.insert(bankTransactionTO);
        return bankTransactionTO;
    }

    public BankTransactionTO setBankCardTransaction(String title ,
                                                    String description,
                                                    long sourceCheckingAccountId,
                                                    long sourceBankCardId,
                                                    long destinationCheckingAccount ,
                                                    long money)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        bankTransactionTO.setDate(PersianDate.getDateAsNumber());
        bankTransactionTO.setCustomerId(this.customerId);
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setSourceBankCardId(sourceBankCardId);
        bankTransactionTO.setDestinationCheckingAccountId(destinationCheckingAccount);
        bankTransactionTO.setMoney(money);

        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);
        bankTransactionTO = bankTransactionDAO.insertBankCardToCheckingAccount(bankTransactionTO);
        return bankTransactionTO;
    }

    public BankTransactionTO setBankCardTransaction(String title , String description,long sourceCheckingAccountId,long sourceBankCardId,
                                                    long destinationCheckingAccount,long destinationBankCard,long money)throws Exception{
        BankTransactionTO bankTransactionTO = new BankTransactionTO();
        bankTransactionTO.setTitle(title);
        bankTransactionTO.setDescription(description);
        // bankTransactionTO.setDate();
        bankTransactionTO.setCustomerId(this.customerId);
        bankTransactionTO.setSourceCheckingAccountId(sourceCheckingAccountId);
        bankTransactionTO.setSourceBankCardId(sourceBankCardId);
        bankTransactionTO.setDestinationCheckingAccountId(destinationCheckingAccount);
        bankTransactionTO.setDestinationBankCardId(destinationBankCard);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO(this.connection,this.statement);
      //  bankTransactionTO = bankTransactionDAO.insert(bankTransactionTO);
        return bankTransactionTO;
    }

    public void commit() throws Exception {
        this.connection.commit();
        //statement.close();
        //connection.close();
    }

}
