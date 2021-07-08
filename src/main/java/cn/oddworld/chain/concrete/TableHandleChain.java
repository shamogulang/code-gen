package cn.oddworld.chain.concrete;

import cn.oddworld.base.CodeGenContext;
import cn.oddworld.base.TableConfig;
import cn.oddworld.chain.HandleChain;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;

public class TableHandleChain implements HandleChain {

    @Override
    public void doTask(Context context, CodeGenContext genContext, String moduleRootPath) {

        List<TableConfig> tables = genContext.getTable();
        tables.forEach(table -> {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(table.getName());
            GeneratedKey key = new GeneratedKey(table.getColumn(),"Mysql", true, null );
            tableConfiguration.setGeneratedKey(key);
            tableConfiguration.setAllColumnDelimitingEnabled(false);
            tableConfiguration.setCountByExampleStatementEnabled(false);
            tableConfiguration.setInsertStatementEnabled(false);
            tableConfiguration.addProperty("modelOnly", genContext.isModelOnly()+"");
            context.addTableConfiguration(tableConfiguration);
        });
    }
}
