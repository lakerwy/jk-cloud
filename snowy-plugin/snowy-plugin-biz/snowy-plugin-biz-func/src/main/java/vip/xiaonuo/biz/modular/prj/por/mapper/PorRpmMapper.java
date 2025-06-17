package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorRpm;
import vip.xiaonuo.biz.modular.prj.por.vo.PorRpmVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
public interface PorRpmMapper extends BaseMapper<PorRpm> {

    Page<PorRpmVo> getPageList(Page<PorRpmVo> page, @Param(Constants.WRAPPER) Wrapper<PorRpm> queryWrapper);

    PorRpmVo getDetail(String id);

    /**
     * 根据立项id,获取责任上缴
     * @param bizProjectId
     * @return
     */
    List<PorRpmVo> getRpmListByPrjId(String bizProjectId);
}
