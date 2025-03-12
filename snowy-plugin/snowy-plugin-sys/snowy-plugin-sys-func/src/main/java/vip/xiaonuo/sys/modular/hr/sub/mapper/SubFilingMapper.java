package vip.xiaonuo.sys.modular.hr.sub.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFiling;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubFilingVo;

import java.util.List;

/**
 * 分包单位备案Mapper接口
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
public interface SubFilingMapper extends BaseMapper<SubFiling> {

    Page<SubFilingVo> getSubFilingPage(Page<SubFilingVo> page, @Param(Constants.WRAPPER) Wrapper<SubFiling> queryWrapper);

    List<SubFilingVo> getSubFilingList(@Param(Constants.WRAPPER) QueryWrapper<SubFiling> queryWrapper);
}
