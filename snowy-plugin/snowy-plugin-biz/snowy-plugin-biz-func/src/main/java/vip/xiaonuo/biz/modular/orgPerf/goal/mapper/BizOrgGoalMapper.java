package vip.xiaonuo.biz.modular.orgPerf.goal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryListVo;

import java.util.List;

/**
 * 子公司及生产部门绩效考核-目标登记Mapper接口
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
public interface BizOrgGoalMapper extends BaseMapper<BizOrgGoal> {

    List<OrgSummaryListVo> orgSummaryList(@Param("param") OrgSummaryListParam orgSummaryListParam);

}
