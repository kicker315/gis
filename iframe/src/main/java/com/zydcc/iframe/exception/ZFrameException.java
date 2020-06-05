package com.zydcc.iframe.exception;

import com.zydcc.iframe.utils.ExceptionUtil;
import com.zydcc.iframe.utils.StringUtil;

/**
 * =======================================
 * 工具类异常
 * Create by ningsikai 2020/6/4:4:47 PM
 * ========================================
 */
public class ZFrameException extends RuntimeException {

    public ZFrameException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public ZFrameException(String message) {
        super(message);
    }

    public ZFrameException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public ZFrameException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ZFrameException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }
}