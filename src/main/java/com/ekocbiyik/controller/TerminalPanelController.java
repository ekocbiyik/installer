package com.ekocbiyik.controller;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * enbiya 22.07.2020
 */
public class TerminalPanelController implements IPanelController {

    public TextArea terminalView;
    public boolean isWindowClosed;

    public void initComponents(String containerName) {
        Platform.runLater(() -> execute("docker logs -f --tail 200 " + containerName));
    }

    private void execute(String command) {
        isWindowClosed = false;
        Thread thread = new Thread(() -> {
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null && !isWindowClosed) {
                    String finalLine = line;
                    Platform.runLater(() -> terminalView.appendText(finalLine + "\n"));
                }
                p.waitFor();
                p.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
