package com.motech.test.persistEntityDefinition;

public class TableDefinition {

    private int id;
    private String tableName;
    private String tableDefinition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableDefinition() {
        return tableDefinition;
    }

    public void setTableDefinition(String tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    public String getTableName() {

        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
