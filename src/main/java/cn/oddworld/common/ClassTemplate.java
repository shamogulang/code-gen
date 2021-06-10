package cn.oddworld.common;

public interface ClassTemplate {


    String outputContent = "package %s;\n" +
            "\n" +
            "import %s;\n" +
            "import %s;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Component;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Component\n" +
            "public class %sRepository {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private %sMapper %sMapper;\n" +
            "\n" +
            "    public int insertBatch(List<%s> %sList){\n" +
            "\n" +
            "        return %sMapper.batchInsert(%sList);\n" +
            "    }\n" +
            "\n" +
            "    private int insertBatchSelective(List<%s> %sList){\n" +
            "\n" +
            "        return %sMapper.batchInsertSelective(%sList);\n" +
            "    }\n" +
            "}";
}
