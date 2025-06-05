package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSubSettle;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSubSettleVo;

/**
 * 项目产值台账 - 项目管理 - 项目分包结算记录Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:53
 **/
public interface PorSubSettleMapper extends BaseMapper<PorSubSettle> {

    Page<PorSubSettleVo> getPageList(Page<PorSubSettleVo> page, @Param(Constants.WRAPPER) Wrapper<PorSubSettle> queryWrapper);

    PorSubSettleVo getDetail(String id);

}
