package org.chocodb;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.impl.AbstractTable;

import java.util.List;

public class ChocoTable extends AbstractTable {

    final String schemaName;
    final String tableName;
    final List<String> fieldsName;
    final List<Object> fieldsType;

    ChocoTable(String tableName, List<String> fieldsName, List<Object> fieldsType) {
        this.tableName = tableName;
        this.fieldsName = fieldsName;
        this.fieldsType = fieldsType;
        this.schemaName = "Test";
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return null;
    }

}
