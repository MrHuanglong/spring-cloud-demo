package com.springcloud.edu.dataservice.config.common.exception;

import com.springcloud.edu.dataservice.config.common.util.ExceptionUtil;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 10:04
 **/
public enum CommonError {
    /**
     * 1001, "用户信息为空"
     */
    AUTH_EMPTY_ERROR(10001, "the user is null, please check");

    private Integer code;
    private String message;

    CommonError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeEn() {
        return ExceptionUtil.errorToCodeEN(this);
    }
}
