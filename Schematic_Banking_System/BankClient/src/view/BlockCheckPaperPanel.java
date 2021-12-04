package view;

import controller.BlockCheckPaperPanelController;
import controller.MainFrameHolder;
import se.datadosen.component.RiverLayout;

import javax.swing.*;

public class BlockCheckPaperPanel extends BasePanel {

    public JLabel bankCheckIdLabel;
    public JLabel checkPaperIdLabel;
    public JLabel bankCheckIdStarLabel;
    public JLabel checkPaperIdStarLabel;
    public JTextField bankCheckIdTextField;
    public JTextField checkPaperIdTextField;
    public JButton blockCheckPaperButton;

    public JPanel firstPanel;

    public BlockCheckPaperPanelController blockCheckPaperPanelController;

    public BlockCheckPaperPanel() {

        super();
        titleLabel.setText("انسداد برگه چک");
        blockCheckPaperPanelController = new BlockCheckPaperPanelController(this);
        showFirstPanel();
    }

    public void showFirstPanel() {
        firstPanel = new JPanel();
        firstPanel.setLayout(new RiverLayout());
        firstPanel.setComponentOrientation(MainFrameHolder.getOrientation());
        setCurrentPanel(firstPanel);

        bankCheckIdLabel = new JLabel("شماره دسته چک : ");
        checkPaperIdLabel = new JLabel("شماره بگه چک : ");
        bankCheckIdStarLabel = new JLabel("*");
        bankCheckIdStarLabel.setVisible(false);
        checkPaperIdStarLabel = new JLabel("*");
        checkPaperIdStarLabel.setVisible(false);
        bankCheckIdTextField = new JTextField(10);
        checkPaperIdTextField = new JTextField(2);
        blockCheckPaperButton = new JButton("مسدود کن");
        blockCheckPaperButton.setActionCommand("blockCheckPaper");
        blockCheckPaperButton.addActionListener(blockCheckPaperPanelController);

        firstPanel.add("right", bankCheckIdLabel);
        firstPanel.add("right tab", bankCheckIdTextField);
        firstPanel.add("right", bankCheckIdStarLabel);
        firstPanel.add("br right", checkPaperIdLabel);
        firstPanel.add("right tab", checkPaperIdTextField);
        firstPanel.add("right", checkPaperIdStarLabel);
        firstPanel.add("br center", blockCheckPaperButton);

    }

    public void clearErrorPanelErrors() {
        super.clearAlarmPanelAlarms();
        bankCheckIdStarLabel.setVisible(false);
        checkPaperIdStarLabel.setVisible(false);
    }

}
