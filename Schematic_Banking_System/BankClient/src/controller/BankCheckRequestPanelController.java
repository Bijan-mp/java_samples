package controller;


import model.bl.CustomerServerInterface;
import model.to.CheckingAccountTO;
import model.to.CustomerTO;
import tools.RegularExpression;
import view.BankCheckRequestPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class BankCheckRequestPanelController implements ActionListener {

    BankCheckRequestPanel bankCheckRequestPanel;

    public BankCheckRequestPanelController(BankCheckRequestPanel bankCheckRequestPanel) {
        this.bankCheckRequestPanel = bankCheckRequestPanel;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("BankCheckRequest")) {
            checkRequest();
        } else if (e.getActionCommand().equals("s")) {

        }

    }

    public void getCheckingAccountList() {
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<CheckingAccountTO> checkingAccountTOArrayList = customerServer.viewAllCheckingAccounts(MainFrame.loggedInCode);
                if (checkingAccountTOArrayList.isEmpty()) {
                    bankCheckRequestPanel.addAlarmToAlarmPanel("هیچ حساب جاری برای شما ثبت نشده است.");
                } else {
                    bankCheckRequestPanel.showCheckingAccountList(checkingAccountTOArrayList);
                }
            } catch (RemoteException e) {
                //show Alarm for network error
                bankCheckRequestPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز");
                System.out.println("BankCheckRequestPanelController.getCheckingAccountList() err : ");
                e.printStackTrace();

            }
        } catch (Exception e) {
            //show Alarm for database error
            bankCheckRequestPanel.addAlarmToAlarmPanel("اشکال در خواندن داده ها");
            System.out.println("BankCheckRequestPanelController.getCheckingAccountList() err : ");
            e.printStackTrace();

        }
    }

    public void checkRequest() {

        bankCheckRequestPanel.clearAlarmPanelAlarms();

        boolean trueCondition = true;
        int selectedRow;
        Object selectedSourceCheckingAccountId = new Long(0l);

        try {
            selectedRow = bankCheckRequestPanel.checkingAccountTable.getSelectedRow();
            selectedSourceCheckingAccountId = bankCheckRequestPanel.checkingAccountTable.getValueAt(selectedRow, 0);
        } catch (Exception e) {
            bankCheckRequestPanel.addAlarmToAlarmPanel("یک حساب را از لیست حسابهای جاری انتخاب کنید.");
            trueCondition = false;
        }

        if (trueCondition) {
            long sourceCheckingAccountId = Long.parseLong(selectedSourceCheckingAccountId.toString());
            try {
                try {

                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    if (!customerServer.isCurrentUserCheckingAccount(MainFrame.loggedInCode, sourceCheckingAccountId)) {
                        MainFrameHolder.getMainFrame().showLoginPanel();
                    } else {

                        if (customerServer.isBankCheckRequestPossible(MainFrame.loggedInCode, sourceCheckingAccountId)) {
                            customerServer.requestBankCheck(MainFrame.loggedInCode, sourceCheckingAccountId);
                            bankCheckRequestPanel.addAlarmToAlarmPanel("درخواست شما با موفقیت انجام شد. تا فردا دسته چک درخواستی برای شما پست میشود.");
                        } else {

                            String error = "<html> <div align=\"right\">";
                            error += "درخاست برای شماره حساب انتخاب شده امکانپذیر نمیباشد  <br/> (ممکن است چک برگشتی داشته باشید .  <br/> ممکن است نوز تمام برگه های چک قبلی شما به بانک باز نگشته باشد . <br/> ممکن است درخواست قبلی شما هنوز چک نشده باشد.).";
                            error +="</div></html>";
                            bankCheckRequestPanel.addAlarmToAlarmPanel(error);
                        }
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("BankCheckRequestPanelController.checkRequest net error :");
                    e.printStackTrace();
                    bankCheckRequestPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                }
            } catch (Exception e) {
                System.out.println("BankCheckRequestPanelController.checkRequest other error :");
                e.printStackTrace();
                bankCheckRequestPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }
    }

}

