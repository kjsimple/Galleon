package com.gydoc.galleon.datasource;

import com.gydoc.galleon.tenant.TenantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MultiTenantDataSourceAdapter implements DataSource {
    
    private static Logger log = LoggerFactory.getLogger(MultiTenantDataSourceAdapter.class);
    private Map<Object, DataSource> tenantToDS = new HashMap<Object, DataSource>();
    private String dataSourcePattern = null;
    private TenantManager tenantManager = null;
    
    public MultiTenantDataSourceAdapter() {
        if (log.isInfoEnabled()) {
            log.info("MultiTenantDataSourceAdapter created.");
        }
    }
    
    public Connection getConnection() throws SQLException {
        return getOriginalDataSource().getConnection();
    }
    
    public Connection getConnection(String username, String password) throws SQLException {
        return getOriginalDataSource().getConnection(username, password);
    }
    
    private Object getTenantName() {
        return tenantManager.getTenant();
    }
    
    private synchronized DataSource getOriginalDataSource() throws SQLException {
        Object tname = getTenantName();
        DataSource result = tenantToDS.get(tname);
        if (result != null) {
            return result;
        }
        
        String dsName = String.format(dataSourcePattern, tname);
        try {
            Context ctx = new InitialContext();
            result = (DataSource) ctx.lookup(dsName);
            tenantToDS.put(tname, result);
            ActivitiInit.initActivitiForTenant(dsName);
            return result;
        } catch (NamingException e) {
            String msg = String.format("Cannot find data source by name '%1$s'.", dsName);
            if (log.isErrorEnabled()) {
                log.error(msg);
            }
            throw new RuntimeException(msg, e);
        } catch (IOException e) {
            String msg = String.format("Cannot initialize Activiti for tenant '%1$s'.", dsName);
            if (log.isErrorEnabled()) {
                log.error(msg);
            }
            throw new RuntimeException(msg, e);
        }
    }
    
    public int getLoginTimeout() throws SQLException {
        return getOriginalDataSource().getLoginTimeout();
    }
    
    public void setLoginTimeout(int timeout) throws SQLException {
        getOriginalDataSource().setLoginTimeout(timeout);
    }
    
    public void setLogWriter(PrintWriter out) throws SQLException {
        getOriginalDataSource().setLogWriter(out);
    }
    
    public PrintWriter getLogWriter() throws SQLException {
        return getOriginalDataSource().getLogWriter();
    }
    
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getOriginalDataSource().isWrapperFor(iface);
    }
    
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getOriginalDataSource().unwrap(iface);
    }
    
    public void setDataSourcePattern(String pattern) {
        dataSourcePattern = pattern;
    }
    
    public String getDataSourcePattern() {
        return dataSourcePattern;
    }
    
    public void setTenantManager(TenantManager tenantManager) {
        this.tenantManager = tenantManager;
    }
    
    public TenantManager getTenantManager() {
        return tenantManager;
    }
    
    private static class ActivitiInit {
        
        private static Map<String, Boolean> initializedTenant = new HashMap<String, Boolean>();
        
        private ActivitiInit() {
            
        }
        
        public synchronized static void initActivitiForTenant(Object t) throws IOException {
            String tenant = (String) t;
            if (initializedTenant.get(t) != null) {
                return ;
            }
            initActiviti(tenant);
            log.info(String.format("Initialize Activiti for tenant %1$s.", tenant));
            initializedTenant.put(tenant, Boolean.TRUE);
        }

        private static void initActiviti(String tenant) throws IOException {
            Reader reader = new InputStreamReader(ActivitiInit.class.getResourceAsStream("/initActivit.xml"));
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024*80];
            int index = -1;
            while ((index = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, index);
            }
            String configStr = sb.toString();
            configStr = String.format(configStr, tenant);

            AbstractApplicationContext applicationContext = new GenericXmlApplicationContext(new ByteArrayResource(configStr.getBytes()));
            applicationContext.getBean("processEngine");
        }

    }
    
}
