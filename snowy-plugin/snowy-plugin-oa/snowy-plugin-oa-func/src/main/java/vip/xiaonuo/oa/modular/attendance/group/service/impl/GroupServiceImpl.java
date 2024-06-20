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
package vip.xiaonuo.oa.modular.attendance.group.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.core.constant.AttCacheConstants;
import vip.xiaonuo.oa.modular.attendance.client.WebServerClient;
import vip.xiaonuo.oa.modular.attendance.group.entity.AttObject;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.mapper.GroupMapper;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupAddParam;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupEditParam;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupIdParam;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupPageParam;
import vip.xiaonuo.oa.modular.attendance.group.service.GroupService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 考勤-考勤组Service接口实现类
 *
 * @author mayike
 * @date  2024/06/05 15:51
 **/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Resource
    private WebServerClient webServerClient;

    @Resource
    private CommonCacheOperator cacheOperator;

    @Override
    public Page<Group> page(GroupPageParam groupPageParam) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(groupPageParam.getGroupName())) {
            queryWrapper.lambda().like(Group::getGroupName, groupPageParam.getGroupName());
        }
        if(ObjectUtil.isAllNotEmpty(groupPageParam.getSortField(), groupPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(groupPageParam.getSortOrder());
            queryWrapper.orderBy(true, groupPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(groupPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Group::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    /**
     * todo
     * 考勤对象的部门或者角色发生变化以后需要同步更新 考勤人的规则
     * @param groupAddParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(GroupAddParam groupAddParam) {
        checkGroupName(groupAddParam.getGroupName());
        if(CollUtil.isEmpty(groupAddParam.getAssistAdmins())){
            List<AttObject> assistAdmins = new ArrayList<>();
            SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            assert loginUser != null;
            assistAdmins.add(new AttObject(loginUser.getId(),loginUser.getName(),2));
            groupAddParam.setAssistAdmins(assistAdmins);
        }
        List<AttObject> participates = groupAddParam.getParticipates();
        List<AttObject> trueParticipates = buildAttObjects(participates);
        if(CollUtil.isEmpty(trueParticipates)){
            throw  new CommonException("考勤对象未设置.");
        }
        Group group = BeanUtil.toBean(groupAddParam, Group.class);
        group.setTrueParticipants(trueParticipates);
        this.save(group);
    }

    public  List<AttObject> buildAttObjects(List<AttObject> participates){
        List<AttObject> trueParticipates = new ArrayList<>();
        if(CollUtil.isNotEmpty(participates)){
            participates.forEach(attObject -> {
                if(attObject.getType() == 0 || attObject.getType() == 1){
                    //获取公司或者部门下的所有用户信息
                    String childrenUser = webServerClient.getChildrenUser(attObject.getObjectId());
                    JSONArray jsonArray = JSONUtil.parseArray(childrenUser);
                    trueParticipates.addAll(jsonArray.stream()
                            .map(json -> {
                                JSONObject jsonObject = (JSONObject) json;
                                return new AttObject(
                                        jsonObject.getStr("id"),
                                        jsonObject.getStr("name"),
                                        2
                                );
                            })
                            .collect(Collectors.toList()));
                }else {
                    trueParticipates.add(attObject);
                }
            });
        }
        return trueParticipates;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(GroupEditParam groupEditParam) {
        Group group = this.queryEntity(groupEditParam.getId());

        List<AttObject> assistAdmins = group.getAssistAdmins();
        Set<String> collect = assistAdmins.stream().map(AttObject::getObjectId).collect(Collectors.toSet());
        if(!collect.contains(Objects.requireNonNull(StpLoginUserUtil.getLoginUser()).getId())){
            throw new CommonException("非考勤管理员不得修改.");
        }

        checkGroupName(groupEditParam.getGroupName());

        List<AttObject> participates = groupEditParam.getParticipates();
        List<AttObject> trueParticipates = buildAttObjects(participates);
        if(CollUtil.isEmpty(trueParticipates)){
            throw  new CommonException("考勤对象未设置.");
        }
        BeanUtil.copyProperties(groupEditParam, group);
        group.setTrueParticipants(trueParticipates);

        this.updateById(group);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<GroupIdParam> groupIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(groupIdParamList, GroupIdParam::getId));
    }

    @Override
    public Group detail(GroupIdParam groupIdParam) {
        return this.queryEntity(groupIdParam.getId());
    }

    @Override
    public Group queryEntity(String id) {
        Group group = this.getById(id);
        if(ObjectUtil.isEmpty(group)) {
            throw new CommonException("考勤-考勤组不存在，id值为：{}", id);
        }
        return group;
    }

    private void checkGroupName(String groupName){
        Group existGroup = this.getOne(new LambdaQueryWrapper<Group>().eq(Group::getGroupName, groupName));
        if(ObjectUtil.isNotEmpty(existGroup)){
            throw new CommonException("名称为{}的考勤组存在.");
        }
    }

    @Override
    public Group getGroupByUserId(String userId) {
        //从缓存中获取
        Object groupId = cacheOperator.get(AttCacheConstants.ATT_GROUP_USERID_CACHE_PRE + userId);
        if(ObjectUtil.isNull(groupId)){
            LambdaQueryWrapper<Group> apply = new LambdaQueryWrapper<Group>().apply("JSON_CONTAINS(TRUE_PARTICIPANTS, JSON_OBJECT('objectId', {0}))", userId);
            Group group = baseMapper.selectOne(apply);
            if(ObjectUtil.isNull(group)){
                throw new CommonException("当前用户未配置考勤组.");
            }
            // 排班存入缓存
            cacheOperator.put(AttCacheConstants.ATT_GROUP_CACHE_PRE+group.getId(),group);
            List<AttObject> trueParticipants = group.getTrueParticipants();
            trueParticipants.forEach(attObject -> cacheOperator.put(AttCacheConstants.ATT_GROUP_USERID_CACHE_PRE+attObject.getObjectId(),group.getId()));
            return group;
        }else {
            Object o = cacheOperator.get(AttCacheConstants.ATT_GROUP_CACHE_PRE + groupId);
            if(ObjectUtil.isNull(o)){
                Group group = this.getById(String.valueOf(groupId));
                if(ObjectUtil.isNull(group)){
                    cacheOperator.removeKeys(AttCacheConstants.ATT_GROUP_USERID_CACHE_PRE);
                    cacheOperator.remove(AttCacheConstants.ATT_GROUP_CACHE_PRE+group);
                    throw  new CommonException("用户关联得考勤组不存在.");
                }
                return group;
            }else {
                return (Group) o;
            }
        }
    }
}
