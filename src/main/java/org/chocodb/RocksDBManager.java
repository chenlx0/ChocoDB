package org.chocodb;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rocksdb.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RocksDBManager {

    public RocksDBManager INSTANCE = null;
    private final String directory;
    private DBOptions options;
    private RocksDB db;
    private Map<String, ColumnFamilyHandle> nameToTableHandler;

    public void InitRocksDB(String path, DBOptions options) {
        if (INSTANCE == null) {
            RocksDB.loadLibrary();
            INSTANCE = new RocksDBManager(path, options);
            INSTANCE.connect();
        }
    }

    public RocksDBManager(String path, DBOptions options) {
        this.directory = path;
        this.options = options;
        this.db = null;
    }

    private boolean connect() {
        try {
            if (options == null) {
                options = new DBOptions().setCreateIfMissing(true);
            }

            // fetch all column families
            List<byte[]> existingFamilies = RocksDB.listColumnFamilies(new Options(), directory);
            List<ColumnFamilyDescriptor> familyDescriptors = Lists.newArrayList();
            List<ColumnFamilyHandle> familyHandles = Lists.newArrayList();
            nameToTableHandler = Maps.newHashMap();
            for (byte[] cfName : existingFamilies) {
                familyDescriptors.add(new ColumnFamilyDescriptor(cfName));
            }

            // open db and initialize family handlers
            familyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY));
            db = RocksDB.open(options, directory, familyDescriptors, familyHandles);
            for (int i = 0; i < existingFamilies.size(); i++) {
                nameToTableHandler.put(new String(existingFamilies.get(i)), familyHandles.get(i));
            }
            nameToTableHandler.put("system", familyHandles.get(familyHandles.size()-1));
        } catch (RocksDBException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void close() {
        db.close();
    }

    public boolean createTable(String tableName) {
        if (nameToTableHandler.get(tableName) != null) {
            // table already exist
            return false;
        }

        ColumnFamilyDescriptor cfd = new ColumnFamilyDescriptor(tableName.getBytes(StandardCharsets.UTF_8));
        try {
            ColumnFamilyHandle handle = db.createColumnFamily(cfd);
            nameToTableHandler.put(tableName, handle);
        } catch (RocksDBException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public byte[] get(String tableName, byte[] key) {
        ColumnFamilyHandle handle = nameToTableHandler.get(tableName);
        if (handle == null) {
            return null;
        }

        try {
            return db.get(handle, key);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RocksIterator getIterator(String tableName) {
        ColumnFamilyHandle handle = nameToTableHandler.get(tableName);
        if (handle == null) {
            return null;
        }

        return db.newIterator(handle);
    }

    public boolean put(String tableName, byte[] key, byte[] value) {
        ColumnFamilyHandle handle = nameToTableHandler.get(tableName);
        if (handle == null) {
            return false;
        }

        try {
            db.put(handle, key, value);
            return true;
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RocksDB.loadLibrary();
        RocksDBManager inst = new RocksDBManager("/Users/bytedance/dev_remote", null);
        inst.connect();
        inst.put("system", "test".getBytes(), "tv".getBytes());
        byte[] res = inst.get("system", "test".getBytes());
        System.out.println("get " + new String(res));
    }

}
