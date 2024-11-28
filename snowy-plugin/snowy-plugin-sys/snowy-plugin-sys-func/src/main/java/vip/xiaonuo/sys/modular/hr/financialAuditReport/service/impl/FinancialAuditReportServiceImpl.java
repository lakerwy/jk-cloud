package vip.xiaonuo.sys.modular.hr.financialAuditReport.service.impl;

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
import vip.xiaonuo.sys.modular.hr.financialAuditReport.entity.FinancialAuditReport;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.mapper.FinancialAuditReportMapper;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportAddParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportEditParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportIdParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportPageParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.service.FinancialAuditReportService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 财务审计报告Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 14:51
 **/
@Service
public class FinancialAuditReportServiceImpl extends ServiceImpl<FinancialAuditReportMapper, FinancialAuditReport> implements FinancialAuditReportService {

    @Override
    public Page<FinancialAuditReport> page(FinancialAuditReportPageParam financialAuditReportPageParam) {
        QueryWrapper<FinancialAuditReport> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(financialAuditReportPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinancialAuditReport::getName,financialAuditReportPageParam.getSearchKey())
                    .or().like(FinancialAuditReport::getYear,financialAuditReportPageParam.getSearchKey())
                    .or().like(FinancialAuditReport::getAuditorCompany,financialAuditReportPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(financialAuditReportPageParam.getSortField(), financialAuditReportPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(financialAuditReportPageParam.getSortOrder());
            queryWrapper.orderBy(true, financialAuditReportPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(financialAuditReportPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinancialAuditReport::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinancialAuditReportAddParam financialAuditReportAddParam) {
        FinancialAuditReport financialAuditReport = BeanUtil.toBean(financialAuditReportAddParam, FinancialAuditReport.class);
        this.save(financialAuditReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinancialAuditReportEditParam financialAuditReportEditParam) {
        FinancialAuditReport financialAuditReport = this.queryEntity(financialAuditReportEditParam.getId());
        BeanUtil.copyProperties(financialAuditReportEditParam, financialAuditReport);
        this.updateById(financialAuditReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinancialAuditReportIdParam> financialAuditReportIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(financialAuditReportIdParamList, FinancialAuditReportIdParam::getId));
    }

    @Override
    public FinancialAuditReport detail(FinancialAuditReportIdParam financialAuditReportIdParam) {
        return this.queryEntity(financialAuditReportIdParam.getId());
    }

    @Override
    public FinancialAuditReport queryEntity(String id) {
        FinancialAuditReport financialAuditReport = this.getById(id);
        if(ObjectUtil.isEmpty(financialAuditReport)) {
            throw new CommonException("财务审计报告不存在，id值为：{}", id);
        }
        return financialAuditReport;
    }
}
