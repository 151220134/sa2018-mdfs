package com.xyz.sa2018.FSNamesystem;

import org.eclipse.jgit.internal.storage.reftable.BlockSizeTooSmallException;

import java.util.ArrayList;

public class BlockInfo {/*TODO implements Comparable<byte[]> & Writable*/
    private String id;
    private String name;
    private String data;
    //private INodeFile inode;
    //private ArrayList<String> dataNodeUrls;

    BlockInfo(String id) {
        this.id=id;
        this.name=null;
        this.data=null;
    }
    BlockInfo(String id, String data) {
        this.id=id;
        //this.name = name;
        this.data=data;
    }
    BlockInfo(String id, String name, String data) {
        this.id=id;
        this.name =name;
        this.data=data;
    }
    BlockInfo(BlockInfo other){
        this.id=other.id;
        this.data=other.data;
        this.name=other.name;
        //this.dataNodeUrls=other.dataNodeUrls;
    }

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
        this.name =name;
    }

//    public INodeFile getINode() {
//        return inode;
//    }
//
//    public void setINode(INodeFile inode) {
//        this.inode = inode;
//    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

//    public ArrayList<String> getDataNodeUrls() {
//        return dataNodeUrls;
//    }
//
//    public void setDataNodeUrls(ArrayList<String> dataNodeUrls) {
//        this.dataNodeUrls = dataNodeUrls;
//    }
//
//    public void addDataNodeUrl(String url){
//        dataNodeUrls.add(url);
//    }
//
//    public void deleteDataNodeUrl(String url){
//        dataNodeUrls.remove(url);
//    }

//    @Override
//    public int compareTo(BlockInfo o) {
//        // TODO Auto-generated method stub
//        if ((this.id - o.getId()>0) {
//            return (this.id - o.getId());
//        }
//        if (this.age < o.getAge()) {
//            return (this.age - o.getAge());
//        }
//
//        return 0;
//    }

}
