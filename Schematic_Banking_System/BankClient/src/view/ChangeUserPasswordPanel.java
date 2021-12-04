package view;

import controller.ChangeUserPasswordPanelController;
import controller.MainFrameHolder;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class ChangeUserPasswordPanel extends BasePanel {

    public JLabel oldPasswordLabel;
    public JLabel oldPasswordStarLabel;
    public JLabel newPasswordLabel;
    public JLabel newPasswordStarLabel;
    public JLabel newPasswordConfirmLabel;
    public JLabel newPasswordConfirmStarLabel;
    public JPasswordField oldPasswordField;
    public JPasswordField newPasswordField;
    public JPasswordField newPasswordConfirmField;
    public JButton changePasswordButton;
    public JPanel firstPanel;

    public ChangeUserPasswordPanelController changeUserPasswordPanelController;

    public ChangeUserPasswordPanel() {
        super();
        titleLabel.setText("پنل تغییر رمز عبور");
        changeUserPasswordPanelController = new ChangeUserPasswordPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {

        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        oldPasswordLabel = new JLabel("رمز عبور : ");
        newPasswordLabel = new JLabel("رمز عبور جدید : ");
        newPasswordConfirmLabel = new JLabel("تکرار رمز عبور جدید : ");

        oldPasswordStarLabel = new JLabel("*");
        newPasswordConfirmStarLabel = new JLabel("*");
        newPasswordStarLabel = new JLabel("*");
        oldPasswordStarLabel.setVisible(false);
        newPasswordStarLabel.setVisible(false);
        newPasswordConfirmStarLabel.setVisible(false);

        oldPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        newPasswordConfirmField = new JPasswordField(20);

        changePasswordButton = new JButton("تغییر رمز عبور");
        changePasswordButton.setActionCommand("changePassword");
        changePasswordButton.addActionListener(changeUserPasswordPanelController);

        firstPanel.add("center", oldPasswordLabel);
        firstPanel.add("right tab", oldPasswordField);
        firstPanel.add("right", oldPasswordStarLabel);

        firstPanel.add("br right", newPasswordLabel);
        firstPanel.add("right tab", newPasswordField);
        firstPanel.add("right", newPasswordStarLabel);

        firstPanel.add("br right", newPasswordConfirmLabel);
        firstPanel.add("right tab", newPasswordConfirmField);
        firstPanel.add("right", newPasswordConfirmStarLabel);

        firstPanel.add("br right", changePasswordButton);

    }

    public void clearFields(){
        oldPasswordField.setText("");
        newPasswordConfirmField.setText("");
        newPasswordField.setText("");
    }


    public void clearAlarmPanelAlarms() {
        super.clearAlarmPanelAlarms();
        oldPasswordStarLabel.setVisible(false);
        newPasswordStarLabel.setVisible(false);
        newPasswordConfirmStarLabel.setVisible(false);
    }


}
