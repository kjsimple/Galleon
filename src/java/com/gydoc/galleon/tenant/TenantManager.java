package com.gydoc.galleon.tenant;

public class TenantManager {
    
    public static final String TENANT_KEY = "tenant";
    private ThreadLocal<Object> tl = new ThreadLocal<Object>();
    private static TenantManager instance = new TenantManager();
    
    private TenantManager() {
        
    }
    
    public static TenantManager getInstance() {
        return instance;
    }
    
    public void setTenant(Object tenant) {
        tl.set(tenant);
    }
    
    public Object getTenant() {
        Object rs = tl.get();
        if (rs == null) {
            rs = getDefaultTenant();
        }
        return rs;
    }
    
    public String getDefaultTenant() {
        return "galleon";
    }
    
}
