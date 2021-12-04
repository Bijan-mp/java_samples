package view;

import controller.MainFrameHolder;
import controller.MoneyTransferFromCheckingAccountToCheckingAccountPanelController;
import model.to.BankTransactionTO;
import model.to.CheckingAccountTO;
import se.datadosen.component.RiverLayout;
import tools.PersianDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MoneyTransferFromCheckingAccountToCheckingAccountPanel extends BasePanel {

    public long sourceCheckingAccountId;
    public long destinationCheckingAccountId;
    public long money;

    public JLabel moneyValueLabel;
    public JTextField moneyValueTextField;
    public JLabel moneyValueStarLabel;
    public JLabel destinationCheckingAccountIdLabel;
    public JTextField destinationCheckingAccountTextField;
    public JLabel destinationCheckingAccountStarLabel;
    private JLabel checkingAccountListTitleLabel;
    public JTable checkingAccountTable;
    public JScrollPane jScrollPane;
    public JButton checkMoneyTransferDataButton;
    public JButton confirmButton;
    public JLabel confirmLabel;
    public JLabel reportLabel;
    public JButton backButton;
    public JPanel firstPanel;
    public JPanel confirmPanel;
    public JPanel reportPanel;

    public MoneyTransferFromCheckingAccountToCheckingAccountPanelController moneyTransferFromCheckingAccountToCheckingAccountPanelController;

    public MoneyTransferFromCheckingAccountToCheckingAccountPanel() {

        super();

        titleLabel.setText("انتقال حساب از حساب جاری به حساب جاری");

        moneyTransferFromCheckingAccountToCheckingAccountPanelController = new MoneyTransferFromCheckingAccountToCheckingAccountPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        checkingAccountListTitleLabel = new JLabel("شماره حساب های جاری شما (حساب مبدأ را از بین آنها انتخاب کنید).");
        moneyValueLabel = new JLabel("مبلغ : ");
        moneyValueTextField = new JTextField(14);
        moneyValueStarLabel = new JLabel("*");
        moneyValueStarLabel.setVisible(false);
        destinationCheckingAccountIdLabel = new JLabel("شماره حساب مقصد : ");
        destinationCheckingAccountTextField = new JTextField(14);
        destinationCheckingAccountStarLabel = new JLabel("*");
        destinationCheckingAccountStarLabel.setVisible(false);


        checkingAccountTable = new JTable();
        checkingAccountTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkingAccountTable);
        jScrollPane.setVisible(false);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());

        checkMoneyTransferDataButton = new JButton("بررسی اطلاعات");
        checkMoneyTransferDataButton.setActionCommand("checkMoneyTransferData");
        checkMoneyTransferDataButton.addActionListener(moneyTransferFromCheckingAccountToCheckingAccountPanelController);

        firstPanel.add("br right", moneyValueLabel);
        firstPanel.add("right tab tab", moneyValueTextField);
        firstPanel.add("right", moneyValueStarLabel);
        firstPanel.add("br right", destinationCheckingAccountIdLabel);
        firstPanel.add("right tab", destinationCheckingAccountTextField);
        firstPanel.add("right", destinationCheckingAccountStarLabel);
        firstPanel.add("br right", checkingAccountListTitleLabel);
        firstPanel.add("br hfill vfill", jScrollPane);
        firstPanel.add("br center", checkMoneyTransferDataButton);


        moneyTransferFromCheckingAccountToCheckingAccountPanelController.getCheckingAccountList();

    }

    public void showConfirmPanel(long sourceCheckingAccountId, long destinationCheckingAccountId, String destinationCheckingAccountOwner, long money) {

        confirmPanel = new JPanel();
        confirmPanel.setLayout(new RiverLayout());
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(confirmPanel);

        this.sourceCheckingAccountId = sourceCheckingAccountId;
        this.destinationCheckingAccountId = destinationCheckingAccountId;
        this.money = money;

        String informationText = "<html> <div align=\"right\"";
        informationText += "انتقال حساب از حساب جاری به حساب جاری :" + "<br/>";
        informationText += "حساب مبدأ : " + sourceCheckingAccountId + "<br/>";
        informationText += "حساب مقصد : " + destinationCheckingAccountId + "<br/>";
        informationText += "به نام : " + destinationCheckingAccountOwner + "<br/>";
        informationText += "مبلغ : " + money + "</div></html>";


        confirmLabel = new JLabel(informationText);
        confirmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        confirmButton = new JButton("پرداخت");
        confirmButton.setActionCommand("confirmTransferMoney");
        confirmButton.addActionListener(moneyTransferFromCheckingAccountToCheckingAccountPanelController);
        backButton = new JButton("بازگشت به عقب");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(moneyTransferFromCheckingAccountToCheckingAccountPanelController);

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
        reportText += "مبلغ انتقال یافته : " +bankTransactionTO.getMoney();
        reportText +="</div></html>" ;

        reportLabel = new JLabel(reportText);
        reportLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goBack");
        backButton.addActionListener(moneyTransferFromCheckingAccountToCheckingAccountPanelController);
        reportPanel.add("center",reportLabel);
        reportPanel.add("br center",backButton);
    }

    public void showCheckingAccountList(ArrayList<CheckingAccountTO> checkingAccountTOArrayListList) {

        jScrollPane.setVisible(true);
        checkingAccountListTitleLabel.setText("شماره حساب های جاری شما (حساب مبدأ را از بین آنها انتخاب کنید).");
        DefaultTableModel model = new DefaultTableModel();
        checkingAccountTable.setModel(model);
        model.addColumn("شماره حساب");
        model.addColumn("میزان حساب");
        model.addColumn("تاریخ افتتاح حساب");
        model.addColumn("شعبه بانک");
        int i = 0;
        while (i < checkingAccountTOArrayListList.size()) {
            JButton jButton = new JButton("view");
            model.addRow(new Object[]{
                    checkingAccountTOArrayListList.get(i).getId(),
                    checkingAccountTOArrayListList.get(i).getMoney(),
                    checkingAccountTOArrayListList.get(i).getOpeningDate(),
                    checkingAccountTOArrayListList.get(i).getBranchId()
            });
            i++;
        }
    }

    public void clearAlarmPanelAlarms(){
        super.clearAlarmPanelAlarms();
        if (moneyValueStarLabel != null){
            moneyValueStarLabel.setVisible(false);
            destinationCheckingAccountStarLabel.setVisible(false);
        }

    }





}
