package cn.oddworld.base;
import cn.oddworld.chain.HandleChainClient;
import cn.oddworld.common.FileHandleUtil;
import cn.oddworld.common.ClassTemplate;
import cn.oddworld.yml.YmlHandleUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class BaseHandler implements Handler{


    /**
     * 如果生成失败，返回对应的异常信息
     * @param inputStream
     * @return
     */
    public List<String> genCode(InputStream inputStream, String moduleRootPath) {

        List<String> warnings = new ArrayList<>();
        if(inputStream == null){
            warnings.add(String.format("BaseHandler codeGen inputStream is null, please set the correct config path"));
            return warnings;
        }

        try {
            // 读取配置文件
            CodeGenContext genContext = YmlHandleUtils.yaml2CodeGenContext(inputStream);
            if(genContext.getTable() == null || genContext.getTable().isEmpty()){
                warnings.add(String.format("BaseHandler codeGen table is null, please set the table info"));
                return warnings;
            }

            Context context = new Context(ModelType.FLAT);

            new HandleChainClient().execute(context,genContext, moduleRootPath);

            Configuration config = new Configuration();
            config.addContext(context);
            // 支持覆盖原来生成的代码
            DefaultShellCallback callback = new DefaultShellCallback(Boolean.TRUE);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 出发生成代码的动作
            myBatisGenerator.generate(null);
        }catch (IOException e0){
            warnings.add(String.format("BaseHandler codeGen IOException => %s", e0.getMessage()));
        }catch (InvalidConfigurationException e2){
            warnings.add(String.format("BaseHandler codeGen InvalidConfigurationException => %s", e2.getMessage()));
        }catch (SQLException e3){
            warnings.add(String.format("BaseHandler codeGen SQLException => %s", e3.getMessage()));
        }catch (InterruptedException e4){
            warnings.add(String.format("BaseHandler codeGen InterruptedException => %s", e4.getMessage()));
        }
        return warnings;
    }
}
