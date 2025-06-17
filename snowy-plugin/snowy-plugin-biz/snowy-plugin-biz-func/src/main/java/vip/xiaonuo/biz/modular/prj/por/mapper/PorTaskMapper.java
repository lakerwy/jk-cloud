package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorTask;
import vip.xiaonuo.biz.modular.prj.por.vo.PorTaskVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
public interface PorTaskMapper extends BaseMapper<PorTask> {

    Page<PorTaskVo> getPageList(Page<PorTaskVo> page, @Param(Constants.WRAPPER) Wrapper<PorTask> queryWrapper);

    PorTaskVo getDetail(String id);


    List<PorTaskVo> getList(String prjId);
}
