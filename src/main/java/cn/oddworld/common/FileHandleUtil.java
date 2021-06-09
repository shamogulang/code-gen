package cn.oddworld.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandleUtil {

    public static void fileOutput(String fileContent, String dir, String file) throws IOException {
        Path path0 = Paths.get(dir);
        if(!Files.exists(path0)){
            Files.createDirectories(path0);
        }
        Path file0  = Paths.get(file);
        BufferedWriter bufferedWriter0 = Files.newBufferedWriter(file0);
        bufferedWriter0.write(fileContent);
        bufferedWriter0.flush();
    }

    /**
     * 生成驼峰数据，但是第一个字符大写
     * @param table
     * @return
     */
    public static String generateName(String table){
        // 生成驼峰格式数据
        String result = toCamelCase(table);
        // 第一个字母大小
        result = result.substring(0,1).toUpperCase()+result.substring(1);
        return result;
    }

    public static String lowerStart(String result){
        return result.substring(0,1).toLowerCase()+result.substring(1);
    }

    /**
     * 生成驼峰格式的属性
     * @param columnName
     * @return
     */
    public static String toCamelCase(String columnName){
        String[] temp = columnName.split("_");
        String result = null;
        for(int j = 0; j< temp.length;j++){
            if(j==0){
                result = temp[j];
            }else{
                result = result+temp[j].substring(0,1).toUpperCase()+temp[j].substring(1);
            }
        }
        return result;
    }
}
