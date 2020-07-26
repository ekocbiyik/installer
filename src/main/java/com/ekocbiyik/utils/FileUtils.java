package com.ekocbiyik.utils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * enbiya 22.07.2020
 */
public class FileUtils {

    public static String copyImageToHost(String image) {

        String destination = System.getProperty("user.home") + "/" + image + ".tar";
        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream("docker-images/" + image + ".tar")) {
            Files.copy(is, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

}
