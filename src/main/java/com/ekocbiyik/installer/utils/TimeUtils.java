package com.ekocbiyik.installer.utils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * enbiya 13.07.2020
 */
public class TimeUtils {

    public static void sleep(long second) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                TimeUnit.SECONDS.sleep(second);
                return null;
            }
        };
        worker.execute();
        while (!worker.isDone()) {
        }
    }

}
