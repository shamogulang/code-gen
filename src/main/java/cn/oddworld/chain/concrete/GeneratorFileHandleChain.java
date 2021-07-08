package cn.oddworld.chain.concrete;

import cn.oddworld.base.CodeGenContext;
import cn.oddworld.chain.HandleChain;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

import java.io.IOException;

public class GeneratorFileHandleChain implements HandleChain {

    @Override
    public void doTask(Context context, CodeGenContext genContext, String moduleRootPath) throws IOException {
        // xml mapper setting
        JavaClientGeneratorConfiguration javaCli = new JavaClientGeneratorConfiguration();
        javaCli.setTargetPackage(genContext.getMapper().getTargetPackage());
        javaCli.setTargetProject(String.format(genContext.getMapper().getTargetProject(), moduleRootPath));
        javaCli.setConfigurationType("MAPPER");

        // mapper interface setting
        SqlMapGeneratorConfiguration sqlMapGen= new SqlMapGeneratorConfiguration();
        sqlMapGen.setTargetPackage(genContext.getXmlMapper().getTargetPackage());
        sqlMapGen.setTargetProject(String.format(genContext.getXmlMapper().getTargetProject(), moduleRootPath));


        // entity setting
        JavaModelGeneratorConfiguration javaModelGen = new JavaModelGeneratorConfiguration();
        javaModelGen.setTargetPackage(genContext.getEntity().getTargetPackage());
        javaModelGen.setTargetProject(String.format(genContext.getEntity().getTargetProject(), moduleRootPath));

        context.setJavaClientGeneratorConfiguration(javaCli);
        context.setJavaModelGeneratorConfiguration(javaModelGen);
        context.setSqlMapGeneratorConfiguration(sqlMapGen);

        context.setJdbcConnectionConfiguration(genContext.getConnection());

        context.setId("Mysql");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty("javaFileEncoding", "UTF-8");
    }
}
