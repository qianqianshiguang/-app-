package utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author: gq
 * @createtime: 2020/12/11 22:55
 * @description: TODO
 */
public class ClientUtil {
    static CloseableHttpClient client = HttpClientBuilder.create().build();
    public static CloseableHttpClient getDefaultClient(){
        return client;
    }
}
