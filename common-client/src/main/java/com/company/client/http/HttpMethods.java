package com.company.client.http;

import com.company.util.CloseUtil;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Http请求
 *
 * @author wangzhj
 */
public abstract class HttpMethods {

    private static final Logger logger = LoggerFactory.getLogger(HttpMethods.class);

    //==================== 请求信息 ====================
    //URL
    protected String url = null;
    //请求参数
    protected Map<String, String> paramMap = null;
    //文件参数
    protected Map<String, byte[]> fileMap = null;
    //实体编码
    protected Charset charset = Charsets.UTF_8;
    //请求头
    protected Map<String, String> headerMap = null;

    //==================== 配置信息 ====================
    //连接超时时间
    protected int connectTimeout = 20 * 1000;
    //读取超时时间
    protected int readTimeout = 20 * 1000;

    //==================== 响应信息 ====================
    //状态码
    private int statusCode;
    //响应头
    private Header[] headers = null;
    //响应数据
    private byte[] data = null;

    protected HttpMethods(String url, Map<String, String> paramMap) {
        this.url = url;
        this.paramMap = paramMap;
    }

    protected HttpMethods(String url, Map<String, String> paramMap, Map<String, byte[]> fileMap) {
        this.url = url;
        this.paramMap = paramMap;
        this.fileMap = fileMap;
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout
     */
    public HttpMethods connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param readTimeout
     */
    public HttpMethods readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 读取请求字符集
     *
     * @param charset
     */
    public HttpMethods charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 设置请求头
     *
     * @param headerMap
     */
    public HttpMethods headers(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    /**
     * 释放连接
     *
     * @param httpRequest
     */
    protected void releaseConnection(HttpRequestBase httpRequest) {
        if (httpRequest != null) {
            httpRequest.releaseConnection();
        }
    }

    /**
     * 解析响应
     *
     * @param response
     */
    protected final void parseHttpResponse(HttpResponse response) {
        InputStream is = null;
        try {
            if (response != null) {
                StatusLine statusLine = response.getStatusLine();
                statusCode = statusLine.getStatusCode();
                logger.info("status code===> {}", statusCode);
                if (statusCode == HttpStatus.SC_OK) {
                    is = response.getEntity().getContent();
                    data = ByteStreams.toByteArray(is);
                }
                //响应头
                headers = response.getAllHeaders();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseUtil.closeQuietly(is);
        }
    }

    /**
     * 构造Http方法
     *
     * @return HttpRequestBase
     */
    public abstract HttpRequestBase buildHttpMethod();

    /**
     * 同步提交
     *
     * @return HttpMethods
     */
    public abstract HttpMethods submit();

    /**
     * 异步提交
     *
     * @return HttpMethods
     */
    public abstract HttpMethods asyncSubmit();

    /**
     * 获取结果
     *
     * @return ResultBody
     */
    public final ResultBody result() {
        ResultBody resultBody = new ResultBody(statusCode, headers, data);
        return resultBody;
    }
}
