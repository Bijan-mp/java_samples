package controller;

import model.bl.CustomerServerInterface;
import model.to.BankCardTO;
import tools.PersianDate;
import tools.RegularExpression;
import view.BlockBankCardPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class BlockBankCardPanelController implements ActionListener {

    BlockBankCardPanel blockBankCardPanel;

    public BlockBankCardPanelController(BlockBankCardPanel blockBankCardPanel) {
        this.blockBankCardPanel = blockBankCardPanel;
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("checkBlockBankCardData")) {
            checkBlockBankCardData();
        } else if (e.getActionCommand().equals("confirmBlockBankCard")) {
            blockBankCard();
        } else if (e.getActionCommand().equals("goBack")) {
            showFirstPanel();
        }
    }

    public void checkBlockBankCardData() {

        blockBankCardPanel.clearAlarmPanelAlarms();
        boolean trueCondition = true;

        if (blockBankCardPanel.bankCardIdTextField.getText().isEmpty()) {
            blockBankCardPanel.addAlarmToAlarmPanel("شماره کارت را وارد کنید.");
            blockBankCardPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockBankCardPanel.bankCardIdTextField.getText()))) {
            blockBankCardPanel.addAlarmToAlarmPanel("شماره کارت باید فقط شامل ارقام باشد");
            blockBankCardPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (blockBankCardPanel.bankCardIdTextField.getText().isEmpty()) {
            blockBankCardPanel.addAlarmToAlarmPanel("رمز کارت را وارد کنید.");
            blockBankCardPanel.bankCardInternetPasswordStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (blockBankCardPanel.bankCardCVV2TextField.getText().isEmpty()) {
            blockBankCardPanel.addAlarmToAlarmPanel("CVV2" + "را پر کنید.");
            blockBankCardPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockBankCardPanel.bankCardCVV2TextField.getText()))) {
            blockBankCardPanel.addAlarmToAlarmPanel("CVV2 فقط باید شامل ارقام باشد.");
            blockBankCardPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        }
        if (blockBankCardPanel.bankCardExpireDateMonthTextField.getText().isEmpty()) {
            blockBankCardPanel.addAlarmToAlarmPanel("ماه را وارد کنید.");
            blockBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockBankCardPanel.bankCardCVV2TextField.getText())) ||
                Long.parseLong(blockBankCardPanel.bankCardExpireDateMonthTextField.getText()) > 12) {
            blockBankCardPanel.addAlarmToAlarmPanel("ماه را بصورت صحیح وارد کنید.");
            blockBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (blockBankCardPanel.bankCardExpireDateYearTextField.getText().isEmpty()) {
            blockBankCardPanel.addAlarmToAlarmPanel("سال را وارد کنید.");
            blockBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockBankCardPanel.bankCardExpireDateYearTextField.getText())) ||
                Long.parseLong(blockBankCardPanel.bankCardExpireDateYearTextField.getText()) < (Math.abs(PersianDate.getDateAsNumber() / 10000))) {
            blockBankCardPanel.addAlarmToAlarmPanel("سال را بصورت صحیح وارد کنید.");
            blockBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (trueCondition) {

            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(Long.parseLong(blockBankCardPanel.bankCardIdTextField.getText()));
            bankCardTO.setInternetPassword(blockBankCardPanel.bankCardInternetPasswordField.getText());
            bankCardTO.setCVV2(Integer.parseInt(blockBankCardPanel.bankCardCVV2TextField.getText()));
            int expireDateMonth = Integer.parseInt(blockBankCardPanel.bankCardExpireDateMonthTextField.getText());
            int expireDateYear = Integer.parseInt(blockBankCardPanel.bankCardExpireDateYearTextField.getText());
            bankCardTO.setExpireDate(expireDateYear * 100 + expireDateMonth);

            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    customerServer.checkBankCard(bankCardTO);
                    blockBankCardPanel.showConfirmPanel(bankCardTO);

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    blockBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                } catch (SQLException e) {
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData sql error :");
                    e.printStackTrace();
                    blockBankCardPanel.addAlarmToAlarmPanel("مشخصات کارت نامعتبر است.");

                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                blockBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }

    }

    public void blockBankCard() {

        blockBankCardPanel.clearAlarmPanelAlarms();
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                customerServer.blockBankCard(blockBankCardPanel.bankCardTO);
                blockBankCardPanel.showReportPanel();
            } catch (RemoteException e) {
                //network error
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney net error :");
                e.printStackTrace();
                blockBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

            } catch (SQLException e) {
                //BankCard info is wrong.
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney sql error :");
                e.printStackTrace();
                blockBankCardPanel.addAlarmToAlarmPanel("اطلاعات کارت صحیح نمیباشد.");
            }
        } catch (Exception e) {
            System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney other error :");
            e.printStackTrace();
            blockBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
        }

    }

    public void showFirstPanel() {
        blockBankCardPanel.showFirstPanel();
    }
}