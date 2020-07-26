package com.ekocbiyik.layouts;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

/**
 * enbiya 14.07.2020
 */
public class MainScreen {

    private static BorderPane borderPane;
    private static PanelGenerator topPanel;
    private static PanelGenerator leftPanel;
    private static PanelGenerator bottomPanel;
    private static PanelGenerator dashboardPanel;
    private static PanelGenerator versionPanel;

    public MainScreen() {
        topPanel = new PanelGenerator("/layouts/TopPanel.fxml");
        leftPanel = new PanelGenerator("/layouts/LeftPanel.fxml");
        bottomPanel = new PanelGenerator("/layouts/BottomPanel.fxml");
        dashboardPanel = new PanelGenerator("/layouts/DashboardPanel.fxml");
        versionPanel = new PanelGenerator("/layouts/VersionPanel.fxml");
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

    public static PanelGenerator getDashboardPanel() {
        return dashboardPanel;
    }

    public static PanelGenerator getVersionPanel() {
        return versionPanel = new PanelGenerator("/layouts/VersionPanel.fxml");
    }

}
