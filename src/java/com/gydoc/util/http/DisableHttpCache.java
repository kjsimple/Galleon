package com.gydoc.util.http;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DisableHttpCache implements Filter {
    
    public void init(FilterConfig config) {
        
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
            
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader("Pragma", "no-cache");
        httpResponse.addHeader("Cache-Control", "no-cache");
        httpResponse.addHeader("Cache-Control", "no-store");
        httpResponse.addHeader("Cache-Control", "must-revalidate");
        httpResponse.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
        chain.doFilter(request, response);
    }
    
    public void destroy() {
        
    }
    
}
