package view;

import controller.*;
import tools.Encryption;

import javax.swing.*;
import java.awt.*;
import java.util.Date;


public class MainFrame extends JFrame {

    public static String loggedInCode = "";
    public static String userName = "";

    public BasePanel currentPanel;

    //MenuBar--------------------------------------------------------
    public JMenuBar jMenuBar;


    public JMenu payBillMenu;
    public JMenuItem payBillByCheckingAccountMenuItem;
    public JMenuItem payBillByBankCardMenuItem;

    public JMenu moneyTransferMenu;
    public JMenuItem moneyTransferFromAndToCheckingAccountMenuItem;
    public JMenuItem moneyTransferBankCardToCheckingAccountMenuItem;
    public JMenuItem moneyTransferBankCardToBankCardMenuItem;

    public JMenu accountMenu;
    public JMenuItem checkingAccountListMenuItem;
    public JMenuItem depositAccountListMenuItem;
    public JMenuItem requestAccountMenuItem;

    public JMenu bankCardMenu;
    public JMenuItem getBankCardMoneyMenuItem;
    public JMenuItem blockBankCardMenuItem;
    public JMenuItem bankCardTransactionListMenuItem;

    public JMenu bankCheckMenu;
    public JMenuItem requestBankCheckMenuItem;
    public JMenuItem checkBankReportMenuItem;


    public JMenu userMenu;
    public JMenuItem changeUserPasswordMenuItem;

    public JButton transactionPursuitMenu;

    public JButton logout;
    //End-MenuBar-------------------------------------------------------


    //Panels
    public LoginPanel loginPanel;

    public HomePanel homePanel;

    public ChangeUserPasswordPanel changeUserPasswordPanel;

    public CheckingAccountListPanel checkingAccountListPanel;
    public DepositAccountListPanel depositAccountListPanel;

    public MoneyTransferFromCheckingAccountToCheckingAccountPanel moneyTransferFromCheckingAccountToCheckingAccountPanel;
    public MoneyTransferFromBankCardToCheckingAccountPanel moneyTransferFromBankCardToCheckingAccountPanel;

    public ViewBankCardMoneyPanel viewBankCardMoneyPanel;
    public BlockBankCardPanel blockBankCardPanel;
    public BankCardTransactionListPanel bankCardTransactionListPanel;

    public BankCheckReportPanel bankCheckReportPanel;
    public BankCheckRequestPanel bankCheckRequestPanel;
    public BlockCheckPaperPanel blockCheckPaperPanel;

    public PayBillByBankCardPanel payBillByBankCardPanel;
    public PayBillByCheckingAccountPanel payBillByCheckingAccountPanel;

    public TransactionPursuitPanel transactionPursuitPanel;

    //controller
    MainFrameController mainFrameController;

    public MainFrame() {

        try {

           // System.out.println(Encryption.getMD5("1234"));
            //Set lookAndFeel
            UIManager uiManager = new UIManager();
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


            this.setSize(700, 400);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

            MainFrameHolder.setMainFrame(this);
            MainFrameHolder.setOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            mainFrameController = new MainFrameController(this);

            showLoginPanel();
            initial();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MainFrame.MainFrame : " + e.getMessage());
        }



    }

    public void start() {

        jMenuBar.setVisible(true);
        jMenuBar.setEnabled(true);
        showHomePanel();

    }

    public void showHomePanel() {
        homePanel = new HomePanel();
        removeAndSetCurrentPanel(homePanel);
        new LoginController().testLoggedIn();
    }

    public void showBankCardTransactionListPanel() {
        bankCardTransactionListPanel = new BankCardTransactionListPanel();
        removeAndSetCurrentPanel(bankCardTransactionListPanel);
        new LoginController().testLoggedIn();
    }

    public void showBlockCheckPaperPanel() {
        blockCheckPaperPanel = new BlockCheckPaperPanel();
        removeAndSetCurrentPanel(blockCheckPaperPanel);
        new LoginController().testLoggedIn();

    }

    public void showBankCheckRequestPanel() {
        bankCheckRequestPanel = new BankCheckRequestPanel();
        removeAndSetCurrentPanel(bankCheckRequestPanel);
        new LoginController().testLoggedIn();
    }

    public void showTransactionPursuitPanel() {
        transactionPursuitPanel = new TransactionPursuitPanel();
        removeAndSetCurrentPanel(transactionPursuitPanel);
        new LoginController().testLoggedIn();
    }

    public void showViewBankCardMoneyPanel() {
        viewBankCardMoneyPanel = new ViewBankCardMoneyPanel();
        removeAndSetCurrentPanel(viewBankCardMoneyPanel);
        new LoginController().testLoggedIn();
    }

    public void showBlockBankCardPanel() {
        blockBankCardPanel = new BlockBankCardPanel();
        removeAndSetCurrentPanel(blockBankCardPanel);
        new LoginController().testLoggedIn();
    }

    public void showLoginPanel() {
        loginPanel = new LoginPanel();
        if (currentPanel == null) {
            setCurrentPanel(loginPanel);
        } else {
            this.removeMenu();
            removeAndSetCurrentPanel(loginPanel);
        }
    }

    public void showPayBillByBankCardPanel() {

        payBillByBankCardPanel = new PayBillByBankCardPanel();
        removeAndSetCurrentPanel(payBillByBankCardPanel);

    }

    public void showMoneyTransferFromCheckingAccountToCheckingAccountPanel() {
        moneyTransferFromCheckingAccountToCheckingAccountPanel = new MoneyTransferFromCheckingAccountToCheckingAccountPanel();
        removeAndSetCurrentPanel(moneyTransferFromCheckingAccountToCheckingAccountPanel);
        new LoginController().testLoggedIn();
    }

    public void showMoneyTransferFromBankCardToCheckingAccountPanel() {
        moneyTransferFromBankCardToCheckingAccountPanel = new MoneyTransferFromBankCardToCheckingAccountPanel();
        removeAndSetCurrentPanel(moneyTransferFromBankCardToCheckingAccountPanel);
        new LoginController().testLoggedIn();
    }


    public void showCheckingAccountListPanel() {
        checkingAccountListPanel = new CheckingAccountListPanel();
        removeAndSetCurrentPanel(checkingAccountListPanel);
        new LoginController().testLoggedIn();
    }

    public void showBankCheckReportPanel() {
        bankCheckReportPanel = new BankCheckReportPanel();
        removeAndSetCurrentPanel(bankCheckReportPanel);
        new LoginController().testLoggedIn();
    }

    public void showDepositAccountListPanel() {
        depositAccountListPanel = new DepositAccountListPanel();
        removeAndSetCurrentPanel(depositAccountListPanel);
        new LoginController().testLoggedIn();
    }

    public void showPayBillByCheckingAccount() {
        /*if (new LoginController().isLoggedIn()){
            payBillByCheckingAccountPanel = new PayBillByCheckingAccountPanel();
            removeAndSetCurrentPanel(payBillByCheckingAccountPanel);
        } else
        {
            showLoginPanel();
        }*/
        payBillByCheckingAccountPanel = new PayBillByCheckingAccountPanel();
        removeAndSetCurrentPanel(payBillByCheckingAccountPanel);
        new LoginController().testLoggedIn();
    }

    public void showChangePasswordPanel() {
        changeUserPasswordPanel = new ChangeUserPasswordPanel();
        removeAndSetCurrentPanel(changeUserPasswordPanel);
        new LoginController().testLoggedIn();
    }

    public void initial() {

        //Initial menus
        userMenu = new JMenu();
        changeUserPasswordMenuItem = new JMenuItem("تغییر رمز عبور");
        changeUserPasswordMenuItem.setActionCommand("changePassword");
        changeUserPasswordMenuItem.addActionListener(mainFrameController);
        userMenu.add(changeUserPasswordMenuItem);


        accountMenu = new JMenu("حساب های بانکی");
        checkingAccountListMenuItem = new JMenuItem("مشاهده لیست حساب های جاری");
        depositAccountListMenuItem = new JMenuItem("مشاهده لیست حساب های پس انداز");
        requestAccountMenuItem = new JMenuItem("درخواست افتتاح حساب");
        checkingAccountListMenuItem.setActionCommand("checkingAccountList");
        depositAccountListMenuItem.setActionCommand("depositAccountList");
        requestAccountMenuItem.setActionCommand("requestAccount");
        checkingAccountListMenuItem.addActionListener(mainFrameController);
        depositAccountListMenuItem.addActionListener(mainFrameController);
        requestAccountMenuItem.addActionListener(mainFrameController);
        accountMenu.add(checkingAccountListMenuItem);
        accountMenu.add(depositAccountListMenuItem);
        accountMenu.add(requestAccountMenuItem);

        moneyTransferMenu = new JMenu("انتقال وجه");
        moneyTransferFromAndToCheckingAccountMenuItem = new JMenuItem("انتقال وجه از حساب جاری به حساب جاری ");
        moneyTransferBankCardToCheckingAccountMenuItem = new JMenuItem("انتقال وجه از کارت به حساب جاری");
        moneyTransferBankCardToBankCardMenuItem = new JMenuItem("انتقال وجه کارت به کارت");
        moneyTransferFromAndToCheckingAccountMenuItem.setActionCommand("moneyTransferFromAndToCheckingAccount");
        moneyTransferBankCardToCheckingAccountMenuItem.setActionCommand("moneyTransferBankCardToCheckingAccount");
        moneyTransferBankCardToBankCardMenuItem.setActionCommand("moneyTransferBankCardToBankCard");
        moneyTransferFromAndToCheckingAccountMenuItem.addActionListener(mainFrameController);
        moneyTransferBankCardToCheckingAccountMenuItem.addActionListener(mainFrameController);
        moneyTransferBankCardToBankCardMenuItem.addActionListener(mainFrameController);
        moneyTransferMenu.add(moneyTransferFromAndToCheckingAccountMenuItem);
        moneyTransferMenu.add(moneyTransferBankCardToCheckingAccountMenuItem);
        moneyTransferMenu.add(moneyTransferBankCardToBankCardMenuItem);

        bankCardMenu = new JMenu("عملیات کارت بانک");
        getBankCardMoneyMenuItem = new JMenuItem("مشاهده مانده حساب");
        bankCardTransactionListMenuItem = new JMenuItem("مشاهده تراکنشهای کارت");
        blockBankCardMenuItem = new JMenuItem("مسدود کردن کارت");
        getBankCardMoneyMenuItem.setActionCommand("getBankCardMoney");
        bankCardTransactionListMenuItem.setActionCommand("bankCardTransactionList");
        blockBankCardMenuItem.setActionCommand("blockBankCard");
        getBankCardMoneyMenuItem.addActionListener(mainFrameController);
        bankCardTransactionListMenuItem.addActionListener(mainFrameController);
        blockBankCardMenuItem.addActionListener(mainFrameController);
        bankCardMenu.add(getBankCardMoneyMenuItem);
        bankCardMenu.add(bankCardTransactionListMenuItem);
        bankCardMenu.add(blockBankCardMenuItem);


        bankCheckMenu = new JMenu("عملیات چک");
        checkBankReportMenuItem = new JMenuItem("گذارش دسته چک");
        requestBankCheckMenuItem = new JMenuItem("درخواست دسته چک");
        bankCheckMenu.add(checkBankReportMenuItem);
        bankCheckMenu.add(requestBankCheckMenuItem);
        checkBankReportMenuItem.setActionCommand("checkBankReport");
        requestBankCheckMenuItem.setActionCommand("requestBankCheck");
        checkBankReportMenuItem.addActionListener(mainFrameController);
        requestBankCheckMenuItem.addActionListener(mainFrameController);

        payBillMenu = new JMenu("پرداخت قبوض");
        payBillByCheckingAccountMenuItem = new JMenuItem("پرداخت قبض توسط حساب جاری");
        payBillByBankCardMenuItem = new JMenuItem("پرداخت قبض توسط عابر بانک");
        payBillByCheckingAccountMenuItem.setActionCommand("payBillByCheckingAccount");
        payBillByBankCardMenuItem.setActionCommand("payBillByBankCard");
        payBillByCheckingAccountMenuItem.addActionListener(mainFrameController);
        payBillByBankCardMenuItem.addActionListener(mainFrameController);
        payBillMenu.add(payBillByCheckingAccountMenuItem);
        payBillMenu.add(payBillByBankCardMenuItem);

        transactionPursuitMenu = new JButton("پیگری تراکنش");
        transactionPursuitMenu.setActionCommand("transactionPursuit");
        transactionPursuitMenu.addActionListener(mainFrameController);

        logout = new JButton("خروج");
        logout.setActionCommand("logout");
        logout.addActionListener(mainFrameController);


        jMenuBar = new JMenuBar();
        jMenuBar.setVisible(false);
        jMenuBar.setEnabled(false);
        jMenuBar.add(userMenu);
        jMenuBar.add(accountMenu);
        jMenuBar.add(moneyTransferMenu);
        jMenuBar.add(bankCardMenu);
        jMenuBar.add(bankCheckMenu);
        jMenuBar.add(payBillMenu);
        jMenuBar.add(transactionPursuitMenu);
        jMenuBar.add(logout);

        this.setJMenuBar(jMenuBar);
        setMenuComponentOrientation(jMenuBar, ComponentOrientation.RIGHT_TO_LEFT);



    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

    }

    public void removeAndSetCurrentPanel(BasePanel basePanel) {
        this.currentPanel.setVisible(false);
        this.currentPanel.setEnabled(false);
        this.remove(currentPanel);

        setCurrentPanel(basePanel);
    }

    public void setCurrentPanel(BasePanel basePanel) {
        this.currentPanel = basePanel;
        this.currentPanel = basePanel;
        this.add(currentPanel);
    }

    public void setMenuComponentOrientation(JMenuBar jMenuBar, ComponentOrientation componentOrientation) {

        jMenuBar.setComponentOrientation(componentOrientation);
        int i = 0;
        while (i < jMenuBar.getMenuCount()-2) {
            jMenuBar.getMenu(i).setComponentOrientation(componentOrientation);
            int j = 0;
            while (j < jMenuBar.getMenu(i).getItemCount()) {
                jMenuBar.getMenu(i).getItem(j).setComponentOrientation(componentOrientation);
                j++;
            }
            i++;
        }
    }

    public void removeMenu() {
        this.jMenuBar.setVisible(false);
        this.jMenuBar.setEnabled(false);
        this.remove(this.jMenuBar);
    }

}
