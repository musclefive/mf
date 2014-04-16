package com.mf.util.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by shixin on 4/2/14.
 */
public class HttpClientUtil {
    private static HttpClient getHttpClient() {
        HttpClient client = new HttpClient();
        client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        client.getParams().setParameter("http.protocol.single-cookie-header", true);
        return client;
    }

    private static String buildQueryUrl(String url, NameValuePair[] params) throws UnsupportedEncodingException {
        for (int i = 0; i < params.length; i++) {
            if (url.indexOf("?") != -1) {
                url += "&" + params[i].getName() + "=" + URLEncoder.encode(StringUtils.defaultString(params[i].getValue()), "UTF-8");
            } else {
                url += "?" + params[i].getName() + "=" + URLEncoder.encode(StringUtils.defaultString(params[i].getValue()), "UTF-8");
            }
        }
        return url;
    }

    private static void setHeaders(HttpMethod method) {
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        method.setRequestHeader("Accept-Language", "zh-cn");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        method.setRequestHeader("Accept-Charset", "UTF-8");
        method.setRequestHeader("Keep-Alive", "300");
        method.setRequestHeader("Connection", "Keep-Alive");
        method.setRequestHeader("Cache-Control", "no-cache");
    }

    private static String readInputStream(InputStream is) throws IOException {
        byte[] b = new byte[4096];
        StringBuilder builder = new StringBuilder();
        int bytesRead = 0;
        while (true) {
            bytesRead = is.read(b, 0, 4096);
            if (bytesRead == -1) {
                return builder.toString();
            }
            builder.append(new String(b, 0, bytesRead, "UTF-8"));
        }
    }

    private static InputStream doGetWithHeaders(String url, NameValuePair[] params, NameValuePair[] headers) throws IOException {
        HttpClient client = getHttpClient();
        if (params != null) {
            url = buildQueryUrl(url, params);
        }
        GetMethod method = new GetMethod(url);

        setHeaders(method);
        if (headers != null) {
            for (NameValuePair hdr : headers) {
                method.setRequestHeader(hdr.getName(), hdr.getValue());
            }
        }
        method.setRequestHeader("Connection", "close");
        client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        client.getParams().setSoTimeout(120000);
        client.executeMethod(method);
        return method.getResponseBodyAsStream();
    }

    public static String doGet(String url, NameValuePair[] params) throws IOException {
        String responseStr = readInputStream(doGetWithHeaders(url, params, null));
        return responseStr;
    }
}
