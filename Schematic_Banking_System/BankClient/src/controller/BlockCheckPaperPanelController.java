package controller;

import model.bl.CustomerServerInterface;
import tools.RegularExpression;
import view.BlockCheckPaperPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class BlockCheckPaperPanelController implements ActionListener {

    public BlockCheckPaperPanel blockCheckPaperPanel;

    public BlockCheckPaperPanelController(BlockCheckPaperPanel blockCheckPaperPanel) {
        this.blockCheckPaperPanel = blockCheckPaperPanel;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("blockCheckPaper")) {
            blockCheckPaperPanel();
        }

    }

    public void blockCheckPaperPanel() {

        blockCheckPaperPanel.clearErrorPanelErrors();

        boolean trueCondition = true;

        if (blockCheckPaperPanel.bankCheckIdTextField.getText().isEmpty()) {
            blockCheckPaperPanel.addAlarmToAlarmPanel("شماره دسته چک را وارد کنید.");
            blockCheckPaperPanel.bankCheckIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockCheckPaperPanel.bankCheckIdTextField.getText()))) {
            blockCheckPaperPanel.addAlarmToAlarmPanel("شماره دسته چک باید فقط شامل ارقام باشد");
            blockCheckPaperPanel.bankCheckIdStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (blockCheckPaperPanel.checkPaperIdTextField.getText().isEmpty()) {
            blockCheckPaperPanel.addAlarmToAlarmPanel("شماره برگه چک را وارد کنید.");
            blockCheckPaperPanel.checkPaperIdStarLabel.setVisible(true);
            trueCondition = false;
        } else if (!(new RegularExpression().isNumber(blockCheckPaperPanel.checkPaperIdTextField.getText()))) {
            blockCheckPaperPanel.addAlarmToAlarmPanel("شماره برگه چک فقط باید شامل ارقام باشد.");
            blockCheckPaperPanel.checkPaperIdStarLabel.setVisible(true);
            trueCondition = false;
        }

        if (trueCondition) {
            long bankCheckId = Long.parseLong(blockCheckPaperPanel.bankCheckIdTextField.getText());
            int checkPaperId = Integer.parseInt(blockCheckPaperPanel.checkPaperIdTextField.getText());
            try {
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    if (customerServer.existCheckPaper(MainFrame.loggedInCode, bankCheckId, checkPaperId)) {
                        customerServer.blockCheckPaper(MainFrame.loggedInCode, bankCheckId, checkPaperId);
                        blockCheckPaperPanel.addAlarmToAlarmPanel("چک شماره " + checkPaperId + "از دسته چک شماره " + bankCheckId + "مسدود شد.");
                    } else {
                        blockCheckPaperPanel.addAlarmToAlarmPanel("مشخصات چک نا معتبر است.");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    blockCheckPaperPanel.addAlarmToAlarmPanel("مشکل در برقراری ارتباط با مرکز.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                blockCheckPaperPanel.addAlarmToAlarmPanel("مشکل در ارتباط با پایگاه داده ها .");
            }
        }


    }
}
