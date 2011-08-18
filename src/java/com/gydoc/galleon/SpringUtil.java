package com.gydoc.galleon;

import com.gydoc.galleon.tenant.TenantManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SpringUtil {
    
    private static String FILES = "";
    private static Map<String, AbstractApplicationContext> tenantSpringCtx = new HashMap<String, AbstractApplicationContext>();
    
    private SpringUtil() {
        
    }

    public static void init(String files) {
        FILES = files;
    }

    public synchronized static ApplicationContext getSpringContext() {
        String tenant = (String) TenantManager.getInstance().getTenant();
        AbstractApplicationContext springCtx = tenantSpringCtx.get(tenant);
        try {
            if (springCtx == null) {
                String[] files = FILES.split(";");
                for (int i = 0; i < files.length; i++) {
                    files[i] = files[i].trim();
                }
                springCtx = new GenericXmlApplicationContext(convertToResources(files, tenant));
                springCtx.registerShutdownHook();
                tenantSpringCtx.put(tenant, springCtx);
            }
        } catch (IOException e) {
            throw new GalleonException(Constant.ERROR_COMMON_INIT_TENANT);
        }
        return springCtx;
    }
    
    private static Resource[] convertToResources(String[] files, String tenant) throws IOException {
        List<Resource> result = new ArrayList<Resource>();
        for (String filePath : files) {
            String content = getResourceString(filePath);
            if (filePath.equals("galleon-data-spec.xml")) {
                content = String.format(content, tenant);
            }
            result.add(new ByteArrayResource(content.getBytes()));
        }
        return result.toArray(new Resource[0]);
    }

    private static String getResourceString(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(SpringUtil.class.getResourceAsStream("/" + filePath)));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    public static Object getBean(String id) {
        return getSpringContext().getBean(id);
    }
    
}
