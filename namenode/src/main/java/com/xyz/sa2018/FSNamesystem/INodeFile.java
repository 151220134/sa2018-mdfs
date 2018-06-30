package com.xyz.sa2018.FSNamesystem;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class INodeFile extends INode {
    //@Value(value="${block.default-size}")
    public final int BLOCK_SIZE=4;

//    @Value(value="${block.default-replicas}")
//    public int REPLICA_NUM;

    //protected long header;
    protected String fileName;
    //protected String fileContent;
    //文件数据block块
    protected ArrayList<BlockInfo> blocks= new ArrayList<BlockInfo>();
    //protected BlockInfo blocks[] = null;

    public INodeFile(){
        super();
        this.fileName=null;
//        this.fileContent=null;
    }

    public INodeFile(String localName){
        super(localName);
        Path path = Paths.get(localName);
        this.fileName = path.getFileName().toString();
//        this.fileContent=null;
    }

    public INodeFile(String localName,String content){
        super(localName);
        Path path = Paths.get(localName);
        this.fileName = path.getFileName().toString();
        //this.blocks=String2Blocks(content);
//        BlockInfo block= new BlockInfo(name+"0",content);
        //List<String> result = new ArrayList<String>();
        int iPos = 0;
        int iLen = content.length();
        int count=iLen%BLOCK_SIZE==0?iLen/BLOCK_SIZE:iLen/BLOCK_SIZE+1;
        //System.err.println(count);
        for(int i=0;i<count;i++){//while(content.length()>=BLOCK_SIZE){
            String tmp = content.substring(iPos, iPos+BLOCK_SIZE);
            BlockInfo block= new BlockInfo(name+Integer.toString(i),fileName,tmp);
            this.blocks.add(block);
            content= content.substring(BLOCK_SIZE);
            //iLen= content.length();
            if(content.length()<BLOCK_SIZE){
                tmp=content;
                BlockInfo bloc= new BlockInfo(name+Integer.toString(++i),fileName,tmp);
                this.blocks.add(bloc);
//                System.out.println(bloc.getId());
//                System.out.println(bloc.getData());
            }
//            System.out.println(block.getId());
//            System.out.println(block.getData());
        }
    }

    public INodeFile(INodeFile other){
        this.name=other.name;
        this.fileName=other.fileName;
        this.blocks=other.blocks;
    }

    protected ArrayList<BlockInfo> String2Blocks(String content){
        ArrayList<BlockInfo> list = new ArrayList<BlockInfo>();
        BlockInfo block= new BlockInfo(name+"0",content);
        list.add(block);
        //List<String> result = new ArrayList<String>();
        int iPos = 0;
        int iLen = content.length();
        int count=0;
//        while(iLen>=BLOCK_SIZE){
//            String tmp = content.substring(iPos, iPos+BLOCK_SIZE);
//            BlockInfo block= new BlockInfo(name+Integer.toString(count),fileName,tmp);
//            list.add(block);
//            content= content.substring(BLOCK_SIZE);
//            iLen= content.length();
//            count++;
//        }
            return list;
    }

    public boolean isDirectory() {
        return false;
    }

    public ArrayList<BlockInfo> getBlocks() {
        return this.blocks;
    }

//    public String getFileContent(){
//        return this.fileContent;
//    }

    /**
     * add a block to the block list
     * 往block列表中添加block块
     */
    void addBlock(BlockInfo newblock) {
//        if (this.blocks == null) {
//            this.blocks = new BlockInfo[1];
//            this.blocks[0] = newblock;
//        } else {
//            int size = this.blocks.length;
//            //BlocksMap.BlockInfo[] newlist = new BlocksMap.BlockInfo[size + 1];
//            BlockInfo[] newlist = new BlockInfo[size + 1];
//            System.arraycopy(this.blocks, 0, newlist, 0, size);
//            newlist[size] = newblock;
//            this.blocks = newlist;
//        }
        blocks.add(newblock);
    }

    /**
     * Set file block
     */
//    void setBlock(int idx, BlockInfo blk) {
//        this.blocks[idx] = blk;
//    }

    //将自身拥有的block块加入到参数block列表中
    int collectSubtreeBlocksAndClear(List<BlockInfo> v) {
        parent = null;
        for (BlockInfo blk : blocks) {
            v.add(blk);
        }
        blocks = null;
        return 1;
    }
}
