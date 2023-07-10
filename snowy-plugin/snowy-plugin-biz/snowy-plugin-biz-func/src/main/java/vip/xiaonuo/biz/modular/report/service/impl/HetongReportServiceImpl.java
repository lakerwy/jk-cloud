package vip.xiaonuo.biz.modular.report.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.crm.bidopen.entity.Bidopen;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.report.mapper.ReportMapper;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;
import vip.xiaonuo.biz.modular.report.service.HetongReportService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/10 9:11
 */
@DS("slave_1")
@Service
public class HetongReportServiceImpl extends ServiceImpl<ReportMapper, Hetong> implements HetongReportService {

    @Resource
    private HetongService hetongService;
    @Override
    public HetongReportResult listReport(HetongReportParam hetongReportParam) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hetongReportParam.getYear())) {
            queryWrapper.lambda().apply("YEAR(CREATE_TIME) = {0}", hetongReportParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(hetongReportParam.getStartDate()) && ObjectUtil.isNotEmpty(hetongReportParam.getEndDate())) {
            queryWrapper.lambda().between(Hetong::getCreateTime, hetongReportParam.getStartDate(), hetongReportParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(hetongReportParam.getType())) {
            queryWrapper.lambda().like(Hetong::getPartnerType, hetongReportParam.getType());
        }
        List<Hetong> list = hetongService.list(queryWrapper);
        return null;
    }
}
