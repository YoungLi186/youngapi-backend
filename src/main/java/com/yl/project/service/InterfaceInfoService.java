package com.yl.project.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.project.model.vo.InterfaceInfoVO;
import yapicommon.model.entity.InterfaceInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 18683
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-10-12 16:25:48
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {


    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add           是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);


    /**
     * 获取VO类
     *
     * @param interfaceInfo
     * @param request
     * @return
     */
    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);


    /**
     * 获取VO分页Page
     *
     * @param interfaceInfoPage
     * @param request
     * @return
     */
    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);


}
