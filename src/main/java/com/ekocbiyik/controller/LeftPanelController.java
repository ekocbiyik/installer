package com.ekocbiyik.controller;

import com.ekocbiyik.layouts.MainScreen;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * enbiya 16.07.2020
 */
public class LeftPanelController implements IPanelController {

    public AnchorPane leftPanel;
    public Button btnDashboard;
    public Button btnVersion;
    public Button btnTest;

    public void btnDashboardOnClickAction(ActionEvent e) {
        setActive(((Button) e.getSource()), MainScreen.getDashboardPanel().getPanel());
    }

    public void btnVersionOnClickAction(ActionEvent e) {
        setActive(((Button) e.getSource()), MainScreen.getVersionPanel().getPanel());
    }

    public void btnTestOnClickAction(ActionEvent e) {
        System.out.println("test clicked!");
    }

    private void setActive(Button btn, Region panel) {
        ((BorderPane) leftPanel.getParent()).setCenter(panel);
        ((BorderPane) leftPanel.getParent()).setAlignment(panel, Pos.TOP_LEFT);

        btnDashboard.getStyleClass().remove("btnDashboard-active");
        btnVersion.getStyleClass().remove("btnVersion-active");
        btn.getStyleClass().add(btn.getId() + "-active");
    }

}
