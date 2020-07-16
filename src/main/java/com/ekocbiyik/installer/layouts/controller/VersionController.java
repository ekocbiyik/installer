package com.ekocbiyik.installer.layouts.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * enbiya 16.07.2020
 */
public class VersionController implements IPanelController, Initializable {

    public TableView tblVersion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblVersion.setPlaceholder(new Label("Kayıt bulunamadı."));
        execute();
    }

    public void execute() {
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("docker ps -a");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
        }
    }
}
