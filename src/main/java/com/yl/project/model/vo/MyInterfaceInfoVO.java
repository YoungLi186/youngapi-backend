package com.yl.project.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yapicommon.model.entity.InterfaceInfo;

import java.util.Date;

/**
 * 个人接口信息封装视图
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyInterfaceInfoVO extends InterfaceInfo {


    /**
     * 接口id
     */
    private Long interfaceInfoId;


    /**
     * 名称
     */
    private String name;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 接口调用次数
     */
    private Integer totalNum;
    private static final long serialVersionUID = 1L;
}