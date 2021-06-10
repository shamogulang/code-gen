package cn.oddworld.chain;

import cn.oddworld.base.CodeGenContext;
import cn.oddworld.chain.concrete.GeneratorFileHandleChain;
import cn.oddworld.chain.concrete.PluginHandleChain;
import cn.oddworld.chain.concrete.RepositoryHandleChain;
import cn.oddworld.chain.concrete.TableHandleChain;
import org.mybatis.generator.config.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleChainClient {

    private List<HandleChain> handleChains = new ArrayList<>();
    public HandleChainClient() {
        handleChains.add(new GeneratorFileHandleChain());
        handleChains.add(new TableHandleChain());
        handleChains.add(new PluginHandleChain());
        handleChains.add(new RepositoryHandleChain());
    }

    public void execute(Context context, CodeGenContext genContext, String moduleRootPath) throws IOException {

        for (HandleChain handleChain : handleChains) {
            handleChain.doTask(context, genContext, moduleRootPath);
        }
    }
}
