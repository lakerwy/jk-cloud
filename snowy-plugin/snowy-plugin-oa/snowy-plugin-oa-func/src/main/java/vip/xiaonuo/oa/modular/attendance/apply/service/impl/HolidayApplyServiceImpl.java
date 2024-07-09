package vip.xiaonuo.oa.modular.attendance.apply.service.impl;

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
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayApply;
import vip.xiaonuo.oa.modular.attendance.apply.mapper.HolidayApplyMapper;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyPageParam;
import vip.xiaonuo.oa.modular.attendance.apply.service.HolidayApplyService;

import java.util.List;

/**
 * 请假管理表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/09 12:00
 **/
@Service
public class HolidayApplyServiceImpl extends ServiceImpl<HolidayApplyMapper, HolidayApply> implements HolidayApplyService {

    @Override
    public Page<HolidayApply> page(HolidayApplyPageParam holidayApplyPageParam) {
        QueryWrapper<HolidayApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(holidayApplyPageParam.getSortField(), holidayApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(holidayApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, holidayApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(holidayApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HolidayApply::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HolidayApplyAddParam holidayApplyAddParam) {
        HolidayApply holidayApply = BeanUtil.toBean(holidayApplyAddParam, HolidayApply.class);
        this.save(holidayApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HolidayApplyEditParam holidayApplyEditParam) {
        HolidayApply holidayApply = this.queryEntity(holidayApplyEditParam.getId());
        BeanUtil.copyProperties(holidayApplyEditParam, holidayApply);
        this.updateById(holidayApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HolidayApplyIdParam> holidayApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(holidayApplyIdParamList, HolidayApplyIdParam::getId));
    }

    @Override
    public HolidayApply detail(HolidayApplyIdParam holidayApplyIdParam) {
        return this.queryEntity(holidayApplyIdParam.getId());
    }

    @Override
    public HolidayApply queryEntity(String id) {
        HolidayApply holidayApply = this.getById(id);
        if(ObjectUtil.isEmpty(holidayApply)) {
            throw new CommonException("请假管理表不存在，id值为：{}", id);
        }
        return holidayApply;
    }
}
