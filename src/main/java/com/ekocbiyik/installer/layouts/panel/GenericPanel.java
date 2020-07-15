package com.ekocbiyik.installer.layouts.panel;

import com.ekocbiyik.installer.layouts.controller.IPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * enbiya 15.07.2020
 */
public class GenericPanel {

    private AnchorPane panel;
    private IPanelController controller;
    private String fxmlPath;

    public GenericPanel(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public AnchorPane getPanel() {
        panel = null;
        if (panel == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                panel = loader.load();
                controller = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return panel;
    }

    public IPanelController getController() {
        return controller;
    }

}
