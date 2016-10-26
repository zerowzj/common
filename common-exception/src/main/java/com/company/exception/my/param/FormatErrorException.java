package com.company.exception.my.param;

import com.company.exception.base.ParamException;

/**
 * 参数格式错误异常
 *
 * @author wangzhj
 */
public class FormatErrorException extends ParamException {

    public FormatErrorException(String paramName, Object paramValue) {
        super("1002", "参数格式错误", paramName, paramValue);
    }
}
