package com.company.validation.xml.validator;

import com.company.exception.param.FormatErrorException;
import com.company.validation.MessageUtil;
import com.company.validation.xml.Param;
import com.company.validation.xml.rule.StringRule;

import java.text.MessageFormat;

/**
 * 字符串规则验证器
 *
 * @author wangzhj
 */
public class StringValidator implements Validator {

    @Override
    public boolean support(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Param param, Object value) {
        String paramName = param.getParamName();
        int length = value.toString().length();
        StringRule rule = (StringRule) param.getRule();

        //最小长度
        int minLength = rule.getMinLength();
        if (minLength > 0 && length < minLength) {
            String key = "param.error.minLength";
            throw new FormatErrorException(paramName, value, MessageUtil.format(key, paramName, minLength));
        }
        //最大长度
        int maxLength = rule.getMaxLength();
        if (maxLength > 0 && length > maxLength) {
            String key = "param.error.maxLength";
            throw new FormatErrorException(paramName, value, MessageUtil.format(key, paramName, maxLength));
        }
    }
}
