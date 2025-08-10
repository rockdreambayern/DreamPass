package com.dreampass.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class MySQLPaginationPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 给 Example 类加 limit/offset 字段
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        // limit
        Field limit = new Field("limit", new FullyQualifiedJavaType("java.lang.Integer"));
        limit.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(limit);
        topLevelClass.addMethod(getGetter(limit));
        topLevelClass.addMethod(getSetter(limit));

        // offset
        Field offset = new Field("offset", new FullyQualifiedJavaType("java.lang.Integer"));
        offset.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(offset);
        topLevelClass.addMethod(getGetter(offset));
        topLevelClass.addMethod(getSetter(offset));

        return true;
    }

    private Method getGetter(Field field) {
        Method method = new Method("get" + capitalize(field.getName()));
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(field.getType());
        method.addBodyLine("return " + field.getName() + ";");
        return method;
    }

    private Method getSetter(Field field) {
        Method method = new Method("set" + capitalize(field.getName()));
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(field.getType(), field.getName()));
        method.addBodyLine("this." + field.getName() + " = " + field.getName() + ";");
        return method;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 修改 selectByExampleWithoutBLOBs 节点 SQL，添加分页
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        addLimitOffsetElement(element);
        return true;
    }

    /**
     * 修改 selectByExampleWithBLOBs 节点 SQL，添加分页
     */
    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        addLimitOffsetElement(element);
        return true;
    }

    /**
     * 给 SQL 添加 LIMIT offset, limit
     */
    private void addLimitOffsetElement(XmlElement element) {
        XmlElement ifLimitNotNull = new XmlElement("if");
        ifLimitNotNull.addAttribute(new Attribute("test", "limit != null"));
        ifLimitNotNull.addElement(new TextElement("LIMIT #{offset}, #{limit}"));
        element.addElement(ifLimitNotNull);
    }
}
