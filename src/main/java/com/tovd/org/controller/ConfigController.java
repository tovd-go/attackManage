package com.tovd.org.controller;


import com.alibaba.fastjson.JSON;
import com.tovd.org.utils.readJson;
import com.tovd.org.utils.saveJson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/config")
@Mapper
public class ConfigController {


    @GetMapping("/tyc")
    public int saveCookie(@RequestParam String cookie){
        readJson read=new readJson();

        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        map.put("tycCookie",cookie);
        JSON.toJSON(map);
        saveJson configsave=new saveJson();
        if (configsave.saveJsonFile(JSON.toJSON(map),"D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json")){
         return 200;
        }
        return 400;
    }
    @GetMapping("/fofa")
    public int savefofa(@RequestParam String fofaemail,@RequestParam String fofaapi){
        readJson read=new readJson();

        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        map.put("fofaEmail",fofaemail);
        map.put("fofaApiKey",fofaapi);
        JSON.toJSON(map);
        saveJson configsave=new saveJson();
        if (configsave.saveJsonFile(JSON.toJSON(map),"D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json")){
            return 200;
        }
        return 400;
    }
    @GetMapping("/securityTrails")
    public int savesecurityTrails(@RequestParam String securityTrails){
        readJson read=new readJson();

        String configJson= read.readJsonFile("D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json");
        Map map= JSON.parseObject(configJson);
        map.put("securityTrailsApiKey",securityTrails);
        JSON.toJSON(map);
        saveJson configsave=new saveJson();
        if (configsave.saveJsonFile(JSON.toJSON(map),"D:\\Code\\Java\\Spring-all\\attackManage\\src\\main\\resources\\config.json")){
            return 200;
        }
        return 400;
    }

}
