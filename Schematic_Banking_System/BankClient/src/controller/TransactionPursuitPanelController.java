package controller;

import model.bl.CustomerServerInterface;
import model.to.BankTransactionTO;
import tools.RegularExpression;
import view.MainFrame;
import view.TransactionPursuitPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class TransactionPursuitPanelController implements ActionListener {

    private TransactionPursuitPanel transactionPursuitPanel;

    public TransactionPursuitPanelController(TransactionPursuitPanel transactionPursuitPanel) {
        this.transactionPursuitPanel = transactionPursuitPanel;
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getActionCommand().equals("pursuitTransaction")) {
            pursuitTransaction();
        } else if (event.getActionCommand().equals("goBack")) {
           goBack();
        }
    }

    public void pursuitTransaction() {
        transactionPursuitPanel.clearErrorPanelErrors();
        if (transactionPursuitPanel.transactionIdTextField.getText().isEmpty()) {
            transactionPursuitPanel.addAlarmToAlarmPanel("فیلد تراکنش را پر کنید.");
            transactionPursuitPanel.transactionIdStarLabel.setVisible(true);
        } else if (!(new RegularExpression().isNumber(transactionPursuitPanel.transactionIdTextField.getText()))) {
            transactionPursuitPanel.addAlarmToAlarmPanel("فیلد تراکنش باید فقط شامل ارقام باشد.");
            transactionPursuitPanel.transactionIdStarLabel.setVisible(true);
        } else if (Long.parseLong(transactionPursuitPanel.transactionIdTextField.getText()) <= 0) {
            transactionPursuitPanel.addAlarmToAlarmPanel("شماره تراکنش نمیتواند 0 یا کوپکتر از 0 باشد.");
            transactionPursuitPanel.transactionIdStarLabel.setVisible(true);
        } else {
            try {
                long transactionId = Long.parseLong(transactionPursuitPanel.transactionIdTextField.getText());
                try {
                    CustomerServerInterface customerServer=(CustomerServerInterface) Naming.lookup("CustomerServer");
                    BankTransactionTO bankTransactionTO = customerServer.getTransactionDetails(MainFrame.loggedInCode, transactionId);
                    transactionPursuitPanel.showReportPanel(bankTransactionTO);
                } catch (RemoteException e) {
                    transactionPursuitPanel.addAlarmToAlarmPanel("اشکال در برقراری ارتباط با مرکز.");
                    e.printStackTrace();
                } catch (SQLException e) {
                    transactionPursuitPanel.addAlarmToAlarmPanel("چنین تراکنشی ثبت نشده است .");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                transactionPursuitPanel.addAlarmToAlarmPanel("اشکال در سیستم");
            }
        }
    }

    public void goBack(){
        transactionPursuitPanel.showFirstPanel();
    }

}
