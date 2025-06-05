package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;
import vip.xiaonuo.biz.modular.prj.por.vo.PorReportVo;

/**
 * 项目产值台账 - 项目管理 - 项目周报|月报Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:44
 **/
public interface PorReportMapper extends BaseMapper<PorReport> {

    Page<PorReportVo> getPageList(Page<PorReportVo> page, @Param(Constants.WRAPPER) Wrapper<PorReport> queryWrapper);

    PorReportVo getDetail(String id);

}
