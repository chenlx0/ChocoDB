package org.chocodb;

import java.util.List;

public class ChocoTableFieldInfo {

    private String tableName;
    private List<String> fieldsName;
    private List<Object> fieldsTypes;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
