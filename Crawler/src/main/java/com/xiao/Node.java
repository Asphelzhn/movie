package com.xiao;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class Node {

    private String name;
    private String ip;
    private int port;

    public Node(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
