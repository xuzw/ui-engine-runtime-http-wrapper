package com.github.xuzw.ui_engine_runtime_http_wrapper;

import javax.servlet.http.HttpServletRequest;

import com.github.xuzw.ui_engine_runtime.UiEngine;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 下午12:00:05
 */
public interface WebUiEngineProvider {
    UiEngine get(HttpServletRequest request);
}
