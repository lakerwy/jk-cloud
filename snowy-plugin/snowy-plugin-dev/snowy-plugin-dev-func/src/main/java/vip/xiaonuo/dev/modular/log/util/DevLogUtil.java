/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dev.modular.log.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.util.*;
import vip.xiaonuo.dev.modular.log.entity.DevLog;
import vip.xiaonuo.dev.modular.log.enums.DevLogCategoryEnum;
import vip.xiaonuo.dev.modular.log.enums.DevLogExeStatusEnum;
import vip.xiaonuo.dev.modular.log.service.DevLogService;
import vip.xiaonuo.ten.api.TenApi;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志工具类
 *
 * @author xuyuxiang
 * @date 2022/9/2 15:26
 */
@Slf4j
public class DevLogUtil {

    private static final DevLogService devLogService = SpringUtil.getBean(DevLogService.class);

    private static final TenApi tenApi = SpringUtil.getBean(TenApi.class);
    /**
     * 记录操作日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:31
     */
    public static void executeOperationLog(CommonLog commonLog, String userName, JoinPoint joinPoint, String resultJson,long elapsedTime) {
        HttpServletRequest request = CommonServletUtil.getRequest();
        DevLog devLog = genBasOpLog();
        devLog.setTenantId(tenApi.getCurrentTenId());
        devLog.setCategory(DevLogCategoryEnum.OPERATE.getValue());
        devLog.setName(commonLog.value());
        devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
        devLog.setClassName(joinPoint.getTarget().getClass().getName());
        devLog.setMethodName(joinPoint.getSignature().getName());
        devLog.setReqUrl(request.getRequestURI());
        devLog.setParamJson(CommonJoinPointUtil.getArgsJsonString(joinPoint));
        devLog.setResultJson(resultJson);
        devLog.setOpTime(DateTime.now());
        devLog.setOpUser(userName);
        creatLogSignValue(devLog);
        devLogService.save(devLog);
        // MDC 扩展logback字段，具体请看logback-spring.xml的自定义日志输出格式
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String description = methodSignature.getMethod().getAnnotation(ApiOperation.class).value();
        String date = DateTime.now().toString("yyyy-MM-dd");
        try {
            MDC.put("elapsedTime", StrUtil.toString(elapsedTime));
            MDC.put("description", description);
            MDC.put("region", devLog.getOpAddress());
            MDC.put("username", devLog.getOpUser());
            MDC.put("date", date);
            MDC.put("paramJson", devLog.getParamJson());
            MDC.put("resultJson", resultJson);
            MDC.put("clientIP", devLog.getOpIp());
            MDC.put("reqUrl", devLog.getReqUrl());
            log.info("{}操作日志---", description);
        }finally {
            MDC.clear();
        }
    }

    /**
     * 记录异常日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:31
     */
    public static void executeExceptionLog(CommonLog commonLog, String userName, JoinPoint joinPoint, Exception exception,long elapsedTime) {
        HttpServletRequest request = CommonServletUtil.getRequest();
        DevLog devLog = genBasOpLog();
        devLog.setTenantId(tenApi.getCurrentTenId());
        devLog.setCategory(DevLogCategoryEnum.EXCEPTION.getValue());
        devLog.setName(commonLog.value());
        devLog.setExeStatus(DevLogExeStatusEnum.FAIL.getValue());
        devLog.setExeMessage(ExceptionUtil.stacktraceToString(exception, Integer.MAX_VALUE));
        devLog.setClassName(joinPoint.getTarget().getClass().getName());
        devLog.setMethodName(joinPoint.getSignature().getName());
        devLog.setReqMethod(request.getMethod());
        devLog.setReqUrl(request.getRequestURI());
        devLog.setParamJson(CommonJoinPointUtil.getArgsJsonString(joinPoint));
        devLog.setOpTime(DateTime.now());
        devLog.setOpUser(userName);
        creatLogSignValue(devLog);
        devLogService.save(devLog);
        // MDC 扩展logback字段，具体请看logback-spring.xml的自定义日志输出格式
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String description = methodSignature.getMethod().getAnnotation(ApiOperation.class).value();
        String date = DateTime.now().toString("yyyy-MM-dd");
        try {
            MDC.put("elapsedTime", StrUtil.toString(elapsedTime));
            MDC.put("description", description);
            MDC.put("region", devLog.getOpAddress());
            MDC.put("username", devLog.getOpUser());
            MDC.put("date", date);
            MDC.put("paramJson", devLog.getParamJson());
            MDC.put("exception", devLog.getExeMessage());
            MDC.put("clientIP", devLog.getOpIp());
            MDC.put("category", DevLogCategoryEnum.EXCEPTION.getValue());
            MDC.put("reqUrl", devLog.getReqUrl());
            log.error("{}异常日志---", description);
        }finally {
            MDC.clear();
        }
    }

    /**
     * 记录登录日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 16:08
     */
    public static void executeLoginLog(String userName) {
        DevLog devLog = genBasOpLog();
        devLog.setTenantId(tenApi.getCurrentTenId());
        devLog.setCategory(DevLogCategoryEnum.LOGIN.getValue());
        devLog.setName("用户登录");
        devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
        devLog.setOpTime(DateTime.now());
        devLog.setOpUser(userName);
        creatLogSignValue(devLog);
        devLogService.save(devLog);
    }

    /**
     * 记录登出日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 16:08
     */
    public static void executeLogoutLog(String userName) {
        DevLog devLog = genBasOpLog();
        devLog.setTenantId(tenApi.getCurrentTenId());
        devLog.setCategory(DevLogCategoryEnum.LOGOUT.getValue());
        devLog.setName("用户登出");
        devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
        devLog.setOpTime(DateTime.now());
        devLog.setOpUser(userName);
        creatLogSignValue(devLog);
        devLogService.save(devLog);
    }

    /**
     * 构建基础操作日志
     *
     * @author xuyuxiang
     * @date 2020/3/19 14:44
     */
    private static DevLog genBasOpLog() {
        HttpServletRequest request = CommonServletUtil.getRequest();
        String ip = CommonIpAddressUtil.getIp(request);
        DevLog devLog = new DevLog();
        devLog.setOpIp(CommonIpAddressUtil.getIp(request));
        devLog.setOpAddress(CommonIpAddressUtil.getCityInfo(ip));
        devLog.setOpBrowser(CommonUaUtil.getBrowser(request));
        devLog.setOpOs(CommonUaUtil.getOs(request));
        return devLog;
    }

    /**
     * 构建日志完整性保护签名数据
     */
    private static void creatLogSignValue (DevLog devLog) {
        String logStr = devLog.toString().replaceAll(" +","");
        devLog.setSignData(CommonCryptogramUtil.doSignature(logStr));
    }

    /**
     * 网盘文件操作-日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 16:08
     */
    public static void executeFileActionLog(String userName, String actionType,String message, List<String> userFileIds) {
        try {
            List<DevLog> addList = new ArrayList<>();
            for (String userFileId : userFileIds) {
                DevLog devLog = genBasOpLog();
                devLog.setTenantId(tenApi.getCurrentTenId());
                devLog.setCategory(DevLogCategoryEnum.FILE_ACTION.getValue()); //分类
                devLog.setName(actionType);  //文件操作类型
                devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
                devLog.setOpTime(DateTime.now());
                devLog.setOpUser(userName);      //操作人
                devLog.setParamJson(userFileId); //文件Id
                devLog.setExeMessage(message);  //备注
                creatLogSignValue(devLog);
                addList.add(devLog);
            }
            if (CollUtil.isNotEmpty(addList)) {
                devLogService.saveBatch(addList);
            }
        }catch (Exception e){
            //记录日志异常不做处理
        }
    }

    /**
     * 网盘文件操作-日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 16:08
     */
    public static void asynExecuteFileActionLog(String userName, String actionType,String message, List<String> userFileIds) {
        try {
            List<DevLog> addList = new ArrayList<>();
            for (String userFileId : userFileIds) {
                DevLog devLog = genBasOpLog();
                devLog.setTenantId(tenApi.getCurrentTenId());
                devLog.setCategory(DevLogCategoryEnum.FILE_ACTION.getValue()); //分类
                devLog.setName(actionType);  //文件操作类型
                devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
                devLog.setOpTime(DateTime.now());
                devLog.setOpUser(userName);      //操作人
                devLog.setParamJson(userFileId); //文件Id
                devLog.setExeMessage(message);  //备注
                asynCreatLogSignValue(devLog);
                addList.add(devLog);
            }
            if (CollUtil.isNotEmpty(addList)) { devLogService.saveBatch(addList);}
        }catch (Exception e){
            //记录日志异常不做处理
        }
    }

    /**
     * 构建日志完整性保护签名数据,全部数据改ID
     */
    private static void asynCreatLogSignValue (DevLog devLog) {
        String logStr = devLog.getId().replaceAll(" +","");
        devLog.setSignData(CommonCryptogramUtil.doSignature(logStr));
    }
}
