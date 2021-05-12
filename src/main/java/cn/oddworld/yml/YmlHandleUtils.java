package cn.oddworld.yml;

import cn.oddworld.base.CodeGenContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class YmlHandleUtils {

    public static CodeGenContext yaml2CodeGenContext(InputStream in){

        Yaml yaml = new Yaml(new Constructor(CodeGenContext.class));
        CodeGenContext genContext = yaml.load(in);
        return genContext;
    }
}
