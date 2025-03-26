package vip.xiaonuo.biz.modular.prj.exp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFee;
import vip.xiaonuo.biz.modular.prj.exp.vo.ExpMonthTotal;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单Mapper接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:14
 **/
public interface BizFormFeeMapper extends BaseMapper<BizFormFee> {

    /**
     * 费用报销 - 月度统计 - 【成本一览表数据】
     * @param queryWrapper
     * @return
     */
    List<ExpMonthTotal> getFeeCostMonthTotal(@Param(Constants.WRAPPER) Wrapper<BizFormFee> queryWrapper);


    /**
     * 不区分 报销类型 统计在一起
     * @param queryWrapper
     * @return
     */
    List<ExpMonthTotal> getMonthTotal(@Param(Constants.WRAPPER) Wrapper<BizFormFee> queryWrapper);

}
