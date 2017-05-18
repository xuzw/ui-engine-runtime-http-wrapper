package com.github.xuzw.ui_engine_runtime_http_wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.github.xuzw.ui_engine_runtime.UiEngine;
import com.github.xuzw.ui_engine_runtime.input.Inputs;
import com.github.xuzw.ui_engine_runtime.page.AbstractPage;
import com.github.xuzw.ui_engine_runtime_http_wrapper.cookie.WebUiEngineCookies;
import com.github.xuzw.ui_engine_runtime_http_wrapper.provider.WebUiEngineProvider;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月12日 下午2:35:14
 */
public abstract class AbstractWebUiEngineRuntimeHttpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        httpResponse.setContentType("text/html;charset=UTF-8");
        String sourcePageName = getSourcePageName(httpRequest);
        UiEngine engine = getWebUiEngineProvider().get(httpRequest);
        Inputs inputs = getInputs(httpRequest);
        AbstractPage response = engine.getPage(sourcePageName).filter(inputs);
        PrintWriter writer = httpResponse.getWriter();
        writer.println(response.toHtml());
        IOUtils.closeQuietly(writer);
    }

    protected String getSourcePageName(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    protected Inputs getInputs(HttpServletRequest httpRequest) {
        return new WebUiEngineCookies(httpRequest.getCookies()).getInputs();
    }

    protected abstract WebUiEngineProvider getWebUiEngineProvider();
}
