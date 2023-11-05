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
package vip.xiaonuo.sys.modular.hr.conference.config.service.impl;

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
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.mapper.ConferenceConfigMapper;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;

import java.util.List;

/**
 * 会议预约-会议室数据Service接口实现类
 *
 * @author ljy
 * @date  2023/11/05 23:12
 **/
@Service
public class ConferenceConfigServiceImpl extends ServiceImpl<ConferenceConfigMapper, ConferenceConfig> implements ConferenceConfigService {

    @Override
    public Page<ConferenceConfig> page(ConferenceConfigPageParam conferenceConfigPageParam) {
        QueryWrapper<ConferenceConfig> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceConfigPageParam.getStatus())) {
            queryWrapper.lambda().eq(ConferenceConfig::getStatus, conferenceConfigPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceConfigPageParam.getSortField(), conferenceConfigPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceConfigPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceConfigPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceConfigPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceConfig::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceConfigAddParam conferenceConfigAddParam) {
        ConferenceConfig conferenceConfig = BeanUtil.toBean(conferenceConfigAddParam, ConferenceConfig.class);
        this.save(conferenceConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceConfigEditParam conferenceConfigEditParam) {
        ConferenceConfig conferenceConfig = this.queryEntity(conferenceConfigEditParam.getId());
        BeanUtil.copyProperties(conferenceConfigEditParam, conferenceConfig);
        this.updateById(conferenceConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceConfigIdParam> conferenceConfigIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceConfigIdParamList, ConferenceConfigIdParam::getId));
    }

    @Override
    public ConferenceConfig detail(ConferenceConfigIdParam conferenceConfigIdParam) {
        return this.queryEntity(conferenceConfigIdParam.getId());
    }

    @Override
    public ConferenceConfig queryEntity(String id) {
        ConferenceConfig conferenceConfig = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceConfig)) {
            throw new CommonException("会议预约-会议室数据不存在，id值为：{}", id);
        }
        return conferenceConfig;
    }
}
