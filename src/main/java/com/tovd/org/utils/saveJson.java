package com.tovd.org.utils;

import com.alibaba.fastjson.JSON;
import org.mybatis.logging.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Filter;
import java.util.logging.Logger;

public class saveJson {
    public static boolean saveJsonFile(Object jsonData,String filePath){

        String content= JSON.toJSONString(jsonData);
        boolean flag=true;

        try {
            File file=new File(filePath);
            Writer writer=new OutputStreamWriter(new FileOutputStream(file),"utf-8");
            writer.write(content);
            writer.flush();
            writer.close();
        }catch (Exception e){
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }

}
