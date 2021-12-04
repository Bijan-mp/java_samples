package controller;

import model.bl.CustomerServerInterface;
import model.to.CheckingAccountTO;
import model.to.DepositAccountTO;
import view.DepositAccountListPanel;
import view.DepositAccountListPanel;
import view.MainFrame;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DepositAccountListPanelController {

    public DepositAccountListPanel depositAccountListPanel;
    public DepositAccountListPanelController(DepositAccountListPanel depositAccountListPanel){
        this.depositAccountListPanel = depositAccountListPanel;
        getDepositAccountList();
    }

    public void getDepositAccountList() {
        try {
            try {
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<DepositAccountTO> depositAccountTOArrayList = customerServer.viewAllDepositAccounts(MainFrame.loggedInCode);
                if (depositAccountTOArrayList.isEmpty()) {
                    depositAccountListPanel.addAlarmToAlarmPanel("هیچ حساب پس اندازی برای شما ثبت نشده است.");
                } else {
                    depositAccountListPanel.showDepositAccountList(depositAccountTOArrayList);
                }
            } catch (RemoteException e) {
                //show Alarm for network error
                depositAccountListPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز");
                System.out.println("DepositAccountListPanelController.getCheckingAccountList() err : ");
                e.printStackTrace();

            }
        } catch (Exception e) {
            //show Alarm for database error
            depositAccountListPanel.addAlarmToAlarmPanel("اشکال در خواندن داده ها");
            System.out.println("DepositAccountListPanelController.getCheckingAccountList() err : ");
            e.printStackTrace();

        }
    }
}
