package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFee;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSiteFeeVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
public interface PorSiteFeeMapper extends BaseMapper<PorSiteFee> {

    Page<PorSiteFeeVo> getPageList(Page<PorSiteFeeVo> page, @Param(Constants.WRAPPER) Wrapper<PorSiteFee> queryWrapper);

    PorSiteFeeVo getDetail(String id);

    /**
     * 根据立项id,获取现场成本费
     * @param bizProjectId
     * @return
     */
    List<PorSiteFeeVo> getSiteListByPrjId(String bizProjectId);

}
