package com.xyz.sa2018.DataNode;

public class Block  {/*TODO implements Comparable<byte[]> & Writable*/
    private String id;
    private String name;
    private String data;

    Block(String id) {
        this.id=id;
        this.name=null;
        this.data=null;
    }
    Block(String id,String data) {
        this.id=id;
        //this.name = name;
        this.data=data;
    }
    Block(String id,String name,String data) {
        this.id=id;
        this.name = name;
        this.data=data;
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
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
