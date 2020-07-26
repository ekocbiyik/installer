package com.ekocbiyik.layouts;

import com.ekocbiyik.controller.IPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * enbiya 15.07.2020
 */
public class PanelGenerator {

    private Region panel;
    private IPanelController controller;
    private String fxmlPath;

    public PanelGenerator(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public Region getPanel() {
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
