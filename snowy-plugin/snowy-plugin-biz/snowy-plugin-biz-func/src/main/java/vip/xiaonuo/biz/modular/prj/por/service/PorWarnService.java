package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorWarn;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnPageParam;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录Service接口
 *
 * @author yy
 * @date  2025/06/03 09:41
 **/
public interface PorWarnService extends IService<PorWarn> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目预警记录分页
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    Page<PorWarn> page(PorWarnPageParam porWarnPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    void add(PorWarnAddParam porWarnAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    void edit(PorWarnEditParam porWarnEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    void delete(List<PorWarnIdParam> porWarnIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目预警记录详情
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    PorWarn detail(PorWarnIdParam porWarnIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目预警记录详情
     *
     * @author yy
     * @date  2025/06/03 09:41
     **/
    PorWarn queryEntity(String id);
}
