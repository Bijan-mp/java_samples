package view;

import controller.*;
import se.datadosen.component.RiverLayout;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel {


    public JLabel userNameLabel;
    public JLabel passwordLabel;
    public JLabel userNameStarLabel;
    public JLabel  passwordStarLabel;
    public JTextField userNameTextField;
    public JPasswordField passwordField;
    public JButton loginButton;

    public JPanel firstPanel;

    public LoginController loginController;

    public LoginPanel() {

        super();



        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        this.add("br center",firstPanel);

        loginController = new LoginController(this);

        showLogin();

    }

    public void showLogin() {

        userNameTextField = new JTextField();
        userNameTextField.setColumns(20);
        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        loginButton = new JButton("ورود");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(loginController);

        userNameLabel = new JLabel("نام کاربری : ");
        passwordLabel = new JLabel("رمز عبور : ");
        userNameStarLabel = new JLabel("*");
        userNameStarLabel.setVisible(false);
        passwordStarLabel = new JLabel("*");
        passwordStarLabel.setVisible(false);


        firstPanel.add("right",userNameLabel );
        firstPanel.add("tight tab",userNameTextField);
        firstPanel.add("right",userNameStarLabel);
        firstPanel.add("br right",passwordLabel);
        firstPanel.add("right tab",passwordField);
        firstPanel.add("right",passwordStarLabel);
        firstPanel.add("br center",loginButton);



    }



    public void clearAlarmPanelAlarms() {
        super.clearAlarmPanelAlarms();
        userNameStarLabel.setVisible(false);
        passwordStarLabel.setVisible(false);

    }




}
