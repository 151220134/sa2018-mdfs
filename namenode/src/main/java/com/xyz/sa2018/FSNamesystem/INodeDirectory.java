package com.xyz.sa2018.FSNamesystem;

import java.util.*;

public class INodeDirectory extends INode {
    //保存子目录或子文件
    private List<INode> children;

    /** constructor */
    public INodeDirectory(){
        super();
        this.children=null;
    }
    public INodeDirectory(String localName) {
        super(localName);
        this.children=null;
    }
    /** copy constructor
     *
     * @param other
     */
    public INodeDirectory(INodeDirectory other) {
        super(other);
        this.children = other.getChildren();
    }

    List<INode> getChildren() {
        return children==null ? new ArrayList<INode>() : children;
    }
    INode getChild(String name) {
        if (children == null) {
            return null;
        }
        int low = Collections.binarySearch(children, name);
        if (low >= 0) {
            return children.get(low);
        }
        return null;
    }

    /**
     * Check whether it's a directory
     */
    public boolean isDirectory() {
        return true;
    }

    //移除节点方法
    INode removeChild(INode node) {
        assert children != null;
        //用二分法寻找文件节点
        int low = Collections.binarySearch(children, node.name);
        if (low >= 0) {
            return children.remove(low);
        } else {
            return null;
        }
    }

    /**
     * Retrieve existing INodes from a path. If existing is big enough to store
     * all path components (existing and non-existing), then existing INodes
     * will be stored starting from the root INode into existing[0]; if
     * existing is not big enough to store all path components, then only the
     * last existing and non existing INodes will be stored so that
     * existing[existing.length-1] refers to the target INode.
     *
     * <p>
     * Example: <br>
     * Given the path /c1/c2/c3 where only /c1/c2 exists, resulting in the
     * following path components: ["","c1","c2","c3"],
     *
     * <p>
     * <code>getExistingPathINodes(["","c1","c2"], [?])</code> should fill the
     * array with [c2] <br>
     * <code>getExistingPathINodes(["","c1","c2","c3"], [?])</code> should fill the
     * array with [null]
     *
     * <p>
     * <code>getExistingPathINodes(["","c1","c2"], [?,?])</code> should fill the
     * array with [c1,c2] <br>
     * <code>getExistingPathINodes(["","c1","c2","c3"], [?,?])</code> should fill
     * the array with [c2,null]
     *
     * <p>
     * <code>getExistingPathINodes(["","c1","c2"], [?,?,?,?])</code> should fill
     * the array with [rootINode,c1,c2,null], <br>
     * <code>getExistingPathINodes(["","c1","c2","c3"], [?,?,?,?])</code> should
     * fill the array with [rootINode,c1,c2,null]
     */

    /**
     * Add a child inode to the directory.
     *
     * @param node INode to insert
     * @return  null if the child with this name already exists;
     *          node, otherwise
     */
    <T extends INode> T addChild(final T node) {
        if (children == null) {
            children = new ArrayList<INode>();
        }
        //二分查找
        int low = Collections.binarySearch(children, node.name);
        if(low >= 0)
            return null;
        node.parent = this;
        //在孩子列表中进行添加
        children.add(-low - 1, node);
        return node;
    }

    /**
     * Given a child's name, return the index of the next child
     *
     * @param name a child's name
     * @return the index of the next child
     */
    int nextChild(String name) {
        if (name.length() == 0) { // empty name
            return 0;
        }
        int nextPos = Collections.binarySearch(children, name) + 1;
        if (nextPos >= 0) {
            return nextPos;
        }
        return -nextPos;
    }

    //递归删除文件目录下的所有block块
    int collectSubtreeBlocksAndClear(List<BlockInfo> v) {
        int total = 1;
        //直到是空目录的情况，才直接返回
        if (children == null) {
            return total;
        }
        for (INode child : children) {
            //递归删除
            total += child.collectSubtreeBlocksAndClear(v);
        }

        //删除完毕之后，置为空操作，并返回文件数计数结果
        parent = null;
        children = null;
        return total;
    }
}
