package model.bl;

import model.dao.*;
import model.to.*;
import model.transactionsobjects.MoneyTransferTransaction;
import tools.Encryption;
import tools.Message;
import tools.PersianDate;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class CustomerServer extends UnicastRemoteObject implements CustomerServerInterface {

    private  Map loggedInUsers;
    private  Map loggedInCustomersId;
    private  Map userActivityTime;
    private Timer checkLoginTimer;

    public CustomerServer() throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        Naming.rebind("CustomerServer", this);
        System.out.println("----------------------------\n" +
                "CustomerServer is running on 1099 port \n" +
                "---------------------------");

        loggedInUsers = new HashMap();
        loggedInCustomersId = new HashMap();
    }


    public BankTransactionTO checkingAccountMoneyTransfer(String loggedInCode, long fromAccountId, long toAccountId, long money) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        MoneyTransferTransaction moneyTransferTransaction = new MoneyTransferTransaction(getLoggedInCustomerID(loggedInCode));
        return moneyTransferTransaction.checkingAccountToCheckingAccount(fromAccountId, toAccountId, money);
    }

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public BankTransactionTO bankCardToCheckingAccountTransfer(BankCardTO bankCardTO, long toAccountId, long money) throws Exception {
        MoneyTransferTransaction moneyTransferTransaction = new MoneyTransferTransaction();
        return moneyTransferTransaction.bankCardToCheckingAccount(bankCardTO, toAccountId, money);
    }

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public BankTransactionTO bankCardToBankCardTransfer(BankCardTO fromBankCardTO, Long toBankCardId, long money) throws Exception {
        MoneyTransferTransaction moneyTransferTransaction = new MoneyTransferTransaction();
        return moneyTransferTransaction.bankCardToBankCard(fromBankCardTO, toBankCardId, money);
    }

    public ArrayList<BankTransactionTO> getCheckingAccountTransactionList(String loggedInId, long checkingAccountId) throws Exception {
        if (getLoggedInCustomerID(loggedInId) == null) {
            return null;
        }
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        ArrayList<BankTransactionTO> checkingAccountTransactionList = bankTransactionDAO.selectByCheckingAccountId(checkingAccountId);
        return checkingAccountTransactionList;
    }

    public ArrayList<BankTransactionTO> getBankCardTransactionList(String loggedInId, long bankCardId) throws Exception {
        if (getLoggedInCustomerID(loggedInId) == null) {
            return null;
        }
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        ArrayList<BankTransactionTO> bankCardTransactionList = bankTransactionDAO.selectByBankCardId(bankCardId);
        return bankCardTransactionList;
    }

    public ArrayList<BankTransactionTO> getCustomerTransactionList(String loggedInId, long customerId) throws Exception {
        if (getLoggedInCustomerID(loggedInId) == null) {
            return null;
        }
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        ArrayList<BankTransactionTO> customerTransactionList = bankTransactionDAO.selectByCustomerId(customerId);
        return customerTransactionList;
    }

    public BankTransactionTO getTransactionDetails(String loggedInId, long transactionId) throws Exception {
        if (getLoggedInCustomerID(loggedInId) == null) {
            return null;
        }
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        BankTransactionTO bankTransactionTO = bankTransactionDAO.selectByTransactionId(transactionId);
        bankTransactionDAO.close();
        return bankTransactionTO;
    }

    //Bank-card methods ------------------------------------------------------------------------------------------------

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public void blockBankCard(BankCardTO bankCardTO) throws Exception {
        BankCardDAO bankCardDAO = new BankCardDAO();
        bankCardTO.setBlockedFlag(1);
        bankCardDAO.updateBlockFlag(bankCardTO);//password check here
        bankCardDAO.close();
    }

    //(Note. Doesn't need to loggedInCode because bank-card has password.)
    public void unblockBankCard(BankCardTO bankCardTO) throws Exception {
        BankCardDAO bankCardDAO = new BankCardDAO();
        bankCardTO.setBlockedFlag(0);
        bankCardDAO.updateBlockFlag(bankCardTO);//password check here
        bankCardDAO.close();
    }

    public CustomerTO getBankCardOwner(long bankCardId) throws Exception { //return valu can be just a String (name + family)

        BankCardDAO bankCardDAO = new BankCardDAO();
        BankCardTO bankCardTO = bankCardDAO.selectById(bankCardId);
        bankCardDAO.close();

        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
        CheckingAccountTO checkingAccountTO = checkingAccountDAO.selectById(bankCardTO.getCheckingAccountId());
        checkingAccountDAO.close();

        CustomerDAO customerDAO = new CustomerDAO();
        CustomerTO customerTO = customerDAO.selectById(checkingAccountTO.getCustomerId());
        customerDAO.close();

        customerTO.setCustomerInformationTO(null);
        customerTO.setDocumentPictureAddress(null);
        customerTO.setBirthPlace(null);
        customerTO.setBirthDate(0);
        customerTO.setBirthCertificateId(0);
        customerTO.setFatherName(null);
        customerTO.setId(0);
        customerTO.setNationalId(0);

        return customerTO;
    }

    public Boolean checkBankCard(BankCardTO bankCardTO) throws Exception {
        try {
            BankCardDAO bankCardDAO = new BankCardDAO();
            bankCardTO = bankCardDAO.selectUnBlockedBankCard(bankCardTO);
            bankCardDAO.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getBankCardMoney(BankCardTO bankCardTO) throws Exception {
        try {
            BankCardDAO bankCardDAO = new BankCardDAO();
            bankCardTO = bankCardDAO.selectUnBlockedBankCard(bankCardTO);
            bankCardDAO.close();

            CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
            CheckingAccountTO checkingAccountTO = checkingAccountDAO.selectById(bankCardTO.getCheckingAccountId());
            checkingAccountDAO.close();

            return checkingAccountTO.getMoney();
        } catch (Exception e) {
            e.printStackTrace();
            return 1l;
        }

    }


    //Bank-Check methods -----------------------------------------------------------------------------------------------

    public Boolean isBankCheckRequestPossible(String loggedInCode, long checkingAccountId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }

        ArrayList<BankCheckTO> checkingAccountBankCheckList;
        try {
            BankCheckDAO bankCheckDAO = new BankCheckDAO();
            checkingAccountBankCheckList = bankCheckDAO.selectByCheckingAccountID(checkingAccountId);
            bankCheckDAO.close();
        } catch (Exception e) {
            e.printStackTrace();
            return true; //because any BankCheck doesn't inserted for checking account until now;
        }

        try { //check if there is Checking account BankCheckRequest that isn't checked .
            BankCheckRequestDAO bankCheckRequestDAO = new BankCheckRequestDAO();
            ArrayList<BankCheckRequestTO> bankCheckRequestTOArrayList = bankCheckRequestDAO.selectByCheckingAccountID(checkingAccountId);
            int j = 0;
            while (j < bankCheckRequestTOArrayList.size()) {
                if (bankCheckRequestTOArrayList.get(j).getCheckedFlag() != 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        try {
            int i = 0;
            while (i < checkingAccountBankCheckList.size()) {
                CheckPaperDAO checkPaperDAO = new CheckPaperDAO();
                checkPaperDAO.selectNotPassedCheckPaper(checkingAccountBankCheckList.get(i).getId());
                checkPaperDAO.close();
                i++;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public BankCheckRequestTO requestBankCheck(String loggedInCode, long checkingAccountId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        BankCheckRequestTO bankCheckRequestTO = new BankCheckRequestTO();
        bankCheckRequestTO.setCheckingAccountId(checkingAccountId);
        bankCheckRequestTO.setCustomerId(getLoggedInCustomerID(loggedInCode));
        bankCheckRequestTO.setRequestDate(PersianDate.getDateAsNumber());
        bankCheckRequestTO.setCheckedFlag(-1);
        BankCheckRequestDAO bankCheckRequestDAO = new BankCheckRequestDAO();
        bankCheckRequestTO = bankCheckRequestDAO.insert(bankCheckRequestTO);
        bankCheckRequestDAO.close();

        return bankCheckRequestTO;
    }

    //Get a list of check-paper of special bank-check papers
    public ArrayList<CheckPaperTO> getBankCheckReport(String loggedInCode, long bankCheckId) throws Exception {//Return a list of CheckPaperTO
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        CheckPaperDAO checkPaperDAO = new CheckPaperDAO();
        ArrayList<CheckPaperTO> arrayList = checkPaperDAO.selectByBankCheckId(bankCheckId);
        checkPaperDAO.close();
        return arrayList;
    }

    //INCOMPLETE
    public Boolean blockCheckPaper(String loggedInCode, long bankCheckId, int checkPaperId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        CheckPaperDAO checkPaperDAO = new CheckPaperDAO();
        CheckPaperTO checkPaperTO = checkPaperDAO.updateBarredFlag(bankCheckId, checkPaperId, 1);
        checkPaperDAO.close();
        return true;
    }

    //INCOMPLETE
    public Boolean unBlockCheckPaper(String loggedInCode, long bankCheckId, int checkPaperId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        CheckPaperDAO checkPaperDAO = new CheckPaperDAO();
        CheckPaperTO checkPaperTO = checkPaperDAO.updateBarredFlag(bankCheckId, checkPaperId, 0);
        return true;
    }

    public Boolean existBankCheck(String loggedInCode, long bankCheckId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        try {
            BankCheckDAO bankCheckDAO = new BankCheckDAO();
            bankCheckDAO.selectByID(bankCheckId);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean existCheckPaper(String loggedInCode, long bankCheckId, int checkPaperId) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        try {
            CheckPaperDAO checkPaperDAO = new CheckPaperDAO();
            checkPaperDAO.selectByBankCheckIdAndCheckPaperId(bankCheckId, checkPaperId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Account methods --------------------------------------------------------------------------------------------------

    //INCOMPLETE
    public Message requestAccount(String loggedInCode, CustomerTO customerTO) throws Exception {//Params can be customerTO , accountFromNumber def null,bankCard def null openingMoney

        return new Message();
    }

    public CheckingAccountTO getCheckingAccountDetails(String loggedInCode, Long accountId) throws Exception {

        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        if (!isCurrentUserCheckingAccount(loggedInCode, accountId)) {
            return null;
        }
        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
        CheckingAccountTO checkingAccountTO = checkingAccountDAO.selectByCustomerIDAndAccountId(getLoggedInCustomerID(loggedInCode), accountId);
        checkingAccountDAO.close();
        return checkingAccountTO;

    }

    //it may be not useful.
    public Long getCheckingAccountCustomerId(long checkingAccountId) throws Exception {
        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
        CheckingAccountTO checkingAccountTO =
                checkingAccountDAO.selectById(checkingAccountId);
        checkingAccountDAO.close();

        CustomerDAO customerDAO = new CustomerDAO();
        CustomerTO customerTO = customerDAO.selectById(checkingAccountTO.getCustomerId());
        customerDAO.close();

        return customerTO.getId();
    }

    //Check existence of deposit account id. (Note. loggedInCode isn't necessary.)
    // [may this method isn't necessary.]
    public Boolean existDepositAccountId(long accountId) throws Exception {
        try {
            DepositAccountDAO depositAccountDAO = new DepositAccountDAO();
            depositAccountDAO.selectByAccountId(accountId);
            depositAccountDAO.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Check existence of checking account id. (Note. loggedInCode isn't necessary.)
    // [may this method isn't necessary.]
    public Boolean existCheckingAccountId(long accountId) throws Exception {
        try {
            CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
            checkingAccountDAO.selectById(accountId);
            checkingAccountDAO.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isCurrentUserCheckingAccount(String loggedInCode, long accountId) throws Exception {
        try {
            if (getLoggedInCustomerID(loggedInCode) == null) {
                return null;
            }
            CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
            checkingAccountDAO.selectByCustomerIDAndAccountId(getLoggedInCustomerID(loggedInCode), accountId);
            checkingAccountDAO.close();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public Boolean isCurrentUserDepositAccount(String loggedInCode, long accountId) throws Exception {
        try {
            if (getLoggedInCustomerID(loggedInCode) == null) {
                return null;
            }
            DepositAccountDAO depositAccountDAO = new DepositAccountDAO();
            depositAccountDAO.selectByCustomerIdAndAccountId(getLoggedInCustomerID(loggedInCode), accountId);
            depositAccountDAO.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList viewAllCheckingAccounts(String loggedInCode) throws Exception {

        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO();
        ArrayList arrayList = checkingAccountDAO.selectByCustomerId(getLoggedInCustomerID(loggedInCode));
        checkingAccountDAO.close();
        return arrayList;

    }

    public ArrayList<DepositAccountTO> viewAllDepositAccounts(String loggedInCode) throws Exception {
        try {
            if (getLoggedInCustomerID(loggedInCode) == null) {
                return null;
            }
            DepositAccountDAO depositAccountDAO = new DepositAccountDAO();
            ArrayList arrayList = depositAccountDAO.selectByCustomerId(getLoggedInCustomerID(loggedInCode));
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public CheckingAccountRequestTO requestCheckingAccount(String loggedInCode, CheckingAccountRequestTO checkingAccountRequestTO) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        CheckingAccountRequestDAO checkingAccountRequestDAO = new CheckingAccountRequestDAO();
        checkingAccountRequestTO = checkingAccountRequestDAO.insert(checkingAccountRequestTO);
        checkingAccountRequestDAO.close();
        return checkingAccountRequestTO;
    }

    public DepositAccountRequestTO requestDepositAccount(String loggedInCode, DepositAccountRequestTO depositAccountRequestTO) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        DepositAccountRequestDAO depositAccountRequestDAO = new DepositAccountRequestDAO();
        depositAccountRequestTO = depositAccountRequestDAO.insert(depositAccountRequestTO);
        depositAccountRequestDAO.close();

        return depositAccountRequestTO;
    }

    public CustomerTO getCheckingAccountOwner(Long checkingAccountId) throws Exception {

        try {

            CustomerDAO customerDAO = new CustomerDAO();
            CustomerTO customerTO = customerDAO.selectById(22l);
            //customerDAO.close();

            customerTO.setId(0);
            customerTO.setDocumentPictureAddress("");
            customerTO.setBirthCertificateId(0);
            customerTO.setNationalId(0);
            customerTO.setBirthDate(0);
            customerTO.setBirthPlace("");
            customerTO.setCustomerInformationTO(null);
            return customerTO;
        } catch (Exception e) {
            System.out.println("aaaaaaaaa");
            e.printStackTrace();
            return null;
        }

    }

    //Bill Methods -----------------------------------------------------------------------------------------------------

    public BillTO getBillDetails(Long id, Long payId) throws Exception {
        try {
            BillDAO billDAO = new BillDAO();
            BillTO billTO = billDAO.select(id, payId);
            billDAO.close();
            return billTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public BankTransactionTO payBillByCheckingAccount(String loggedInCode, BillTO billTO, long checkingAccount) throws Exception {

        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }

        MoneyTransferTransaction moneyTransferTransaction =
                new MoneyTransferTransaction(getLoggedInCustomerID(loggedInCode));
        return moneyTransferTransaction.payBillByCheckingAccount(billTO, checkingAccount);
    }

    public BankTransactionTO payBillByBankCard(BillTO billTO, BankCardTO bankCardTO) throws Exception {
        MoneyTransferTransaction moneyTransferTransaction = new MoneyTransferTransaction();
        return moneyTransferTransaction.payBillByBankCard(billTO, bankCardTO);
    }

    public Boolean isBillPayed(BillTO billTO) throws Exception {
        BillDAO billDAO = new BillDAO();
        BillTO billTO1 = billDAO.select(billTO.getId(), billTO.getPayId());
        if (billTO1.getPayedFlag() == 1) {
            return true;
        } else {
            return false;
        }
    }


    //profile and login methods ----------------------------------------------------------------------------------------

    //If authenticate is ok return username
    public String changePassword(String loggedInCode, String password, String newPassword) throws Exception {
        if (getLoggedInCustomerID(loggedInCode) == null) {
            return null;
        }
        AuthenticationInformationDAO authenticationInformationDAO = new AuthenticationInformationDAO();
        AuthenticationInformationTO authenticationInformationTO = new AuthenticationInformationTO();
        authenticationInformationDAO.updatePassword(this.getLoggedInUserName(loggedInCode), password, newPassword);
        authenticationInformationDAO.close();
        return this.getLoggedInUserName(loggedInCode);
    }

    public String login(String userName, String password) throws Exception {

        AuthenticationInformationDAO authenticationInformationDAO = new AuthenticationInformationDAO();
        AuthenticationInformationTO authenticationInformationTO = authenticationInformationDAO.select(userName, password);
        authenticationInformationDAO.close();


        String loggedInCode = createLoggedInCode(authenticationInformationTO.getCustomerId());
        putLoggedInUsersDetails(loggedInCode, authenticationInformationTO.getCustomerId(), authenticationInformationTO.getUserName());
        return loggedInCode;
    }

    public String isLoggedIn(String loggedInId) throws Exception {
        if (getLoggedInCustomerID(loggedInId) == null) {
            return null;
        } else {
            return getLoggedInUserName(loggedInId);
        }
    }

    public void logout(String loggedInCode) throws Exception {
        this.loggedInUsers.remove(loggedInCode);
        this.loggedInCustomersId.remove(loggedInCode);
    }

    public void putLoggedInUsersDetails(String loggedInCode, long customerID, String userName) throws Exception {
        this.loggedInUsers.put(loggedInCode, userName);
        this.loggedInCustomersId.put(loggedInCode, customerID);
    }

    public String createLoggedInCode(Long customerID) throws Exception {
        return Encryption.getMD5(String.valueOf(customerID + new Date().getTime()));
    }

    public Long getLoggedInCustomerID(String loggedInCode) {
        return (Long) this.loggedInCustomersId.get(loggedInCode);
    }

    public String getLoggedInUserName(String loggedInCode) {
        return (String) this.loggedInUsers.get(loggedInCode);
    }





}
