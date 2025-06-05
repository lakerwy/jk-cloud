package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.vo.PorProjectVo;

/**
 * 项目产值台账 - 项目管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
public interface PorProjectMapper extends BaseMapper<PorProject> {

    Page<PorProjectVo> getPageList(Page<PorProjectVo> page, @Param(Constants.WRAPPER) Wrapper<PorProject> queryWrapper);

    PorProjectVo getDetail(String id);
}
