package org.chocodb;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.rocksdb.DBOptions;

import java.util.Map;

public class ChocoSchema extends AbstractSchema {

    private final String path;
    private final DBOptions options;

    public ChocoSchema(String path, DBOptions options) {
        this.path = path;
        this.options = options;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return null;
    }

}
