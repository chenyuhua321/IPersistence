package com.cyh.config;

import com.cyh.enums.SqlCommandType;
import com.cyh.pojo.Configuration;
import com.cyh.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/22 18:18
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        mappedStatementBuild(namespace, rootElement.selectNodes("//select"), SqlCommandType.SELECT);
        mappedStatementBuild(namespace, rootElement.selectNodes("//insert"), SqlCommandType.INSERT);
        mappedStatementBuild(namespace, rootElement.selectNodes("//update"), SqlCommandType.UPDATE);
        mappedStatementBuild(namespace, rootElement.selectNodes("//delete"), SqlCommandType.DELETE);
    }

    private void mappedStatementBuild(String namespace, List<Element> list, SqlCommandType sqlCommandType) {
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = MappedStatement.builder()
                    .id(id)
                    .resultType(resultType)
                    .paramterType(paramterType)
                    .resultType(resultType)
                    .sql(sqlText)
                    .sqlCommandType(sqlCommandType)
                    .build();
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }

}
