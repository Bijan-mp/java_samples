package controller;

import model.bl.CustomerServerInterface;
import model.to.BankCardTO;
import model.to.BankTransactionTO;
import tools.PersianDate;
import tools.RegularExpression;
import view.BankCardTransactionListPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankCardTransactionListPanelController implements ActionListener {

    BankCardTransactionListPanel bankCardTransactionListPanel;

    public BankCardTransactionListPanelController(BankCardTransactionListPanel bankCardTransactionListPanel) {
        this.bankCardTransactionListPanel = bankCardTransactionListPanel;
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("checkBankCardData")) {
            checkMoneyTransferData();
        } else if (e.getActionCommand().equals("confirmTransferMoney")) {
            transferMoney();
        } else if (e.getActionCommand().equals("goBack")) {
            goFirstPanel();
        }
    }

    public void checkMoneyTransferData() {

        bankCardTransactionListPanel.clearErrorPanelErrors();

        boolean trueCondition = true;

        if (bankCardTransactionListPanel.bankCardIdTextField.getText().isEmpty()) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("شماره کارت را وارد کنید.");
            bankCardTransactionListPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(bankCardTransactionListPanel.bankCardIdTextField.getText()))) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("شماره کارت باید فقط شامل ارقام باشد");
            bankCardTransactionListPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (bankCardTransactionListPanel.bankCardIdTextField.getText().isEmpty()) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("رمز کارت را وارد کنید.");
            bankCardTransactionListPanel.bankCardInternetPasswordStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (bankCardTransactionListPanel.bankCardCVV2TextField.getText().isEmpty()) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("CVV2" + "را پر کنید.");
            bankCardTransactionListPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(bankCardTransactionListPanel.bankCardCVV2TextField.getText()))) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("CVV2 فقط باید شامل ارقام باشد.");
            bankCardTransactionListPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        }
        if (bankCardTransactionListPanel.bankCardExpireDateMonthTextField.getText().isEmpty()) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("ماه را وارد کنید.");
            bankCardTransactionListPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(bankCardTransactionListPanel.bankCardCVV2TextField.getText())) ||
                Long.parseLong(bankCardTransactionListPanel.bankCardExpireDateMonthTextField.getText()) > 12) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("ماه را بصورت صحیح وارد کنید.");
            bankCardTransactionListPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (bankCardTransactionListPanel.bankCardExpireDateYearTextField.getText().isEmpty()) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("سال را وارد کنید.");
            bankCardTransactionListPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(bankCardTransactionListPanel.bankCardExpireDateYearTextField.getText())) ||
                Long.parseLong(bankCardTransactionListPanel.bankCardExpireDateYearTextField.getText()) < (Math.abs(PersianDate.getDateAsNumber() / 10000))) {
            bankCardTransactionListPanel.addAlarmToAlarmPanel("سال را بصورت صحیح وارد کنید.");
            bankCardTransactionListPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (trueCondition) {

            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(Long.parseLong(bankCardTransactionListPanel.bankCardIdTextField.getText()));
            bankCardTO.setInternetPassword(bankCardTransactionListPanel.bankCardInternetPasswordField.getText());
            bankCardTO.setCVV2(Integer.parseInt(bankCardTransactionListPanel.bankCardCVV2TextField.getText()));
            int expireDateMonth = Integer.parseInt(bankCardTransactionListPanel.bankCardExpireDateMonthTextField.getText());
            int expireDateYear = Integer.parseInt(bankCardTransactionListPanel.bankCardExpireDateYearTextField.getText());
            bankCardTO.setExpireDate(expireDateYear * 100 + expireDateMonth);


            try {
                try {

                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");

                    if (!customerServer.checkBankCard(bankCardTO)) {
                        bankCardTransactionListPanel.addAlarmToAlarmPanel("مشخصات کارت نامعتبر است.");
                        trueCondition = false;
                    }
                    if (trueCondition) {
                         ArrayList<BankTransactionTO> bankTransactionTOArrayList = customerServer.getBankCardTransactionList(MainFrame.loggedInCode, bankCardTO.getId());
                        bankCardTransactionListPanel.showBankCardTransactionListTable(bankTransactionTOArrayList,bankCardTO.getId());
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    bankCardTransactionListPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                } catch (SQLException e) {
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    bankCardTransactionListPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");
                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                bankCardTransactionListPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }
    }

    public void transferMoney() {
        bankCardTransactionListPanel.clearErrorPanelErrors();
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
            } catch (RemoteException e) {
                //network error
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney net error :");
                e.printStackTrace();
                bankCardTransactionListPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

            }
        } catch (Exception e) {
            System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney other error :");
            e.printStackTrace();
            bankCardTransactionListPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
        }

    }

    public void goFirstPanel() {
        bankCardTransactionListPanel.showFirstPanel();
    }

}
