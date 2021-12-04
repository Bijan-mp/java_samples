package controller;

import model.bl.CustomerServerInterface;
import model.to.CheckingAccountTO;
import view.CheckingAccountListPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckingAccountListPanelController  implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("moneyTransfer")) {
            System.out.println("");
            int c=CheckingAccountListPanel.checkingAccountTable.getSelectedColumn();
            int r=CheckingAccountListPanel.checkingAccountTable.getSelectedRow();

            Object testO=CheckingAccountListPanel.checkingAccountTable.getValueAt(r,c);
            System.out.println(testO);
            //CheckingAccountListPanel.checkingAccountTable.getModel().
        }
    }

    public void getCheckingAccountList() {
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<CheckingAccountTO> checkingAccountTOArrayList = customerServer.viewAllCheckingAccounts(MainFrame.loggedInCode);
                if (checkingAccountTOArrayList.isEmpty()) {
                    CheckingAccountListPanel.setTitleLabelText("هیچ حساب جاری برای شما ثبت نشده است.");
                } else {
                    CheckingAccountListPanel.showCheckingAccountList(checkingAccountTOArrayList);
                }
            } catch (RemoteException e) {
                //show Alarm for network error
                CheckingAccountListPanel.setAlarmLabelText("اشکال در برقراری ارتباط با مرکز");
                System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
                e.printStackTrace();

            }
        } catch (Exception e) {
            //show Alarm for database error
            CheckingAccountListPanel.setAlarmLabelText("اشکال در خواندن داده ها");
            System.out.println("CheckingAccountListPanelController.getCheckingAccountList() err : ");
            e.printStackTrace();

        }
    }


}
