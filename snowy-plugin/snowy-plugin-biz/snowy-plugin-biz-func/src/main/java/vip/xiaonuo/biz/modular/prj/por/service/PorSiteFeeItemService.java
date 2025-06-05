package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFeeItem;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemPageParam;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细Service接口
 *
 * @author yy
 * @date  2025/06/03 09:47
 **/
public interface PorSiteFeeItemService extends IService<PorSiteFeeItem> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细分页
     *
     * @author yy
     * @date  2025/06/03 09:47
     */
    Page<PorSiteFeeItem> page(PorSiteFeeItemPageParam porSiteFeeItemPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
     *
     * @author yy
     * @date  2025/06/03 09:47
     */
    void add(PorSiteFeeItemAddParam porSiteFeeItemAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
     *
     * @author yy
     * @date  2025/06/03 09:47
     */
    void edit(PorSiteFeeItemEditParam porSiteFeeItemEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细
     *
     * @author yy
     * @date  2025/06/03 09:47
     */
    void delete(List<PorSiteFeeItemIdParam> porSiteFeeItemIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细详情
     *
     * @author yy
     * @date  2025/06/03 09:47
     */
    PorSiteFeeItem detail(PorSiteFeeItemIdParam porSiteFeeItemIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细详情
     *
     * @author yy
     * @date  2025/06/03 09:47
     **/
    PorSiteFeeItem queryEntity(String id);
}
