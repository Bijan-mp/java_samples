package view;

import controller.MainFrameHolder;
import controller.MoneyTransferFromBankCardToCheckingAccountPanelController;
import model.to.BankCardTO;
import model.to.BankTransactionTO;
import se.datadosen.component.RiverLayout;
import tools.PersianDate;

import javax.swing.*;

public class MoneyTransferFromBankCardToCheckingAccountPanel extends BasePanel {


    private BankCardTO bankCardTO;
    private long destinationCheckingAccountId;

    private long money;

    public JLabel moneyValueLabel;
    public JTextField moneyValueTextField;
    public JLabel moneyValueStarLabel;

    public JLabel destinationCheckingAccountIdLabel;
    public JTextField destinationCheckingAccountTextField;
    public JLabel destinationCheckingAccountStarLabel;

    public JLabel bankCardIdLabel;
    public JLabel bankCardInternetPasswordLabel;
    public JLabel bankCardCVV2Label;
    public JLabel bankCardExpireDateLabel;
    public JLabel bankCardIdStarLabel;
    public JLabel bankCardInternetPasswordStarLabel;
    public JLabel bankCardCVV2StarLabel;
    public JLabel bankCardExpireDateStarLabel;
    public JTextField bankCardIdTextField;
    public JPasswordField bankCardInternetPasswordField;
    public JTextField bankCardCVV2TextField;
    public JTextField bankCardExpireDateMonthTextField;
    public JTextField bankCardExpireDateYearTextField;


    public JButton checkMoneyTransferDataButton;
    public JButton confirmButton;
    public JLabel confirmLabel;
    public JLabel reportLabel;
    public JButton backButton;
    public JPanel firstPanel;
    public JPanel confirmPanel;
    public JPanel reportPanel;

    public MoneyTransferFromBankCardToCheckingAccountPanelController moneyTransferFromBankCardToCheckingAccountPanelController;

    public MoneyTransferFromBankCardToCheckingAccountPanel() {
        super();
        titleLabel.setText("انتقال وجه از کارت بانکی به حساب جاری");
        moneyTransferFromBankCardToCheckingAccountPanelController = new MoneyTransferFromBankCardToCheckingAccountPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
            setCurrentPanel(firstPanel);

        moneyValueLabel = new JLabel("مبلغ : ");
        destinationCheckingAccountIdLabel = new JLabel("شماره حساب مقصد : ");
        bankCardIdLabel = new JLabel("شماره کارت : ");
        bankCardInternetPasswordLabel = new JLabel("رمز اینترنتی کارت : ");
        bankCardCVV2Label = new JLabel("CVV2 :");
        bankCardExpireDateLabel = new JLabel("تاریخ انقضاء کارت : ");


        moneyValueStarLabel = new JLabel("*");
        moneyValueStarLabel.setVisible(false);
        destinationCheckingAccountStarLabel = new JLabel("*");
        destinationCheckingAccountStarLabel.setVisible(false);
        bankCardIdStarLabel = new JLabel("*");
        bankCardIdStarLabel.setVisible(false);
        bankCardInternetPasswordStarLabel = new JLabel("*");
        bankCardInternetPasswordStarLabel.setVisible(false);
        bankCardCVV2StarLabel = new JLabel("*");
        bankCardCVV2StarLabel.setVisible(false);
        bankCardExpireDateStarLabel = new JLabel("*");
        bankCardExpireDateStarLabel.setVisible(false);

        moneyValueTextField = new JTextField(14);
        destinationCheckingAccountTextField = new JTextField(14);
        bankCardIdTextField = new JTextField(24);
        bankCardInternetPasswordField = new JPasswordField(16);
        bankCardCVV2TextField = new JTextField(4);
        bankCardExpireDateMonthTextField = new JTextField(2);
        bankCardExpireDateYearTextField = new JTextField(4);



        checkMoneyTransferDataButton = new JButton("بررسی اطلاعات");
        checkMoneyTransferDataButton.setActionCommand("checkMoneyTransferData");
        checkMoneyTransferDataButton.addActionListener(moneyTransferFromBankCardToCheckingAccountPanelController);



        firstPanel.add("right", moneyValueLabel);
        firstPanel.add("right tab tab", moneyValueTextField);
        firstPanel.add("right", moneyValueStarLabel);
        firstPanel.add("br right", destinationCheckingAccountIdLabel);
        firstPanel.add("right tab", destinationCheckingAccountTextField);
        firstPanel.add("right", destinationCheckingAccountStarLabel);
        firstPanel.add("br right", bankCardIdLabel);
        firstPanel.add("right tab ", bankCardIdTextField);
        firstPanel.add("right", bankCardIdStarLabel);
        firstPanel.add("br right", bankCardInternetPasswordLabel);
        firstPanel.add("right tab ", bankCardInternetPasswordField);
        firstPanel.add("right", bankCardInternetPasswordStarLabel);
        firstPanel.add("br right", bankCardCVV2Label);
        firstPanel.add("right tab tab ", bankCardCVV2TextField);
        firstPanel.add("right", bankCardCVV2StarLabel);
        firstPanel.add("br right", bankCardExpireDateLabel);
        firstPanel.add("right tab tab", bankCardExpireDateMonthTextField);
        firstPanel.add("right", bankCardExpireDateYearTextField);
        firstPanel.add("right",bankCardExpireDateStarLabel);
        firstPanel.add("br center", checkMoneyTransferDataButton);

    }


    public void showConfirmPanel(BankCardTO bankCardTO, long destinationCheckingAccountId, String destinationCheckingAccountOwner, long money) {

        confirmPanel = new JPanel();
        confirmPanel.setLayout(new RiverLayout());
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(confirmPanel);

        this.bankCardTO = bankCardTO;
        this.destinationCheckingAccountId = destinationCheckingAccountId;
        this.money = money;

        String informationText = "<html> <div align=\"right\">";
        informationText += "انتقال حساب از کارت بانکی به حساب جاری : " + "<br/>";
        informationText += "شماره کارت مبدأ : " + bankCardTO.getId() + "<br/>";
        informationText += "حساب مقصد : " + destinationCheckingAccountId + "<br/>";
        informationText += "به نام : " + destinationCheckingAccountOwner + "<br/>";
        informationText += "مبلغ : " + money + "</div></html>";


        confirmLabel = new JLabel(informationText);
        confirmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        confirmButton = new JButton("پرداخت");
        confirmButton.setActionCommand("confirmTransferMoney");
        confirmButton.addActionListener(moneyTransferFromBankCardToCheckingAccountPanelController);
        backButton = new JButton("بازگشت به عقب");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(moneyTransferFromBankCardToCheckingAccountPanelController);

       // confirmPanel.add("right", errorPanel);
        confirmPanel.add("br br center", confirmLabel);
        confirmPanel.add("br center", confirmButton);
        confirmPanel.add("center", backButton);

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
        reportText += "شماره حساب مقصد : " + bankTransactionTO.getDestinationCheckingAccountId() + "<br/>";
        reportText += "مبلغ انتقال یافته : " + bankTransactionTO.getMoney();
        reportText += "</div></html>";

        reportLabel = new JLabel(reportText);
        reportLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(moneyTransferFromBankCardToCheckingAccountPanelController);
        reportPanel.add("center", reportLabel);
        reportPanel.add("br center", backButton);
    }



    public void clearErrorPanelErrors() {
        super.clearAlarmPanelAlarms();
        if (moneyValueStarLabel != null){
            moneyValueStarLabel.setVisible(false);
            destinationCheckingAccountStarLabel.setVisible(false);
            bankCardIdStarLabel.setVisible(false);
            bankCardInternetPasswordStarLabel.setVisible(false);
            bankCardCVV2StarLabel.setVisible(false);
            bankCardExpireDateStarLabel.setVisible(false);
        }
    }

    public BankCardTO getBankCardTO() {
        return bankCardTO;
    }

    public long getDestinationCheckingAccountId() {
        return destinationCheckingAccountId;
    }

    public long getMoney() {
        return money;
    }


}
