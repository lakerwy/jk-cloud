package vip.xiaonuo.sys.modular.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.order.entity.OmOrder;
import vip.xiaonuo.sys.modular.order.param.OmOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderPageParam;

import java.util.List;

/**
 * 运维工单Service接口
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 **/
public interface OmOrderService extends IService<OmOrder> {

    /**
     * 获取运维工单分页
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    Page<OmOrder> page(OmOrderPageParam omOrderPageParam);

    /**
     * 添加运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    void add(OmOrderAddParam omOrderAddParam);

    /**
     * 编辑运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    void edit(OmOrderEditParam omOrderEditParam);

    /**
     * 删除运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    void delete(List<OmOrderIdParam> omOrderIdParamList);

    /**
     * 获取运维工单详情
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    OmOrder detail(OmOrderIdParam omOrderIdParam);

    /**
     * 获取运维工单详情
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     **/
    OmOrder queryEntity(String id);
}
