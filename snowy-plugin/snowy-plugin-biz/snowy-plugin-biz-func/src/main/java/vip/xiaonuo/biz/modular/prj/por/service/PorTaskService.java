package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorTask;
import vip.xiaonuo.biz.modular.prj.por.param.PorTaskAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorTaskEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorTaskIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorTaskPageParam;
import vip.xiaonuo.biz.modular.prj.por.vo.PorTaskVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
public interface PorTaskService extends IService<PorTask> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目计划管理分页
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    Page<PorTaskVo> page(PorTaskPageParam porTaskPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    void add(PorTaskAddParam porTaskAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    void edit(PorTaskEditParam porTaskEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    void delete(List<PorTaskIdParam> porTaskIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目计划管理详情
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    PorTaskVo detail(PorTaskIdParam porTaskIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目计划管理详情
     *
     * @author yy
     * @date  2025/06/03 09:42
     **/
    PorTask queryEntity(String id);
}
