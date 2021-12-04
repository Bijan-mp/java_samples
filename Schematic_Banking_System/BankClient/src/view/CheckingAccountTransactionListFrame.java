package view;

import controller.CheckingAccountTransactionListFrameController;
import controller.MainFrameHolder;
import model.to.BankTransactionTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CheckingAccountTransactionListFrame extends JFrame {

    public long checkingAccountId;

    public static JLabel alarmLabel;
    public static JLabel titleLabel;

    public static JTable checkingAccountTransactionTable;
    public static JScrollPane jScrollPane;

    public CheckingAccountTransactionListFrame(long checkingAccountId) {

        this.setLayout(new RiverLayout());
        this.setComponentOrientation(MainFrameHolder.getOrientation());//may be not necessary
        this.setTitle("لیست تراکنش های انجام شده توسط حساب جاری به شماره : "+checkingAccountId);
        this.checkingAccountId = checkingAccountId;

        alarmLabel = new JLabel();
        alarmLabel.setComponentOrientation(MainFrameHolder.getOrientation());
        alarmLabel.setVisible(false);
        this.add("center", alarmLabel);

        titleLabel = new JLabel();
        this.add("br center", titleLabel);

        checkingAccountTransactionTable = new JTable();
        checkingAccountTransactionTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkingAccountTransactionTable);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());
        this.add("br hfill vfill", jScrollPane);

        CheckingAccountTransactionListFrameController checkingAccountTransactionListFrameController = new CheckingAccountTransactionListFrameController();
        checkingAccountTransactionListFrameController.showCheckingAccountTransactionList(this.checkingAccountId);

    }

    public static void showCheckingAccountTransactionList(ArrayList<BankTransactionTO> checkingAccountTransactionList,long checkingAccountId) {
        jScrollPane.setVisible(true);
        setTitleLabelText("تراکنش های انجام شده حساب جاری به شماره حساب : "+checkingAccountId);
        DefaultTableModel model = new DefaultTableModel();
        checkingAccountTransactionTable.setModel(model);
        model.addColumn("شماره تراکنش");
        model.addColumn("تاریخ");
        model.addColumn("عنوان");
        model.addColumn("شرح تراکنش");
        model.addColumn("حساب مقصد");
        model.addColumn("حساب مبدأ");
        model.addColumn("شماره کارت مقصد");
        model.addColumn("شماره کارت مبدأ");
        int i=0;
        while (i < checkingAccountTransactionList.size()){
            model.addRow(new Object[]{
                    checkingAccountTransactionList.get(i).getId(),
                    checkingAccountTransactionList.get(i).getDate(),
                    checkingAccountTransactionList.get(i).getTitle(),
                    checkingAccountTransactionList.get(i).getDescription(),
                    checkingAccountTransactionList.get(i).getSourceCheckingAccountId(),
                    checkingAccountTransactionList.get(i).getDestinationCheckingAccountId(),
                    checkingAccountTransactionList.get(i).getSourceBankCardId(),
                    checkingAccountTransactionList.get(i).getDestinationBankCardId()
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
