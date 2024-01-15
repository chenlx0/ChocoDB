package org.chocodb;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class ChocoSchemaFactory implements SchemaFactory {

    // Public singleton
    public static final ChocoSchemaFactory INSTANCE = new ChocoSchemaFactory();

    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
        // fetch schema from files and return
        return null;
    }

}
