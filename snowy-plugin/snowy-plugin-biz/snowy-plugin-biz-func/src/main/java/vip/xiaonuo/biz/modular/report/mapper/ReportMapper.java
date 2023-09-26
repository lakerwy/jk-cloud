package vip.xiaonuo.biz.modular.report.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.report.result.HTBillResult;
import vip.xiaonuo.biz.modular.report.result.HTFilingPageResult;
import vip.xiaonuo.biz.modular.report.result.HTThreeMonthUnFiling;
import vip.xiaonuo.biz.modular.report.result.HTYearFilingRate;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/10 9:20
 */
public interface ReportMapper extends BaseMapper<Hetong> {

    /** 分公司近一年的合同归档率统计 **/
    List<HTYearFilingRate> getHtYearFilingRate(DateTime startDate, DateTime endDate,String partnerType);

    /** 分公司合同超过3个月未归档统计 **/
    List<HTThreeMonthUnFiling> getHtThreeMonthUnFiling(DateTime startDate, DateTime endDate,String partnerType);

    /** 根据合同类型、统计时间段、报表类型 获取收入合同清单报表数据 */
    IPage<HTBillResult> getBillPageList(Page<HTBillResult> page, @Param(Constants.WRAPPER) Wrapper<Hetong> queryWrapper);

    /** 分页获取合同信息+所属责任单位合同归档率+超过3个月未归档统计数据 */
    IPage<HTFilingPageResult> getHtFilingPageList(Page<HTFilingPageResult> page, @Param(Constants.WRAPPER) Wrapper<Hetong> queryWrapper);
}
