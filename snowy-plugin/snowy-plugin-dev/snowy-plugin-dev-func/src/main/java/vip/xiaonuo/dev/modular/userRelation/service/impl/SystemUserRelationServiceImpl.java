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
package vip.xiaonuo.dev.modular.userRelation.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.server.entity.ExtMessage;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.userRelation.entity.SystemUserRelation;
import vip.xiaonuo.dev.modular.userRelation.mapper.SystemUserRelationMapper;
import vip.xiaonuo.dev.modular.userRelation.param.*;
import vip.xiaonuo.dev.modular.userRelation.service.SystemUserRelationService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目系统和账户的关联关系表Service接口实现类
 *
 * @author mayike
 * @date  2024/02/29 11:22
 **/
@Service
@DS("common")
public class SystemUserRelationServiceImpl extends ServiceImpl<SystemUserRelationMapper, SystemUserRelation> implements SystemUserRelationService {

    @Resource
    DevConfigService devConfigService;

    @Resource
    private StreamBridge streamBridge;

    @Override
    public Page<SystemUserRelation> page(SystemUserRelationPageParam systemUserRelationPageParam) {
        QueryWrapper<SystemUserRelation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(systemUserRelationPageParam.getSortField(), systemUserRelationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(systemUserRelationPageParam.getSortOrder());
            queryWrapper.orderBy(true, systemUserRelationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(systemUserRelationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SystemUserRelation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemUserRelationAddParam systemUserRelationAddParam) {
        SystemUserRelation systemUserRelation = BeanUtil.toBean(systemUserRelationAddParam, SystemUserRelation.class);
        this.save(systemUserRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SystemUserRelationEditParam systemUserRelationEditParam) {
        SystemUserRelation systemUserRelation = this.queryEntity(systemUserRelationEditParam.getId());
        BeanUtil.copyProperties(systemUserRelationEditParam, systemUserRelation);
        this.updateById(systemUserRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SystemUserRelationIdParam> systemUserRelationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(systemUserRelationIdParamList, SystemUserRelationIdParam::getId));
    }

    @Override
    public SystemUserRelation detail(SystemUserRelationIdParam systemUserRelationIdParam) {
        return this.queryEntity(systemUserRelationIdParam.getId());
    }

    @Override
    public SystemUserRelation queryEntity(String id) {
        SystemUserRelation systemUserRelation = this.getById(id);
        if(ObjectUtil.isEmpty(systemUserRelation)) {
            throw new CommonException("项目系统和账户的关联关系表不存在，id值为：{}", id);
        }
        return systemUserRelation;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBySystemIds(List<String> systemIds) {
        this.remove(new LambdaQueryWrapper<SystemUserRelation>().in(SystemUserRelation::getSystemId,systemIds));
    }

    @Override
    public void syncUser(SyncParam syncParam) {
        // 分两步：1、同步到关系表中 2、 同步到项目系统中
        List<SyncParam.SyncUser> syncUsers = syncParam.getSyncUsers();
        if(CollUtil.isEmpty(syncUsers)){
            throw new CommonException("同步人员列表为空");
        }

       // baseMapper.delete(new LambdaQueryWrapper<SystemUserRelation>().eq(SystemUserRelation::getSystemId,syncParam.getId()).eq(SystemUserRelation::getUserType,"1"));
        baseMapper.deleteBySystemId(syncParam.getId());
        List<SystemUserRelation> collect = syncUsers.stream().map(syncUser -> {
            SystemUserRelation systemUserRelation = new SystemUserRelation();
            systemUserRelation.setSystemId(syncParam.getId());
            systemUserRelation.setUserAccount(syncUser.getAccount());
            systemUserRelation.setUserId(syncUser.getUserId());
            systemUserRelation.setRoleKey(syncUser.getRoleId());
            systemUserRelation.setUserName(syncUser.getUserName());
            systemUserRelation.setUserType(1);
            return systemUserRelation;
        }).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(collect)){
            saveBatch(collect);
        }


//        Map<String,SyncParam.SyncUser> params = new HashMap<>();
//        for (SyncParam.SyncUser syncUser : syncUsers) {
//            params.put(syncUser.getAccount()+syncUser.getRoleId(),syncUser);
//        }
//
//        List<SyncParam.SyncUser> remove = new ArrayList<>();
//        //原关系人员
//        List<SystemUserRelation> list = list(new LambdaQueryWrapper<SystemUserRelation>().eq(SystemUserRelation::getSystemId, syncParam.getId()).eq(SystemUserRelation::getUserType,1));
//        //获取需要修改的，新增的，删除的
//        //List<SystemUserRelation> relations = new ArrayList<>();  //新增列表
//        List<SystemUserRelation> deletes = new ArrayList<>();   //删除列表
//        if(CollUtil.isNotEmpty(list)){
//            for (SystemUserRelation userRelation : list) {
//                SyncParam.SyncUser syncUser = params.get(userRelation.getUserAccount() + userRelation.getRoleKey());
//                if(null!=syncUser){
//                    //原来的从新的里面可以获取的到，说明存在，从新的params 移除，就剩下新增的了
//                    remove.add(syncUser);
//                    params.remove(userRelation.getUserAccount() + userRelation.getRoleKey());
//                }else {
//                    deletes.add(userRelation);
//                }
//            }
//        }
//
//
//        List<SystemUserRelation> collect = params.values().stream().map(syncUser -> {
//            SystemUserRelation systemUserRelation = new SystemUserRelation();
//            systemUserRelation.setSystemId(syncParam.getId());
//            systemUserRelation.setUserAccount(syncUser.getAccount());
//            systemUserRelation.setUserId(syncUser.getUserId());
//            systemUserRelation.setRoleKey(syncUser.getRoleId());
//            systemUserRelation.setUserName(syncUser.getUserName());
//            systemUserRelation.setUserType(1);
//            return systemUserRelation;
//        }).collect(Collectors.toList());
//        if(CollUtil.isNotEmpty(collect)){
//            saveOrUpdateBatch(collect);
////            ExtMessage extMessage = new ExtMessage();
////            extMessage.setId(IdWorker.getIdStr());
////            extMessage.setOperateType(3);
////            extMessage.setData(JSONUtil.toJsonStr(collect));
////            //系统项目创建完成以后，就通知项目系统去初始化项目信息
////            streamBridge.send(RabbitConstant.DEFAULT_EXT_CHANNEL,extMessage);
////            String valueUrl = devConfigService.getValueByKey("EPC_SYSUSER");
////            if(StrUtil.isNotEmpty(valueUrl)){
////                String json = JSONUtil.parseArray(collect).toString();
////                //异步执行
////                HttpUtil.createPost(valueUrl).body(json).execute(true);
////            }
//        }
//        if(CollUtil.isNotEmpty(deletes)){
//            List<String> ids = deletes.stream().map(SystemUserRelation::getId).collect(Collectors.toList());
//            removeByIds(ids);
//        }
//        List<SystemUserRelation> collect1 = remove.stream().map(syncUser -> {
//            SystemUserRelation systemUserRelation = new SystemUserRelation();
//            systemUserRelation.setSystemId(syncParam.getId());
//            systemUserRelation.setUserAccount(syncUser.getAccount());
//            systemUserRelation.setUserId(syncUser.getUserId());
//            systemUserRelation.setRoleKey(syncUser.getRoleId());
//            systemUserRelation.setUserName(syncUser.getUserName());
//            systemUserRelation.setUserType(1);
//            return systemUserRelation;
//        }).collect(Collectors.toList());
//        collect.addAll(collect1);
        if(CollUtil.isNotEmpty(collect)){
            ExtMessage extMessage = new ExtMessage();
            extMessage.setId(IdWorker.getIdStr());
            extMessage.setOperateType(3);
            extMessage.setData(JSONUtil.toJsonStr(collect));
            //系统项目创建完成以后，就通知项目系统去初始化项目信息
            streamBridge.send(RabbitConstant.DEFAULT_EXT_CHANNEL,extMessage);
        }

    }

    @Override
    public Page<SystemServer> myServer(SystemUserRelationPageParam systemUserRelationPageParam) {
        QueryWrapper<SystemServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.USER_ID", StpUtil.getLoginIdAsString());
        if(ObjectUtil.isNotEmpty(systemUserRelationPageParam.getSearchKey())){
            queryWrapper.like("a.SYSTEM_NAME",systemUserRelationPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(systemUserRelationPageParam.getSystemType())){
            queryWrapper.like("a.SYSTEM_TYPE",systemUserRelationPageParam.getSystemType());
        }
        return baseMapper.myServer(CommonPageRequest.defaultPage(),queryWrapper);
    }

    @Override
    public Map<String, List<SystemUserRelation>> getListByRoleKey(String systemId) {
        List<SystemUserRelation> originalList = this.list(new LambdaQueryWrapper<SystemUserRelation>().eq(SystemUserRelation::getSystemId, systemId).eq(SystemUserRelation::getUserType,1));

        return originalList.stream()
                .filter(systemUserRelation -> StrUtil.isNotEmpty(systemUserRelation.getRoleKey())) // 过滤掉 roleKey 为空的对象
                .collect(Collectors.groupingBy(SystemUserRelation::getRoleKey)); // 按照 roleKey 进行分组
    }
}
