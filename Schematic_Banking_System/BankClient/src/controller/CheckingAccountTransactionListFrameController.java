package controller;

import model.bl.CustomerServerInterface;
import model.to.BankTransactionTO;
import view.CheckingAccountTransactionListFrame;
import view.MainFrame;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class CheckingAccountTransactionListFrameController {

    public void showCheckingAccountTransactionList(long checkingAccountId){
        try{
            try{
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                ArrayList<BankTransactionTO> checkingAccountTransactionList =
                        customerServer.getCheckingAccountTransactionList(MainFrame.loggedInCode , checkingAccountId);
                if (checkingAccountTransactionList.isEmpty()){
                    CheckingAccountTransactionListFrame.setTitleLabelText("برای این حساب هیچ تراکنشی ثبت نشده است .");
                }   else {
                    CheckingAccountTransactionListFrame.showCheckingAccountTransactionList(checkingAccountTransactionList,checkingAccountId);
                }

            }   catch (RemoteException e){
                //Network error
                System.out.println("CheckingAccountTransactionListFrameController.showCheckingAccountTransactionList() : \n");
                e.printStackTrace();
                CheckingAccountTransactionListFrame.setAlarmLabelText("اشکال در برقراری ارتباط با مرکز");
            }
        }catch (Exception e){
            //Other error(sqlException and ...)
            System.out.println("CheckingAccountTransactionListFrameController.showCheckingAccountTransactionList() : \n");
            e.printStackTrace();
            CheckingAccountTransactionListFrame.setAlarmLabelText("اشکال در دریافت داد هها از پایگاه");
        }

    }


}
