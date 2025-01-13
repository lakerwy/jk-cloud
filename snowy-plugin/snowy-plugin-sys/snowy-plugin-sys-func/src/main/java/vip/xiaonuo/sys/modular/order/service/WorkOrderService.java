package vip.xiaonuo.sys.modular.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.order.entity.WorkOrder;
import vip.xiaonuo.sys.modular.order.param.WorkOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderPageParam;

import java.util.List;

/**
 * 事件处理单Service接口
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
public interface WorkOrderService extends IService<WorkOrder> {

    /**
     * 获取事件处理单分页
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    Page<WorkOrder> page(WorkOrderPageParam workOrderPageParam);

    /**
     * 添加事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    void add(WorkOrderAddParam workOrderAddParam);

    /**
     * 编辑事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    void edit(WorkOrderEditParam workOrderEditParam);

    /**
     * 删除事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    void delete(List<WorkOrderIdParam> workOrderIdParamList);

    /**
     * 获取事件处理单详情
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    WorkOrder detail(WorkOrderIdParam workOrderIdParam);

    /**
     * 获取事件处理单详情
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     **/
    WorkOrder queryEntity(String id);
}
