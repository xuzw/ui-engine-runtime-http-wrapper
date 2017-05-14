package com.github.xuzw.ui_engine_runtime_http_wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.xuzw.ui_engine_runtime.UiEngine;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 上午11:13:16
 */
public abstract class AbstractSessionWebUiEngineProvider implements WebUiEngineProvider {
    public static final String key = UiEngine.class.getName();

    @Override
    public UiEngine get(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object engine = session.getAttribute(key);
        if (engine == null) {
            UiEngine newInstance = newInstance(session);
            session.setAttribute(key, newInstance);
            return newInstance;
        }
        return (UiEngine) engine;
    }

    public abstract UiEngine newInstance(HttpSession session);
}
