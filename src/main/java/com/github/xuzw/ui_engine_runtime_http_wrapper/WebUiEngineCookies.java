package com.github.xuzw.ui_engine_runtime_http_wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import com.github.xuzw.ui_engine_runtime.event.Event;
import com.github.xuzw.ui_engine_runtime.event.RefreshEvent;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 下午9:34:46
 */
public class WebUiEngineCookies {
    public static final String key_event_type = "uiEngine.eventType";
    public static final String key_prefix_input = "uiEngine.input.";
    private Cookie[] rawCookies;
    private List<Cookie> inputs = new ArrayList<>();
    private Cookie eventType;

    public WebUiEngineCookies(Cookie[] cookies) {
        this.rawCookies = cookies;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key_event_type)) {
                eventType = cookie;
            }
            if (cookie.getName().startsWith(key_prefix_input)) {
                inputs.add(cookie);
            }
        }
    }

    public Cookie getCookie(String name) {
        for (Cookie cookie : rawCookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public Event getEvent() {
        if (eventType == null) {
            return new RefreshEvent();
        }
        try {
            return (Event) Class.forName(eventType.getValue()).newInstance();
        } catch (Exception e) {
            return new RefreshEvent();
        }
    }

    public List<Cookie> getInputs() {
        return inputs;
    }
}
