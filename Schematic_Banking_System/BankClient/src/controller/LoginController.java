package controller;


import model.bl.CustomerServerInterface;
import tools.Encryption;
import view.LoginPanel;
import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class LoginController implements ActionListener {
    public LoginPanel loginPanel;

    public LoginController() {
    }

    public LoginController(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public void login() {
        loginPanel.clearAlarmPanelAlarms();
        boolean trueCondition = true;

        if (loginPanel.userNameTextField.getText().isEmpty()) {
            loginPanel.userNameStarLabel.setVisible(true);
            loginPanel.addAlarmToAlarmPanel("نام کاربری را وارد کنید.");
            trueCondition = false;
        }
        if (loginPanel.passwordField.getText().isEmpty()) {
            loginPanel.passwordStarLabel.setVisible(true);
            loginPanel.addAlarmToAlarmPanel("رمز عبور را وارد کنید.");
            trueCondition = false;
        }
        if (trueCondition) {
            try {
                try {

                    CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
                    String userName = loginPanel.userNameTextField.getText();
                    String password = loginPanel.passwordField.getText();
                    MainFrame.loggedInCode = customerServer.login(userName, password);
                    MainFrame.userName = userName;
                    MainFrameHolder.getMainFrame().userMenu.setText(userName);
                    MainFrameHolder.getMainFrame().start();

                } catch (RemoteException e) {
                    loginPanel.addAlarmToAlarmPanel("اشکال در ارتباط با مرکز.");
                } catch (SQLException e) {
                    loginPanel.addAlarmToAlarmPanel("نام کاربری با رمز عبور مطابقت ندارد.");
                }
            } catch (Exception e) {
                loginPanel.addAlarmToAlarmPanel("اشکال در سیستم.");
            }
        }

    }

    public boolean isLoggedIn() {
        try {

            CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
            String userName = customerServer.isLoggedIn(MainFrame.loggedInCode);

            if (!userName.equals(MainFrame.userName)) {
                //MainFrameHolder.getMainFrame().showLoginPanel();
                return false;
            }
            return true;

        } catch (Exception e) {
            //MainFrameHolder.getMainFrame().showLoginPanel();
            e.printStackTrace();
            return false;
        }


    }

    //or
    public void testLoggedIn() {
        try {
            CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
            String userName = customerServer.isLoggedIn(MainFrame.loggedInCode);
            if (!userName.equals(MainFrame.userName)) {
                MainFrameHolder.getMainFrame().showLoginPanel();
            }
        } catch (Exception e) {
            MainFrameHolder.getMainFrame().showLoginPanel();
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("login")) {
            login();
        }
    }

    public void logout() {
        try {
            try {
                CustomerServerInterface customerServer=(CustomerServerInterface)Naming.lookup("CustomerServer");
                customerServer.logout(MainFrame.loggedInCode);
                MainFrameHolder.getMainFrame().showLoginPanel();

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
