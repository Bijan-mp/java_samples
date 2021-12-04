package view;

import controller.*;
import model.to.CheckingAccountTO;
import model.to.DepositAccountTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DepositAccountListPanel extends BasePanel {

    public DepositAccountListPanelController depositAccountListPanelController;

    public DepositAccountListPanel() {

        titleLabel.setText("لیست حساب های پس انداز");


        DepositAccountListPanelController depositAccountListPanelController = new DepositAccountListPanelController(this);
    }

    public void showDepositAccountList(ArrayList<DepositAccountTO> depositAccountTOArrayList) {

        DefaultTableModel model = new DefaultTableModel();
        JTable depositAccountTable = new JTable(model);
        depositAccountTable.setComponentOrientation(MainFrameHolder.getOrientation());
        JScrollPane jScrollPane = new JScrollPane(depositAccountTable);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());


        model.addColumn("شماره حساب");
        model.addColumn("میزان حساب");
        model.addColumn("تاریخ افتتاح حساب");
        model.addColumn("شعبه بانک");
        model.addColumn("شماره حساب جاری برای انتقال سود");
        model.addColumn("نوع حساب پس انداز");
        model.addColumn("مدت(ماه)");
        model.addColumn("میزان سود سالیانه");

        int i = 0;
        while (i < depositAccountTOArrayList.size()) {
            JButton jButton = new JButton("view");
            model.addRow(new Object[]{
                    depositAccountTOArrayList.get(i).getId(),
                    depositAccountTOArrayList.get(i).getMoney(),
                    depositAccountTOArrayList.get(i).getOpeningDate(),
                    depositAccountTOArrayList.get(i).getBranchId(),
                    depositAccountTOArrayList.get(i).getCheckingAccountId(),
                    depositAccountTOArrayList.get(i).getDepositAccountTypeTO().getName(),
                    depositAccountTOArrayList.get(i).getDepositAccountTypeTO().getDuration(),
                    "%" + depositAccountTOArrayList.get(i).getDepositAccountTypeTO().getAnnualInterestPercent()

            });
            i++;
        }

        this.add("br hfill vfill", jScrollPane);

    }

}
