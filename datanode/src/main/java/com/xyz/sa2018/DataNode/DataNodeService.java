package com.xyz.sa2018.DataNode;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataNodeService {
    private Map<String, Block> localMap= new HashMap<String, Block>();


    String setDataBlock(Block dataBlock){
        localMap.put(dataBlock.getId(), dataBlock);
        System.out.println("add block"+dataBlock.getId());
        return "succeed to put "+dataBlock.getName()+"'s "+dataBlock.getData()+": "+dataBlock.getData();
    }

    Block getDataBlock(String id) {
        System.out.println("find block"+id);
        return localMap.get(id);
    }

    String removeDataBlock(String id){
        String name=localMap.get(id).getName();
        String data=localMap.get(id).getData();
        localMap.remove(id);
        System.out.println("remove block"+id);
        return "suceed to remove "+name+"'s "+id+": "+name;
    }
}
