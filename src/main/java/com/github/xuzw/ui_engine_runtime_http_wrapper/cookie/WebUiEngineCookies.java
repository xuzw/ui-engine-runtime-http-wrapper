package com.github.xuzw.ui_engine_runtime_http_wrapper.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.xuzw.ui_engine_runtime.input.Inputs;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年5月14日 下午9:34:46
 */
public class WebUiEngineCookies {
    private static final Logger log = LoggerFactory.getLogger(WebUiEngineCookies.class);
    public static final String key_prefix_input = "uiEngine.input.";
    private Cookie[] rawCookies;
    private Inputs inputs = new Inputs();

    public WebUiEngineCookies(Cookie[] cookies) {
        this.rawCookies = cookies;
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().startsWith(key_prefix_input)) {
                        String decodeValue = URLDecoder.decode(URLDecoder.decode(cookie.getValue(), "utf-8"), "utf-8");
                        inputs.put(cookie.getName().substring(key_prefix_input.length()), decodeValue);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
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

    public Inputs getInputs() {
        return inputs;
    }
}
