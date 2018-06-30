package com.xyz.sa2018.NameNode;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class DataNode {
    //名称为主机名加端口号
    public String IP;      /// hostname:portNumber
    //唯一的存储ID
    public String storageID; /// unique per cluster storageID
    //服务端口号
    protected int infoPort;     /// the port where the infoserver is running
    //进程间通讯端口号
    //public int ipcPort;     /// the port where the ipc server is running



    DataNode(String id,String ip,int port){
        storageID=id;
        IP=ip;
        infoPort=port;
    }

    public String getID(){
        return storageID;
    }

    public void setID(String id){
        storageID=id;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        this.IP = ip;
    }

    public int getPort(){
        return infoPort;
    }

    public void setPort(int port){
        infoPort=port;
    }

//    public void setLoad(int load) {
//        this.load = load;
//    }
//
//    public int getLoad(){return load;}
//
//    public void addLoad() {
//        load += 2;
//    }

    public String getData(String id) {
//        return "MOCK";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> paramMap = new HashMap<>();
        String url = getUrl() + "?id={id}";
        paramMap.put("id", id);
        HttpEntity<String> response = restTemplate.getForEntity(url, String.class, paramMap);
        return response.getBody();
    }

    public void removeData(String id) {
//        return "MOCK";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> paramMap = new HashMap<>();
        String url = getUrl() + "?id={id}";
        paramMap.put("id", id);
        restTemplate.delete(url, paramMap);
    }

    public void setData(String id, String data/*, String name*/) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> paramMap = new HashMap<>();
        String url = getUrl() + "?id={id}&data={data}";
        paramMap.put("id", id);
        paramMap.put("data", data);
        //paramMap.put("name", name);
        restTemplate.put(url, "", paramMap);
    }

    private String getUrl() {
        return "http://" + IP + ":" + infoPort + "/block";
    }
}
