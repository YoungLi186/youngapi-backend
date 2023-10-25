package com.yl.project.model.vo;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import yapicommon.model.entity.InterfaceInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 接口信息封装视图
 */
@Data
public class InterfaceInfoVO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求参数说明
     */
    private List<RequestParamsRemarkVO> requestParamsRemark;

    /**
     * 响应参数说明
     */
    private List<ResponseParamsRemarkVO> responseParamsRemark;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 接口调用次数
     */
    private Integer totalNum;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param interfaceInfoVO
     * @return
     */
    public static InterfaceInfo voToObj(InterfaceInfoVO interfaceInfoVO) {
        if (interfaceInfoVO == null) {
            return null;
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoVO, interfaceInfo);
        List<RequestParamsRemarkVO> requestParamsRemark1 = interfaceInfoVO.getRequestParamsRemark();
        List<ResponseParamsRemarkVO> responseParamsRemark1 = interfaceInfoVO.getResponseParamsRemark();
        if (requestParamsRemark1 != null) {
            interfaceInfo.setRequestParamsRemark(JSONUtil.toJsonStr(requestParamsRemark1));
        }
        if (responseParamsRemark1 != null) {
            interfaceInfo.setResponseParamsRemark(JSONUtil.toJsonStr(responseParamsRemark1));
        }

        return interfaceInfo;
    }

    /**
     * 对象转包装类
     *
     * @param interfaceInfo
     * @return
     */
    public static InterfaceInfoVO objToVo(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            return null;
        }
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
        String requestParamsRemark1 = interfaceInfo.getRequestParamsRemark();
        String responseParamsRemark1 = interfaceInfo.getResponseParamsRemark();
        if (StrUtil.isNotBlank(requestParamsRemark1)) {
            interfaceInfoVO.setRequestParamsRemark(JSONUtil.toList(requestParamsRemark1, RequestParamsRemarkVO.class));
        }

        if (StrUtil.isNotBlank(responseParamsRemark1)) {
            interfaceInfoVO.setResponseParamsRemark(JSONUtil.toList(responseParamsRemark1, ResponseParamsRemarkVO.class));
        }

        return interfaceInfoVO;
    }


}