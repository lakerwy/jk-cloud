package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOwnerSettle;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOwnerSettleVo;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
public interface PorOwnerSettleMapper extends BaseMapper<PorOwnerSettle> {

    Page<PorOwnerSettleVo> getPageList(Page<PorOwnerSettleVo> page, @Param(Constants.WRAPPER) Wrapper<PorOwnerSettle> queryWrapper);

    PorOwnerSettleVo getDetail(String id);

}
