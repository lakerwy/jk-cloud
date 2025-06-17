package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSign;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSignVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
public interface PorSignMapper extends BaseMapper<PorSign> {


    Page<PorSignVo> getPageList(Page<PorSignVo> page, @Param(Constants.WRAPPER) Wrapper<PorSign> queryWrapper);

    PorSignVo getDetail(String id);

    /**
     * 根据立项id,获取签字费
     * @param bizProjectId
     * @return
     */
    List<PorSignVo> getSignListByPrjId(String bizProjectId);
}
