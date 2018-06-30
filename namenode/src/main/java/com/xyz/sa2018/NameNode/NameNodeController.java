package com.xyz.sa2018.NameNode;

import com.netflix.appinfo.InstanceInfo;
import com.xyz.sa2018.FSNamesystem.INode;
import com.xyz.sa2018.FSNamesystem.INodeFile;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
//import org.json.JSONObject;


@RestController
public class NameNodeController {

    private final NameNodeService nameNodeService;

//    @Autowired
//    NameNodeController(){
//        nameNodeService=new NameNodeService();
//    }
    @Autowired
    NameNodeController(NameNodeService NNService) {
        nameNodeService = NNService;
    }

    //    @Autowired
//    private NameNodeService nameNodeService;

    /*上传*//*TODO*/
    @RequestMapping(value = "/**", method = RequestMethod.PUT)
    public String uploadFile(HttpServletRequest request,@RequestParam(value = "file") String file) {
        String iNodeName = request.getServletPath();
        return nameNodeService.addINode(iNodeName,file);
    }

    /*删除*/
    @RequestMapping(value = "/**", method = RequestMethod.DELETE)
    public String deleteFile(HttpServletRequest request) {
        String iNodeName = request.getServletPath();
        return nameNodeService.removeINode(iNodeName);
    }

    /*下载*/
    /*@RequestMapping(value = "/GET/**", method = RequestMethod.GET)
    public String downloadFile(@PathVariable("file")String filename
        return "downloadFile:"+filename;
    }*/

    /*查看和下载*/
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String getFile(HttpServletRequest request) {
        String iNodeName = request.getServletPath();//request.getRequestURI();//
        //nameNodeService.initDir();
        INode targetINode =nameNodeService.getINodeByName(iNodeName);
        if(targetINode==null) return "NULL";
        else{
            if(targetINode.isDirectory()) return nameNodeService.getINodeList(targetINode.getName());
            else return nameNodeService.getFileContent((INodeFile)targetINode);//((INodeFile)targetINode).getFileContent();
        }
    }

    /*查看根目录*/
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllFile(HttpServletRequest request) {
        return nameNodeService.getAllINodeFile();
    }


    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {

        System.err.println(event.getAppName()+ " 服务下线");
        nameNodeService.onDataNodeDown(event.getServerId());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        //InstanceInfo instanceInfo = event.getInstanceInfo();
//        String dataNodeUrl = instanceInfo.getHomePageUrl();
//        System.err.println(dataNodeUrl + "进行注册");
//        nameNodeService.registerNewDataNode(dataNodeUrl);

        InstanceInfo instanceInfo = event.getInstanceInfo();
        // 检查 status
        if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
            System.err.println(instanceInfo.getAppName()+ instanceInfo.getInstanceId() + "进行注册");
            nameNodeService.onDataNodeRegister(instanceInfo.getInstanceId(), instanceInfo.getIPAddr(), instanceInfo.getPort());
        }
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        System.err.println(event.getServerId() + "\t" + event.getAppName() + " 服务进行续约");

    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        System.err.println("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        System.err.println("Eureka Server 启动");
    }
}
