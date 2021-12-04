package model.bl;


import model.to.*;
import tools.Message;

import java.rmi.Remote;
import java.util.ArrayList;

public abstract interface CustomerServerInterface extends Remote {


    //Checking-account to checking-account money transfer
    public BankTransactionTO checkingAccountMoneyTransfer(String loggedInCode, long fromAccountId, long toAccountId, long money) throws Exception;

    //BankCard to checking account money transfer . (Note. Doesn't need to loggedInCode because bank-card has password.)
    public BankTransactionTO bankCardToCheckingAccountTransfer(BankCardTO bankCardTO, long toAccountId, long money) throws Exception;

    public BankTransactionTO bankCardToBankCardTransfer(BankCardTO fromBankCardTO, Long toBankCardId, long money) throws Exception;

    public ArrayList<BankTransactionTO> getCheckingAccountTransactionList(String loggedInId, long checkingAccountId) throws Exception;

    public ArrayList<BankTransactionTO> getBankCardTransactionList(String loggedInId, long bankCardId) throws Exception;

    public ArrayList<BankTransactionTO> getCustomerTransactionList(String loggedInId, long customerId) throws Exception;

    public BankTransactionTO getTransactionDetails(String loggedInId,long transactionId)throws Exception;

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public void blockBankCard(BankCardTO bankCardTO) throws Exception;

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public void unblockBankCard(BankCardTO bankCardTO) throws Exception;

    public CustomerTO getBankCardOwner(long bankCardId) throws Exception;

    public Boolean checkBankCard(BankCardTO bankCardTO) throws Exception;

    public long getBankCardMoney(BankCardTO bankCardTO)throws Exception;


    public Boolean isBankCheckRequestPossible(String loggedInCode, long checkingAccountId) throws Exception;

    public BankCheckRequestTO requestBankCheck(String loggedInCode, long checkingAccountId) throws Exception;

    public  ArrayList<CheckPaperTO> getBankCheckReport(String loggedInCode, long bankCheckId) throws Exception;

    public Boolean blockCheckPaper(String loggedInCode,long bankCheckId,int checkPaperId) throws Exception;

    public Boolean unBlockCheckPaper(String loggedInCode,long bankCheckId,int checkPaperId) throws Exception;

    public Boolean existBankCheck(String loggedInCode, long bankCheckId) throws Exception;

    public Boolean existCheckPaper(String loggedInCode, long bankCheckId, int checkPaperId) throws Exception;


    //INCOMPLETE
    public Message requestAccount(String loggedInCode, CustomerTO customerTO) throws Exception;

    public CheckingAccountTO getCheckingAccountDetails(String loggedInCode, Long accountId) throws Exception;

    public Long getCheckingAccountCustomerId(long checkingAccountId) throws Exception;

    //Check existence of deposit account id. (Note. loggedInCode isn't necessary.)
    // [may this method isn't necessary.]
    public Boolean existDepositAccountId(long accountId) throws Exception;

    //Check existence of checking account id. (Note. loggedInCode isn't necessary.)
    // [may this method isn't necessary.]
    public Boolean existCheckingAccountId(long accountId) throws Exception;

    public Boolean isCurrentUserCheckingAccount(String loggedInCode, long accountId) throws Exception;

    public Boolean isCurrentUserDepositAccount(String loggedInCode, long accountId) throws Exception;

    public ArrayList viewAllCheckingAccounts(String loggedInCode) throws Exception;

    public ArrayList viewAllDepositAccounts(String loggedInCode) throws Exception;

    public CheckingAccountRequestTO requestCheckingAccount(String loggedInCode, CheckingAccountRequestTO checkingAccountRequestTO) throws Exception;

    public DepositAccountRequestTO requestDepositAccount(String loggedInCode, DepositAccountRequestTO depositAccountRequestTO) throws Exception;

    public CustomerTO getCheckingAccountOwner(Long checkingAccountId) throws Exception;


    public BillTO getBillDetails(Long id, Long payId) throws Exception;

    public BankTransactionTO payBillByCheckingAccount(String loggedInCode, BillTO billTO, long checkingAccount) throws Exception;

    public BankTransactionTO payBillByBankCard(BillTO billTO, BankCardTO bankCardTO) throws Exception;

    public Boolean isBillPayed(BillTO billTO) throws Exception;


    public String login(String userName, String passWord) throws Exception;

    public String isLoggedIn(String loggedInId) throws Exception;

    public void logout(String loggedInCode) throws Exception;

    public String changePassword(String loggedInCode, String password, String newPassword) throws Exception;


}
