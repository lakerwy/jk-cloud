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
package vip.xiaonuo.sys.modular.person.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonSnUtil;
import vip.xiaonuo.sys.modular.person.entity.UserSurveyCollect;
import vip.xiaonuo.sys.modular.person.enums.ActionTypeEnum;
import vip.xiaonuo.sys.modular.person.enums.SurveyStatusEnum;
import vip.xiaonuo.sys.modular.person.mapper.UserSurveyCollectMapper;
import vip.xiaonuo.sys.modular.person.param.*;
import vip.xiaonuo.sys.modular.person.service.UserSurveyCollectService;

import java.util.Date;
import java.util.List;

/**
 * 用户问卷收集-问题|意见Service接口实现类
 *
 * @author yong.ye
 * @date  2024/01/25 10:44
 **/
@Service
public class UserSurveyCollectServiceImpl extends ServiceImpl<UserSurveyCollectMapper, UserSurveyCollect> implements UserSurveyCollectService {

    @Override
    public Page<UserSurveyCollect> page(UserSurveyCollectPageParam userSurveyCollectPageParam) {
        QueryWrapper<UserSurveyCollect> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getType())) {
            queryWrapper.lambda().eq(UserSurveyCollect::getType, userSurveyCollectPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getProType())) {
            queryWrapper.lambda().eq(UserSurveyCollect::getProType, userSurveyCollectPageParam.getProType());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getTitle())) {
            queryWrapper.lambda().like(UserSurveyCollect::getTitle, userSurveyCollectPageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getSn())) {
            queryWrapper.lambda().like(UserSurveyCollect::getSn, userSurveyCollectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getIsAnony())) {
            queryWrapper.lambda().eq(UserSurveyCollect::getIsAnony, userSurveyCollectPageParam.getIsAnony());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getStartSubmitTime()) && ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getEndSubmitTime())) {
            queryWrapper.lambda().between(UserSurveyCollect::getSubmitTime, userSurveyCollectPageParam.getStartSubmitTime(), userSurveyCollectPageParam.getEndSubmitTime());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getStartReplyTime()) && ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getEndReplyTime())) {
            queryWrapper.lambda().between(UserSurveyCollect::getReplyTime, userSurveyCollectPageParam.getStartReplyTime(), userSurveyCollectPageParam.getEndReplyTime());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getStatus())) {
            queryWrapper.lambda().eq(UserSurveyCollect::getStatus, userSurveyCollectPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getSubmitName())){
            queryWrapper.lambda().like(UserSurveyCollect::getSubmitName,userSurveyCollectPageParam.getSubmitName());
        }
        if(ObjectUtil.isNotEmpty(userSurveyCollectPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(UserSurveyCollect::getTitle,userSurveyCollectPageParam.getSearchKey())
                    .or().like(UserSurveyCollect::getSn,userSurveyCollectPageParam.getSearchKey())
                    .or().like(UserSurveyCollect::getSubmitName,userSurveyCollectPageParam.getSearchKey())
                    .or().like(UserSurveyCollect::getReplyName,userSurveyCollectPageParam.getSearchKey()));
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(UserSurveyCollect::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(UserSurveyCollect::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isAllNotEmpty(userSurveyCollectPageParam.getSortField(), userSurveyCollectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userSurveyCollectPageParam.getSortOrder());
            queryWrapper.orderBy(true, userSurveyCollectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userSurveyCollectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserSurveyCollect::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserSurveyCollectAddParam userSurveyCollectAddParam) {
        UserSurveyCollect userSurveyCollect = BeanUtil.toBean(userSurveyCollectAddParam, UserSurveyCollect.class);
        userSurveyCollect.setSubmitName(StpLoginUserUtil.getLoginUser().getName());
        userSurveyCollect.setSubmitTime(new Date());
        userSurveyCollect.setStatus(SurveyStatusEnum.NO_REPLY.getValue());
        //处理Sn编号
        UserSurveyCollect tmp = this.getOne(new QueryWrapper<UserSurveyCollect>().lambda().orderByDesc(UserSurveyCollect::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(userSurveyCollectAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        userSurveyCollect.setSn(newSn);
        this.save(userSurveyCollect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserSurveyCollectEditParam userSurveyCollectEditParam) {
        UserSurveyCollect userSurveyCollect = this.queryEntity(userSurveyCollectEditParam.getId());
        BeanUtil.copyProperties(userSurveyCollectEditParam, userSurveyCollect);
        this.updateById(userSurveyCollect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserSurveyCollectIdParam> userSurveyCollectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(userSurveyCollectIdParamList, UserSurveyCollectIdParam::getId));
    }

    @Override
    public UserSurveyCollect detail(UserSurveyCollectIdParam userSurveyCollectIdParam) {
        return this.queryEntity(userSurveyCollectIdParam.getId());
    }

    @Override
    public UserSurveyCollect queryEntity(String id) {
        UserSurveyCollect userSurveyCollect = this.getById(id);
        if(ObjectUtil.isEmpty(userSurveyCollect)) {
            throw new CommonException("用户问卷收集-问题|意见不存在，id值为：{}", id);
        }
        return userSurveyCollect;
    }

    @Override
    public void doAction(ActionParam actionParam) {
        UserSurveyCollect userSurveyCollect = this.queryEntity(actionParam.getId());
        if(ActionTypeEnum.DELETE.getValue().equals(actionParam.getActionType())){
            this.removeById(userSurveyCollect);
            return;
        }
        userSurveyCollect.setReplyTime(new Date());
        userSurveyCollect.setReviewContent(actionParam.getReviewContent());
        userSurveyCollect.setMemo(actionParam.getMemo());
        userSurveyCollect.setReplyName(StpLoginUserUtil.getLoginUser().getName());
        if(ActionTypeEnum.REPLY.getValue().equals(actionParam.getActionType())){
            userSurveyCollect.setStatus(SurveyStatusEnum.HAVE_REPLY.getValue());
        }else if(ActionTypeEnum.CLOSE.getValue().equals(actionParam.getActionType())){
            userSurveyCollect.setStatus(SurveyStatusEnum.HAVE_CLOSE.getValue());
        }
        this.updateById(userSurveyCollect);
    }
}
