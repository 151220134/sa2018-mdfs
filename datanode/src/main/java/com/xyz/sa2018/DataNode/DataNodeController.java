package com.xyz.sa2018.DataNode;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/block")
public class DataNodeController {

    private DataNodeService dataNodeService;

    @Autowired
    public DataNodeController(DataNodeService dataNodeService) {
        this.dataNodeService = dataNodeService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String setBlock(@RequestParam(value = "id")String id,
                          @RequestParam(value = "data")String data
            //, @RequestParam(value = "name")String name
                           ) {
        return dataNodeService.setDataBlock(new Block(id, data));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getBlock(@RequestParam(value = "id")String id) {
        Block dataBlock = dataNodeService.getDataBlock(id);
        return dataBlock.getData();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String removeBlock(@RequestParam(value = "id")String id) {
        return dataNodeService.removeDataBlock(id);
    }
}
