package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorWarn;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorWarnMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorWarnService;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:41
 **/
@Service
@DS("slave_1")
public class PorWarnServiceImpl extends ServiceImpl<PorWarnMapper, PorWarn> implements PorWarnService {

    @Override
    public Page<PorWarn> page(PorWarnPageParam porWarnPageParam) {
        QueryWrapper<PorWarn> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(porWarnPageParam.getPorProjectId())) {
            queryWrapper.lambda().eq(PorWarn::getPorProjectId, porWarnPageParam.getPorProjectId());
        }
        if(ObjectUtil.isNotEmpty(porWarnPageParam.getWarnLevel())) {
            queryWrapper.lambda().eq(PorWarn::getWarnLevel, porWarnPageParam.getWarnLevel());
        }
        if(ObjectUtil.isNotEmpty(porWarnPageParam.getStartWarnTime()) && ObjectUtil.isNotEmpty(porWarnPageParam.getEndWarnTime())) {
            queryWrapper.lambda().between(PorWarn::getWarnTime, porWarnPageParam.getStartWarnTime(), porWarnPageParam.getEndWarnTime());
        }
        if(ObjectUtil.isNotEmpty(porWarnPageParam.getWarnType())) {
            queryWrapper.lambda().eq(PorWarn::getWarnType, porWarnPageParam.getWarnType());
        }
        if(ObjectUtil.isAllNotEmpty(porWarnPageParam.getSortField(), porWarnPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porWarnPageParam.getSortOrder());
            queryWrapper.orderBy(true, porWarnPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porWarnPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PorWarn::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorWarnAddParam porWarnAddParam) {
        PorWarn porWarn = BeanUtil.toBean(porWarnAddParam, PorWarn.class);
        this.save(porWarn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorWarnEditParam porWarnEditParam) {
        PorWarn porWarn = this.queryEntity(porWarnEditParam.getId());
        BeanUtil.copyProperties(porWarnEditParam, porWarn);
        this.updateById(porWarn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorWarnIdParam> porWarnIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porWarnIdParamList, PorWarnIdParam::getId));
    }

    @Override
    public PorWarn detail(PorWarnIdParam porWarnIdParam) {
        return this.queryEntity(porWarnIdParam.getId());
    }

    @Override
    public PorWarn queryEntity(String id) {
        PorWarn porWarn = this.getById(id);
        if(ObjectUtil.isEmpty(porWarn)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目预警记录不存在，id值为：{}", id);
        }
        return porWarn;
    }
}
