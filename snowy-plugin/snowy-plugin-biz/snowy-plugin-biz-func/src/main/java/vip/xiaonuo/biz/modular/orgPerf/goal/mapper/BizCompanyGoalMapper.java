package vip.xiaonuo.biz.modular.orgPerf.goal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;

import java.util.List;

/**
 * 公司绩效考核-目标登记Mapper接口
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
public interface BizCompanyGoalMapper extends BaseMapper<BizCompanyGoal> {

    List<CompanySummaryListVo> companySummaryList(@Param("companyId") String companyId);

}
