package view;

import controller.MainFrameHolder;
import controller.TransactionPursuitPanelController;
import model.to.BankTransactionTO;
import org.jdesktop.swingx.table.LabelProperties;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class TransactionPursuitPanel extends BasePanel {

    public JLabel transactionIdLabel;
    public JLabel transactionIdStarLabel;
    public JTextField transactionIdTextField;
    public JButton pursuitTransactionButton;
    public JLabel transactionReportLabel;
    public JButton goBackButton;

    public JPanel firstPanel;
    public JPanel reportPanel;

    public TransactionPursuitPanelController transactionPursuitPanelController;

    public TransactionPursuitPanel() {

        super();

        titleLabel.setText("پیگیری تراکنش");
        this.transactionPursuitPanelController = new TransactionPursuitPanelController(this);
        showFirstPanel();

    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        if (currentPanel == null) {
            setCurrentPanel(firstPanel);
        } else {
            setCurrentPanel(firstPanel);
        }

        transactionIdLabel = new JLabel("شماره تراکنش : ");
        transactionIdStarLabel = new JLabel("*");
        transactionIdStarLabel.setVisible(false);
        transactionIdTextField = new JTextField(20);
        pursuitTransactionButton = new JButton("پیگیری تراکنش");
        pursuitTransactionButton.setActionCommand("pursuitTransaction");
        pursuitTransactionButton.addActionListener(transactionPursuitPanelController);

        firstPanel.add("center", transactionIdLabel);
        firstPanel.add("center", transactionIdTextField);
        firstPanel.add("center", transactionIdStarLabel);
        firstPanel.add("br center", pursuitTransactionButton);

    }

    public void showReportPanel(BankTransactionTO bankTransactionTO) {
        reportPanel = new JPanel();
        reportPanel.setLayout(new RiverLayout());
        reportPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(reportPanel);

        String report = "<html> <div align=\"right\">";
        report += "نوع تراکنش : " + bankTransactionTO.getTitle() + "<br/><br/>";
        report += "توضیحات ثبت شده : " + bankTransactionTO.getDescription() + "<br/>";
        report +="</div>" ;
        report += "<div align=\"center\">-----------------------------------------------------------------------</div>" ;
        report +="<html> <div align=\"right\">";
        report += " مشخصات ثبت شده : "+ "<br/>";
        report += "شماره حساب مبدأ : " + bankTransactionTO.getSourceCheckingAccountId() + "<br/>";
        report += "شماره کارت مبدأ : " + bankTransactionTO.getSourceBankCardId() + "<br/>";
        report += "شماره حساب مقصد : " + bankTransactionTO.getDestinationCheckingAccountId() + "<br/>";
        report += "شماره کارت مقصد : " + bankTransactionTO.getDestinationBankCardId() + "<br/>";
        report += "مبلغ تراکنش : " + bankTransactionTO.getMoney();
        report +="</div></html>" ;
        transactionReportLabel = new JLabel(report);
        transactionIdStarLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        goBackButton = new JButton("بازکشت به صفحه اول");
        goBackButton.setActionCommand("goBack");
        goBackButton.addActionListener(transactionPursuitPanelController);

        reportPanel.add("center", transactionReportLabel);
        reportPanel.add("br center", goBackButton);
    }

    public void clearErrorPanelErrors() {
        super.clearAlarmPanelAlarms();
        if (transactionIdStarLabel != null) {
            transactionIdStarLabel.setVisible(false);
        }
    }


}
