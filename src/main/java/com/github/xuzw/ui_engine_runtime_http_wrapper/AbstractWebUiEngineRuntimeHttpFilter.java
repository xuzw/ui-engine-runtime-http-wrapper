package com.github.xuzw.ui_engine_runtime_http_wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.github.xuzw.ui_engine_runtime.UiEngine;
import com.github.xuzw.ui_engine_runtime.event.RefreshEvent;
import com.github.xuzw.ui_engine_runtime.page.Page;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月12日 下午2:35:14
 */
public abstract class AbstractWebUiEngineRuntimeHttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setContentType("text/html;charset=UTF-8");
        String sourcePageName = getSourcePageName(httpRequest);
        PrintWriter writer = httpResponse.getWriter();
        UiEngine engine = getWebUiEngineProvider().get(httpRequest);
        Page response = engine.execute(new RefreshEvent(engine.getPage(sourcePageName)));
        writer.println(response.toHtml());
        IOUtils.closeQuietly(writer);
    }

    protected abstract String getSourcePageName(HttpServletRequest httpRequest);

    protected abstract WebUiEngineProvider getWebUiEngineProvider();
}
