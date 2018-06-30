package com.xyz.sa2018.FSNamesystem;

import java.util.*;

public class FSDirectory {
    private INodeDirectory root;

    public INode getRoot() {
        root.setName("root");
        return root;
    }

    public List<INode> getRootChildren(){
        return root.getChildren();
    }

    /*public INode getINodeByName(String name){
        //二分查找
        int low = Collections.binarySearch(children, node.name);
        if(low >= 0)
            return null;
    }*/
    public INode getINodeByName(String name){return root;}

    public void initForTest(){
        INodeDirectory usr=new INodeDirectory("usr");
        INodeFile a=new INodeFile("a.zip");
        INodeFile b=new INodeFile("b.zip");
        root.addChild(usr);
        root.addChild(a);
        root.addChild(b);
    }

}
