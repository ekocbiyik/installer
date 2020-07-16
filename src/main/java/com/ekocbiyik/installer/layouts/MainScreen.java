package com.ekocbiyik.installer.layouts;

import com.ekocbiyik.installer.layouts.panel.GenericPanel;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

/**
 * enbiya 14.07.2020
 */
public class MainScreen {

    private static BorderPane borderPane;
    private static GenericPanel topPanel;
    private static GenericPanel leftPanel;
    private static GenericPanel bottomPanel;
    private static GenericPanel dashboardPanel;
    private static GenericPanel versionPanel;
    private static GenericPanel setupPanel;

    public MainScreen() {
        topPanel = new GenericPanel("/layouts/TopPanel.fxml");
        leftPanel = new GenericPanel("/layouts/LeftPanel.fxml");
        bottomPanel = new GenericPanel("/layouts/BottomPanel.fxml");
        dashboardPanel = new GenericPanel("/layouts/DashboardPanel.fxml");
        versionPanel = new GenericPanel("/layouts/VersionPanel.fxml");
        setupPanel = new GenericPanel("/layouts/SetupPanel.fxml");
    }

    public BorderPane getAppWindow() {
        if (borderPane == null) {
            borderPane = new BorderPane();
            borderPane.setTop(topPanel.getPanel());
            borderPane.setLeft(leftPanel.getPanel());
            borderPane.setBottom(bottomPanel.getPanel());

            //..
            borderPane.setCenter(dashboardPanel.getPanel());
            borderPane.setAlignment(borderPane.getCenter(), Pos.TOP_LEFT);
        }
        return borderPane;
    }

    public static GenericPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public static GenericPanel getVersionPanel() {
        return versionPanel;
    }

    public static GenericPanel getSetupPanel() {
        return setupPanel;
    }
}
