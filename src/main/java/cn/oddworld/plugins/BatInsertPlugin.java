package cn.oddworld.plugins;

import cn.oddworld.common.Constant;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BatInsertPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        // 因为不是做什么限制，直接让验证通过即可
        return true;
    }

    /**
     * 生成mapper接口的方法
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        Method method = new Method( Constant.METHOD_BATCH_INSERT);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        FullyQualifiedJavaType tList = FullyQualifiedJavaType.getNewListInstance();
        tList.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        method.addParameter(new Parameter(tList, "list", "@Param(\"list\")"));
        // interface 增加方法
        interfaze.addMethod(method);
        interfaze.addImportedType(tList);
        interfaze.addImportedType(FullyQualifiedJavaType.getNewListInstance());
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

          // 构建batchInsert的xml
          batchInsert(document, introspectedTable);


        return true;
    }


    public void batchInsert(Document document, IntrospectedTable introspectedTable){
        // xml的insert元素
        XmlElement xml = new XmlElement("insert");
        xml.addAttribute(new Attribute("id", Constant.METHOD_BATCH_INSERT));
        // xml
        xml.addAttribute(new Attribute("parameterType", "java.util.List"));

        StringBuilder insertClause = new StringBuilder();


        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append(" (");

        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            // 生成foreach下插入values
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            if (i + 1 < columns.size()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }
        }

        insertClause.append(')');
        xml.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());


        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));

//        for (String clause :  ) {
//            foreachElement.addElement(new TextElement(clause));
//        }

        // values 构建
        xml.addElement(new TextElement("values"));
        xml.addElement(foreachElement);
        document.getRootElement().addElement(xml);
    }
}
