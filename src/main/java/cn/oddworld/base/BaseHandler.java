package cn.oddworld.base;
import cn.oddworld.common.FileHandleUtil;
import cn.oddworld.common.RepositoryTemplate;
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
    public List<String> genCode(InputStream inputStream) {
        List<String> warnings = new ArrayList<>();
        if(inputStream == null){
            warnings.add(String.format("BaseHandler codeGen inputStream is null, please set the correct config path"));
            return warnings;
        }
        // 读取配置文件
        try {
            Configuration config = new Configuration();
            Context context = new Context(ModelType.FLAT);
            CodeGenContext genContext = YmlHandleUtils.yaml2CodeGenContext(inputStream);
            if(genContext.getTable() == null || genContext.getTable().isEmpty()){
                warnings.add(String.format("BaseHandler codeGen table is null, please set the table info"));
                return warnings;
            }

            String contextPath = System.getProperty("user.dir");
            contextPath = contextPath.replace("\\", "/");

            // xml mapper setting
            JavaClientGeneratorConfiguration javaCli = new JavaClientGeneratorConfiguration();
            javaCli.setTargetPackage(genContext.getXmlMapper().getTargetPackage());
            javaCli.setTargetProject(String.format(genContext.getXmlMapper().getTargetProject(), contextPath));
            javaCli.setConfigurationType("MAPPER");

            // entity setting
            JavaModelGeneratorConfiguration javaModelGen = new JavaModelGeneratorConfiguration();
            javaModelGen.setTargetPackage(genContext.getEntity().getTargetPackage());
            javaModelGen.setTargetProject(String.format(genContext.getEntity().getTargetProject(), contextPath));

            // mapper interface setting
            SqlMapGeneratorConfiguration sqlMapGen= new SqlMapGeneratorConfiguration();
            sqlMapGen.setTargetPackage(genContext.getMapper().getTargetPackage());
            sqlMapGen.setTargetProject(String.format(genContext.getMapper().getTargetProject(), contextPath));

            context.setJavaClientGeneratorConfiguration(javaCli);
            context.setJavaModelGeneratorConfiguration(javaModelGen);
            context.setSqlMapGeneratorConfiguration(sqlMapGen);

            context.setJdbcConnectionConfiguration(genContext.getConnection());

            context.setId("Mysql");
            context.setTargetRuntime("MyBatis3Simple");


            List<TableConfig> tables = genContext.getTable();
            tables.forEach(table -> {
                TableConfiguration tableConfiguration = new TableConfiguration(context);
                tableConfiguration.setTableName(table.getName());
                GeneratedKey key = new GeneratedKey(table.getColumn(),"Mysql", true, null );
                tableConfiguration.setGeneratedKey(key);
                tableConfiguration.setAllColumnDelimitingEnabled(false);
                tableConfiguration.setCountByExampleStatementEnabled(false);
                tableConfiguration.setInsertStatementEnabled(false);
                context.addTableConfiguration(tableConfiguration);
            });

            // 基本插件，会生成对应的注释
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
            pluginConfiguration.addProperty("mappers","tk.mybatis.mapper.common.Mapper");
            context.addPluginConfiguration(pluginConfiguration);

            // 添加批量操作的插件
            PluginConfiguration batchInsertPlugin = new PluginConfiguration();
            batchInsertPlugin.setConfigurationType("cn.oddworld.plugins.BatInsertPlugin");
            batchInsertPlugin.addProperty("mappers","tk.mybatis.mapper.common.Mapper");
            context.addPluginConfiguration(batchInsertPlugin);

            // 添加批量操作的插件
            PluginConfiguration batchInsertSelectivePlugin = new PluginConfiguration();
            batchInsertSelectivePlugin.setConfigurationType("cn.oddworld.plugins.BatchInsertSelectivePlugin");
            batchInsertSelectivePlugin.addProperty("mappers","tk.mybatis.mapper.common.Mapper");
            context.addPluginConfiguration(batchInsertSelectivePlugin);

            config.addContext(context);
            // 支持覆盖原来生成的代码
            DefaultShellCallback callback = new DefaultShellCallback(Boolean.TRUE);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 出发生成代码的动作
            myBatisGenerator.generate(null);

            // 需要生成repository
            if(!genContext.isModelOnly() && !genContext.getRepository().isIgnore()){

                String targetPackage = genContext.getRepository().getTargetPackage();
                String targetProject = genContext.getRepository().getTargetProject();
                List<TableConfig> tableList = genContext.getTable();
                for (TableConfig table : tableList) {
                    String content = RepositoryTemplate.outputContent;
                    String tableName = FileHandleUtil.generateName(table.getName());
                    String lowerName = FileHandleUtil.lowerStart(tableName);
                    String importEntity = genContext.getEntity().getTargetPackage()+"."+tableName+"Entity";
                    String importMapper = genContext.getMapper().getTargetPackage()+"."+tableName+"Mapper";
                    content = String.format(content, targetPackage, importEntity, importMapper
                            ,tableName, tableName, lowerName, tableName, lowerName,
                            lowerName, lowerName, tableName, lowerName, lowerName, lowerName);

                    String path =  String.format(targetProject,contextPath) +"/"+ targetPackage.replaceAll("\\.", "/");
                    String repositoryFile = path+"/"+tableName+"Repository.java";
                    FileHandleUtil.fileOutput(content,path,repositoryFile);
                }
            }
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
