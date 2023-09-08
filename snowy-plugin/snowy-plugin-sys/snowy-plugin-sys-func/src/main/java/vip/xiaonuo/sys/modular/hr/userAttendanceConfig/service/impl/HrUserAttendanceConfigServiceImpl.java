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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.impl;

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
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.mapper.HrUserAttendanceConfigMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;

import java.util.List;

/**
 * 打卡班次配置表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Service
public class HrUserAttendanceConfigServiceImpl extends ServiceImpl<HrUserAttendanceConfigMapper, HrUserAttendanceConfig> implements HrUserAttendanceConfigService {

    @Override
    public Page<HrUserAttendanceConfig> page(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam) {
        QueryWrapper<HrUserAttendanceConfig> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigPageParam.getTenantId())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfig::getTenantId, hrUserAttendanceConfigPageParam.getTenantId());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceConfigPageParam.getSortField(), hrUserAttendanceConfigPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceConfigPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceConfigPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceConfigPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceConfig::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceConfigAddParam hrUserAttendanceConfigAddParam) {
        HrUserAttendanceConfig hrUserAttendanceConfig = BeanUtil.toBean(hrUserAttendanceConfigAddParam, HrUserAttendanceConfig.class);
        this.save(hrUserAttendanceConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam) {
        HrUserAttendanceConfig hrUserAttendanceConfig = this.queryEntity(hrUserAttendanceConfigEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceConfigEditParam, hrUserAttendanceConfig);
        this.updateById(hrUserAttendanceConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceConfigIdParam> hrUserAttendanceConfigIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceConfigIdParamList, HrUserAttendanceConfigIdParam::getId));
    }

    @Override
    public HrUserAttendanceConfig detail(HrUserAttendanceConfigIdParam hrUserAttendanceConfigIdParam) {
        return this.queryEntity(hrUserAttendanceConfigIdParam.getId());
    }

    @Override
    public HrUserAttendanceConfig queryEntity(String id) {
        HrUserAttendanceConfig hrUserAttendanceConfig = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceConfig)) {
            throw new CommonException("打卡班次配置表不存在，id值为：{}", id);
        }
        return hrUserAttendanceConfig;
    }
}
