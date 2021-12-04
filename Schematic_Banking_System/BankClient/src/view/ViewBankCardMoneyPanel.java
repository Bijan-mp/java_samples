package view;

import controller.BlockBankCardPanelController;
import controller.MainFrameHolder;
import controller.ViewBankCardMoneyPanelController;
import model.to.BankCardTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class ViewBankCardMoneyPanel extends BasePanel {


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
    public JLabel reportLabel;
    public JButton backButton;
    public JPanel firstPanel;
    public JPanel reportPanel;


    public ViewBankCardMoneyPanelController viewBankCardMoneyPanelController;


    public ViewBankCardMoneyPanel() {
        super();

        titleLabel.setText("مشاهده مانده حساب کارت بانکی");

        this.viewBankCardMoneyPanelController = new ViewBankCardMoneyPanelController(this);

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

        checkMoneyTransferDataButton = new JButton("مشاهده");
        checkMoneyTransferDataButton.setActionCommand("showBankCardMoney");
        checkMoneyTransferDataButton.addActionListener(viewBankCardMoneyPanelController);

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


    public void showReportPanel(BankCardTO bankCardTO, Long bankCardMoney) {

        reportPanel = new JPanel();
        reportPanel.setLayout(new RiverLayout());
        reportPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(reportPanel);

        String informationText = "<html> <div align=\"right\"";
        informationText += "شماره کارت : " + bankCardTO.getId() + "<br/>";
        informationText += "میزان حساب کارت : " + bankCardMoney + "<br/>";
        informationText += "</div></html>";
        reportLabel = new JLabel(informationText);
        reportLabel.setComponentOrientation(MainFrameHolder.getOrientation());

        backButton = new JButton("بازگشت به عقب");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(viewBankCardMoneyPanelController);

        reportPanel.add("br center", reportLabel);
        reportPanel.add("br center", backButton);

    }

    public void clearAlarmPanelAlarms(){
        super.clearAlarmPanelAlarms();
        bankCardIdStarLabel.setVisible(false);
        bankCardInternetPasswordStarLabel = new JLabel("*");
        bankCardCVV2StarLabel = new JLabel("*");
        bankCardExpireDateStarLabel = new JLabel("*");
    }


}
