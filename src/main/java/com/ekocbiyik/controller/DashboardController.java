package com.ekocbiyik.controller;

import com.ekocbiyik.layouts.PanelGenerator;
import com.ekocbiyik.model.DockerStats;
import com.ekocbiyik.utils.DockerUtils;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * enbiya 16.07.2020
 */
public class DashboardController implements IPanelController, Initializable {

    public VBox vBoxMain;
    private Map<String, PanelGenerator> panelMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateComponents(DockerUtils.getContainerStats());
        new Thread(() -> refresh()).start();
    }

    private void generateComponents(Map<String, DockerStats> contList) {
        vBoxMain.getChildren().clear();
        contList.entrySet().forEach(c -> updateFields(c.getKey(), c.getValue()));
    }

    private void updateFields(String containerName, DockerStats stats) {
        PanelGenerator containerStats = new PanelGenerator("/layouts/ContainerStats.fxml");
        TitledPane panel = (TitledPane) containerStats.getPanel();
        panel.setId(containerName);
        ((ContainerStatsController) containerStats.getController()).initComponents(stats);

        vBoxMain.getChildren().add(panel);
        panelMap.put(containerName, containerStats);
    }

    void refresh() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                Map<String, DockerStats> stats = DockerUtils.getContainerStats();
                Platform.runLater(() -> generateComponents(stats));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
