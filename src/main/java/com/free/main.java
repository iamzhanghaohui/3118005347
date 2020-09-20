package com.free;
import cn.hutool.core.io.file.FileReader;

/**
 * @ClassNamemain
 * @Description
 * @Author Free
 * @Date2020/9/20 11:40
 * @Version V1.0
 **/
public class main {
    public static void main(String[] args) {
        System.out.println("初始化仓库");
        //默认UTF-8编码，可以在构造中传入第二个参数做为编码
        FileReader fileReader = new FileReader("D:\\Free\\课程\\软件工程\\数据源\\test\\test\\orig.txt");
        String result = fileReader.readString();
        System.out.println(result);

    }
}
