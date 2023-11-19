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
package vip.xiaonuo.sys.modular.hr.atdData.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.atdData.entity.OriginalAtdData;
import vip.xiaonuo.sys.modular.hr.atdData.mapper.OriginalAtdDataMapper;
import vip.xiaonuo.sys.modular.hr.atdData.param.OriginalAtdDataAddParam;
import vip.xiaonuo.sys.modular.hr.atdData.param.OriginalAtdDataEditParam;
import vip.xiaonuo.sys.modular.hr.atdData.param.OriginalAtdDataIdParam;
import vip.xiaonuo.sys.modular.hr.atdData.param.OriginalAtdDataPageParam;
import vip.xiaonuo.sys.modular.hr.atdData.service.OriginalAtdDataService;


import java.util.List;

/**
 * 统计-员工考勤原始记录统计表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/17 11:36
 **/
@Service
public class OriginalAtdDataServiceImpl extends ServiceImpl<OriginalAtdDataMapper, OriginalAtdData> implements OriginalAtdDataService {

    @Override
    public Page<OriginalAtdData> page(OriginalAtdDataPageParam originalAtdDataPageParam) {
        QueryWrapper<OriginalAtdData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(originalAtdDataPageParam.getAtdId())) {
            queryWrapper.lambda().eq(OriginalAtdData::getAtdId, originalAtdDataPageParam.getAtdId());
        }
        if(ObjectUtil.isNotEmpty(originalAtdDataPageParam.getUserId())) {
            queryWrapper.lambda().eq(OriginalAtdData::getUserId, originalAtdDataPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(originalAtdDataPageParam.getAtdGroupId())) {
            queryWrapper.lambda().eq(OriginalAtdData::getAtdGroupId, originalAtdDataPageParam.getAtdGroupId());
        }
        if(ObjectUtil.isNotEmpty(originalAtdDataPageParam.getOrgId())) {
            queryWrapper.lambda().eq(OriginalAtdData::getOrgId, originalAtdDataPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(originalAtdDataPageParam.getAbnormalFlag())) {
            queryWrapper.lambda().eq(OriginalAtdData::getAbnormalFlag, originalAtdDataPageParam.getAbnormalFlag());
        }
        if(StringUtils.isNotEmpty(originalAtdDataPageParam.getStartTime()) && StringUtils.isNotEmpty(originalAtdDataPageParam.getEndTime())){
            queryWrapper.lambda().between(OriginalAtdData::getCheckTime,originalAtdDataPageParam.getStartTime(),originalAtdDataPageParam.getEndTime());
        }
        if(CollectionUtil.isNotEmpty(originalAtdDataPageParam.getUserIds())){
            queryWrapper.lambda().in(OriginalAtdData::getUserId,originalAtdDataPageParam.getUserIds());
        }
        if(CollectionUtil.isNotEmpty(originalAtdDataPageParam.getAttendanceGroupIds())){
            queryWrapper.lambda().in(OriginalAtdData::getAtdGroupId,originalAtdDataPageParam.getAttendanceGroupIds());
        }
        if(ObjectUtil.isAllNotEmpty(originalAtdDataPageParam.getSortField(), originalAtdDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(originalAtdDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, originalAtdDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(originalAtdDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(OriginalAtdData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OriginalAtdDataAddParam originalAtdDataAddParam) {
        OriginalAtdData originalAtdData = BeanUtil.toBean(originalAtdDataAddParam, OriginalAtdData.class);
        this.save(originalAtdData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OriginalAtdDataEditParam originalAtdDataEditParam) {
        OriginalAtdData originalAtdData = this.queryEntity(originalAtdDataEditParam.getId());
        BeanUtil.copyProperties(originalAtdDataEditParam, originalAtdData);
        this.updateById(originalAtdData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OriginalAtdDataIdParam> originalAtdDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(originalAtdDataIdParamList, OriginalAtdDataIdParam::getId));
    }

    @Override
    public OriginalAtdData detail(OriginalAtdDataIdParam originalAtdDataIdParam) {
        return this.queryEntity(originalAtdDataIdParam.getId());
    }

    @Override
    public OriginalAtdData queryEntity(String id) {
        OriginalAtdData originalAtdData = this.getById(id);
        if(ObjectUtil.isEmpty(originalAtdData)) {
            throw new CommonException("统计-员工考勤原始记录统计表不存在，id值为：{}", id);
        }
        return originalAtdData;
    }
}
