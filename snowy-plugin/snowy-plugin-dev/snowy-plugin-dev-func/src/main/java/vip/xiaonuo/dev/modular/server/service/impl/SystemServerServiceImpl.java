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
package vip.xiaonuo.dev.modular.server.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.epcCenter.entity.SysProject;
import vip.xiaonuo.dev.modular.epcCenter.service.SysProjectService;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.entity.ExtMessage;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.entity.TemplateInfo;
import vip.xiaonuo.dev.modular.server.enums.SystemTypeEnum;
import vip.xiaonuo.dev.modular.server.mapper.SystemServerMapper;
import vip.xiaonuo.dev.modular.server.message.MessageService;
import vip.xiaonuo.dev.modular.server.param.*;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;
import vip.xiaonuo.dev.modular.server.util.TemplateUtil;
import vip.xiaonuo.dev.modular.server.vo.SystemRoleVo;
import vip.xiaonuo.dev.modular.userRelation.service.SystemUserRelationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统服务表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/29 11:31
 **/
@Service
@DS("common")
@Slf4j
public class SystemServerServiceImpl extends ServiceImpl<SystemServerMapper, SystemServer> implements SystemServerService {

    @Resource
    SystemConfExecService systemConfExecService;
    @Resource
    SysProjectService sysProjectService;
    @Resource
    SystemUserRelationService systemUserRelationService;

    @Resource
    DevConfigService devConfigService;

    @Resource
    private MessageService messageService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<SystemServer> page(SystemServerPageParam systemServerPageParam) {
        QueryWrapper<SystemServer> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemName())) {
            queryWrapper.lambda().like(SystemServer::getSystemName, systemServerPageParam.getSystemName());
        }
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemType())) {
            queryWrapper.lambda().eq(SystemServer::getSystemType, systemServerPageParam.getSystemType());
        }
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSystemStatus())) {
            queryWrapper.lambda().eq(SystemServer::getSystemStatus, systemServerPageParam.getSystemStatus());
        }
        if(ObjectUtil.isNotEmpty(systemServerPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SystemServer::getSystemName,systemServerPageParam.getSearchKey())
                    .or().like(SystemServer::getSystemDomain,systemServerPageParam.getSearchKey())
                    .or().like(SystemServer::getSystemSource,systemServerPageParam.getSearchKey())
                    .or().like(SystemServer::getSystemSourceId,systemServerPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(systemServerPageParam.getSortField(), systemServerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(systemServerPageParam.getSortOrder());
            queryWrapper.orderBy(true, systemServerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(systemServerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SystemServer::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
//        getSystemExec(page);
//        return page;
        //return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }
    private void getSystemExec(Page<SystemServer> page){
        if(CollectionUtil.isNotEmpty(page.getRecords())){
            List<String> systemIds = page.getRecords().stream().map((record -> record.getId())).collect(Collectors.toList());
            List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().in(SystemConfExec::getSystemId,systemIds));
            Map<String,SystemConfExec> map = new HashMap<>();
            systemConfExecs.forEach(exec -> map.put(exec.getSystemId(),exec));
            page.getRecords().forEach(record -> {
                if(map.get(record.getId()) != null){
                    record.setSystemConfExec(map.get(record.getId()));
                }
            });
        }
    }


    @DSTransactional
    @Override
    public void add(SystemServerAddParam systemServerAddParam) {
        SystemServer systemServer = BeanUtil.toBean(systemServerAddParam, SystemServer.class);
        long count = this.count(new QueryWrapper<SystemServer>().lambda().eq(SystemServer::getSystemSource,systemServerAddParam.getSystemSource()));
        if(count>0){
            throw new CommonException("{}服务已存在",systemServerAddParam.getSystemSource());
        }
        systemServer.setSystemName(systemServer.getSystemSource());
        systemServer.setSystemStatus(0);
        this.save(systemServer);
        List<SystemRoleVo> systemRoleVos = getSystemRoleVos(systemServerAddParam.getSystemType());
        systemServer.setSystemRoleVos(systemRoleVos);
        ExtMessage extMessage = new ExtMessage();
        extMessage.setId(IdWorker.getIdStr());
        extMessage.setOperateType(1);
        extMessage.setData(JSONUtil.toJsonStr(systemServer));
        //系统项目创建完成以后，就通知项目系统去初始化项目信息
        String routingKey = SystemTypeEnum.getRoutingKeyByTypeName(systemServerAddParam.getSystemType());
        boolean flag = false;
        if(null == routingKey){
           log.info("not found routingKey.");
        }else {
            flag = messageService.sendMessage(extMessage,routingKey);
        }
        //        boolean send = streamBridge.send(RabbitConstant.DEFAULT_EXT_CHANNEL, extMessage);
        if(!flag){
            //修改子系统状态为创建失败
            update(new LambdaUpdateWrapper<SystemServer>().set(SystemServer::getSystemStatus,2).eq(SystemServer::getId,systemServer.getId()));
            log.error("send message info fail");
        }
        //不能修改项目状态，只有子系统消费了，创建租户了才能修改状态
//        //初始化项目成功，则修改项目的状态
//        if(bo){
//            this.update(new UpdateWrapper<SystemServer>().lambda().set(SystemServer::getSystemStatus,1).eq(SystemServer::getId,systemServer.getId()));
//        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SystemServerEditParam systemServerEditParam) {
        SystemServer systemServer = this.queryEntity(systemServerEditParam.getId());
        long count = this.count(new QueryWrapper<SystemServer>().lambda().eq(SystemServer::getSystemSourceId,systemServerEditParam.getSystemSourceId()).ne(SystemServer::getId,systemServerEditParam.getId()));
        if(count>0){
            throw new CommonException("{}服务已存在",systemServerEditParam.getSystemSourceId());
        }
        String oldSystemDomain = StringUtils.isNotEmpty(systemServer.getSystemDomain()) ? systemServer.getSystemDomain() : "";
        String newSystemDomain = StringUtils.isNotEmpty(systemServerEditParam.getSystemDomain()) ? systemServerEditParam.getSystemDomain() : "";
        if(!oldSystemDomain.equals(newSystemDomain)){
            List<SysProject> sysProjectList = sysProjectService.list(new LambdaQueryWrapper<SysProject>().eq(SysProject::getPrjSn,systemServer.getSystemSourceId()));
            if(CollectionUtil.isNotEmpty(sysProjectList)){
                SysProject sysProject = sysProjectList.get(0);
                sysProject.setPrjUrl(newSystemDomain);
                sysProjectService.saveOrUpdate(sysProject);
            }
        }
        BeanUtil.copyProperties(systemServerEditParam, systemServer);
        this.updateById(systemServer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SystemServerIdParam> systemServerIdParamList) {
        //犹豫区分了菜单，批量删除是删除的是同一类的系统，只有根据systemServerIdParamList 中的某一个获取系统类型即可
        SystemServer systemServer = this.queryEntity(systemServerIdParamList.get(0).getId());
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(systemServerIdParamList, SystemServerIdParam::getId));

        ExtMessage extMessage = new ExtMessage();
        extMessage.setId(IdWorker.getIdStr());
        JSONObject data = new JSONObject();
        data.set("systemIds",CollStreamUtil.toList(systemServerIdParamList, SystemServerIdParam::getId));
        extMessage.setData(JSONUtil.toJsonStr(data));
        extMessage.setOperateType(2);
        //删除关联表中数据
        systemUserRelationService.deleteBySystemIds(CollStreamUtil.toList(systemServerIdParamList, SystemServerIdParam::getId));

        String routingKey = SystemTypeEnum.getRoutingKeyByTypeName(systemServer.getSystemType());
        if(null == routingKey){
            log.info("not found routingKey.");
        }else {
            boolean flag = messageService.sendMessage(extMessage,routingKey);
            log.info("delete system result {}.",flag);
        }
    }

    @Override
    public SystemServer detail(SystemServerIdParam systemServerIdParam) {
        SystemServer systemServer = this.queryEntity(systemServerIdParam.getId());
        List<SystemConfExec> systemConfExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,systemServerIdParam.getId()));
        systemServer.setSystemConfExec(CollectionUtil.isNotEmpty(systemConfExecList) ? systemConfExecList.get(0) : new SystemConfExec());
        return systemServer;
    }

    @Override
    public SystemServer queryEntity(String id) {
        SystemServer systemServer = this.getById(id);
        if(ObjectUtil.isEmpty(systemServer)) {
            throw new CommonException("系统服务表不存在，id值为：{}", id);
        }
        return systemServer;
    }

    @Override
    public Page<SystemServer> myServerTask(MyServerParam myServerParam){
        try {

            QueryWrapper<SystemServer> queryWrapper = new QueryWrapper<>();
            if(ObjectUtil.isNotEmpty(myServerParam.getSystemName())) {
                queryWrapper.lambda().like(SystemServer::getSystemName, myServerParam.getSystemName());
            }
            if(ObjectUtil.isNotEmpty(myServerParam.getSystemType())) {
                queryWrapper.lambda().eq(SystemServer::getSystemType, myServerParam.getSystemType());
            }
            if(ObjectUtil.isNotEmpty(myServerParam.getSystemStatus())) {
                queryWrapper.lambda().eq(SystemServer::getSystemStatus, myServerParam.getSystemStatus());
            }
            if(ObjectUtil.isNotEmpty(myServerParam.getSearchKey())){
                queryWrapper.lambda().and(q->q.like(SystemServer::getSystemName,myServerParam.getSearchKey())
                        .or().like(SystemServer::getSystemDomain,myServerParam.getSearchKey())
                        .or().like(SystemServer::getSystemSource,myServerParam.getSearchKey())
                        .or().like(SystemServer::getSystemSourceId,myServerParam.getSearchKey())
                );
            }
            if(ObjectUtil.isAllNotEmpty(myServerParam.getSortField(), myServerParam.getSortOrder())) {
                CommonSortOrderEnum.validate(myServerParam.getSortOrder());
                queryWrapper.orderBy(true, myServerParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                        StrUtil.toUnderlineCase(myServerParam.getSortField()));
            } else {
                queryWrapper.lambda().orderByDesc(SystemServer::getCreateTime);
            }
            String sqlSegment = String.format(" FIND_IN_SET('%s', PARTER) > 0",StpLoginUserUtil.getLoginUser().getId());
            queryWrapper.apply(sqlSegment);
            Page<SystemServer> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
            return page;
            //List<SystemServer> systemServerList = systemServerMapper.selectServerList(StpLoginUserUtil.getLoginUser().getId());
        }catch (Exception e){
            log.error("数据查询失败!"+e.getMessage());
            throw new CommonException("数据查询失败!"+e.getMessage());
        }
    }

    public List<SystemRoleVo> getSystemRoleVos(String type){
        String typeConfigValue = SystemTypeEnum.getConfigByTypeName(type);
        String valueByKey = devConfigService.getValueByKey(typeConfigValue);
        List<SystemRoleVo>  systemRoleVos = new ArrayList<>();
        //String RoleJson = devConfigService.getValueByKey(valueByKey);
        try{
            if(StringUtils.isNotEmpty(valueByKey)){
                systemRoleVos = JSONArray.parseArray(valueByKey, SystemRoleVo.class);
            }
        }catch (Exception e){
            log.error("获取"+type+"字典表数据失败.");
        }
        return systemRoleVos;
    }

    @Override
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        try {
            InputStream inputStream = POICacheManager.getFile("系统初始化模板.xlsx");
            byte[] bytes = IoUtil.readBytes(inputStream);
            CommonDownloadUtil.download("系统初始化模板.xlsx", bytes, response);
        } catch (Exception e) {
            log.error(">>> 系统初始化模板.xlsx下载失败：", e);
            CommonResponseUtil.renderError(response, "下载系统初始化模板失败");
        }
    }

    @Override
    public void parseExcel(MultipartFile file, String systemType) {
        try {
            File uploadTemplate = File.createTempFile("uploadTemplate", ".xlsx");
            file.transferTo(uploadTemplate);
            List<TemplateInfo> templateInfoList = TemplateUtil.parseExcel(uploadTemplate);
            String jsonStr = JSONUtil.toJsonStr(templateInfoList);
            commonCacheOperator.put(InitConfConstant.SYSTEM_INIT_TEMPLATE_PRE +systemType,jsonStr);
            List<Map<String, Object>> roleMap = templateInfoList.stream().filter(templateInfo -> !templateInfo.getType().equals(1))
                    .map(info -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("roleName", info.getPositionOrRole());
                        map.put("roleId", info.getCode());
                        return map;
                    }).collect(Collectors.toList());
            commonCacheOperator.put(InitConfConstant.SYSTEM_INIT_ROLE_PRE+systemType,roleMap);
        }catch (Exception e){
            log.error("模板解析失败.",e.getMessage());
        }
    }

    @Override
    public List<SystemRoleVo> getSystemRoles(String systemType) {
        Object o = commonCacheOperator.get(InitConfConstant.SYSTEM_INIT_ROLE_PRE + systemType);
        if(ObjectUtil.isNotEmpty(o)){
            return (List<SystemRoleVo>) o;
        }
        List<SystemRoleVo> systemRoleVos = getSystemRoleVos(systemType);
        if(CollUtil.isNotEmpty(systemRoleVos)){
            return systemRoleVos;
        }
        return Collections.emptyList();
    }
}
