package view;

import controller.MainFrameHolder;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class BasePanel extends JPanel {
    public JPanel alarmPanel;
    public JPanel currentPanel;
    public JLabel titleLabel;

    public BasePanel(){

            this.setLayout(new RiverLayout());
            this.setComponentOrientation(MainFrameHolder.getOrientation());//may be not necessary

            alarmPanel = new JPanel();
            alarmPanel.setLayout(new RiverLayout());
            alarmPanel.setComponentOrientation(MainFrameHolder.getOrientation());
            titleLabel = new JLabel();
            this.add("center",titleLabel);
            this.add("br right",alarmPanel);
    }

    public void addAlarmToAlarmPanel(String errorText) {
        JLabel errorLabel = new JLabel(errorText);
        alarmPanel.add("br right", errorLabel);
    }

    public void clearAlarmPanelAlarms() {
        alarmPanel.removeAll();
        alarmPanel.setVisible(false);
        alarmPanel.setVisible(true);
    }

    public void setCurrentPanel(JPanel jPanel) {
        if (currentPanel != null){
            this.currentPanel.setVisible(false);
            this.currentPanel.setEnabled(false);
            this.remove(currentPanel);
        }
        primarySetCurrentPanel(jPanel);
    }

    public void primarySetCurrentPanel(JPanel jPanel) {
        this.currentPanel = jPanel;
        this.currentPanel = jPanel;
        this.add("br hfill vfill", currentPanel);
    }
}
