package cn.oddworld.chain.concrete;

import cn.oddworld.base.CodeGenContext;
import cn.oddworld.chain.HandleChain;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;

public class PluginHandleChain implements HandleChain {


    @Override
    public void doTask(Context context, CodeGenContext genContext, String moduleRootPath) {

        // 基本插件，会生成对应的注释
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers","tk.mybatis.mapper.common.Mapper");
        context.addPluginConfiguration(pluginConfiguration);

        // 如果是只生成model
        // 那么这个插件的方法不加入进去
        if(genContext.isModelOnly()){
            return;
        }
        // 添加批量操作的插件
        PluginConfiguration batchInsertPlugin = new PluginConfiguration();
        batchInsertPlugin.setConfigurationType("cn.oddworld.plugins.BatInsertPlugin");
        batchInsertPlugin.addProperty("mappers","tk.mybatis.mapper.common.Mapper");
        context.addPluginConfiguration(batchInsertPlugin);
    }
}
