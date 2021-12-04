package view;

import controller.MainFrameHolder;
import controller.PayBillByCheckingAccountPanelController;
import model.to.BankTransactionTO;
import model.to.BillTO;
import model.to.CheckingAccountTO;
import se.datadosen.component.RiverLayout;
import tools.PersianDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PayBillByCheckingAccountPanel extends BasePanel {

    public BillTO billTO;
    public long sourceCheckingAccountId;

    public JLabel billIdStarLabel;
    public JLabel billPayIdStarLabel;
    public JLabel billIdLabel;
    public JLabel billPayIdLabel;
    public JTextField billIdTextField;
    public JTextField billPayIdTextField;
    public JButton checkBillButton;
    public JTable checkingAccountTable;
    public JScrollPane jScrollPane;

    public JLabel confirmLabel;
    public JButton confirmButton;
    public JButton backButton;
    public JLabel reportLabel;

    public JPanel firstPanel;
    public JPanel confirmPanel;
    public JPanel reportPanel;


    public PayBillByCheckingAccountPanelController payBillByCheckingAccountPanelController;

    public PayBillByCheckingAccountPanel() {
        super();
        titleLabel.setText("پرداخت قبض توسط حساب جاری");
        payBillByCheckingAccountPanelController = new PayBillByCheckingAccountPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        billIdStarLabel = new JLabel("*");
        billIdStarLabel.setVisible(false);
        billPayIdStarLabel = new JLabel("*");
        billPayIdStarLabel.setVisible(false);
        billIdLabel = new JLabel("شناسه قبض : ");
        billPayIdLabel = new JLabel("شناسه پرداخت : ");
        billIdTextField = new JTextField(15);
        billPayIdTextField = new JTextField(15);
        checkBillButton = new JButton("بررسی قبض");
        checkBillButton.setActionCommand("checkPayBillData");
        checkBillButton.addActionListener(payBillByCheckingAccountPanelController);
        checkingAccountTable = new JTable();
        checkingAccountTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkingAccountTable);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());

        firstPanel.add("right", billIdLabel);
        firstPanel.add("right tab", billIdTextField);
        firstPanel.add("right", billIdStarLabel);
        firstPanel.add("br ", billPayIdLabel);
        firstPanel.add("right tab", billPayIdTextField);
        firstPanel.add("right", billPayIdStarLabel);
        firstPanel.add("br hfill vfill", jScrollPane);
        firstPanel.add("br center", checkBillButton);

        payBillByCheckingAccountPanelController.getCheckingAccountList();


    }


    public void showConfirmPanel(BillTO billTO, long sourceCheckingAccountId) {

        confirmPanel = new JPanel();
        confirmPanel.setLayout(new RiverLayout());
        confirmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(confirmPanel);

        this.billTO = billTO;
        this.sourceCheckingAccountId = sourceCheckingAccountId;

        String msg = "<html><div align=\"right\">";
        msg += "شناسه قبض : ";
        msg += billTO.getId() + "<br />";
        msg += " شناسه پرداخت : ";
        msg += billTO.getPayId() + "<br />";
        msg += "نوع قبض : ";
        msg += billTO.getType() + "<br />";
        msg += "مبلغ قبض : ";
        msg += billTO.getPrice() + "<br />";
        msg += "شماره حساب برای پرداخت : " + sourceCheckingAccountId;
        msg += "</div></html>";

        confirmLabel = new JLabel(msg);
        confirmButton = new JButton("تایید");
        confirmButton.setActionCommand("payBill");
        confirmButton.addActionListener(payBillByCheckingAccountPanelController);
        backButton = new JButton("بازگشت به صفحه اول");
        backButton.setActionCommand("goFirst");
        backButton.addActionListener(payBillByCheckingAccountPanelController);

        confirmPanel.add("center", confirmLabel);
        confirmPanel.add("br center", confirmButton);
        confirmPanel.add("center",backButton);


    }

    public void showReportPanel(BankTransactionTO bankTransactionTO){

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
        backButton.addActionListener(payBillByCheckingAccountPanelController);
        reportPanel.add("center",reportLabel);
        reportPanel.add("br center",backButton);

    }


    public void showCheckingAccountList(ArrayList<CheckingAccountTO> checkingAccountTOArrayListList) {

        JLabel checkingAccountListLabel = new JLabel("شماره حساب های  جاری شما (شماره حسابهای مبدأ) :");
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

}
