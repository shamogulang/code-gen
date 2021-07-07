package cn.oddworld.common;

public interface ClassTemplate {


    String outputRepository = "package %s;\n" +
            "\n" +
            "import %s;\n" +
            "import %s;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Component;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Component\n" +
            "public class UPNAME_Repository {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private UPNAME_Mapper LOWNAME_Mapper;\n" +
            "\n" +
            "    public int insertBatch(List<UPNAME_> LOWNAME_List){\n" +
            "\n" +
            "        return LOWNAME_Mapper.batchInsert(LOWNAME_List);\n" +
            "    }\n" +
            "\n" +
            "    public int insert(UPNAME_ LOWNAME_){\n" +
            "\n" +
            "        return LOWNAME_Mapper.insert(LOWNAME_);\n" +
            "    }\n" +
            "\n" +
            "    public int insertSelective(UPNAME_ LOWNAME_){\n" +
            "\n" +
            "        return LOWNAME_Mapper.insertSelective(LOWNAME_);\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    public int updateByPrimaryKeySelective(UPNAME_ LOWNAME_){\n" +
            "\n" +
            "        return LOWNAME_Mapper.updateByPrimaryKeySelective(LOWNAME_);\n" +
            "    }\n" +
            "\n" +
            "    public int updateByPrimaryKey(UPNAME_ LOWNAME_){\n" +
            "\n" +
            "        return LOWNAME_Mapper.updateByPrimaryKey(LOWNAME_);\n" +
            "    }\n" +
            "\n" +
            "    public UPNAME_ selectByPrimaryKey(UPNAME_ LOWNAME_){\n" +
            "\n" +
            "        return LOWNAME_Mapper.selectByPrimaryKey(LOWNAME_);\n" +
            "    }\n" +
            "\n" +
            "    public List<UPNAME_> selectAll(){\n" +
            "\n" +
            "        return LOWNAME_Mapper.selectAll();\n" +
            "    }\n" +
            "}";
}
