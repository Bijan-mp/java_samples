package controller;

import model.bl.CustomerServerInterface;
import model.to.BankTransactionTO;
import model.to.CheckingAccountTO;
import model.to.CustomerTO;
import view.MainFrame;
import view.MoneyTransferFromCheckingAccountToCheckingAccountPanel;
import tools.RegularExpression;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class MoneyTransferFromCheckingAccountToCheckingAccountPanelController implements ActionListener {

    private MoneyTransferFromCheckingAccountToCheckingAccountPanel moneyTransferFromCheckingAccountToCheckingAccountPanel;

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("checkMoneyTransferData")) {
            checkMoneyTransferData();
        } else if (e.getActionCommand().equals("confirmTransferMoney")) {
            transferMoney();
        } else if (e.getActionCommand().equals("goBack")) {
            goFirstPanel();
        }
    }

    public MoneyTransferFromCheckingAccountToCheckingAccountPanelController(MoneyTransferFromCheckingAccountToCheckingAccountPanel moneyTransferFromCheckingAccountToCheckingAccountPanel) {
        this.moneyTransferFromCheckingAccountToCheckingAccountPanel = moneyTransferFromCheckingAccountToCheckingAccountPanel;
    }

    public void getCheckingAccountList() {
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<CheckingAccountTO> checkingAccountTOArrayList = customerServer.viewAllCheckingAccounts(MainFrame.loggedInCode);
                if (checkingAccountTOArrayList.isEmpty()) {
                    moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("هیچ حساب جاری برای شما ثبت نشده است.");
                } else {
                    moneyTransferFromCheckingAccountToCheckingAccountPanel.showCheckingAccountList(checkingAccountTOArrayList);
                }
            } catch (RemoteException e) {
                //show Alarm for network error
                moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز");
                System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
                e.printStackTrace();

            }
        } catch (Exception e) {
            //show Alarm for database error
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("اشکال در خواندن داده ها");
            System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
            e.printStackTrace();

        }
    }

    public void checkMoneyTransferData() {

        moneyTransferFromCheckingAccountToCheckingAccountPanel.clearAlarmPanelAlarms();

        boolean trueCondition = true;
        int selectedRow;
        Object selectedSourceCheckingAccountId = new Long(0l);

        if (moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueTextField.getText().isEmpty()) {
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ مورد نظر برای انتقال را وارد کنید.");
            moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueTextField.getText()))) {
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ باید فقط شامل ارقام باشد.");
            moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        } else if (Long.parseLong(moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueTextField.getText()) < 0 ||
                Long.parseLong(moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueTextField.getText()) == 0 ){
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ نمیتواند 0 یا کمتر از 0 باشد .");
            moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountTextField.getText().isEmpty()) {
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب مقصد را وارد کنید.");
            moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountTextField.getText()))) {
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب باید فقط شامل ارقام باشد.");
            moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountStarLabel.setVisible(true);
            trueCondition = false;
        }
        try {
            selectedRow = moneyTransferFromCheckingAccountToCheckingAccountPanel.checkingAccountTable.getSelectedRow();
            selectedSourceCheckingAccountId = moneyTransferFromCheckingAccountToCheckingAccountPanel.checkingAccountTable.getValueAt(selectedRow, 0);
        } catch (Exception e) {
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("یک حساب را از لیست حسابهای جاری انتخاب کنید.");
            trueCondition = false;
        }

        if (trueCondition) {
            long sourceCheckingAccountId = Long.parseLong(selectedSourceCheckingAccountId.toString());
            long destinationCheckingAccountId = Long.parseLong(moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountTextField.getText());
            long money = Long.parseLong(moneyTransferFromCheckingAccountToCheckingAccountPanel.moneyValueTextField.getText());

            try {
                try {

                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");

                    if (!customerServer.existCheckingAccountId(destinationCheckingAccountId)) {
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("شماره حساب مقصد نامعتبر است .");
                        trueCondition = false;
                    }
                    if (!customerServer.isCurrentUserCheckingAccount(MainFrame.loggedInCode, sourceCheckingAccountId)) {
                        MainFrameHolder.getMainFrame().showLoginPanel();
                    } else {
                        CheckingAccountTO sourceCheckingAccountTO = customerServer.getCheckingAccountDetails(MainFrame.loggedInCode, sourceCheckingAccountId);
                        if (sourceCheckingAccountTO.getMoney() < money) {
                            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("مبلغ وارد شده از موجودی حساب بیشتر است .");
                            trueCondition = false;
                        }
                    }
                    if (trueCondition) {
                        CustomerTO destinationAccountOwnerCustomerTO = customerServer.getCheckingAccountOwner(destinationCheckingAccountId);
                        String destinationCheckingAccountOwner = destinationAccountOwnerCustomerTO.getName() + destinationAccountOwnerCustomerTO.getFamily();
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.remove(moneyTransferFromCheckingAccountToCheckingAccountPanel.firstPanel);
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.showConfirmPanel(sourceCheckingAccountId, destinationCheckingAccountId, destinationCheckingAccountOwner, money);
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }
    }

    public void transferMoney() {
        moneyTransferFromCheckingAccountToCheckingAccountPanel.clearAlarmPanelAlarms();
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                BankTransactionTO bankTransactionTO = customerServer.checkingAccountMoneyTransfer(
                        MainFrame.loggedInCode,
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.sourceCheckingAccountId,
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.destinationCheckingAccountId,
                        moneyTransferFromCheckingAccountToCheckingAccountPanel.money);
                moneyTransferFromCheckingAccountToCheckingAccountPanel.showReportPanel(bankTransactionTO);
            } catch (RemoteException e) {
                //network error
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney net error :");
                e.printStackTrace();
                moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

            }
        } catch (Exception e) {
            System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.transferMoney other error :");
            e.printStackTrace();
            moneyTransferFromCheckingAccountToCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
        }

    }

    public void goFirstPanel() {
        moneyTransferFromCheckingAccountToCheckingAccountPanel.showFirstPanel();
    }
}
