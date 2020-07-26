package com.ekocbiyik.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * enbiya 16.07.2020
 */
public class TerminalUtils {
    public static List<String> execute(String command) {
        List<String> result = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                result.add(line);
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
