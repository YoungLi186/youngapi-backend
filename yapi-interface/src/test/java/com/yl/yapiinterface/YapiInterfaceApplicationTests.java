package com.yl.yapiinterface;

import com.yl.yapiclientsdk.client.YApiClient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YapiInterfaceApplicationTests {


    @Resource
    private YApiClient yApiClient;



}
