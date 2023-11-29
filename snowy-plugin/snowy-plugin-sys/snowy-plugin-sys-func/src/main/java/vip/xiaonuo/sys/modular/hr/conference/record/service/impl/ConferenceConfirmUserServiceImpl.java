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
package vip.xiaonuo.sys.modular.hr.conference.record.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceConfirmUser;
import vip.xiaonuo.sys.modular.hr.conference.record.mapper.ConferenceConfirmUserMapper;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceConfirmUserAddParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceConfirmUserEditParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceConfirmUserIdParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceConfirmPageParam;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceConfirmUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会议记录确认表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/28 22:28
 **/
@Service
public class ConferenceConfirmUserServiceImpl extends ServiceImpl<ConferenceConfirmUserMapper, ConferenceConfirmUser> implements ConferenceConfirmUserService {

    @Override
    public Page<ConferenceConfirmUser> page(ConferenceConfirmPageParam conferenceConfirmPageParam) {
        QueryWrapper<ConferenceConfirmUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceConfirmPageParam.getRecordId())) {
            queryWrapper.lambda().eq(ConferenceConfirmUser::getRecordId, conferenceConfirmPageParam.getRecordId());
        }
        if(ObjectUtil.isNotEmpty(conferenceConfirmPageParam.getConfirmUserId())) {
            queryWrapper.lambda().eq(ConferenceConfirmUser::getConfirmUserId, conferenceConfirmPageParam.getConfirmUserId());
        }
        if(ObjectUtil.isNotEmpty(conferenceConfirmPageParam.getConfirmUserName())) {
            queryWrapper.lambda().like(ConferenceConfirmUser::getConfirmUserName, conferenceConfirmPageParam.getConfirmUserName());
        }
        if(ObjectUtil.isNotEmpty(conferenceConfirmPageParam.getStatus())) {
            queryWrapper.lambda().eq(ConferenceConfirmUser::getStatus, conferenceConfirmPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceConfirmPageParam.getSortField(), conferenceConfirmPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceConfirmPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceConfirmPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceConfirmPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceConfirmUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceConfirmUserAddParam conferenceConfirmUserAddParam) {
        ConferenceConfirmUser conferenceConfirmUser = BeanUtil.toBean(conferenceConfirmUserAddParam, ConferenceConfirmUser.class);
        this.save(conferenceConfirmUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceConfirmUserEditParam conferenceConfirmUserEditParam) {
        ConferenceConfirmUser conferenceConfirmUser = this.queryEntity(conferenceConfirmUserEditParam.getId());
        BeanUtil.copyProperties(conferenceConfirmUserEditParam, conferenceConfirmUser);
        this.updateById(conferenceConfirmUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceConfirmUserIdParam> conferenceConfirmUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceConfirmUserIdParamList, ConferenceConfirmUserIdParam::getId));
    }

    @Override
    public ConferenceConfirmUser detail(ConferenceConfirmUserIdParam conferenceConfirmUserIdParam) {
        return this.queryEntity(conferenceConfirmUserIdParam.getId());
    }

    @Override
    public ConferenceConfirmUser queryEntity(String id) {
        ConferenceConfirmUser conferenceConfirmUser = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceConfirmUser)) {
            throw new CommonException("会议记录确认表不存在，id值为：{}", id);
        }
        return conferenceConfirmUser;
    }
}
