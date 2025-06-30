package vip.xiaonuo.biz.modular.orgPerf.goal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoal;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryListVo;

import java.util.List;

/**
 * 职能部门绩效考核-目标登记Mapper接口
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
public interface BizFunDeptGoalMapper extends BaseMapper<BizFunDeptGoal> {

    List<FunDeptSummaryListVo> funDeptList(@Param("param") FunDeptSummaryListParam param);

}
