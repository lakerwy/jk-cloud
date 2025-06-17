package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PorReportVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目周报|月报Service接口
 *
 * @author yy
 * @date  2025/06/03 09:44
 **/
public interface PorReportService extends IService<PorReport> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目周报|月报分页
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    Page<PorReportVo> page(PorReportPageParam porReportPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    void add(PorReportAddParam porReportAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    void edit(PorReportEditParam porReportEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    void delete(List<PorReportIdParam> porReportIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目周报|月报详情
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    PorReportVo detail(PorReportIdParam porReportIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目周报|月报详情
     *
     * @author yy
     * @date  2025/06/03 09:44
     **/
    PorReport queryEntity(String id);
}
