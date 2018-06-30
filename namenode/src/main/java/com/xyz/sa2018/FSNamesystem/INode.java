package com.xyz.sa2018.FSNamesystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class INode implements Comparable<String> {
    //name只能用绝对路径形式
    protected String name;/*byte[]*/
    //父目录
    protected INodeDirectory parent;

    //constructor
    INode() {
        this.name=null;
        this.parent=null;
    }
    INode(String iNodeName) {
        this.name=iNodeName;
        Path path = Paths.get(iNodeName);
        //this.parent= INodeDirectory(path.getParent().toString());
        //this.filename = path.getFileName().toString();
    }
    INode(INode other) {
        this.name=other.getName();
        this.parent = other.getParent();
    }

    public String getName(){ return this.name; }
    public void setName(String name){this.name=name;}
    public INodeDirectory getParent() {
        return this.parent;
    }
    public String getParentName(){
        return this.parent.getName();
//        Path path = Paths.get(name);
//        return path.getParent().toString();
    }
    public void setParentName(String parentName){
        this.parent.setName(parentName);
    }

    public String getFileName(){
        Path path = Paths.get(name);
        return path.getFileName().toString();
    }

    /**
     * Check whether this is the root inode.
     * 根节点的判断标准是名字长度为0
     */
    boolean isRoot() {
        return name.length() == 0;
    }

    /**
     * Check whether it's a directory
     */
    public abstract boolean isDirectory();

    /**
     * Collect all the blocks in all children of this INode.
     * Count and return the number of files in the sub tree.
     * Also clears references since this INode is deleted.
     */
    abstract int collectSubtreeBlocksAndClear(List<BlockInfo> v);



    //移除自身节点方法
    boolean removeNode() {
        if (parent == null) {
            return false;
        } else {

            parent.removeChild(this);
            parent = null;
            return true;
        }
    }

    //
    // Comparable interface
    //
    /**
     * Compare two byte arrays.
     *
     * @return a negative integer, zero, or a positive integer
     * as defined by {@link #compareTo(String)}.
     */
    static int compareBytes(byte[] a1, byte[] a2) {//getBytes() or string2Bytes(String s)
        if (a1==a2)
            return 0;
        int len1 = (a1==null ? 0 : a1.length);
        int len2 = (a2==null ? 0 : a2.length);
        int n = Math.min(len1, len2);
        byte b1, b2;
        for (int i=0; i<n; i++) {
            b1 = a1[i];
            b2 = a2[i];
            if (b1 != b2)
                return b1 - b2;
        }
        return len1 - len2;
    }
    public int compareTo(String o) {
        return compareBytes(name.getBytes(), o.getBytes());
    }

    public boolean equals(Object o) {
        if (!(o instanceof INode)) {
            return false;
        }
        return Arrays.equals(this.name.getBytes(), ((INode)o).name.getBytes());
    }

    public int hashCode() {
        return Arrays.hashCode(this.name.getBytes());
    }
}
