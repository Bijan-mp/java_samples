package model.transactionsobjects;

import model.dao.BankCardDAO;
import model.dao.BillDAO;
import model.dao.CheckingAccountDAO;
import model.dao.CustomerDAO;
import model.to.*;
import tools.Message;

public class MoneyTransferTransaction extends TransactionObject {

    public MoneyTransferTransaction() throws Exception {
        super();
    }

    public MoneyTransferTransaction(long customerId) throws Exception {
        super(customerId);
    }

    public BankTransactionTO checkingAccountToCheckingAccount(long sourceCheckingAccountId, long destinationCheckingAccountId, long money) throws Exception {

        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO(this.connection, this.statement);
        CustomerDAO customerDAO = new CustomerDAO(this.connection, this.statement);
        CustomerTO sourceCustomerTO;
        CustomerTO destinationCustomerTO;
        CheckingAccountTO sourceCheckingAccountTO;
        CheckingAccountTO destinationCheckingAccountTO;

        sourceCheckingAccountTO = checkingAccountDAO.selectById(sourceCheckingAccountId);
        long sourceAccountMoney = sourceCheckingAccountTO.getMoney();
        //Check account money is enough.
        if ((sourceAccountMoney - money) < 0 || money < 0) {
            return null;
        }
        checkingAccountDAO.updateMoney(sourceCheckingAccountId, (sourceAccountMoney - money));
        sourceCustomerTO = customerDAO.selectById(sourceCheckingAccountTO.getCustomerId());
        destinationCheckingAccountTO = checkingAccountDAO.selectById(destinationCheckingAccountId);
        long destinationAccountMoney = destinationCheckingAccountTO.getMoney();
        checkingAccountDAO.updateMoney(destinationCheckingAccountId, (destinationAccountMoney + money));
        destinationCustomerTO = customerDAO.selectById(destinationCheckingAccountTO.getCustomerId());

        String transactionDescription = "<div align=\"right\">";
        transactionDescription += "شماره حساب مبدا : " + sourceCheckingAccountId + "<br/>";
        transactionDescription += "به نام : " + sourceCustomerTO.getName() + " " + sourceCustomerTO.getFamily() + "<br/>";
        transactionDescription += "شماره حساب مقصد : " + destinationCheckingAccountId + "<br/>";
        transactionDescription += "به نام : " + destinationCustomerTO.getName() + " " + destinationCustomerTO.getFamily() + "<br/>";
        transactionDescription += "</div>";
        BankTransactionTO bankTransactionTO = setCheckingAccountTransaction(
                "انتقال از حساب جاری به حساب جاری ",
                transactionDescription, sourceCheckingAccountTO.getId(),
                destinationCheckingAccountTO.getId(),
                money);
        this.commit();
        return bankTransactionTO;
    }

    public BankTransactionTO bankCardToCheckingAccount(BankCardTO bankCardTO, long destinationCheckingAccountId, long money) throws Exception {

        BankCardDAO bankCardDAO = new BankCardDAO(this.connection, this.statement);
        CheckingAccountDAO CheckingAccountDAO = new CheckingAccountDAO(this.connection, this.statement);
        CustomerDAO customerDAO = new CustomerDAO(this.connection, this.statement);
        CheckingAccountTO sourceCheckingAccountTO;
        CheckingAccountTO destinationCheckingAccountTO;
        CustomerTO sourceCustomerTO;
        CustomerTO destinationCustomerTO;

        bankCardTO = bankCardDAO.selectUnBlockedBankCard(bankCardTO);
        sourceCheckingAccountTO = CheckingAccountDAO.selectById(bankCardTO.getCheckingAccountId());
        this.customerId = sourceCheckingAccountTO.getCustomerId();
        long fromAccountMoney = sourceCheckingAccountTO.getMoney();
        //Check account money is enough.
        if ((fromAccountMoney - money) < 0 || money <= 0) {
            return null;
        }
        CheckingAccountDAO.updateMoney(bankCardTO.getCheckingAccountId(), (fromAccountMoney - money));
        sourceCustomerTO = customerDAO.selectById(sourceCheckingAccountTO.getCustomerId());
        destinationCheckingAccountTO = CheckingAccountDAO.selectById(destinationCheckingAccountId);
        CheckingAccountDAO.updateMoney(destinationCheckingAccountId, destinationCheckingAccountTO.getMoney() + money);
        destinationCustomerTO = customerDAO.selectById(destinationCheckingAccountTO.getCustomerId());

        String transactionDescription = "<div align=\"right\">";
        transactionDescription += "شماره حساب مبدأ : " + bankCardTO.getCheckingAccountId() + "<br/>";
        transactionDescription += "شماره کارت مبدأ : " + bankCardTO.getId() + "<br/>";
        transactionDescription += "به نام : " + sourceCustomerTO.getName() + sourceCustomerTO.getFamily() + "<br/>";
        transactionDescription += "شماره حساب مقصد : " + destinationCheckingAccountId + "<br/>";
        transactionDescription += "به نام : " + destinationCustomerTO.getName() + destinationCustomerTO.getFamily() + "<br/>";
        transactionDescription += "</div>";
        BankTransactionTO bankTransactionTO = setBankCardTransaction(
                "انتقال از حساب جاری به حساب جاری توسط کارت عابر بانک ",
                transactionDescription,
                sourceCheckingAccountTO.getId(),
                bankCardTO.getId(),
                destinationCheckingAccountId,
                money);
        this.commit();
        return bankTransactionTO;
    }

    public BankTransactionTO bankCardToBankCard(BankCardTO fromBankCardTO, long toBankCardId, long money) throws Exception {

        String transactionDescription;

        BankCardDAO bankCardDAO = new BankCardDAO(this.connection, this.statement);
        fromBankCardTO = bankCardDAO.selectUnBlockedBankCard(fromBankCardTO);

        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO(this.connection, this.statement);
        CheckingAccountTO fromCheckingAccountTO = checkingAccountDAO.selectById(fromBankCardTO.getCheckingAccountId());
        if (fromCheckingAccountTO.getMoney() < money) {
            return null;
        }
        fromCheckingAccountTO = checkingAccountDAO.updateMoney(fromCheckingAccountTO.getId(), fromCheckingAccountTO.getMoney() - money);
        transactionDescription = "میزان پول : " + money + "\n";
        transactionDescription += "مشخصات کارت مبدأ : " + "\n";
        transactionDescription += "شماره کارت : " + fromBankCardTO.getId() + "\n";
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerTO customerTO = customerDAO.selectById(fromCheckingAccountTO.getCustomerId());
        transactionDescription += "نام دارنده کارت : " + customerTO.getName() + " " + customerTO.getFamily() + "\n";
        transactionDescription += "باقی مانده حساب : " + fromCheckingAccountTO.getMoney() + "ریال" + "\n" + "\n";


        BankCardTO toBankCardTO = bankCardDAO.selectById(toBankCardId);
        CheckingAccountTO toCheckingAccountTO = checkingAccountDAO.selectById(toBankCardTO.getCheckingAccountId());
        toCheckingAccountTO = checkingAccountDAO.updateMoney(toCheckingAccountTO.getId(), toCheckingAccountTO.getMoney() + money);
        transactionDescription += "مشخصات کارت مقصد : " + "\n";
        transactionDescription += "شماره کارت : " + toBankCardTO.getId() + "\n";
        customerTO = customerDAO.selectById(toCheckingAccountTO.getCustomerId());
        transactionDescription += "نام دارنده کارت : " + customerTO.getName() + " " + customerTO.getFamily();

        BankTransactionTO bankTransactionTO = setBankCardTransaction("انتقال ازکارت عابربانک به کارت عابربانک ", transactionDescription,
                fromCheckingAccountTO.getId(), fromBankCardTO.getId(),
                toCheckingAccountTO.getId(), toBankCardTO.getId());
        this.commit();
        return bankTransactionTO;
    }

    public BankTransactionTO payBillByCheckingAccount(BillTO billTO, long checkingAccountId) throws Exception {

        try {
            CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO(this.connection, this.statement);
            CheckingAccountTO checkingAccountTO = checkingAccountDAO.selectById(checkingAccountId);
            long updateMoney = checkingAccountTO.getMoney() - billTO.getPrice();
            BillDAO billDAO = new BillDAO(this.connection, this.statement);


            checkingAccountTO = checkingAccountDAO.updateMoney(checkingAccountId, updateMoney);
            billTO = billDAO.update(billTO, 1);


            String transactionDescription = "<html> <div align=\"right\"> ";
            transactionDescription = "شماره حساب : " + "<br/>";
            transactionDescription += " قبض " + billTO.getType() + "<br/>";
            transactionDescription += " شماره قبض : " + billTO.getId() + "<br/>";
            transactionDescription += " شناسه پرداخت : " + billTO.getPayId() + "<br/>";
            transactionDescription += " مبلغ :  " + billTO.getPrice();
            transactionDescription += "</div></html>";

            BankTransactionTO bankTransactionTO = setPayBillByCheckingAccountTransaction(
                    "پرداخت قبض توسط حساب جاری ",
                    transactionDescription,
                    checkingAccountTO.getId(),
                    billTO.getPrice());
            this.commit();
            return bankTransactionTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BankTransactionTO payBillByBankCard(BillTO billTO, BankCardTO bankCardTO) throws Exception {

        BillDAO billDAO = new BillDAO(this.connection, this.statement);
        BankCardDAO bankCardDAO = new BankCardDAO(this.connection, this.statement);
        CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAO(this.connection, this.statement);

        billTO = billDAO.select(billTO.getId(), billTO.getPayId());
        bankCardTO = bankCardDAO.selectUnBlockedBankCard(bankCardTO);
        CheckingAccountTO checkingAccountTO = checkingAccountDAO.selectById(bankCardTO.getCheckingAccountId());
        //return if money isn't enough
        if (checkingAccountTO.getMoney() < billTO.getPrice()) {
            return null;
        }
        checkingAccountTO = checkingAccountDAO.updateMoney(checkingAccountTO.getId(), checkingAccountTO.getMoney() - billTO.getPrice());
        billTO = billDAO.update(billTO, 1);
        //get customer-account-id
        this.customerId = checkingAccountTO.getCustomerId();

        String transactionDescription = "<html> <div align=\"right\"> ";
        transactionDescription += " قبض " + billTO.getType() + "<br/>";
        transactionDescription += " شماره قبض : " + billTO.getId() + "<br/>";
        transactionDescription += " شناسه پرداخت : " + billTO.getPayId() + "<br/>";
        transactionDescription += " مبلغ :  " + billTO.getPrice() + "<br/>";
        transactionDescription += "شماره کارت : " + bankCardTO.getId();
        transactionDescription += "</div></html>";
        BankTransactionTO bankTransactionTO = setPayBillByBankCardTransaction(
               "پرداخت قبض توسط کارت بانکی",
                transactionDescription,
                checkingAccountTO.getId(),
                bankCardTO.getId(),
                billTO.getPrice());
        this.commit();
        return bankTransactionTO;
    }

}
