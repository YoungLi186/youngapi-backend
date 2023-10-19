package yapicommon.service;

import yapicommon.model.entity.UserInterfaceInfo;

/**
 * 内部用户接口信息服务
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId);
}
