package com.company.exception.auth;

import com.company.exception.AuthException;

/**
 * token错误或过期异常
 *
 * @author wangzhj
 */
public class TokenErrorException extends AuthException {

    public TokenErrorException() {
        super("8002", "token错误或过期");
    }
}
