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
        Map<String, List<Integer>> stringListMap = TokenizerUtil.CountWord(path);
        Map<String, List<Integer>> stringListMap2 = TokenizerUtil.CountWord(path2);
        Double aDouble = TokenizerUtil.CountCos(stringListMap, stringListMap2);

        System.out.println(aDouble);

    }
    public static Map<String, List<Integer>> CountWord(String path){
        Map<String, List<Integer>> resMap = new TreeMap<String,List<Integer>>();

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
            String tempWord = iterator.next().toString();
            String afterWord ="";
            for (int i = 0; i < tempWord.length(); i++) {
                char c = tempWord.charAt(i);
                //只判断中文
                if (String.valueOf(c).matches("[\u4e00-\u9fa5]")) {
                    afterWord += c;
                }
            }
            if(resMap.get(afterWord)==null){
                ArrayList<Integer> newList = new ArrayList<Integer>(100);
                newList.add(pos);
                resMap.put(afterWord,newList);
            }
            List<Integer> tempList = resMap.get(afterWord);
            //记录新的位置
            tempList.add(pos);
            pos++;
        }
        return resMap;
    }

    /**
     *
     * @param map1 已经计算好词频的原文
     * @param map2 已经计算好词频的抄袭文
     */
    public static Double CountCos(Map<String, List<Integer>> map1,Map<String, List<Integer>> map2){
        //统计计算了多少个词
        int count = 0;
        //最后的结果
        int result =0;
        //余弦方程的上部
        double equationUp = 0;
        //余弦方程的下部部分一
        double equationDown1 = 0;
        //余弦方程的下部部分二
        double equationDown2 = 0;
        //每一个词的相似度相加
        double sum = 0;
        for(String key:map1.keySet()){
            List<Integer> wordListFromOrign = map1.get(key);
            List<Integer> wordListFromCopy = map2.get(key);
            //第二个文本中也存在这个词，就计算向量
            if(wordListFromCopy!=null){
                equationUp=equationDown1=equationDown2=0;
                //开始遍历每一个词的位置，准备构建余弦公式
                for(int i=0;i<wordListFromOrign.size()&&i<wordListFromCopy.size();i++){
                    Integer posFromOrign = wordListFromOrign.get(i);
                    Integer posFromCopy = wordListFromCopy.get(i);
                    //上面两个数相乘
                    equationUp+= posFromOrign * posFromCopy;
                    //下面相乘 后面要开方
                    equationDown1+= posFromOrign * posFromOrign;
                    equationDown2+= posFromCopy * posFromCopy;
                }
                equationDown1 = Math.sqrt(equationDown1);
                equationDown2 = Math.sqrt(equationDown2);
                double equationDown = equationDown1 * equationDown2;
                if(equationDown!=0){
                    sum+= equationUp/equationDown;
                }
            }
            count++;
        }
        return sum/count;

    }

}
