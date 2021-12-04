package controller;


import model.bl.CustomerServerInterface;
import model.to.BankCardTO;
import model.to.BankTransactionTO;
import model.to.BillTO;
import model.to.CheckingAccountTO;
import tools.PersianDate;
import tools.RegularExpression;
import view.MainFrame;
import view.PayBillByBankCardPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PayBillByBankCardController implements ActionListener {

    public PayBillByBankCardPanel payBillByBankCardPanel;

    public PayBillByBankCardController(PayBillByBankCardPanel payBillByBankCardPanel) {
        this.payBillByBankCardPanel = payBillByBankCardPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("checkBill")) {
            checkBill();
        } else if (e.getActionCommand().equals("confirmPayBill")) {
            payBill();

        } else if (e.getActionCommand().equals("goFirst")) {
            payBillByBankCardPanel.showFirstPanel();
        }
    }

    public void checkBill() {

        payBillByBankCardPanel.clearAlarmPanelAlarms();
        Boolean trueCondition = true;

        if (payBillByBankCardPanel.billIdTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شناسه قبض را وارد کنید.");
            payBillByBankCardPanel.billIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.billIdTextField.getText()))) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شناسه قبض فقط باید شامل ارقام باشد.");
            payBillByBankCardPanel.billIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.billPayIdTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شناسه پرداخت را وارد کنید.");
            payBillByBankCardPanel.billPayIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.billPayIdTextField.getText()))) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شناسه پرداخت فقط باید شامل ارقام باشد.");
            payBillByBankCardPanel.billPayIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.bankCardIdTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شماره کارت را وارد کنید.");
            payBillByBankCardPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.bankCardIdTextField.getText()))) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("شماره کارت باید فقط شامل ارقام باشد");
            payBillByBankCardPanel.bankCardIdStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.bankCardInternetPasswordTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("رمز کارت را وارد کنید.");
            payBillByBankCardPanel.bankCardInternetPasswordStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.bankCardCVV2TextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("CVV2" + "را پر کنید.");
            payBillByBankCardPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.bankCardCVV2TextField.getText()))) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("CVV2 فقط باید شامل ارقام باشد.");
            payBillByBankCardPanel.bankCardCVV2StarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.bankCardExpireDateMonthTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("ماه را وارد کنید.");
            payBillByBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.bankCardCVV2TextField.getText())) ||
                Long.parseLong(payBillByBankCardPanel.bankCardExpireDateMonthTextField.getText()) > 12) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("ماه را بصورت صحیح وارد کنید.");
            payBillByBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }
        if (payBillByBankCardPanel.bankCardExpireDateYearTextField.getText().isEmpty()) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("سال را وارد کنید.");
            payBillByBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(payBillByBankCardPanel.bankCardExpireDateYearTextField.getText())) ||
                Long.parseLong(payBillByBankCardPanel.bankCardExpireDateYearTextField.getText()) < (Math.abs(PersianDate.getDateAsNumber() / 10000))) {
            payBillByBankCardPanel.addAlarmToAlarmPanel("سال را بصورت صحیح وارد کنید.");
            payBillByBankCardPanel.bankCardExpireDateStarLabel.setVisible(true);
            trueCondition = false;
        }


        if (trueCondition) {

            long billId = Long.parseLong(payBillByBankCardPanel.billIdTextField.getText());
            long billPayId = Long.parseLong(payBillByBankCardPanel.billPayIdTextField.getText());

            BankCardTO bankCardTO = new BankCardTO();
            bankCardTO.setId(Long.parseLong(payBillByBankCardPanel.bankCardIdTextField.getText()));
            bankCardTO.setInternetPassword(payBillByBankCardPanel.bankCardInternetPasswordTextField.getText());
            bankCardTO.setCVV2(Integer.parseInt(payBillByBankCardPanel.bankCardCVV2TextField.getText()));
            int expireDateMonth = Integer.parseInt(payBillByBankCardPanel.bankCardExpireDateMonthTextField.getText());
            int expireDateYear = Integer.parseInt(payBillByBankCardPanel.bankCardExpireDateYearTextField.getText());
            bankCardTO.setExpireDate(expireDateYear * 100 + expireDateMonth);

            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    BillTO billTO = customerServer.getBillDetails(billId, billPayId);


                    if (billTO == null) {
                        payBillByBankCardPanel.addAlarmToAlarmPanel("قبض نامعتبر است.");
                        trueCondition = false;
                    } else if (billTO.getPayedFlag() == 1) {
                        payBillByBankCardPanel.addAlarmToAlarmPanel("این قبض  قبلا پرداخت شده است .");
                        trueCondition = false;
                    }
                    if (!customerServer.checkBankCard(bankCardTO)) {
                        payBillByBankCardPanel.addAlarmToAlarmPanel("مشخصات کارت نامعتبر است.");
                        trueCondition = false;
                    }

                    if (customerServer.getBankCardMoney(bankCardTO) < billTO.getPrice()) {
                        payBillByBankCardPanel.addAlarmToAlarmPanel("موجودی حساب کافی نیست.");
                        trueCondition = false;
                    }
                    if (trueCondition) {
                        payBillByBankCardPanel.showConfirmPanel(billTO, bankCardTO);
                    }

                } catch (RemoteException e) {
                    //network error
                    System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData net error :");
                    e.printStackTrace();
                    payBillByBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در ارتباط با مرکز.");

                }
            } catch (Exception e) {
                System.out.println("MoneyTransferFromCheckingAccountToCheckingAccountPanelController.checkMoneyTransferData other error :");
                e.printStackTrace();
                payBillByBankCardPanel.addAlarmToAlarmPanel("عدم موفقیت در گرفتن اطلاعات از مرکز.");
            }
        }


    }

    public void payBill() {
        try {
            try {
                BillTO billTO = payBillByBankCardPanel.billTO;
                BankCardTO bankCardTO = payBillByBankCardPanel.bankCardTO;
                CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                BankTransactionTO bankTransactionTO = customerServer.payBillByBankCard(billTO, bankCardTO);
                payBillByBankCardPanel.showReportPanel(bankTransactionTO);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("aaa");
            e.printStackTrace();
        }

    }


}
