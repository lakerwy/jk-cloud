package vip.xiaonuo.sys.modular.hr.sub.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubGzItemVo;

import java.util.List;

/**
 * 分公司-工资发放（流程）Mapper接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
public interface SubGzMapper extends BaseMapper<SubGz> {

    /**
     * 根据条件获取SubGz记录 【单表】
     * @param queryWrapper
     * @return
     */
    List<SubGz> getSubGzList(@Param(Constants.WRAPPER) Wrapper<SubGz> queryWrapper);

    /**
     * 根据条件，对SubGz记录分组统计，生成月度统计数据(工资部分 - 少五险|公积金个人部分) 【单表】
     * @param queryWrapper
     * @return
     */
    List<MonthVo> getMonthVoList(@Param(Constants.WRAPPER) Wrapper<SubGz> queryWrapper);

    /**
     * 根据条件获取SubGzItem记录【关联查询】
     * @param queryWrapper
     * @return
     */
    List<SubGzItemVo> getSubGzItemList(@Param(Constants.WRAPPER) Wrapper<SubGz> queryWrapper);




}
