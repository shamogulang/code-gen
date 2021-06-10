package cn.oddworld.chain;

import cn.oddworld.base.CodeGenContext;
import org.mybatis.generator.config.Context;

import java.io.IOException;

public interface HandleChain {

    void doTask(Context context, CodeGenContext genContext, String moduleRootPath) throws IOException;
}
