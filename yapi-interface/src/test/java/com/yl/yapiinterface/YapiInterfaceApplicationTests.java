package com.yl.yapiinterface;

import com.yl.yapiclientsdk.client.YApiClient;
import com.yl.yapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YapiInterfaceApplicationTests {


    @Resource
    private YApiClient yApiClient;


    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("hhh");
        String usernameByPost = yApiClient.getUsernameByPost(user);
        System.out.println(usernameByPost);

    }

}