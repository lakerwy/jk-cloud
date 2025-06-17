package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOwnerSettle;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOwnerSettleVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录Service接口
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
public interface PorOwnerSettleService extends IService<PorOwnerSettle> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目业主结算记录分页
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    Page<PorOwnerSettleVo> page(PorOwnerSettlePageParam porOwnerSettlePageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    void add(PorOwnerSettleAddParam porOwnerSettleAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    void edit(PorOwnerSettleEditParam porOwnerSettleEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目业主结算记录
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    void delete(List<PorOwnerSettleIdParam> porOwnerSettleIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目业主结算记录详情
     *
     * @author yy
     * @date  2025/06/03 09:52
     */
    PorOwnerSettleVo detail(PorOwnerSettleIdParam porOwnerSettleIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目业主结算记录详情
     *
     * @author yy
     * @date  2025/06/03 09:52
     **/
    PorOwnerSettle queryEntity(String id);
}
