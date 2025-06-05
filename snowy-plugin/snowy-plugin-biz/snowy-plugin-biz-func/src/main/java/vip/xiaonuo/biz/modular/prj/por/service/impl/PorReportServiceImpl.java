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
import vip.xiaonuo.biz.modular.prj.por.vo.PorReportVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorReportMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorReportService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目周报|月报Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:44
 **/
@Service
@DS("slave_1")
public class PorReportServiceImpl extends ServiceImpl<PorReportMapper, PorReport> implements PorReportService {

    @Resource
    private PorReportMapper porReportMapper;

    @Override
    public Page<PorReportVo> page(PorReportPageParam porReportPageParam) {
        QueryWrapper<PorReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porReportPageParam.getPorProjectId())) {
            queryWrapper.eq("a.POR_PROJECT_ID", porReportPageParam.getPorProjectId());
        }
        if(ObjectUtil.isNotEmpty(porReportPageParam.getReportType())) {
            queryWrapper.eq("a.REPORT_TYPE", porReportPageParam.getReportType());
        }
        if(ObjectUtil.isNotEmpty(porReportPageParam.getReportLeaderName())) {
            queryWrapper.like("a.REPORT_LEADER_NAME", porReportPageParam.getReportLeaderName());
        }
        if(ObjectUtil.isNotEmpty(porReportPageParam.getStartReportFillTime()) && ObjectUtil.isNotEmpty(porReportPageParam.getEndReportFillTime())) {
            queryWrapper.between("a.REPORT_FILL_TIME", porReportPageParam.getStartReportFillTime(), porReportPageParam.getEndReportFillTime());
        }
        if(ObjectUtil.isNotEmpty(porReportPageParam.getReportOptName())) {
            queryWrapper.like("a.REPORT_OPT_NAME", porReportPageParam.getReportOptName());
        }
        if(ObjectUtil.isAllNotEmpty(porReportPageParam.getSortField(), porReportPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porReportPageParam.getSortOrder());
            queryWrapper.orderBy(true, porReportPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porReportPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porReportMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorReportAddParam porReportAddParam) {
        PorReport porReport = BeanUtil.toBean(porReportAddParam, PorReport.class);
        this.save(porReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorReportEditParam porReportEditParam) {
        PorReport porReport = this.queryEntity(porReportEditParam.getId());
        BeanUtil.copyProperties(porReportEditParam, porReport);
        this.updateById(porReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorReportIdParam> porReportIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porReportIdParamList, PorReportIdParam::getId));
    }

    @Override
    public PorReportVo detail(PorReportIdParam porReportIdParam) {
        return porReportMapper.getDetail(porReportIdParam.getId());
    }

    @Override
    public PorReport queryEntity(String id) {
        PorReport porReport = this.getById(id);
        if(ObjectUtil.isEmpty(porReport)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目周报|月报不存在，id值为：{}", id);
        }
        return porReport;
    }
}
