package com.yl.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yapicommon.model.entity.UserInterfaceInfo;

/**
 * @author 18683
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-10-16 15:07:27
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    boolean invokeCount(long interfaceInfoId, long userId);

    boolean addInterfaceInfoTimes(long interfaceInfoId, long userId);
}
