package com.free.util;

import cn.hutool.core.io.file.FileReader;

/**
 * @ClassNameFileutil
 * @Description
 * @Author Free
 * @Date2020/9/20 12:34
 * @Version V1.0
 **/
public class FileUtil {
    /**
     *
     * @param filepath 文件路径
     * @return
     */
    public String readFile(String filepath){
        if(filepath==null ||"".equals(filepath)){
            return null;
        }
        FileReader fileReader = new FileReader(filepath);
        String result = fileReader.readString();
        return result;
    }
}
