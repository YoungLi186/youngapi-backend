package com.yl.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @author 18683
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
 * @createDate 2023-10-16 15:07:26
 * @Entity com.yl.project.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




