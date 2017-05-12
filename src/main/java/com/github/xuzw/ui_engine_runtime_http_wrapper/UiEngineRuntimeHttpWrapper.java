package com.github.xuzw.ui_engine_runtime_http_wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.xuzw.ui_engine_runtime.UiEngine;
import com.github.xuzw.ui_engine_runtime.event.RefreshEvent;
import com.github.xuzw.ui_engine_runtime.page.Page;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月12日 下午2:35:14
 */
public class UiEngineRuntimeHttpWrapper implements Filter {
    public static final String init_param_name_ui_engine_class_name = "uiEngineClassName";
    private static final Logger logger = LoggerFactory.getLogger(UiEngineRuntimeHttpWrapper.class);
    private static Class<? extends UiEngine> uiEngineClass;

    @SuppressWarnings("unchecked")
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init");
        try {
            uiEngineClass = (Class<? extends UiEngine>) Class.forName(filterConfig.getInitParameter(init_param_name_ui_engine_class_name));
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String pageName = getPageName(httpRequest);
        PrintWriter writer = httpResponse.getWriter();
        UiEngine uiEngine = getUiEngine(httpRequest);
        Page response = uiEngine.execute(new RefreshEvent(uiEngine.getPage(pageName)));
        writer.println(response.toHtml());
        IOUtils.closeQuietly(writer);
    }

    private String getPageName(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    private UiEngine getUiEngine(HttpServletRequest httpRequest) {
        String key = uiEngineClass.getName();
        HttpSession session = httpRequest.getSession();
        Object uiEngine = session.getAttribute(key);
        if (uiEngine == null) {
            try {
                UiEngine newInstance = uiEngineClass.newInstance();
                session.setAttribute(key, newInstance);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("", e);
                return null;
            }
        }
        return (UiEngine) uiEngine;
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }

}
