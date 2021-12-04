package controller;

import model.bl.CustomerServerInterface;
import model.to.BankTransactionTO;
import model.to.BillTO;
import model.to.CheckingAccountTO;
import model.to.CustomerTO;
import tools.RegularExpression;
import view.MainFrame;
import view.PayBillByBankCardPanel;
import view.PayBillByCheckingAccountPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PayBillByCheckingAccountPanelController implements ActionListener {

    public PayBillByCheckingAccountPanel payBillByCheckingAccountPanel;

    public PayBillByCheckingAccountPanelController(PayBillByCheckingAccountPanel payBillByCheckingAccountPanel) {
        this.payBillByCheckingAccountPanel = payBillByCheckingAccountPanel;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("checkPayBillData")) {
            checkBill();
        } else if (e.getActionCommand().equals("payBill")) {
            payBill();
        } else if (e.getActionCommand().equals("goFirst")){
              payBillByCheckingAccountPanel.showFirstPanel();
        }
    }

    public void getCheckingAccountList() {
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<CheckingAccountTO> checkingAccountTOArrayList = customerServer.viewAllCheckingAccounts(MainFrame.loggedInCode);
                if (checkingAccountTOArrayList.isEmpty()) {
                    payBillByCheckingAccountPanel.addAlarmToAlarmPanel("هیچ حساب جاری برای شما ثبت نشده است.");
                } else {
                    payBillByCheckingAccountPanel.showCheckingAccountList(checkingAccountTOArrayList);
                }
            } catch (RemoteException e) {
                //show Alarm for network error
                payBillByCheckingAccountPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز");
                System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
                e.printStackTrace();

            }
        } catch (Exception e) {
            //show Alarm for database error
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("اشکال در خواندن داده ها");
            System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
            e.printStackTrace();

        }
    }

    public void checkBill() {

        payBillByCheckingAccountPanel.clearAlarmPanelAlarms();
        Boolean trueCondition = true;
        int selectedRow;
        Object selectedSourceCheckingAccountId = new Long(0l);

        if (payBillByCheckingAccountPanel.billIdTextField.getText().isEmpty()) {
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("شناسه قبض را وارد کنید.");
            payBillByCheckingAccountPanel.billIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByCheckingAccountPanel.billIdTextField.getText()))) {
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("شناسه قبض فقط باید شامل ارقام باشد.");
            payBillByCheckingAccountPanel.billIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByCheckingAccountPanel.billPayIdTextField.getText().isEmpty()) {
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("شناسه پرداخت را وارد کنید.");
            payBillByCheckingAccountPanel.billPayIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByCheckingAccountPanel.billPayIdTextField.getText()))) {
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("شناسه پرداخت فقط باید شامل ارقام باشد.");
            payBillByCheckingAccountPanel.billPayIdStarLabel.setVisible(true);
            trueCondition = false;
        }

        try {
            selectedRow = payBillByCheckingAccountPanel.checkingAccountTable.getSelectedRow();
            selectedSourceCheckingAccountId = payBillByCheckingAccountPanel.checkingAccountTable.getValueAt(selectedRow, 0);
        } catch (Exception e) {
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("یک حساب را از لیست حسابهای جاری انتخاب کنید.");
            trueCondition = false;
        }

        if (trueCondition) {

            long sourceCheckingAccountId = Long.parseLong(selectedSourceCheckingAccountId.toString());
            long billId = Long.parseLong(payBillByCheckingAccountPanel.billIdTextField.getText());
            Long billPayId = Long.parseLong(payBillByCheckingAccountPanel.billPayIdTextField.getText());
            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    BillTO billTO = customerServer.getBillDetails(billId, billPayId);
                    if (billTO == null) {
                        payBillByCheckingAccountPanel.addAlarmToAlarmPanel("قبض نامعتبر است.");
                        trueCondition = false;
                    } else if (billTO.getPayedFlag() == 1) {
                        payBillByCheckingAccountPanel.addAlarmToAlarmPanel("این قبض  قبلا پرداخت شده است .");
                        trueCondition = false;
                    }
                    if (!customerServer.isCurrentUserCheckingAccount(MainFrame.loggedInCode, sourceCheckingAccountId)) {
                        MainFrameHolder.getMainFrame().showLoginPanel();
                    } else {
                        CheckingAccountTO sourceCheckingAccountTO = customerServer.getCheckingAccountDetails(MainFrame.loggedInCode, sourceCheckingAccountId);
                        if (sourceCheckingAccountTO.getMoney() < billTO.getPrice()) {
                            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("موجودی حساب کافی نیست.");
                            trueCondition = false;
                        }
                    }
                    if (trueCondition) {
                        payBillByCheckingAccountPanel.showConfirmPanel(billTO, sourceCheckingAccountId);
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    payBillByCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                payBillByCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }

    }

    public void payBill() {
        try {
            try {
                BillTO billTO = payBillByCheckingAccountPanel.billTO;
                long sourceCheckingAccountId = payBillByCheckingAccountPanel.sourceCheckingAccountId;
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                BankTransactionTO bankTransactionTO = customerServer.payBillByCheckingAccount(
                        MainFrame.loggedInCode,
                        billTO,
                        sourceCheckingAccountId);
                System.out.println(bankTransactionTO.getDate());
                payBillByCheckingAccountPanel.showReportPanel(bankTransactionTO);

            } catch (RemoteException e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                e.printStackTrace();
                payBillByCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");
            }
        } catch (Exception e) {
            System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
            e.printStackTrace();
            payBillByCheckingAccountPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
        }

    }
}
