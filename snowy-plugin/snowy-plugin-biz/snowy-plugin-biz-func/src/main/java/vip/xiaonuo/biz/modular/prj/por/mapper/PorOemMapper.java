package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOem;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOemVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 经营费管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
public interface PorOemMapper extends BaseMapper<PorOem> {

    Page<PorOemVo> getPageList(Page<PorOemVo> page, @Param(Constants.WRAPPER) Wrapper<PorOem> queryWrapper);

    PorOemVo getDetail(String id);

    /**
     * 根据立项id,获取经营费
     * @param bizProjectId
     * @return
     */
    List<PorOemVo> getOemListByPrjId(String bizProjectId);
}
