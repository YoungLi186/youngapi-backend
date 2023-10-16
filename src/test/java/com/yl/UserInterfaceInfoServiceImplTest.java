package com.yl;

import com.yl.project.MainApplication;
import com.yl.project.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Date: 2023/10/16 - 10 - 16 - 16:11
 * @Description: com.yl
 */

@SpringBootTest(classes = MainApplication.class)
public class UserInterfaceInfoServiceImplTest {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void test() {
        userInterfaceInfoService.invokeCount(1, 1);
    }
}
