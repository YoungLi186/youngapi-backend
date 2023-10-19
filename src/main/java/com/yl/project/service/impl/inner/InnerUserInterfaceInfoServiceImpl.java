package com.yl.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yl.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import yapicommon.model.entity.UserInterfaceInfo;
import yapicommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;

/**
 * 内部用户接口信息服务实现类
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId) {
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("interfaceInfoId", interfaceInfoId);
        userInterfaceInfoQueryWrapper.eq("userId", userId);
        return userInterfaceInfoService.getOne(userInterfaceInfoQueryWrapper);
    }
}
