package zeee.blog.common.resttemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author wz
 * @date 2022/12/13
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate getTemplate() {
        HttpClient client = HttpClientBuilder.create()
                // 禁用重试
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                // TODO
                // 设置连接池中的连接存活时间，默认-1代表无限存活，连接使用之后由response header "Keep-Alive:timeout"决定
                .setConnectionTimeToLive(5, TimeUnit.SECONDS)
                .setDefaultRequestConfig(RequestConfig.custom()
                        // 设置socket进行3次握手建立连接的超时时间
                        .setConnectTimeout(5000)
                        // 设置连接池申请连接的超时时间，默认-1为无限时间
                        .setConnectionRequestTimeout(5000)
                        // 设置连接超时时间，即socket读写超时时间)
                        .setSocketTimeout(60000).build())
                // 设置global连接池中的最大连接数，默认20
                .setMaxConnTotal(1024)
                .setMaxConnPerRoute(64)
                .build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }
}
