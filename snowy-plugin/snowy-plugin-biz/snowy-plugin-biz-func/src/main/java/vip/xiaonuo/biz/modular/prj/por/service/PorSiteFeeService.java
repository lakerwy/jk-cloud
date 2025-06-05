package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFee;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeePageParam;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSiteFeeVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用Service接口
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
public interface PorSiteFeeService extends IService<PorSiteFee> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用分页
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    Page<PorSiteFeeVo> page(PorSiteFeePageParam porSiteFeePageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    void add(PorSiteFeeAddParam porSiteFeeAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    void edit(PorSiteFeeEditParam porSiteFeeEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    void delete(List<PorSiteFeeIdParam> porSiteFeeIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用详情
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    PorSiteFeeVo detail(PorSiteFeeIdParam porSiteFeeIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用详情
     *
     * @author yy
     * @date  2025/06/03 09:45
     **/
    PorSiteFee queryEntity(String id);
}
