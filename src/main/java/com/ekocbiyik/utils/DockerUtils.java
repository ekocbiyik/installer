package com.ekocbiyik.utils;

import com.ekocbiyik.model.DockerImage;
import com.ekocbiyik.model.DockerStats;

import java.util.*;

/**
 * enbiya 17.07.2020
 */
public class DockerUtils {

    public static List<DockerImage> getDockerImages() {
        String command = "docker images --filter \"dangling=false\" --format \"{{.Repository}}#{{.Tag}}#{{.CreatedAt}}#{{.Size}}#{{.ID}}\"";
        List<String> lines = TerminalUtils.execute(command);
        List<DockerImage> images = new ArrayList<>();
        for (String l : lines) {
            try {
                List<String> columns = new ArrayList<>(Arrays.asList(l.split("#")));
                DockerImage i = new DockerImage();
                i.setName(columns.get(0).trim());
                i.setDescription(i.getName());
                i.setVersion(columns.get(1).trim());
                i.setCreatedAt(columns.get(2).trim().substring(0, 19));
                i.setSize(columns.get(3).trim());
                i.setHash(columns.get(4).trim().substring(0, 10));
                images.add(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(images, Comparator.comparing(DockerImage::getName));
        return images;
    }

    public static Map<String, DockerStats> getContainerStats() {
        List<String> resultContainer = TerminalUtils.execute("docker ps -a --format \"{{.ID}}#{{.Names}}#{{.Image}}#{{.Status}}#{{.CreatedAt}}#{{.Ports}}#{{.Size}}\"");
        Map<String, DockerStats> statsMap = new HashMap<>();
        for (String l : resultContainer) {
            try {
                List<String> columns = new ArrayList<>(Arrays.asList(l.split("#")));
                DockerStats s = new DockerStats();
                s.setId(columns.get(0).trim());
                s.setName(columns.get(1).trim());
                s.setVersion(columns.get(2).trim().split(":")[1]);
                s.setCreatedAt(columns.get(4).trim());
                s.setPort(columns.get(5).trim());
                s.setSize(columns.get(6).trim());
                s.setStatus(columns.get(3));
                statsMap.put(s.getName(), s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<String> statsResult = TerminalUtils.execute("docker stats --no-stream --format \"{{.Name}}#{{.CPUPerc}}#{{.MemUsage}}#{{.MemPerc}}#{{.NetIO}}#{{.PIDs}}\"");
        for (String l : statsResult) {
            List<String> columns = new ArrayList<>(Arrays.asList(l.split("#")));
            if (statsMap.containsKey(columns.get(0).trim())) {
                try {
                    DockerStats s = statsMap.get(columns.get(0).trim());
                    s.setCpuPerc(columns.get(1).trim());
                    s.setMemory(columns.get(2).trim());
                    s.setMemoryPerc(columns.get(3).trim());
                    s.setNetwork(columns.get(4).trim());
                    s.setProcessId(columns.get(5).trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return statsMap;
    }

    public static void startStopContainer(String container, boolean isStart) {
        String cmd = String.format("docker %s %s"
                , isStart ? "start" : "stop"
                , container
        );
        TerminalUtils.execute(cmd);
    }

    public static String loadImage(String filePath) {
        String cmd = String.format("docker image load -q -i %s", filePath);
        List<String> result = TerminalUtils.execute(cmd);
        return result.isEmpty() ? "Geçersiz dosya formatı" : result.get(0);
    }

    public static void runContainer(DockerImage image) {
        String command = String.format("docker run -d --restart=always --network=\"host\" --name %s %s:%s", image.getName(), image.getName(), image.getVersion());
        TerminalUtils.execute("docker rm -f " + image.getName());
        TerminalUtils.execute(command);
    }

}
