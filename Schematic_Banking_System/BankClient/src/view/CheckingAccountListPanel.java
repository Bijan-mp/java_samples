package view;


import controller.CheckingAccountListPanelController;
import controller.MainFrameHolder;
import model.to.CheckingAccountTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CheckingAccountListPanel extends BasePanel {

    private static JLabel alarmLabel;
    public ArrayList<CheckingAccountTO> checkingAccountList;

    private static JLabel titleLabel;
    public static JTable checkingAccountTable;
    public static JScrollPane jScrollPane;

    public static JButton showCheckingAccountTransactionListButton;
    public static JButton moneyTransferButton;
    public static JButton payBillButton;


    public CheckingAccountListPanel() {

        this.setLayout(new RiverLayout());
        this.setComponentOrientation(MainFrameHolder.getOrientation());//may be not necessary


        alarmLabel = new JLabel();
        alarmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        alarmLabel.setVisible(false);
        this.add("center", alarmLabel);

        titleLabel = new JLabel();
        this.add("br center", titleLabel);

        checkingAccountTable = new JTable();
        checkingAccountTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkingAccountTable);
        jScrollPane.setVisible(false);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());
        this.add("br hfill vfill", jScrollPane);

        showCheckingAccountTransactionListButton = new JButton("نمایش تراکنش های انجام شده توسط حساب انتخاب شده");
        this.add("br center",showCheckingAccountTransactionListButton);

        moneyTransferButton = new JButton("انتقال پول توسط حساب انتخاب شده");
        moneyTransferButton.setActionCommand("moneyTransfer");
        //moneyTransferButton.setVisible(false);
        moneyTransferButton.addActionListener(new CheckingAccountListPanelController());
        this.add("center",moneyTransferButton);

        payBillButton = new JButton("پرداخت قبض توسط حساب انتخاب شده");
       // payBillButton.setVisible(false);
        this.add(payBillButton);


        CheckingAccountListPanelController checkingAccountListPanelController = new CheckingAccountListPanelController();
        checkingAccountListPanelController.getCheckingAccountList();
    }

    public static void showCheckingAccountList(ArrayList<CheckingAccountTO> checkingAccountTOArrayListList) {

        jScrollPane.setVisible(true);
        setTitleLabelText("حساب های جاری شما");
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

    public static void setAlarmLabelText(String text) {
        alarmLabel.setVisible(true);
        alarmLabel.setText(text);
    }

    public static void setTitleLabelText(String text) {
        titleLabel.setText(text);
    }


}
