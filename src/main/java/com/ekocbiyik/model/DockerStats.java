package com.ekocbiyik.model;

/**
 * enbiya 17.07.2020
 */
public class DockerStats {

    private String id = "-";
    private String name = "-";
    private String version = "-";
    private String status = "-";
    private String createdAt = "-";
    private String port = "-";
    private String size = "-";

    private String cpuPerc = "-";
    private String memory = "-";
    private String memoryPerc = "-";
    private String network = "-";
    private String processId = "-";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCpuPerc() {
        return cpuPerc;
    }

    public void setCpuPerc(String cpuPerc) {
        this.cpuPerc = cpuPerc;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMemoryPerc() {
        return memoryPerc;
    }

    public void setMemoryPerc(String memoryPerc) {
        this.memoryPerc = memoryPerc;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
