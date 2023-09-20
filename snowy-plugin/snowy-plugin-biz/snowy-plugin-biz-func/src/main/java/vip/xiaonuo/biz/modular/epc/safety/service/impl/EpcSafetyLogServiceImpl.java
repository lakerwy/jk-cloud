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
package vip.xiaonuo.biz.modular.epc.safety.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcSafetyLog;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcSafetyLogMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcSafetyLogService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-安全日志Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:58
 **/
@Service
public class EpcSafetyLogServiceImpl extends ServiceImpl<EpcSafetyLogMapper, EpcSafetyLog> implements EpcSafetyLogService {

    @Override
    public Page<EpcSafetyLog> page(EpcSafetyLogPageParam epcSafetyLogPageParam) {
        QueryWrapper<EpcSafetyLog> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcSafetyLogPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcSafetyLog::getTenantId, epcSafetyLogPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyLogPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcSafetyLog::getProjectId, epcSafetyLogPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyLogPageParam.getStartLogTime()) && ObjectUtil.isNotEmpty(epcSafetyLogPageParam.getEndLogTime())) {
            queryWrapper.lambda().between(EpcSafetyLog::getLogTime, epcSafetyLogPageParam.getStartLogTime(), epcSafetyLogPageParam.getEndLogTime());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyLogPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(EpcSafetyLog::getWeekDay,epcSafetyLogPageParam.getSearchKey())
                    .or().like(EpcSafetyLog::getWeather,epcSafetyLogPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(epcSafetyLogPageParam.getSortField(), epcSafetyLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcSafetyLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcSafetyLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcSafetyLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcSafetyLog::getLogTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcSafetyLogAddParam epcSafetyLogAddParam) {
        EpcSafetyLog epcSafetyLog = BeanUtil.toBean(epcSafetyLogAddParam, EpcSafetyLog.class);
        this.save(epcSafetyLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcSafetyLogEditParam epcSafetyLogEditParam) {
        EpcSafetyLog epcSafetyLog = this.queryEntity(epcSafetyLogEditParam.getId());
        BeanUtil.copyProperties(epcSafetyLogEditParam, epcSafetyLog);
        this.updateById(epcSafetyLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcSafetyLogIdParam> epcSafetyLogIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcSafetyLogIdParamList, EpcSafetyLogIdParam::getId));
    }

    @Override
    public EpcSafetyLog detail(EpcSafetyLogIdParam epcSafetyLogIdParam) {
        return this.queryEntity(epcSafetyLogIdParam.getId());
    }

    @Override
    public EpcSafetyLog queryEntity(String id) {
        EpcSafetyLog epcSafetyLog = this.getById(id);
        if(ObjectUtil.isEmpty(epcSafetyLog)) {
            throw new CommonException("EPC-安全日志不存在，id值为：{}", id);
        }
        return epcSafetyLog;
    }
}
