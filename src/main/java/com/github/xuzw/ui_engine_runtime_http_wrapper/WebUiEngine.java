package com.github.xuzw.ui_engine_runtime_http_wrapper;

import com.github.xuzw.ui_engine_runtime.UiEngine;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 下午1:39:49
 */
public interface WebUiEngine extends UiEngine {
    String getPageUri(String pageName);
}
