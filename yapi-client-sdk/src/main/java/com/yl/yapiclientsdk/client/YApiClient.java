package com.yl.yapiclientsdk.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.yl.yapiclientsdk.util.SignUtils.genSign;


/**
 * @Date: 2023/10/14 - 10 - 14 - 14:13
 * @Description: com.yl.yapiinterface.client
 */
@Slf4j
public class YApiClient {


    private String accessKey;

    private String secretKey;

    private String host;

    public YApiClient(String accessKey, String secretKey, String host) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.host = host;
    }

    private Map<String, String> getHeaderMap(String body, String method) throws UnsupportedEncodingException {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        body = URLUtil.encode(body, StandardCharsets.UTF_8);
        map.put("sign", genSign(body, secretKey));
        map.put("body", body);
        map.put("method", method);
        return map;
    }

    public String invokeInterface(String params, String url, String method) throws UnsupportedEncodingException {
        try {
            HttpResponse httpResponse = HttpRequest.post(host + url)
                    .header("Accept-Charset", StandardCharsets.UTF_8.name())
                    .addHeaders(getHeaderMap(params, method))
                    .body(params)
                    .timeout(5000) // 设置超时时间，单位为毫秒
                    .execute();
            return JSONUtil.formatJsonStr(httpResponse.body());
        } catch (Exception e) {
            // 处理异常
            log.error(e.getMessage());
            return e.getMessage(); // 返回错误信息
        }
    }
}
