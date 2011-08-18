package com.gydoc.galleon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class IdGeneratorImpl implements IdGenerator {

    private static Logger log = LoggerFactory.getLogger(IdGeneratorImpl.class);

    private DataSource dataSource;
    private int cacheSize = -1;
    private int nextId = -1;
    private int maxId = -1;
    private static IdGenerator instance = new IdGeneratorImpl();
    
    private IdGeneratorImpl() {
        log.info("IdGeneratorImpl instance was created.");
    }
    
    public synchronized static IdGenerator createInstance() {
        return instance;
    }

    public synchronized Long nextId() {
        if (nextId == -1) {
            throw new IllegalStateException("com.gydoc.galleon.IdGeneratorImpl has not been initialized.");
        }
        try {
            int i = nextId++;
            if (maxId < nextId) {
                initId();
            }
            return new Long(i);
        } catch (SQLException e) {
            String msg = "Exception occurred while get next id from IdGenerator.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }
    
    public void init() throws SQLException {
        initId();
    }

    private void initId() throws SQLException {
        Connection conn = getDataSource().getConnection();
        PreparedStatement psmt = conn.prepareStatement("SELECT nid FROM galleon_id");
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            nextId = rs.getInt(1);
            if (cacheSize > 0) {
                maxId = nextId + cacheSize - 1;
                updateId();
            } else {
                maxId = nextId;
            }
            return ;
        }
        throw new IllegalStateException(String.format("There is no record for IdGenerator in table galleon_id. Database is %1$s.", conn.getMetaData().getURL()));
    }

    private void updateId() throws SQLException {
        Connection conn = getDataSource().getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psmt = conn.prepareStatement("UPDATE galleon_id SET nid=" + (maxId+1));
        int result = psmt.executeUpdate();
        if (result != 1) {
            throw new SQLException("Failed to update nid for IdGenerator.");
        }
        conn.commit();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

}
