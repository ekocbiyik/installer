package com.ekocbiyik.controller;

import com.ekocbiyik.layouts.PanelGenerator;
import com.ekocbiyik.model.DockerStats;
import com.ekocbiyik.utils.DockerUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * enbiya 17.07.2020
 */
public class ContainerStatsController implements IPanelController {

    public TitledPane tlPane;
    public Label serviceId;
    public Label serviceName;
    public Label version;
    public Label port;
    public Label size;
    public Label cpu;
    public Label ram;
    public Label network;
    public Button btnStart;
    public Button btnStop;
    public Button btnTerminal;

    public void initComponents(DockerStats d) {
        if (d == null) {
            return;
        }
        tlPane.setText(d.getName() + "   (" + d.getStatus() + ")");
        serviceId.setText(d.getId());
        serviceName.setText(d.getName());
        version.setText(d.getVersion());
        port.setText(d.getPort());
        size.setText(d.getSize());
        cpu.setText(d.getCpuPerc());
        ram.setText(d.getMemory() + " (" + d.getMemoryPerc() + ")");
        network.setText(d.getNetwork());
    }

    public void btnRestartClickAction(ActionEvent e) {
        Platform.runLater(() ->
                DockerUtils.startStopContainer(
                        (((Button) e.getSource()).getParent().getParent()).getParent().getId(), // container name
                        ((Button) e.getSource()).getId().equals("btnStart") // true: start / false: stop
                ));
    }

    public void btnTerminalClickAction(ActionEvent e) {
        String containerName = (((Button) e.getSource()).getParent().getParent()).getParent().getId();// container name
        PanelGenerator terminal = new PanelGenerator("/layouts/TerminalPanel.fxml");

        Scene scene = new Scene(terminal.getPanel());
        ((TerminalPanelController) terminal.getController()).initComponents(containerName);

        Stage planStage = new Stage();
        planStage.setTitle(containerName + " log detay penceresi");
        planStage.getIcons().add(new Image("/images/loading.gif"));
        planStage.initModality(Modality.APPLICATION_MODAL);
        planStage.setOnCloseRequest(windowEvent -> ((TerminalPanelController) terminal.getController()).isWindowClosed = true);

        planStage.setScene(scene);
        planStage.showAndWait();
    }

}
