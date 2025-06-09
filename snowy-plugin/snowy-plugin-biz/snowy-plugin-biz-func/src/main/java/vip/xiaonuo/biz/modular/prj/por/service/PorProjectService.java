package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectPageParam;
import vip.xiaonuo.biz.modular.prj.por.vo.PorProjectVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
public interface PorProjectService extends IService<PorProject> {

    /**
     * 获取项目产值台账 - 项目管理分页
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    Page<PorProjectVo> page(PorProjectPageParam porProjectPageParam);

    /**
     * 添加项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void add(PorProjectAddParam porProjectAddParam);

    /**
     * 编辑项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void edit(PorProjectEditParam porProjectEditParam);

    /**
     * 删除项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void delete(List<PorProjectIdParam> porProjectIdParamList);

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    PorProjectVo detail(PorProjectIdParam porProjectIdParam);

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     **/
    PorProject queryEntity(String id);

    Page<PorProjectVo> pageList(PorProjectPageParam porProjectPageParam);
}
