package controller;


import model.bl.CustomerServerInterface;
import model.to.CheckPaperTO;
import view.BankCheckReportPanel;
import view.MainFrame;
import tools.RegularExpression;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class BankCheckReportPanelController implements ActionListener {

    private BankCheckReportPanel bankCheckReportPanel;

    public BankCheckReportPanelController(BankCheckReportPanel bankCheckReportPanel){
        this.bankCheckReportPanel = bankCheckReportPanel;

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("showCheckPaperList")) {
            showCheckPaperList();
        }
    }

    public void showCheckPaperList() {

       bankCheckReportPanel.clearAlarmPanelAlarms();
        if (bankCheckReportPanel.bankCheckIdTextField.getText().isEmpty()) {
            bankCheckReportPanel.addAlarmToAlarmPanel("فیلد شماره دسته چک باید پر شود.");
            bankCheckReportPanel.bankCheckStarLabel.setVisible(true);
        }else if (!(new RegularExpression().isNumber(bankCheckReportPanel.bankCheckIdTextField.getText()))) {
            bankCheckReportPanel.addAlarmToAlarmPanel("شماره چک تنها شامل ارقام میتواند باشد.");

        }else {
            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    ArrayList<CheckPaperTO> checkPaperList = customerServer.getBankCheckReport(MainFrame.loggedInCode,
                            Long.parseLong(bankCheckReportPanel.bankCheckIdTextField.getText()));
                    if (checkPaperList.isEmpty()){
                        bankCheckReportPanel.addAlarmToAlarmPanel("هیچ برگه چکی برای این شماره دسته چک ثبت نشده است \n" +
                                 "و یا اینکه چنین دسته چکی صادر نشده است.");
                    }else {
                         bankCheckReportPanel.showCheckPaperList(checkPaperList);
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("BankCheckReportPanelController.showCheckPaperList() RemoteException error : ");
                    e.printStackTrace();
                    bankCheckReportPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز.");
                }
            } catch (Exception e) {
                //other exception (sqlException and ...)
                System.out.println("BankCheckReportPanelController.showCheckPaperList() Exception error : ");
                e.printStackTrace();
                bankCheckReportPanel.addAlarmToAlarmPanel("اشکال در دریافت داد هها از پایگاه");
            }
        }
    }
}
