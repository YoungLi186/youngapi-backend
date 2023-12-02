package com.yl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.yl.project.MainApplication;
import com.yl.project.mapper.InterfaceInfoMapper;
import com.yl.project.mapper.UserInterfaceInfoMapper;
import com.yl.project.model.vo.MyInterfaceInfoVO;
import com.yl.project.service.UserInterfaceInfoService;
import com.yl.yapiclientsdk.client.YApiClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import yapicommon.model.entity.UserInterfaceInfo;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Date: 2023/10/16 - 10 - 16 - 16:11
 * @Description: com.yl
 */

@SpringBootTest(classes = MainApplication.class)
@Slf4j
public class UserInterfaceInfoServiceImplTest {


    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    @Resource
    private YApiClient yApiClient;


    @Test
    void test() {

        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(10);
        System.out.println(userInterfaceInfos);
    }


    @Test
    void test1() {
        List<MyInterfaceInfoVO> myInterfaceInfoVOS = userInterfaceInfoMapper.listMyInterfaceInfo(1716000310216142849L);
        System.out.println(myInterfaceInfoVOS);
    }


    @Test
    void test2() {
        List<MyInterfaceInfoVO> myInterfaceInfoVO = userInterfaceInfoService.getMyInterfaceInfoVO(1715268183728062466L);
        System.out.println(myInterfaceInfoVO);
    }


    @Test
    void test3() {

    }


}
