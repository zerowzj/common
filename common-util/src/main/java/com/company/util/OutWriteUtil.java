package com.company.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * OutWrite Util
 *
 * @author wangzhj
 */
public class OutWriteUtil {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static final String AJAX_HEADER = "X-Requested-With";

    /**
     * 写到客户端
     *
     * @param response
     * @param obj
     */
    public static <T> void writeByJson(HttpServletResponse response, Object obj) {
        write(response, JsonUtil.toJson(obj));
    }

    /**
     * 写到客户端
     *
     * @param response
     * @param msg
     */
    public static void write(HttpServletResponse response, String msg) {
        write(response, msg, CHARSET_UTF_8);
    }

    /**
     * 写到客户端
     *
     * @param response
     * @param msg
     * @param charset
     */
    public static void write(HttpServletResponse response, String msg, String charset) {
        PrintWriter out = null;
        try {
            response.setContentType("text/plain;charset=" + charset);
            out = response.getWriter();
            out.write(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
