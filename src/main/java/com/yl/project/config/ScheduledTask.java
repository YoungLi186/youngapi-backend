package com.yl.project.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yl.project.mapper.InterfaceInfoMapper;
import com.yl.project.model.enums.InterfaceInfoStatusEnum;
import com.yl.project.service.InterfaceInfoService;
import com.yl.yapiclientsdk.client.YApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yapicommon.model.entity.InterfaceInfo;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
public class ScheduledTask {


    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private YApiClient yApiClient;

    private final static String HOST = "http://localhost:9090";

    @Scheduled(cron = "0 0/30 * * * ? ")
    //@Scheduled(cron = "*/20 * * * * ?")
    public void ScheduledTaskOne() throws InterruptedException, UnsupportedEncodingException {
        log.info("-------接口检查开始...");
        //1.从数据库中查出状态为开启的接口
        LambdaQueryWrapper<InterfaceInfo> qw = new LambdaQueryWrapper<>();
        qw.eq(InterfaceInfo::getStatus, 1);
        qw.eq(InterfaceInfo::getIsDelete, 0);
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(qw);
        int count = 0;
        //2.遍历调用接口,不经过网关
        for (InterfaceInfo interfaceInfo : interfaceInfos) {
            String url = interfaceInfo.getUrl();
            String requestParams = interfaceInfo.getRequestParams();
            String result = yApiClient.invokeInterface(requestParams, url, HOST);
            if (StrUtil.isBlank(result) || result.contains("<h1>Whitelabel Error Page</h1>")) {
                interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
                interfaceInfoService.updateById(interfaceInfo);
                log.info("接口:{}---已下线", interfaceInfo.getId());
                count++;
            }
        }
        log.info("------- 接口检查结束,本次共有{}个接口下线", count);
    }
}