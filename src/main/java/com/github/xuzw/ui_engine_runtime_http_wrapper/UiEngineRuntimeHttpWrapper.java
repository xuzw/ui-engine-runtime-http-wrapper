package com.github.xuzw.ui_engine_runtime_http_wrapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月12日 下午2:35:14
 */
public class UiEngineRuntimeHttpWrapper implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UiEngineRuntimeHttpWrapper.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }

}
