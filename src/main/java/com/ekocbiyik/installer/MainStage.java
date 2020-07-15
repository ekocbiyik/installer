package com.ekocbiyik.installer;

import com.ekocbiyik.installer.layouts.MainScreen;
import com.ekocbiyik.installer.layouts.SplashScreen;
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

/**
 * enbiya 13.07.2020
 */
@Component
public class MainStage implements ApplicationListener<ApplicationStarter.StageReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(MainStage.class);
    public static JFrame splashWindow;
    private static double APP_WINDOW_WIDTH = 970;
    private static double APP_WINDOW_HEIGHT = 700;
    private TrayIcon trayIcon;

    @Override
    public void onApplicationEvent(ApplicationStarter.StageReadyEvent stageReadyEvent) {
        Stage mainStage = stageReadyEvent.getStage();
        initTrayIcon(mainStage);
        Platform.setImplicitExit(false);

        splashWindow = new SplashScreen().getInitWindow();
        Platform.runLater(() -> {
            try {

//                Scene root = new Scene(new StackPane());
//                mainStage.setScene(root);
                mainStage.setTitle("Installer");
                mainStage.getIcons().add(new javafx.scene.image.Image("/images/logo.ico"));

                mainStage.setWidth(APP_WINDOW_WIDTH);
                mainStage.setHeight(APP_WINDOW_HEIGHT);
                mainStage.setMinWidth(APP_WINDOW_WIDTH);
                mainStage.setMinHeight(APP_WINDOW_HEIGHT);

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                mainStage.setX((screenBounds.getWidth() - APP_WINDOW_WIDTH) / 2);
                mainStage.setY((screenBounds.getHeight() - APP_WINDOW_HEIGHT) / 2);

//                TimeUnit.SECONDS.sleep(5);
                splashWindow.dispose();

                Scene root = new Scene(new MainScreen().getAppWindow());
                mainStage.setScene(root);
                mainStage.show();
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
            try {
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
                java.awt.Image icon = ImageIO.read(getClass().getResource("/images/tray.png"));
                trayIcon = new TrayIcon(icon, "Meyratech", popupMenu);
                trayIcon.addActionListener(showListener);
                tray.add(trayIcon);
            } catch (Exception e) {
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
        if (trayIcon != null)
            trayIcon.displayMessage("Info", "CheckupBOX is still running in background!", TrayIcon.MessageType.INFO);
    }
}
