package utils;


import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author: gq
 * @createtime: 2020/9/23 17:17
 * @description: 将response信息发送至钉钉
 */
public class HttpclientToDingWebhook {
    public static void sendResponseToDingWebhook(String webhook, String text) {
        //声明client
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(webhook);

        //设置请求头
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        //将参数添加到post中
        StringEntity stringEntity = new StringEntity(text, "utf-8");
        httpPost.setEntity(stringEntity);

        // 获取response
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getHttpWebApi(Object listMemoinfo) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token=b324c84816371842cfe590d243898eca1eee7a78d18212c2175db0c6fd914052");
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        String text = "{\n" +
                "     \"msgtype\": \"text\",\n" +
                "     \"text\": {\n" +
                "         \"content\": \"pair：system：free" + listMemoinfo + "\"\n" +
                "     },\n" +
                "     \"at\": {\n" +
                "         \"atMobiles\": [\n" +
                "             \"  \"\n" +
                "         ], \n" +
                "         \"isAtAll\": false\n" +
                "     }\n" +
                " }";
        StringEntity stringEntity = new StringEntity(text, "utf-8");
        httpPost.setEntity(stringEntity);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


