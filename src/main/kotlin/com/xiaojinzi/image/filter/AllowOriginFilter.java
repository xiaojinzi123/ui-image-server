package com.xiaojinzi.image.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllowOriginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "*");
        }
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
    }
}
