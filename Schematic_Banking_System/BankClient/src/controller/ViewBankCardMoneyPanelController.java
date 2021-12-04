package controller;

import model.bl.CustomerServerInterface;
import model.to.BankCardTO;
import tools.PersianDate;
import tools.RegularExpression;
import view.BlockBankCardPanel;
import view.ViewBankCardMoneyPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ViewBankCardMoneyPanelController implements ActionListener {

    ViewBankCardMoneyPanel viewBankCardMoneyPanel;

    public ViewBankCardMoneyPanelController(ViewBankCardMoneyPanel viewBankCardMoneyPanel) {
        this.viewBankCardMoneyPanel = viewBankCardMoneyPanel;
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("showBankCardMoney")) {
            checkBankCardData();
        } else if (e.getActionCommand().equals("goBack")) {
            showFirstPanel();
        }
    }

    public void checkBankCardData() {
        viewBankCardMoneyPanel.clearAlarmPanelAlarms();

        boolean trueCondition = true;

        if (viewBankCardMoneyPanel.bankCardIdTextField.getText().isEmpty()) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("شماره کارت را وارد کنید.");
            viewBankCardMoneyPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(viewBankCardMoneyPanel.bankCardIdTextField.getText()))) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("شماره کارت باید فقط شامل ارقام باشد");
            viewBankCardMoneyPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (viewBankCardMoneyPanel.bankCardIdTextField.getText().isEmpty()) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("رمز کارت را وارد کنید.");
            viewBankCardMoneyPanel.bankCardInternetPasswordStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (viewBankCardMoneyPanel.bankCardCVV2TextField.getText().isEmpty()) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("CVV2" + "را پر کنید.");
            viewBankCardMoneyPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(viewBankCardMoneyPanel.bankCardCVV2TextField.getText()))) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("CVV2 فقط باید شامل ارقام باشد.");
            viewBankCardMoneyPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        }
        if (viewBankCardMoneyPanel.bankCardExpireDateMonthTextField.getText().isEmpty()) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("ماه را وارد کنید.");
            viewBankCardMoneyPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(viewBankCardMoneyPanel.bankCardCVV2TextField.getText())) ||
                Long.parseLong(viewBankCardMoneyPanel.bankCardExpireDateMonthTextField.getText()) > 12) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("ماه را بصورت صحیح وارد کنید.");
            viewBankCardMoneyPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (viewBankCardMoneyPanel.bankCardExpireDateYearTextField.getText().isEmpty()) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("سال را وارد کنید.");
            viewBankCardMoneyPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(viewBankCardMoneyPanel.bankCardExpireDateYearTextField.getText())) ||
                Long.parseLong(viewBankCardMoneyPanel.bankCardExpireDateYearTextField.getText()) < (Math.abs(PersianDate.getDateAsNumber() / 10000))) {
            viewBankCardMoneyPanel.addAlarmToAlarmPanel("سال را بصورت صحیح وارد کنید.");
            viewBankCardMoneyPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (trueCondition) {

            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(Long.parseLong(viewBankCardMoneyPanel.bankCardIdTextField.getText()));
            bankCardTO.setInternetPassword(viewBankCardMoneyPanel.bankCardInternetPasswordField.getText());
            bankCardTO.setCVV2(Integer.parseInt(viewBankCardMoneyPanel.bankCardCVV2TextField.getText()));
            int expireDateMonth = Integer.parseInt(viewBankCardMoneyPanel.bankCardExpireDateMonthTextField.getText());
            int expireDateYear = Integer.parseInt(viewBankCardMoneyPanel.bankCardExpireDateYearTextField.getText());
            bankCardTO.setExpireDate(expireDateYear * 100 + expireDateMonth);

            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    customerServer.checkBankCard(bankCardTO);
                    long bankCardMoney = customerServer.getBankCardMoney(bankCardTO);

                    viewBankCardMoneyPanel.showReportPanel(bankCardTO, bankCardMoney);

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    viewBankCardMoneyPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                } catch (SQLException e) {
                    viewBankCardMoneyPanel.addAlarmToAlarmPanel("مشخصات کارت نامعتبر است.");
                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                viewBankCardMoneyPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }

    }

    public void showFirstPanel() {
        viewBankCardMoneyPanel.showFirstPanel();

    }
}
