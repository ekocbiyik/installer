package com.ekocbiyik.installer;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


@SpringBootApplication
public class ApplicationStarter extends Application {

    private Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

    private final int PORT = 64999;
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private final String messageKey = "Trabzonspor...";
    public static HostServices hostServices;

    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // just for linux
        Application.launch(ApplicationStarter.class);
    }

    @Override
    public void init() throws Exception {
        applicationContext = new SpringApplicationBuilder(ApplicationStarter.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("*************************Application started*************************");
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        hostServices = getHostServices();
        try {
            if (isProgramAlreadyRunning()) {
                logger.info("Application is already running!, Shutting down!");
                System.exit(0);
            }
            applicationContext.publishEvent(new StageReadyEvent(stage));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }

    private boolean isProgramAlreadyRunning() {
        try {
            logger.debug("Checking for single application control...");
            serverSocket = new ServerSocket(PORT, 0, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
            SwingWorker<String, Void> anotherThread = new SwingWorker<>() {
                @Override
                public String doInBackground() {
                    serverSocketListener();
                    return "";
                }
            };
            anotherThread.execute();
        } catch (BindException e) {
            logger.debug("An application is already running!");
            clientSocketListener();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        logger.debug("Any application could not be found! A new instance is starting!");
        return false;
    }

    public void serverSocketListener() {
        while (true) {
            try {
                logger.debug("Listener socket opened to prevent any other application instance.");
                socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = in.readLine();
                logger.info("Retrieved message key(" + msg + ") equals expected: " + messageKey.equals(msg));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void clientSocketListener() {
        try {
            socket = new Socket(InetAddress.getByAddress(new byte[]{127, 0, 0, 1}), PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(messageKey);
        } catch (IOException e) {
            logger.debug("No I/O");
            System.exit(1);
        }
    }

}

