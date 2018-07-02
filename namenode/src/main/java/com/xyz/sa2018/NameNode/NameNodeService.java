package com.xyz.sa2018.NameNode;
import com.xyz.sa2018.FSNamesystem.BlockInfo;
import com.xyz.sa2018.FSNamesystem.INode;
import com.xyz.sa2018.FSNamesystem.INodeDirectory;
import com.xyz.sa2018.FSNamesystem.INodeFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings("unchecked")
public class NameNodeService {
    @Value(value="${block.default-size}")
    public int BLOCK_SIZE;

    @Value(value="${block.default-replicas}")
    public int REPLICA_NUM;

    //private FSDirectory dir;
    private Map<String, INode> dir;
    private List<DataNode> dataNodeList;
    private Map<String, List<DataNode>> BlocksMap;

    NameNodeService(){
        dir= new HashMap<>();
        dataNodeList = new ArrayList<>();
        BlocksMap = new TreeMap<>();
        initDir();
    }
    NameNodeService(NameNodeService other){
        this.dir=other.dir;
        this.dataNodeList=other.dataNodeList;
        this.BlocksMap=other.BlocksMap;
        initDir();
        System.out.println("init");
    }
//    NameNodeService(Map<String, INode> dir){
//        this.dir=dir;
//        initDir();
//    }

    private void initDir(){
        //dir= new HashMap<String, INode>();
        INodeDirectory root=new INodeDirectory();
//        INodeDirectory usr=new INodeDirectory("/usr");
//        INodeFile a=new INodeFile("/a.zip","151220134");
//        INodeFile b=new INodeFile("/b.zip","114514");
//        INodeFile c=new INodeFile("/usr/c.zip","889464");
//        dir.put("root",root);
//        dir.put(usr.getName(),usr);
//        dir.put(a.getName(),a);
//        dir.put(b.getName(),b);
//        dir.put(c.getName(),c);
        //System.out.println(dir);
        addINodeDirectory("/usr");
//        addINode("/a.zip","151220134");
//        addINode("/b.zip","114514");
//        addINode("/usr/c.zip","889464");
    }

    INode getINodeByName(String iNodeName){
        return dir.get(iNodeName);
    }

    String getINodeList(String directoryName){
        StringBuilder re=new StringBuilder("NULL");
        boolean flag=false;
        for (Map.Entry<String, INode> m :dir.entrySet())  {
            if(m.getKey().contains(directoryName)&&!directoryName.equals(m.getKey())){
                //re.append(m.getValue().getFileName()+'\n');
                re.append(m.getKey()+'\n');
                flag=true;
            }
            //System.out.println(m.getKey()+"\t"+m.getValue());
        }
        if(flag) return re.substring("NULL".length());
        else return re.toString();
    }

    String getAllINodeFile(){
        StringBuilder re=new StringBuilder("NULL");
        if(dir.isEmpty())return re.toString();
        for (Map.Entry<String, INode> m :dir.entrySet())  {
            if(!m.getValue().isDirectory())
                re.append(m.getKey()+'\n');
            //System.out.println(m.getKey()+"\t"+m.getValue());
        }
        return re.substring("NULL".length());

    }

    String addINodeDirectory(String iNodeName){
        if(dir.get(iNodeName)==null){
            INodeDirectory inode=new INodeDirectory(iNodeName);
            dir.put(inode.getName(),inode);
            return "succeed to upload "+inode.getName();
        }
        else {
            return iNodeName+" exists";
        }
    }

    String addINode(String iNodeName,String fileContent){
        if(dir.get(iNodeName)==null){
            INodeFile inode=new INodeFile(iNodeName,fileContent);
            dir.put(inode.getName(),inode);
            allocateBlocks(inode.getBlocks());
            return "succeed to upload "+inode.getName();
        }
        else {
            return iNodeName+" exists";
        }
    }

    void allocateBlocks(ArrayList<BlockInfo> blocks){
        for(int i=0;i<blocks.size();i++){//for(BlockInfo key:blocks){
            BlockInfo key=blocks.get(i);
            setBlock(key);
//            if(BlocksMap.get(key.getId())==null){
//                BlocksMap.put(key.getId(),allocateBlock(key));
//            }
        }
    }

    List<DataNode> allocateBlock(BlockInfo block){
        List<DataNode> block2Nodes=new ArrayList<>();
        List<DataNode> dataNodeList =  BlocksMap.get(block.getId());
        //REPLICA_NUM
        if(dataNodeList.size()==0) System.err.println("no datanode");
        if(dataNodeList.size()<REPLICA_NUM+1){
            block2Nodes.add(dataNodeList.get(0));
        }
        else{
            for(int i=0;i<REPLICA_NUM+1;i++){
                block2Nodes.add(dataNodeList.get(i));
            }
        }
//        DataNode targetDataNode=dataNodeList.get(0);//Collections.min(dataNodeSet, Comparator.comparingInt(DataNode::getLoad));
////        if(targetDataNode==null) {
////            System.err.println("fail to add inode");
////            return null;
////        }
//        block2Nodes.add(targetDataNode);
//        targetDataNode=dataNodeList.get(dataNodeList.size()>1?1:0);;//Collections.min(dataNodeSet, Comparator.comparingInt(DataNode::getLoad));
////        if(targetDataNode==null) {
////            System.err.println("fail to add inode");
////            return null;
////        }
//        block2Nodes.add(targetDataNode);
        return block2Nodes;
    }

    String removeINode(String iNodeName){
        boolean flag=false;
        for (Iterator<Map.Entry<String, INode>> it = dir.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, INode> item = it.next();
            String key = item.getKey();
            INode val = item.getValue();
            if(key.contains(iNodeName)){
                if(!val.isDirectory()){
                    //INodeFile file=(INodeFile) val;
                    ArrayList<BlockInfo> blocks=((INodeFile)val).getBlocks();
                    int size=blocks.size();//file.getBlocks().size();
                    for(int i=0;i<size;i++){
                        removeBlock(blocks.get(i));
                    }
                }
                it.remove();//dir.remove(key);
                flag=true;
            }
            //todo with key and val
            //you may remove this item using  "it.remove();"
        }
        /*for (String key : dir.keySet()) {//for (Map.Entry<String, INode> m :dir.entrySet())  {
            if(key.indexOf(iNodeName)!=-1){
                dir.remove(key);
                flag=true;
            }
            //System.out.println(m.getKey()+"\t"+m.getValue());
        }*/
        if(flag)return "succeed to delete "+iNodeName;
        else return "fail to delete" +iNodeName;
    }

//    void deleteBlocks(ArrayList<BlockInfo> blocks){
//
//    }

    public String getFileContent(INodeFile iNodeFile){
        StringBuilder re=new StringBuilder("NULL");
        for(int i=0;i<iNodeFile.getBlocks().size();i++){
            BlockInfo block=iNodeFile.getBlocks().get(i);
            String id=block.getId();
            System.out.println("id:"+id);//
            re.append(getBlock(block));
        }
        return re.substring("NULL".length());
    }

//    String getBlocks(ArrayList<BlockInfo> blocks){
//        for(int i=0;i<blocks.size();i++){
//
//        }
//    }

    public void onDataNodeRegister(String id, String ip, int port) {
        dataNodeList.add(new DataNode(id, ip, port));
    }

    public void onDataNodeDown(String id) {
        DataNode dataNode = null;
        for (DataNode node : dataNodeList) {
            if (node.getID().equals(id)) {
                dataNode = node;
                break;
            }
        }
        dataNodeList.remove(dataNode);
//        for (String key : BlocksMap.keySet()) {
//            Set<DataNode> dataNodeSet = BlocksMap.get(key);
//            if (dataNodeSet.contains(dataNode)) {
//                dataNodeSet.remove(dataNode);
//                setBlock(key);
//            }
//        }
    }

    public void setBlock(BlockInfo block) {
        for (int i = 0; i < dataNodeList.size() && i < REPLICA_NUM+1; i ++) {
            DataNode dataNode = dataNodeList.get(i);
            List<DataNode> dataNodeList = BlocksMap.get(block.getId());
            if (dataNodeList == null) {
                dataNodeList = new ArrayList<>();
                dataNodeList.add(dataNode);
                BlocksMap.put(block.getId(), dataNodeList);
            } else {
                dataNodeList.add(dataNode);
            }
            dataNode.setData(block.getId(), block.getData());
        }
    }

//    class SortByLoad implements Comparator {
//        public int compare(Object o1, Object o2) {
//            DataNode s1 = (DataNode) o1;
//            DataNode s2 = (DataNode) o2;
//            if (s1.getLoad() > s2.getLoad())
//                return 1;
//            else if(s1.getLoad() == s2.getLoad()) return 0;
//            else return -1;
//        }
//    }

    public String getBlock(BlockInfo block) {
        List<DataNode> dataNodeList =  BlocksMap.get(block.getId());
        DataNode dataNode = dataNodeList.get(0);//Collections.min(dataNodeList, Comparator.comparingInt(DataNode::getLoad));
        return dataNode.getData(block.getId());
    }

    public void removeBlock(BlockInfo block) {
        List<DataNode> dataNodeList =  BlocksMap.get(block.getId());
//        Iterator<DataNode> it = dataNodeSet.iterator();
//        while (it.hasNext()) {
//            DataNode str = it.next();
//
//        }
            for (int i=0;i<dataNodeList.size();i++) {
                DataNode node=dataNodeList.get(i);
                node.removeData(block.getId());
                dataNodeList.remove(node);
            }
        }

}
