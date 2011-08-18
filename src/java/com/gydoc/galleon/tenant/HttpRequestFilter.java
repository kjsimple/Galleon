package com.gydoc.galleon.tenant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 */
public class HttpRequestFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        TenantManager.getInstance().setTenant(request.getSession().getAttribute("tenant"));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }

}
