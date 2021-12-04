package controller;

import model.bl.CustomerServerInterface;
import model.to.BankCardTO;
import model.to.BankTransactionTO;
import model.to.CustomerTO;
import tools.PersianDate;
import tools.RegularExpression;
import view.MoneyTransferFromBankCardToCheckingAccountPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MoneyTransferFromBankCardToCheckingAccountPanelController implements ActionListener {

    MoneyTransferFromBankCardToCheckingAccountPanel moneyTransferFromBankCardToCheckingAccountPanel;

    public MoneyTransferFromBankCardToCheckingAccountPanelController(MoneyTransferFromBankCardToCheckingAccountPanel moneyTransferFromBankCardToCheckingAccountPanel) {
        this.moneyTransferFromBankCardToCheckingAccountPanel = moneyTransferFromBankCardToCheckingAccountPanel;
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("checkMoneyTransferData")) {
            checkMoneyTransferData();
        } else if (e.getActionCommand().equals("confirmTransferMoney")) {
            transferMoney();
        } else if (e.getActionCommand().equals("goBack")) {
            goFirstPanel();
        }
    }

    public void checkMoneyTransferData() {

        moneyTransferFromBankCardToCheckingAccountPanel.clearErrorPanelErrors();

        boolean trueCondition = true;

        if (moneyTransferFromBankCardToCheckingAccountPanel.moneyValueTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ مورد نظر برای انتقال را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.moneyValueTextField.getText()))) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ باید فقط شامل ارقام باشد.");
            moneyTransferFromBankCardToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        } else if (Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.moneyValueTextField.getText()) <= 0 ) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ نمیتواند 0 یا کمتر از 0 باشد .");
            moneyTransferFromBankCardToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.destinationCheckingAccountTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب مقصد را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.destinationCheckingAccountStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.destinationCheckingAccountTextField.getText()))) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب باید فقط شامل ارقام باشد.");
            moneyTransferFromBankCardToCheckingAccountPanel.destinationCheckingAccountStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("شماره کارت را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdTextField.getText()))) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("شماره کارت باید فقط شامل ارقام باشد");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("رمز کارت را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardInternetPasswordStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2TextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("CVV2"+"را پر کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2TextField.getText()))) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("CVV2 فقط باید شامل ارقام باشد.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateMonthTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("ماه را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2TextField.getText())) ||
                Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateMonthTextField.getText()) > 12) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("ماه را بصورت صحیح وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateYearTextField.getText().isEmpty()) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("سال را وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateYearTextField.getText())) ||
                Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateYearTextField.getText()) < (Math.abs(PersianDate.getDateAsNumber() / 10000))) {
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("سال را بصورت صحیح وارد کنید.");
            moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (trueCondition) {

            long destinationCheckingAccountId = Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.destinationCheckingAccountTextField.getText());
            long money = Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.moneyValueTextField.getText());
            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(Long.parseLong(moneyTransferFromBankCardToCheckingAccountPanel.bankCardIdTextField.getText()));
            bankCardTO.setInternetPassword(moneyTransferFromBankCardToCheckingAccountPanel.bankCardInternetPasswordField.getText());
            bankCardTO.setCVV2(Integer.parseInt(moneyTransferFromBankCardToCheckingAccountPanel.bankCardCVV2TextField.getText()));
            int expireDateMonth = Integer.parseInt(moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateMonthTextField.getText());
            int expireDateYear = Integer.parseInt(moneyTransferFromBankCardToCheckingAccountPanel.bankCardExpireDateYearTextField.getText());
            bankCardTO.setExpireDate(expireDateYear * 100 + expireDateMonth);


            try {
                try {

                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");

                    if (!customerServer.existCheckingAccountId(destinationCheckingAccountId)) {
                        moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب مقصد نامعتبر است .");
                        trueCondition = false;
                    }

                    if (!customerServer.checkBankCard(bankCardTO)) {
                        moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("مشخصات کارت نامعتبر است.");
                        trueCondition = false;
                    }
                    if (trueCondition) {
                        CustomerTO destinationAccountOwnerCustomerTO = customerServer.getCheckingAccountOwner(destinationCheckingAccountId);
                        String destinationCheckingAccountOwner = destinationAccountOwnerCustomerTO.getName() + destinationAccountOwnerCustomerTO.getFamily();
                        moneyTransferFromBankCardToCheckingAccountPanel.showConfirmPanel(
                                bankCardTO,
                                destinationCheckingAccountId,
                                destinationCheckingAccountOwner,
                                money);
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                } catch (SQLException e) {

                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }
    }

    public void transferMoney() {
        moneyTransferFromBankCardToCheckingAccountPanel.clearErrorPanelErrors();
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                BankTransactionTO bankTransactionTO = customerServer.bankCardToCheckingAccountTransfer(
                        moneyTransferFromBankCardToCheckingAccountPanel.getBankCardTO(),
                        moneyTransferFromBankCardToCheckingAccountPanel.getDestinationCheckingAccountId(),
                        moneyTransferFromBankCardToCheckingAccountPanel.getMoney());
                moneyTransferFromBankCardToCheckingAccountPanel.showReportPanel(bankTransactionTO);
            } catch (RemoteException e) {
                //network error
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney net error :");
                e.printStackTrace();
                moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

            }
        } catch (Exception e) {
            System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney other error :");
            e.printStackTrace();
            moneyTransferFromBankCardToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
        }

    }

    public void goFirstPanel() {
        moneyTransferFromBankCardToCheckingAccountPanel.showFirstPanel();
    }


}
