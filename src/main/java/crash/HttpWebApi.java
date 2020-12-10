package crash;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpWebApi {
    public static void getHttpWebApi(Object listCrash) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token=2437485edf0a6fffab9d716123b9fa9d20d5608f9803eb3556c51d9b642ede5a");
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        String text = "{\n" +
                "     \"msgtype\": \"text\",\n" +
                "     \"text\": {\n" +
                "         \"content\": \"crash  " + listCrash + "\"\n" +
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

//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
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
