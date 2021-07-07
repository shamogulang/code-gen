package cn.oddworld.chain.concrete;

import cn.oddworld.base.CodeGenContext;
import cn.oddworld.base.TableConfig;
import cn.oddworld.chain.HandleChain;
import cn.oddworld.common.ClassTemplate;
import cn.oddworld.common.FileHandleUtil;
import org.mybatis.generator.config.Context;

import java.io.IOException;
import java.util.List;

public class RepositoryHandleChain implements HandleChain {

    @Override
    public void doTask(Context context, CodeGenContext genContext, String moduleRootPath) throws IOException {

        if(genContext.isModelOnly() ||genContext.getRepository().isIgnore() ){
            return;
        }

        // 需要生成repository
        String targetPackage = genContext.getRepository().getTargetPackage();
        String targetProject = genContext.getRepository().getTargetProject();
        List<TableConfig> tableList = genContext.getTable();
        for (TableConfig table : tableList) {
            String content = ClassTemplate.outputRepository;
            String tableName = FileHandleUtil.generateName(table.getName());
            String lowerName = FileHandleUtil.lowerStart(tableName);
            String importEntity = genContext.getEntity().getTargetPackage()+"."+tableName;
            String importMapper = genContext.getMapper().getTargetPackage()+"."+tableName+"Mapper";
            content = String.format(content, targetPackage, importEntity, importMapper);

            content = content.replace("UPNAME_", tableName);
            content = content.replace("LOWNAME_", lowerName);

            String path =  String.format(targetProject, moduleRootPath) +"/"+ targetPackage.replaceAll("\\.", "/");
            String repositoryFile = path+"/"+tableName+"Repository.java";
            FileHandleUtil.fileOutput(content,path,repositoryFile);
        }
    }
}
