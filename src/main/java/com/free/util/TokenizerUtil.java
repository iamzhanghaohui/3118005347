package com.free.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.Word;
import cn.hutool.extra.tokenizer.engine.hanlp.HanLPEngine;


import java.util.*;

/**
 * @ClassNameTokenizerUtil
 * @Description 分词工具
 * @Author Free
 * @Date2020/9/20 13:21
 * @Version V1.0
 **/
public class TokenizerUtil {

    public static void main(String[] args) {
        String path = "D:\\Free\\课程\\软件工程\\数据源\\test\\test\\orig.txt";
        String path2 = "D:\\Free\\课程\\软件工程\\数据源\\test\\test\\orig_0.8_add.txt";
        Map<String, Vector<Integer>> stringVectorMap = TokenizerUtil.CountWord(path);
        Map<String, Vector<Integer>> stringVectorMap2 = TokenizerUtil.CountWord(path2);
    }
    public static Map<String, Vector<Integer>> CountWord(String path){
        Map<String, Vector<Integer>> resMap = new TreeMap<String,Vector<Integer>>();

        TokenizerEngine engine = new HanLPEngine();
        FileUtil fileUtil = new FileUtil();
        String s = fileUtil.readFile(path);
        FileReader fileReader = new FileReader(path);
        String text = fileReader.readString();
        Result result = engine.parse(text);
        //解析文本
        Iterator<Word> iterator = result.iterator();
        int pos=0;
        while(iterator.hasNext()){
            String tempWord = iterator.next().toString(), news = "";
            for (int i = 0; i < tempWord.length(); i++) {
                char c = tempWord.charAt(i);
                if (String.valueOf(c).matches("[\u4e00-\u9fa5]")) {
                    news += c;
                }
            }
            if(resMap.get(news)==null){
                Vector<Integer>off=new Vector<Integer>(100);
                off.add(pos);
                resMap.put(news,off);
            }
            resMap.get(news).add(pos);
            pos++;
        }
        return resMap;
    }

}
