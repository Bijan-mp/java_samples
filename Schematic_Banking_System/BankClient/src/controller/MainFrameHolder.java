package controller;

import view.MainFrame;

import java.awt.*;


public class  MainFrameHolder {

    private static MainFrame mainFrame;
    private static ComponentOrientation orientation;

    public static ComponentOrientation getOrientation() {
        return orientation;
    }

    public static void setOrientation(ComponentOrientation orientation) {
        MainFrameHolder.orientation = orientation;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        MainFrameHolder.mainFrame = mainFrame;
    }
}
