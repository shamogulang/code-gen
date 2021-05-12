package cn.oddworld;

import cn.oddworld.base.BaseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception{
        FileInputStream in = new FileInputStream(new File("E:\\github\\code-gen\\src\\main\\resources\\test.yml"));
        BaseHandler baseHandler = new BaseHandler();
        List<String> strings = baseHandler.genCode(in);
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println();
    }
}
