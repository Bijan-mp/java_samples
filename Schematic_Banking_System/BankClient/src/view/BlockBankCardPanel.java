package view;

import controller.BlockBankCardPanelController;
import controller.MainFrameHolder;
import model.to.BankCardTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class BlockBankCardPanel extends BasePanel {


    public BankCardTO bankCardTO;

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


    public JButton blockBankCardButton;
    public JButton confirmButton;
    public JLabel confirmLabel;
    public JLabel reportLabel;
    public JButton backButton;
    public JPanel firstPanel;
    public JPanel confirmPanel;
    public JPanel reportPanel;
    public JPanel currentPanel;
    public JPanel errorPanel;

    public BlockBankCardPanelController blockBankCardPanelController;


    public BlockBankCardPanel() {

        super();
        titleLabel.setText("مسدود کردن کارت بانکی");
        this.blockBankCardPanelController = new BlockBankCardPanelController(this);
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

        blockBankCardButton = new JButton("مسدود شود");
        blockBankCardButton.setActionCommand("checkBlockBankCardData");
        blockBankCardButton.addActionListener(blockBankCardPanelController);

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
        firstPanel.add("br center", blockBankCardButton);

    }


    public void showConfirmPanel(BankCardTO bankCardTO) {

        confirmPanel = new JPanel();
        confirmPanel.setLayout(new RiverLayout());
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(confirmPanel);

        this.bankCardTO = bankCardTO;

        String informationText = "<html> <div align=\"right\"";
        informationText += "مسدودیت کارت بانکی" + "<br/>";
        informationText += "شماره کارت" + bankCardTO.getId() + "<br/>";
        informationText += "</div></html>";


        confirmLabel = new JLabel(informationText);
        confirmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        confirmButton = new JButton("تأیید");
        confirmButton.setActionCommand("confirmBlockBankCard");
        confirmButton.addActionListener(blockBankCardPanelController);
        backButton = new JButton("بازگشت به عقب");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(blockBankCardPanelController);

        confirmPanel.add("br br center", confirmLabel);
        confirmPanel.add("br center", confirmButton);
        confirmPanel.add("center", backButton);

    }

    public void showReportPanel() {

        reportPanel = new JPanel();
        reportPanel.setLayout(new RiverLayout());
        reportPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(reportPanel);

        String reportText = "<html> <div align=\"right\"> <br/>";
        reportText += "کارت به شماره " + bankCardTO.getId() + "مسدود شد." + "<br/>";
        reportText += "</div></html>";

        reportLabel = new JLabel(reportText);
        reportLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(blockBankCardPanelController);
        reportPanel.add("center", reportLabel);
        reportPanel.add("br center", backButton);
    }


    public void clearAlarmPanelAlarms() {
        super.clearAlarmPanelAlarms();

        if (bankCardIdStarLabel != null) {
            bankCardIdStarLabel.setVisible(false);
            bankCardInternetPasswordStarLabel.setVisible(false);
            bankCardCVV2StarLabel.setVisible(false);
            bankCardExpireDateStarLabel.setVisible(false);
        }
    }


}
