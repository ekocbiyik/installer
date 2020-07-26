package com.ekocbiyik;

import com.ekocbiyik.layouts.MainScreen;
import com.ekocbiyik.layouts.SplashScreen;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * enbiya 13.07.2020
 */
@Component
public class MainStage implements ApplicationListener<ApplicationStarter.StageReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(MainStage.class);
    public static JFrame splashWindow;
    private static double APP_WINDOW_WIDTH = 900;
    private static double APP_WINDOW_HEIGHT = 650;
    private TrayIcon trayIcon;
    private static Stage mainStage;

    @Override
    public void onApplicationEvent(ApplicationStarter.StageReadyEvent stageReadyEvent) {
        mainStage = stageReadyEvent.getStage();
        initTrayIcon(mainStage);
        Platform.setImplicitExit(false);

        splashWindow = new SplashScreen().getInitWindow();
        Platform.runLater(() -> {
            try {

                mainStage.setTitle("Docker Desktop Application");
                mainStage.getIcons().add(new javafx.scene.image.Image("/images/logo.ico"));

                mainStage.setWidth(APP_WINDOW_WIDTH);
                mainStage.setHeight(APP_WINDOW_HEIGHT);
                mainStage.setMinWidth(APP_WINDOW_WIDTH);
                mainStage.setMinHeight(APP_WINDOW_HEIGHT);
                mainStage.setMaxWidth(APP_WINDOW_WIDTH);
                mainStage.setMaxHeight(APP_WINDOW_HEIGHT);

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                mainStage.setX((screenBounds.getWidth() - APP_WINDOW_WIDTH) / 2);
                mainStage.setY((screenBounds.getHeight() - APP_WINDOW_HEIGHT) / 2);

                Scene root = new Scene(new MainScreen().getAppWindow());
                root.getStylesheets().add(getClass().getResource("/Custom.css").toExternalForm());
                mainStage.setScene(root);
                mainStage.show();
                splashWindow.dispose();
            } catch (Exception e) {
                e.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("An error has been occured, Please see log details! ");
                a.showAndWait();
                System.exit(-1);
            }
        });
    }

    private void initTrayIcon(final Stage mainStage) {
        logger.info("trayIcon initialize started...");
        if (SystemTray.isSupported()) {
            mainStage.setOnCloseRequest(e -> hideWindow(mainStage));
            ActionListener closeListener = e -> System.exit(0);
            ActionListener showListener = e -> Platform.runLater(() -> {
                mainStage.show();
                logger.info("Application window is shown!");
            });

            PopupMenu popupMenu = new PopupMenu();
            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popupMenu.add(showItem);

            MenuItem closeItem = new MenuItem("Exit");
            closeItem.addActionListener(closeListener);
            popupMenu.add(closeItem);

            SystemTray tray = SystemTray.getSystemTray();
            Image icon = null;
            try {
                icon = ImageIO.read(getClass().getResource("/images/tray.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            trayIcon = new TrayIcon(icon, "Docker Desktop", popupMenu);
            trayIcon.addActionListener(showListener);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            logger.info("trayIcon has been created!");
        } else {
            logger.info("System does not support trayIcon!");
        }
    }

    private void hideWindow(final Stage stage) {
        Platform.runLater(() -> {
            if (SystemTray.isSupported()) {
                stage.hide();
                showTrayPopup();
                logger.info("Application window is hidden!");
            } else {
                System.exit(0);
            }
        });
    }

    public void showTrayPopup() {
        trayIcon.displayMessage("Bilgi", "Asistan arkaplanda çalışmaya devam ediyor!", TrayIcon.MessageType.INFO);
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}
