package com.yl.yapiclientsdk;

import com.yl.yapiclientsdk.client.YApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Date: 2023/10/14 - 10 - 14 - 14:32
 * @Description: com.yl.yapiclientsdk
 */
@Configuration
@ConfigurationProperties("yapi.client")
@Data
@ComponentScan
public class YApiClientConfig {

    private String accessKey;

    private String secretKey;

    private String host;

    @Bean
    public YApiClient yApiClient() {
        return new YApiClient(accessKey, secretKey, host);
    }


}
