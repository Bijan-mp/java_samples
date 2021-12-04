package view;

import controller.BankCheckReportPanelController;
import controller.MainFrameHolder;
import model.to.CheckPaperTO;
import model.to.CheckingAccountTO;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BankCheckReportPanel extends BasePanel {

    public  JLabel bankCheckIdLabel;
    public  JLabel bankCheckStarLabel;
    public JLabel checkReportLabel;
    public  JTextField bankCheckIdTextField;
    public  JButton showCheckPaperListButton;
    public ArrayList<CheckPaperTO> checkPaperList;

    public  JTable checkPaperTable;
    public  JScrollPane jScrollPane;
    public JPanel firstPanel;

    public BankCheckReportPanelController bankCheckReportPanelController;

    public BankCheckReportPanel() {

        super();

        titleLabel.setText("گذارش دسته چک");

        bankCheckReportPanelController = new BankCheckReportPanelController(this);

        showFirstPanel();


    }

    public void showFirstPanel(){
        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        bankCheckIdLabel = new JLabel("شماره دسته چک : ");
        bankCheckIdTextField = new JTextField(10);
        bankCheckStarLabel = new JLabel("*");
        bankCheckStarLabel.setVisible(false);
        showCheckPaperListButton = new JButton("نمایش لیست برگه های چک");
        showCheckPaperListButton.setActionCommand("showCheckPaperList");
        showCheckPaperListButton.addActionListener(bankCheckReportPanelController);
        checkReportLabel = new JLabel();
        checkPaperTable = new JTable();
        checkPaperTable.setComponentOrientation(MainFrameHolder.getOrientation());
        jScrollPane = new JScrollPane(checkPaperTable);
        jScrollPane.setComponentOrientation(MainFrameHolder.getOrientation());

        firstPanel.add("br right", bankCheckIdLabel);
        firstPanel.add("right", bankCheckIdTextField);
        firstPanel.add("right", bankCheckStarLabel);
        firstPanel.add("right ", showCheckPaperListButton);
        firstPanel.add("br right", checkReportLabel);
        firstPanel.add("br hfill vfill", jScrollPane);

    }

    public void showCheckPaperList(ArrayList<CheckPaperTO> checkPaperList) {


        checkReportLabel.setText("لیست برگه های چک مربوط به دسته چک شماره : " + bankCheckIdTextField.getText());
        DefaultTableModel model = new DefaultTableModel();
        checkPaperTable.setModel(model);
        model.addColumn("شماره برگه چک");
        model.addColumn("مبلغ");
        model.addColumn("تاریخ دریافت");
        model.addColumn("تاریخ دریافت واقعی");
        model.addColumn("وضعیت");
        model.addColumn("حالت مسدود");
        model.addColumn("در وجه");
        model.addColumn("نام دریافت کننده");
        model.addColumn("نام خانوادگی دریافت کننده");
        model.addColumn("شماره ملی دریافت کننده");

        int i = 0;
        while (i < checkPaperList.size()) {
            String status = "---";
            if (checkPaperList.get(i).getPassedFlag() == 1) {
                status = "پاس شده";
            } else if (checkPaperList.get(i).getReturnFlag() == 1) {
                status = "برگشت خورده";
            }

            model.addRow(new Object[]{
                    checkPaperList.get(i).getCheckPaperId(),
                    checkPaperList.get(i).getMoney(),
                    checkPaperList.get(i).getReceiveDate(),
                    checkPaperList.get(i).getRealReceiveDate(),
                    status,
                    checkPaperList.get(i).getBarredFlag(),
                    checkPaperList.get(i).getForPerson(),
                    checkPaperList.get(i).getReceiverName(),
                    checkPaperList.get(i).getReceiverFamily(),
                    checkPaperList.get(i).getReceiverNationalId()
            });
            i++;
        }

    }

    public void clearAlarmPanelAlarms(){
        super.clearAlarmPanelAlarms();
    }
}
