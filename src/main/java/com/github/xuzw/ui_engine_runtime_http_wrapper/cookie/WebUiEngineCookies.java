package com.github.xuzw.ui_engine_runtime_http_wrapper.cookie;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import com.github.xuzw.ui_engine_runtime.div.location.Location;
import com.github.xuzw.ui_engine_runtime.event.Event;
import com.github.xuzw.ui_engine_runtime.event.RefreshEvent;
import com.github.xuzw.ui_engine_runtime.input.Inputs;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 下午9:34:46
 */
public class WebUiEngineCookies {
    public static final String key_event_type = "uiEngine.event.type";
    public static final String key_event_location_className = "uiEngine.event.location.className";
    public static final String key_event_location_id = "uiEngine.event.location.id";
    public static final String key_prefix_input = "uiEngine.input.";
    private Cookie[] rawCookies;
    private List<Cookie> inputs = new ArrayList<>();
    private Cookie eventType;

    public WebUiEngineCookies(Cookie[] cookies) {
        this.rawCookies = cookies;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key_event_type)) {
                    eventType = cookie;
                }
                if (cookie.getName().startsWith(key_prefix_input)) {
                    inputs.add(cookie);
                }
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
        Inputs inputs = getInputs();
        if (eventType == null) {
            Event event = new RefreshEvent();
            event.setInputs(inputs);
            return event;
        }
        try {
            Event event = (Event) Class.forName(eventType.getValue()).newInstance();
            event.setInputs(inputs);
            event.setLocation(getEventLocation());
            return event;
        } catch (Exception e) {
            Event event = new RefreshEvent();
            event.setInputs(inputs);
            return event;
        }
    }

    public Location getEventLocation() {
        return new Location(getCookie(key_event_location_className).getValue(), getCookie(key_event_location_id).getValue());
    }

    public Inputs getInputs() {
        Inputs inputs = new Inputs();
        for (Cookie cookie : this.inputs) {
            inputs.put(cookie.getName().substring(key_prefix_input.length()), cookie.getValue());
        }
        return inputs;
    }
}
