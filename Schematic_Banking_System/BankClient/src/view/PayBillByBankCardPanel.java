package view;

import controller.MainFrameHolder;
import controller.PayBillByBankCardController;


import model.to.BankCardTO;
import model.to.BankTransactionTO;
import model.to.BillTO;
import org.jdesktop.swingx.table.LabelProperties;
import se.datadosen.component.RiverLayout;
import tools.PersianDate;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayBillByBankCardPanel extends BasePanel {

    public BillTO billTO;
    public BankCardTO bankCardTO;

    public JLabel billIdStarLabel;
    public JLabel billPayIdStarLabel;
    public JLabel billIdLabel;
    public JLabel billPayIdLabel;
    public JTextField billIdTextField;
    public JTextField billPayIdTextField;

    public JLabel bankCardIdLabel;
    public JLabel bankCardInternetPasswordLabel;
    public JLabel bankCardCVV2Label;
    public JLabel bankCardExpireDateLabel;
    public JLabel bankCardIdStarLabel;
    public JLabel bankCardInternetPasswordStarLabel;
    public JLabel bankCardCVV2StarLabel;
    public JLabel bankCardExpireDateStarLabel;
    public JTextField bankCardIdTextField;
    public JPasswordField bankCardInternetPasswordTextField;
    public JTextField bankCardCVV2TextField;
    public JTextField bankCardExpireDateMonthTextField;
    public JTextField bankCardExpireDateYearTextField;


    public JButton checkBillButton;

    public JLabel confirmLabel;
    public JButton confirmButton;
    public JButton backButton;
    public JButton payBillButton;

    public JLabel payDetails;
    public JButton printButton;
    public JButton savePdfButton;

    public JPanel firstPanel;
    public JPanel bankCardPanel;
    public JPanel confirmPanel;
    public JLabel reportLabel;

    public JPanel reportPanel;

    public PayBillByBankCardController payBillByBankCardController;

    public PayBillByBankCardPanel() {

        super();
        titleLabel.setText("پرداخت قبض توسک کارت بانکی");
        this.payBillByBankCardController = new PayBillByBankCardController(this);

        showFirstPanel();

    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        billIdLabel = new JLabel("شناسه قبض : ");
        billPayIdLabel = new JLabel("شناسه پرداخت : ");
        billIdTextField = new JTextField(15);
        billPayIdTextField = new JTextField(15);
        checkBillButton = new JButton("بررسی اطلاعات قبض");
        checkBillButton.setActionCommand("checkBill");
        checkBillButton.addActionListener(payBillByBankCardController);
        bankCardIdLabel = new JLabel("شماره کارت :");
        bankCardInternetPasswordLabel = new JLabel("رمز اینترنتی : ");
        bankCardCVV2Label = new JLabel(" CVV2 :");
        bankCardExpireDateLabel = new JLabel("تاریخ انقضاء : ");
        bankCardIdTextField = new JTextField(16);
        bankCardInternetPasswordTextField = new JPasswordField(20);
        bankCardCVV2TextField = new JTextField(5);
        bankCardExpireDateMonthTextField = new JTextField(2);
        bankCardExpireDateYearTextField =new  JTextField(4);
        billIdStarLabel = new JLabel("*");
        billIdStarLabel.setVisible(false);
        billPayIdStarLabel = new JLabel("*");
        billPayIdStarLabel.setVisible(false);
        bankCardIdStarLabel = new JLabel("*");
        bankCardIdStarLabel.setVisible(false);
        bankCardInternetPasswordStarLabel = new JLabel("*");
        bankCardInternetPasswordStarLabel.setVisible(false);
        bankCardCVV2StarLabel = new JLabel("*");
        bankCardCVV2StarLabel.setVisible(false);
        bankCardExpireDateStarLabel = new JLabel("*");
        bankCardExpireDateStarLabel.setVisible(false);

        firstPanel.add("right",billIdLabel);
        firstPanel.add("right tab",billIdTextField);
        firstPanel.add("right",billIdStarLabel);
        firstPanel.add("br right", billPayIdLabel);
        firstPanel.add("right tab",billPayIdTextField);
        firstPanel.add("right",billPayIdStarLabel);
        firstPanel.add("br right", bankCardIdLabel);
        firstPanel.add("right tab ", bankCardIdTextField);
        firstPanel.add("right", bankCardIdStarLabel);
        firstPanel.add("br right", bankCardInternetPasswordLabel);
        firstPanel.add("right tab ", bankCardInternetPasswordTextField);
        firstPanel.add("right", bankCardInternetPasswordStarLabel);
        firstPanel.add("br right", bankCardCVV2Label);
        firstPanel.add("right tab tab ", bankCardCVV2TextField);
        firstPanel.add("right", bankCardCVV2StarLabel);
        firstPanel.add("br right", bankCardExpireDateLabel);
        firstPanel.add("right tab tab", bankCardExpireDateMonthTextField);
        firstPanel.add("right", bankCardExpireDateYearTextField);
        firstPanel.add("right",bankCardExpireDateStarLabel);
        firstPanel.add("br right", checkBillButton);

    }

    public void showConfirmPanel(BillTO billTO,BankCardTO bankCardTO) {

        this.billTO = billTO;
        this.bankCardTO = bankCardTO;

        confirmPanel = new JPanel();
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        confirmPanel.setLayout(new RiverLayout());
        setCurrentPanel(confirmPanel);


        String msg = "<html><div align=\"right\">";
        msg += "شناسه قبض : ";
        msg += billTO.getId() + "<br />";
        msg += " شناسه پرداخت : ";
        msg += billTO.getPayId() + "<br />";
        msg += "نوع قبض : ";
        msg += billTO.getType() + "<br />";
        msg += "مبلغ قبض : ";
        msg += billTO.getPrice();
        msg += "</div></html>";


        confirmLabel = new JLabel(msg);
        confirmButton = new JButton("تایید");
        confirmButton.setActionCommand("confirmPayBill");
        confirmButton.addActionListener(payBillByBankCardController);

        confirmPanel.add("center", confirmLabel);
        confirmPanel.add("br center", confirmButton);

    }


    public void showReportPanel(BankTransactionTO bankTransactionTO) {

        reportPanel = new JPanel();
        reportPanel.setLayout(new RiverLayout());
        reportPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(reportPanel);

        String reportText = "<html> <div align=\"right\"> <br/>";
        reportText += bankTransactionTO.getTitle() + "<br/>";
        reportText += "تاریخ : " + PersianDate.changeNumberToStringDate(bankTransactionTO.getDate()) + "<br/>";
        reportText += bankTransactionTO.getDescription() + "<br/>";
        reportText += "شماره حساب مبدأ : " + bankTransactionTO.getSourceCheckingAccountId() + "<br/>";
        reportText +="</div></html>" ;
        reportLabel = new JLabel(reportText);
        reportLabel.setComponentOrientation(MainFrameHolder.getOrientation());

        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goFirst");
        backButton.addActionListener(payBillByBankCardController);
        reportPanel.add("center",reportLabel);
        reportPanel.add("br center",backButton);

    }

    public void clearAlarmPanelAlarms() {
        super.clearAlarmPanelAlarms();
        billPayIdStarLabel.setVisible(false);
        billIdStarLabel.setVisible(false);
        bankCardIdStarLabel.setVisible(false);
        bankCardInternetPasswordStarLabel.setVisible(false);
        bankCardCVV2StarLabel.setVisible(false);
        bankCardExpireDateStarLabel.setVisible(false);
    }


}
