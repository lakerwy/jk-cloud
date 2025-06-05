package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSubSettle;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettlePageParam;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSubSettleVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目分包结算记录Service接口
 *
 * @author yy
 * @date  2025/06/03 09:53
 **/
public interface PorSubSettleService extends IService<PorSubSettle> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目分包结算记录分页
     *
     * @author yy
     * @date  2025/06/03 09:53
     */
    Page<PorSubSettleVo> page(PorSubSettlePageParam porSubSettlePageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目分包结算记录
     *
     * @author yy
     * @date  2025/06/03 09:53
     */
    void add(PorSubSettleAddParam porSubSettleAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目分包结算记录
     *
     * @author yy
     * @date  2025/06/03 09:53
     */
    void edit(PorSubSettleEditParam porSubSettleEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目分包结算记录
     *
     * @author yy
     * @date  2025/06/03 09:53
     */
    void delete(List<PorSubSettleIdParam> porSubSettleIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目分包结算记录详情
     *
     * @author yy
     * @date  2025/06/03 09:53
     */
    PorSubSettleVo detail(PorSubSettleIdParam porSubSettleIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目分包结算记录详情
     *
     * @author yy
     * @date  2025/06/03 09:53
     **/
    PorSubSettle queryEntity(String id);
}
