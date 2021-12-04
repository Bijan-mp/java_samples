package controller;

import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController implements ActionListener {

    public MainFrame mainFrame;

    public MainFrameController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getActionCommand().equals("changePassword")){
            mainFrame.showChangePasswordPanel();
        }
        //account
        else if (event.getActionCommand().equals("checkingAccountList")) {
            mainFrame.showCheckingAccountListPanel();

        } else if (event.getActionCommand().equals("depositAccountList")) {
            mainFrame.showDepositAccountListPanel();

        } else if (event.getActionCommand().equals("requestAccount")) {
            //-
        }
        //money transfer
        else if (event.getActionCommand().equals("moneyTransferFromAndToCheckingAccount")) {
            mainFrame.showMoneyTransferFromCheckingAccountToCheckingAccountPanel();

        } else if (event.getActionCommand().equals("moneyTransferBankCardToCheckingAccount")) {
            mainFrame.showMoneyTransferFromBankCardToCheckingAccountPanel();

        } else if (event.getActionCommand().equals("moneyTransferBankCardToBankCard")) {
            //-
        }
        //Bank card
        else if (event.getActionCommand().equals("bankCardTransactionList")) {
            mainFrame.showBankCardTransactionListPanel();

        } else if (event.getActionCommand().equals("getBankCardMoney")) {
            mainFrame.showViewBankCardMoneyPanel();

        } else if (event.getActionCommand().equals("blockBankCard")) {
            mainFrame.showBlockBankCardPanel();
        }
        //Bank Check
        else if (event.getActionCommand().equals("checkBankReport")) {
            mainFrame.showBankCheckReportPanel();

        } else if (event.getActionCommand().equals("requestBankCheck")) {
            mainFrame.showBankCheckRequestPanel();

        } else if (event.getActionCommand().equals("blockCheckPaper")) {
            mainFrame.showBlockCheckPaperPanel();
        }
        //Pay Bill
        else if (event.getActionCommand().equals("payBillByBankCard")) {
            mainFrame.showPayBillByBankCardPanel();

        } else if (event.getActionCommand().equals("payBillByCheckingAccount")) {

            mainFrame.showPayBillByCheckingAccount();
        }
        //Transaction Pursuit
        else if (event.getActionCommand().equals("transactionPursuit")) {
            mainFrame.showTransactionPursuitPanel();

        }else if (event.getActionCommand().equals("logout")){
            new LoginController().logout();
        }



    }
}
