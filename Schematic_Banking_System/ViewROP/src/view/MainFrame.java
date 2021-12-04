package view;

import controller.MainFrameController;
import org.j2os.shine.util.reflect.Application;
import org.j2os.shine.util.reflect.ServerApplication;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public JMenuBar jMenuBar;
    public JMenu moneyTransferMenu;
    public JMenuItem moneyTransferByCheckingAccountMenuItem;
    public JMenuItem getMoneyTransferByBankCardMenuItem;

    public JPanel mainPanel;

    public MainFrameController mainFrameController;

    public MainFrame() {
        try {
            UIManager uiManager = new UIManager();
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


            this.setSize(700, 400);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

            // MainFrameHolder.setMainFrame(this);
            /// MainFrameHolder.setOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            mainFrameController = new MainFrameController(this);

            Application application =  new Application();
            application.distribute()

            initial();
        } catch (Exception e) {

        }
    }

    public void initial() {
        moneyTransferByCheckingAccountMenuItem = new JMenuItem("انتقال از حساب جاری به حساب جاری");
        getMoneyTransferByBankCardMenuItem = new JMenuItem("انتقال توسط کارت بانکی");
        moneyTransferMenu = new JMenu("انتقال پول");
        jMenuBar = new JMenuBar();

        jMenuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        moneyTransferMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        moneyTransferByCheckingAccountMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getMoneyTransferByBankCardMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        moneyTransferMenu.add(moneyTransferByCheckingAccountMenuItem);
        moneyTransferMenu.add(getMoneyTransferByBankCardMenuItem);
        jMenuBar.add(moneyTransferMenu);

        this.setJMenuBar(jMenuBar);

    }

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();


    }


}
