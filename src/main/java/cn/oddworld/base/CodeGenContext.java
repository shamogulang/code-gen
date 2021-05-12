package cn.oddworld.base;


import org.mybatis.generator.config.JDBCConnectionConfiguration;

import java.util.List;

public class CodeGenContext {

    private JDBCConnectionConfiguration connection;
    private List<TableConfig> table;
    private CodeGenPath entity;
    private CodeGenPath mapper;
    private CodeGenPath xmlMapper;
    private CodeGenPath repository;
    private boolean modelOnly;

    public CodeGenPath getEntity() {
        return entity;
    }

    public void setEntity(CodeGenPath entity) {
        this.entity = entity;
    }

    public CodeGenPath getMapper() {
        return mapper;
    }

    public void setMapper(CodeGenPath mapper) {
        this.mapper = mapper;
    }

    public CodeGenPath getXmlMapper() {
        return xmlMapper;
    }

    public void setXmlMapper(CodeGenPath xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public CodeGenPath getRepository() {
        return repository;
    }

    public void setRepository(CodeGenPath repository) {
        this.repository = repository;
    }

    public JDBCConnectionConfiguration getConnection() {
        return connection;
    }

    public void setConnection(JDBCConnectionConfiguration connection) {
        this.connection = connection;
    }

    public List<TableConfig> getTable() {
        return table;
    }

    public void setTable(List<TableConfig> table) {
        this.table = table;
    }

    public boolean isModelOnly() {
        return modelOnly;
    }

    public void setModelOnly(boolean modelOnly) {
        this.modelOnly = modelOnly;
    }

}
