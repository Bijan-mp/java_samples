package view;

import controller.BankCheckRequestPanelController;
import controller.MainFrameHolder;
import model.to.CheckingAccountTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BankCheckRequestPanel extends BasePanel {

    public long checkingAccountId;


    public JTable checkingAccountTable;
    public JScrollPane jScrollPane;
    public JButton setBankCheckRequest;
    public JLabel reportLabel;
    public JPanel firstPanel;

    public BankCheckRequestPanelController bankCheckRequestPanelController;

    public BankCheckRequestPanel() {
        super();
        titleLabel.setText("درخواست دسته چک");

        bankCheckRequestPanelController = new BankCheckRequestPanelController(this);
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


        checkingAccountTable = new JTable();
        checkingAccountTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkingAccountTable);
        jScrollPane.setVisible(false);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());

        setBankCheckRequest = new JButton("درخاست دسته چک");
        setBankCheckRequest.setActionCommand("BankCheckRequest");
        setBankCheckRequest.addActionListener(bankCheckRequestPanelController);

        firstPanel.add("br hfill vfill", jScrollPane);
        firstPanel.add("br center", setBankCheckRequest);


        bankCheckRequestPanelController.getCheckingAccountList();
    }


    public void showCheckingAccountList(ArrayList<CheckingAccountTO> checkingAccountTOArrayListList) {

        jScrollPane.setVisible(true);
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
