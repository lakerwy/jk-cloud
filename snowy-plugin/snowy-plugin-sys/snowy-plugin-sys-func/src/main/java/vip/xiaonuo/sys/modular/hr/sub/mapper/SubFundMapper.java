package vip.xiaonuo.sys.modular.hr.sub.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFund;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubFundItemVo;

import java.util.List;

/**
 * 分公司-五险一金（流程）Mapper接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:12
 **/
public interface SubFundMapper extends BaseMapper<SubFund> {

    /**
     * 根据条件获取SubFund记录 【单表】
     * @param queryWrapper
     * @return
     */
    List<SubFund> getSubFundList(@Param(Constants.WRAPPER) Wrapper<SubFund> queryWrapper);

    /**
     * 根据条件，对SubFund记录分组统计，生成月度统计数据(五险一金) 【单表】
     * @param queryWrapper
     * @return
     */
    List<MonthVo> getMonthVoList(@Param(Constants.WRAPPER) Wrapper<SubFund> queryWrapper);

    /**
     * 根据条件获取SubFundItem记录【关联查询】
     * @param queryWrapper
     * @return
     */
    List<SubFundItemVo> getSubFundItemList(@Param(Constants.WRAPPER) Wrapper<SubFund> queryWrapper);
}
