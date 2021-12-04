package view;

import controller.BankCardTransactionListPanelController;
import controller.MainFrameHolder;
import model.to.BankCardTO;
import model.to.BankTransactionTO;
import se.datadosen.component.RiverLayout;
import tools.PersianDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BankCardTransactionListPanel extends BasePanel {


    private BankCardTO bankCardTO;

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

    public BankCardTransactionListPanelController bankCardTransactionListPanelController;

    public BankCardTransactionListPanel() {

        super();

        titleLabel.setText("انتقال حساب ازکارت بانکی به حساب جاری ");

        bankCardTransactionListPanelController = new BankCardTransactionListPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);


        bankCardIdLabel = new JLabel("شماره کارت : ");
        bankCardInternetPasswordLabel = new JLabel("رمز اینترنتی کارت : ");
        bankCardCVV2Label = new JLabel("CVV2 :");
        bankCardExpireDateLabel = new JLabel("تاریخ انقضاء کارت : ");


        bankCardIdStarLabel = new JLabel("*");
        bankCardIdStarLabel.setVisible(false);
        bankCardInternetPasswordStarLabel = new JLabel("*");
        bankCardInternetPasswordStarLabel.setVisible(false);
        bankCardCVV2StarLabel = new JLabel("*");
        bankCardCVV2StarLabel.setVisible(false);
        bankCardExpireDateStarLabel = new JLabel("*");
        bankCardExpireDateStarLabel.setVisible(false);

        bankCardIdTextField = new JTextField(24);
        bankCardInternetPasswordField = new JPasswordField(16);
        bankCardCVV2TextField = new JTextField(4);
        bankCardExpireDateMonthTextField = new JTextField(2);
        bankCardExpireDateYearTextField = new JTextField(4);


        checkMoneyTransferDataButton = new JButton("بررسی اطلاعات");
        checkMoneyTransferDataButton.setActionCommand("checkBankCardData");
        checkMoneyTransferDataButton.addActionListener(bankCardTransactionListPanelController);

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
        firstPanel.add("right", bankCardExpireDateStarLabel);
        firstPanel.add("br center", checkMoneyTransferDataButton);

    }


    public void showConfirmPanel(BankCardTO bankCardTO, long destinationCheckingAccountId, String destinationCheckingAccountOwner, long money) {

        confirmPanel = new JPanel();
        confirmPanel.setLayout(new RiverLayout());
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(confirmPanel);

        this.bankCardTO = bankCardTO;


        String informationText = "<html> <div align=\"right\"";
        informationText += "انتقال حساب از کارت بانکی به حساب جاری : " + "<br/>";
        informationText += "شماره کارت مبدأ : " + bankCardTO.getId() + "<br/>";
        informationText += "حساب مقصد : " + destinationCheckingAccountId + "<br/>";
        informationText += "به نام : " + destinationCheckingAccountOwner + "<br/>";
        informationText += "مبلغ : " + money + "</div></html>";


        confirmLabel = new JLabel(informationText);
        confirmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        confirmButton = new JButton("پرداخت");
        confirmButton.setActionCommand("checkBankCardData");
        confirmButton.addActionListener(bankCardTransactionListPanelController);
        backButton = new JButton("بازگشت به عقب");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(bankCardTransactionListPanelController);

        confirmPanel.add("br br center", confirmLabel);
        confirmPanel.add("br center", confirmButton);
        confirmPanel.add("center", backButton);

    }

    public void showBankCardTransactionListTable(ArrayList<BankTransactionTO> bankTransactionTOArrayList, long bankCardId) {

        reportPanel = new JPanel();
        reportPanel.setLayout(new RiverLayout());
        reportPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(reportPanel);

        JLabel titleLabel2 = new JLabel("تراکنش های انجام شده توسط کارت بانکی به شماره : " + bankCardId);
        titleLabel2.setComponentOrientation(MainFrameHolder.getOrientation());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("شماره تراکنش");
        model.addColumn("تاریخ");
        model.addColumn(" عنوان");
        model.addColumn("شماره حساب مبدأ");
        model.addColumn("شماره کارت مبدآ");
        model.addColumn("شماره حساب مقصد");
        model.addColumn("شماره کارت مقصد");
        model.addColumn("مبلغ");
        JTable bankCardTransactionListTable = new JTable(model);
        bankCardTransactionListTable.setComponentOrientation(MainFrameHolder.getOrientation());
        JScrollPane jScrollPane = new JScrollPane(bankCardTransactionListTable);

        int i = 0;
        while (i < bankTransactionTOArrayList.size()) {
            model.addRow(new Object[]{
                    bankTransactionTOArrayList.get(i).getId(),
                    PersianDate.changeNumberToStringDate(bankTransactionTOArrayList.get(i).getDate()),
                    bankTransactionTOArrayList.get(i).getTitle(),
                    bankTransactionTOArrayList.get(i).getSourceCheckingAccountId(),
                    bankTransactionTOArrayList.get(i).getSourceBankCardId(),
                    bankTransactionTOArrayList.get(i).getDestinationCheckingAccountId(),
                    bankTransactionTOArrayList.get(i).getDestinationBankCardId(),
                    bankTransactionTOArrayList.get(i).getMoney()
            });
            i++;
        }

        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(bankCardTransactionListPanelController);

        reportPanel.add("center", titleLabel2);
        reportPanel.add("br hfill vfill", jScrollPane);
        reportPanel.add("br center", backButton);
    }



    public void clearErrorPanelErrors() {
        super.clearAlarmPanelAlarms();

        if (bankCardIdStarLabel != null) {
            bankCardIdStarLabel.setVisible(false);
            bankCardInternetPasswordStarLabel.setVisible(false);
            bankCardCVV2StarLabel.setVisible(false);
            bankCardExpireDateStarLabel.setVisible(false);
        }
    }

    public BankCardTO getBankCardTO() {
        return bankCardTO;
    }

}
