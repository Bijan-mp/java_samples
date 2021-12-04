package controller;

import model.bl.CustomerServerInterface;
import tools.Encryption;
import view.ChangeUserPasswordPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.sql.SQLException;

public class ChangeUserPasswordPanelController implements ActionListener {

    public ChangeUserPasswordPanel changeUserPasswordPanel;

    public ChangeUserPasswordPanelController(ChangeUserPasswordPanel changeUserPasswordPanel) {
        this.changeUserPasswordPanel = changeUserPasswordPanel;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("changePassword")) {
            changeUserPassword();
        }

    }

    public void changeUserPassword() {
        changeUserPasswordPanel.clearAlarmPanelAlarms();
        boolean trueCondition = true;

        if (changeUserPasswordPanel.oldPasswordField.getText().isEmpty()) {
            changeUserPasswordPanel.oldPasswordStarLabel.setVisible(true);
            changeUserPasswordPanel.addAlarmToAlarmPanel("رمز عبور را وارد کنید.");
            trueCondition = false;
        }
        if (changeUserPasswordPanel.newPasswordField.getText().isEmpty()) {
            changeUserPasswordPanel.newPasswordStarLabel.setVisible(true);
            changeUserPasswordPanel.addAlarmToAlarmPanel("رمز عبور جدید را وارد کنید.");
            trueCondition = false;
        }else if (changeUserPasswordPanel.newPasswordField.getText().toString().length() < 5 ){
            changeUserPasswordPanel.newPasswordStarLabel.setVisible(true);
            changeUserPasswordPanel.addAlarmToAlarmPanel("رمز عبور نمیتواند کمتر از 5 حرف باشد.");
        }
        if (changeUserPasswordPanel.newPasswordConfirmField.getText().isEmpty()) {
            changeUserPasswordPanel.newPasswordConfirmStarLabel.setVisible(true);
            changeUserPasswordPanel.addAlarmToAlarmPanel("فیلد تکرار رمز عبور جدید را وارد کنید.");
            trueCondition = false;
        }else if (!changeUserPasswordPanel.newPasswordField.getText().isEmpty() &&
                !changeUserPasswordPanel.newPasswordConfirmField.getText().equals(changeUserPasswordPanel.newPasswordField.getText())
                ) {

            changeUserPasswordPanel.newPasswordConfirmStarLabel.setVisible(true);
            changeUserPasswordPanel.newPasswordStarLabel.setVisible(true);
            changeUserPasswordPanel.addAlarmToAlarmPanel("فیلد های رمز عبور جدید با هم برابر نیستند.");
            trueCondition = false;

        }
        if (trueCondition) {
            try {
                String password = changeUserPasswordPanel.oldPasswordField.getText();
                String newPassword = changeUserPasswordPanel.newPasswordConfirmField.getText();
                try {
                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    customerServer.changePassword(MainFrame.loggedInCode, password, newPassword);
                    changeUserPasswordPanel.addAlarmToAlarmPanel("رمز عبور شما با موفقیت تغییر یافت.");
                    changeUserPasswordPanel.clearFields();

                } catch (RuntimeException e) {
                    changeUserPasswordPanel.addAlarmToAlarmPanel("اشکال در ارتباط با مرکز.");
                    e.printStackTrace();

                } catch (SQLException e){
                    changeUserPasswordPanel.addAlarmToAlarmPanel("اشکال در دریافت داده ها از مرکز.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                changeUserPasswordPanel.addAlarmToAlarmPanel("اشکال در سیستم .");
                e.printStackTrace();

            }
        }

    }
}

