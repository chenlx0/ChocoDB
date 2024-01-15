package org.chocodb;

import org.apache.calcite.linq4j.Enumerator;
import org.rocksdb.RocksIterator;

public class ChocoEnumerator implements Enumerator<Object[]> {

    final RocksIterator iterator;
    final String tableName;

    ChocoEnumerator(String tableName, RocksIterator iterator) {
        this.tableName = tableName;
        this.iterator = iterator;
        this.iterator.seekToFirst();
    }

    @Override
    public Object[] current() {
        return null;
    }



}
