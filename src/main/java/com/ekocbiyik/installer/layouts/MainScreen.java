package com.ekocbiyik.installer.layouts;

import com.ekocbiyik.installer.layouts.panel.GenericPanel;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

/**
 * enbiya 14.07.2020
 */
public class MainScreen {

    private BorderPane borderPane;
    private GenericPanel topPanel;
    private GenericPanel leftPanel;
    private GenericPanel bottomPanel;
    private GenericPanel dashboardPanel;
    private GenericPanel versionPanel;

    public MainScreen() {
        topPanel = new GenericPanel("/layouts/TopPanel.fxml");
        leftPanel = new GenericPanel("/layouts/LeftPanel.fxml");
        bottomPanel = new GenericPanel("/layouts/BottomPanel.fxml");
        dashboardPanel = new GenericPanel("/layouts/DashboardPanel.fxml");
        versionPanel = new GenericPanel("/layouts/VersionPanel.fxml");
    }

    public BorderPane getAppWindow() {
        if (borderPane == null) {
            borderPane = new BorderPane();
            borderPane.setTop(topPanel.getPanel());
            borderPane.setLeft(leftPanel.getPanel());
            borderPane.setBottom(bottomPanel.getPanel());

            //..
//            borderPane.setCenter(dashboardPanel.getPanel());
            borderPane.setCenter(versionPanel.getPanel());
            borderPane.setAlignment(borderPane.getCenter(), Pos.TOP_LEFT);
        }
        return borderPane;
    }

}
