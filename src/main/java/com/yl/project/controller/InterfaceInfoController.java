package com.yl.project.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.google.gson.Gson;
import com.yl.project.annotation.AuthCheck;
import com.yl.project.common.*;
import com.yl.project.constant.CommonConstant;
import com.yl.project.constant.UserConstant;
import com.yl.project.esdao.exception.BusinessException;
import com.yl.project.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.yl.project.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.yl.project.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yl.project.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.yl.project.model.enums.InterfaceInfoStatusEnum;
import com.yl.project.service.InterfaceInfoService;
import com.yl.project.service.UserService;
import com.yl.yapiclientsdk.client.YApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import yapicommon.model.entity.InterfaceInfo;
import yapicommon.model.entity.User;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口信息 接口
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;


    @Resource
    private YApiClient yApiClient;

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, InterfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(InterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        InterfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(InterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = InterfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param InterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest InterfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (InterfaceInfoUpdateRequest == null || InterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoUpdateRequest, InterfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = InterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }


    /**
     * 上线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(idRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //判断接口是否可以成功调用
        //todo 修改客户端调用为通用
        com.yl.yapiclientsdk.model.User user = new
                com.yl.yapiclientsdk.model.User();
        user.setUsername("yl");
        String username = yApiClient.getUsernameByPost(user);

        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口无法调用");
        }

        //将状态设置为开启
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }


    /**
     * 下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(idRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //将状态设置为关闭
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

        /**
         * 根据 id 获取
         * @param id
         * @return
         */
        @GetMapping("/get")
        public BaseResponse<InterfaceInfo> getInterfaceInfoById(Long id){
            if (id <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }

            InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
            return ResultUtils.success(interfaceInfo);
        }

        /**
         * 获取列表（仅管理员可使用）
         *
         * @param interfaceInfoQueryRequest
         * @return
         */
        @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
        @GetMapping("/list")
        public BaseResponse<List<InterfaceInfo>> listInterfaceInfo (InterfaceInfoQueryRequest interfaceInfoQueryRequest)
        {
            InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
            if (interfaceInfoQueryRequest != null) {
                BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
            }
            QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
            List<InterfaceInfo> InterfaceInfoList = interfaceInfoService.list(queryWrapper);
            return ResultUtils.success(InterfaceInfoList);
        }

        /**
         * 分页获取列表
         *
         * @param interfaceInfoQueryRequest
         * @param request
         * @return
         */
        @GetMapping("/list/page")
        public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage (InterfaceInfoQueryRequest
        interfaceInfoQueryRequest, HttpServletRequest request){
            if (interfaceInfoQueryRequest == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            InterfaceInfo InterfaceInfoQuery = new InterfaceInfo();
            BeanUtils.copyProperties(interfaceInfoQueryRequest, InterfaceInfoQuery);
            long current = interfaceInfoQueryRequest.getCurrent();
            long size = interfaceInfoQueryRequest.getPageSize();
            String sortField = interfaceInfoQueryRequest.getSortField();
            String sortOrder = interfaceInfoQueryRequest.getSortOrder();
            String description = InterfaceInfoQuery.getDescription();
            InterfaceInfoQuery.setDescription(null);

            // 限制爬虫
            if (size > 50) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(InterfaceInfoQuery);
            queryWrapper.like(StrUtil.isNotBlank(description), "description", description);
            queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                    sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
            Page<InterfaceInfo> InterfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
            return ResultUtils.success(InterfaceInfoPage);
        }


    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        // 判断是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (!interfaceInfo.getStatus().equals(InterfaceInfoStatusEnum.ONLINE.getValue())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口状态不为开启");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        YApiClient yApiClient1 = new YApiClient(accessKey, secretKey);


        Gson gson = new Gson();

        com.yl.yapiclientsdk.model.User user = gson.fromJson(userRequestParams,
                com.yl.yapiclientsdk.model.User.class);
        // todo 更改为根据接口的url地址进行调用
        String result = yApiClient1.getUsernameByPost(user);
        //String result = yApiClient1.getNameByGet("hhhh");
        return ResultUtils.success(result);
    }

}