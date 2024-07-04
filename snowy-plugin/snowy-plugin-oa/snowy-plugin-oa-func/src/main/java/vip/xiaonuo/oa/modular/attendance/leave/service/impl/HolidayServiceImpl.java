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
package vip.xiaonuo.oa.modular.attendance.leave.service.impl;

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
import vip.xiaonuo.oa.modular.attendance.leave.entity.Holiday;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayPageParam;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Holiday;
import vip.xiaonuo.oa.modular.attendance.leave.mapper.HolidayMapper;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.HolidayPageParam;
import vip.xiaonuo.oa.modular.attendance.leave.service.HolidayService;

import java.util.List;

/**
 * 公司员工假期数据表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/24 17:33
 **/
@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    @Override
    public Page<Holiday> page(HolidayPageParam holidayPageParam) {
        QueryWrapper<Holiday> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(holidayPageParam.getSortField(), holidayPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(holidayPageParam.getSortOrder());
            queryWrapper.orderBy(true, holidayPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(holidayPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Holiday::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HolidayAddParam holidayAddParam) {
        Holiday holiday = BeanUtil.toBean(holidayAddParam, Holiday.class);
        this.save(holiday);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HolidayEditParam holidayEditParam) {
        Holiday holiday = this.queryEntity(holidayEditParam.getId());
        BeanUtil.copyProperties(holidayEditParam, holiday);
        this.updateById(holiday);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HolidayIdParam> holidayIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(holidayIdParamList, HolidayIdParam::getId));
    }

    @Override
    public Holiday detail(HolidayIdParam holidayIdParam) {
        return this.queryEntity(holidayIdParam.getId());
    }

    @Override
    public Holiday queryEntity(String id) {
        Holiday holiday = this.getById(id);
        if(ObjectUtil.isEmpty(holiday)) {
            throw new CommonException("公司员工假期数据表不存在，id值为：{}", id);
        }
        return holiday;
    }
}
