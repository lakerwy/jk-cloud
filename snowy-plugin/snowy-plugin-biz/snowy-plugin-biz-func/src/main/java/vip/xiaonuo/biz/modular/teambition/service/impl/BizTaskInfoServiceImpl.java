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
package vip.xiaonuo.biz.modular.teambition.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskInfo;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskProject;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskInfoMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoPageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskInfoService;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务主表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@DS("slave_1")
@Service
public class BizTaskInfoServiceImpl extends ServiceImpl<BizTaskInfoMapper, BizTaskInfo> implements BizTaskInfoService {

    @Resource
    BizTaskProjectService bizTaskProjectService;
    @Resource
    BizOrgService bizOrgService;


    @Override
    public Page<BizTaskInfo> page(BizTaskInfoPageParam bizTaskInfoPageParam) {
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
        QueryWrapper<BizTaskInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getParentId())) {
            queryWrapper.lambda().eq(BizTaskInfo::getParentId, bizTaskInfoPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTaskInfo::getTaskName, bizTaskInfoPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskStartTime()) && ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskEndTime())) {
            queryWrapper.lambda().between(BizTaskInfo::getTaskStartTime, bizTaskInfoPageParam.getTaskStartTime(), bizTaskInfoPageParam.getTaskEndTime());
            queryWrapper.lambda().between(BizTaskInfo::getTaskEndTime, bizTaskInfoPageParam.getTaskStartTime(), bizTaskInfoPageParam.getTaskEndTime());
        }
        /**
         * 任务权限
         * 1.自己负责的任务
         * 2.自己执行的任务
         * 3.自己参与的
         * 4.自己创建的
         * 5.所属同一部门的其他人的任务(任务状态为非私有才可看)
         * 6.若为部门领导，可以看所有部门子任务(任何状态)
         * */
        queryWrapper.lambda().eq(BizTaskInfo::getLeadId, saBaseLoginUser.getId())  //自己负责的任务
                .or().eq(BizTaskInfo::getBelonger, saBaseLoginUser.getId())   //自己执行的任务
                .or().like(BizTaskInfo::getTaskParticipants,saBaseLoginUser.getId())//自己参与的任务
                .or().eq(BizTaskInfo::getCreateUser,saBaseLoginUser.getId())//自己创建的的任务
                .or().eq(BizTaskInfo::getLeadOrg,saBaseLoginUser.getOrgId());//同一部门的非私有任务
        if(bizOrgService.count(new LambdaQueryWrapper<BizOrg>().eq(BizOrg::getDirectorId,saBaseLoginUser.getId())) > 0){
            //创建人部门，执行人部门
            List<String> childrenOrgIds = bizOrgService.getChildren(saBaseLoginUser.getOrgId()).stream().map(org -> org.getId()).collect(Collectors.toList());
            //queryWrapper.lambda().or().in()
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizTaskInfo::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizTaskInfo::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q -> q.like(BizTaskInfo::getTaskName, bizTaskInfoPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskInfoPageParam.getSortField(), bizTaskInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskInfo::getId);
        }
        Page<BizTaskInfo> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        getTaskProject(page);
        return page;
    }
    /** 获取任务的来源详情*/
    private void getTaskProject(Page<BizTaskInfo> page){
        if(CollectionUtil.isNotEmpty(page.getRecords())){
            List<String> proIds = page.getRecords().stream().map(info ->info.getProId()).collect(Collectors.toList());
            List<BizTaskProject> taskProjectList = bizTaskProjectService.list(new LambdaQueryWrapper<BizTaskProject>().in(BizTaskProject::getId,proIds));
            Map<String,BizTaskProject> map = new HashMap<>();
            if(CollectionUtil.isNotEmpty(taskProjectList)){
                taskProjectList.forEach(task -> map.put(task.getId(),task));
                page.getRecords().forEach(info ->info.setBizTaskProject(map.get(info.getId())));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskInfoAddParam bizTaskInfoAddParam) {
        BizTaskInfo bizTaskInfo = BeanUtil.toBean(bizTaskInfoAddParam, BizTaskInfo.class);
        this.save(bizTaskInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskInfoEditParam bizTaskInfoEditParam) {
        BizTaskInfo bizTaskInfo = this.queryEntity(bizTaskInfoEditParam.getId());
        BeanUtil.copyProperties(bizTaskInfoEditParam, bizTaskInfo);
        this.updateById(bizTaskInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskInfoIdParam> bizTaskInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskInfoIdParamList, BizTaskInfoIdParam::getId));
    }

    @Override
    public BizTaskInfo detail(BizTaskInfoIdParam bizTaskInfoIdParam) {
        return this.queryEntity(bizTaskInfoIdParam.getId());
    }

    @Override
    public BizTaskInfo queryEntity(String id) {
        BizTaskInfo bizTaskInfo = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskInfo)) {
            throw new CommonException("任务主表不存在，id值为：{}", id);
        }
        return bizTaskInfo;
    }
}
