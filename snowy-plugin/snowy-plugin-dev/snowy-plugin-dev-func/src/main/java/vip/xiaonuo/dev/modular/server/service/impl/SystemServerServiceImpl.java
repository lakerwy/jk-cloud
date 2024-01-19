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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.mapper.SystemServerMapper;
import vip.xiaonuo.dev.modular.server.param.*;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统服务表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/29 11:31
 **/
@Service
public class SystemServerServiceImpl extends ServiceImpl<SystemServerMapper, SystemServer> implements SystemServerService {

    @Resource
    SystemConfExecService systemConfExecService;
    @Resource
    SystemServerMapper systemServerMapper;
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
            queryWrapper.lambda().orderByAsc(SystemServer::getId);
        }
        Page<SystemServer> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        getSystemExec(page);
        return page;
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemServerAddParam systemServerAddParam) {
        SystemServer systemServer = BeanUtil.toBean(systemServerAddParam, SystemServer.class);
        long count = this.count(new QueryWrapper<SystemServer>().lambda().eq(SystemServer::getSystemSourceId,systemServerAddParam.getSystemSourceId()));
        if(count>0){
            throw new CommonException("{}服务已存在",systemServerAddParam.getSystemSourceId());
        }
        systemServer.setSystemStatus(0);
        this.save(systemServer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SystemServerEditParam systemServerEditParam) {
        SystemServer systemServer = this.queryEntity(systemServerEditParam.getId());
        long count = this.count(new QueryWrapper<SystemServer>().lambda().eq(SystemServer::getSystemSourceId,systemServerEditParam.getSystemSourceId()).ne(SystemServer::getId,systemServerEditParam.getId()));
        if(count>0){
            throw new CommonException("{}服务已存在",systemServerEditParam.getSystemSourceId());
        }
        BeanUtil.copyProperties(systemServerEditParam, systemServer);
        this.updateById(systemServer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SystemServerIdParam> systemServerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(systemServerIdParamList, SystemServerIdParam::getId));
        try{
            List<String> systemIds = systemServerIdParamList.stream().map(param -> param.getId()).collect(Collectors.toList());
            List<SystemConfExec> systemConfExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().in(SystemConfExec::getSystemId,systemIds));
            systemConfExecService.removeBatchByIds(systemConfExecList.stream().map(conf -> conf.getId()).collect(Collectors.toList()));
        }catch (Exception e){
            log.error("批量删除配置失败！e:"+e.getMessage());
            throw new CommonException("批量删除配置失败！e:"+e.getMessage());
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
                queryWrapper.lambda().orderByAsc(SystemServer::getId);
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
}
