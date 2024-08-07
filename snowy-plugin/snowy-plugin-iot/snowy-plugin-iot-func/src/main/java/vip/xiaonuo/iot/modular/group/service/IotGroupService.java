package vip.xiaonuo.iot.modular.group.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.iot.modular.group.entity.IotGroup;
import vip.xiaonuo.iot.modular.group.param.IotGroupAddParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupEditParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupIdParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupPageParam;

import java.util.List;

/**
 * 设备分组Service接口
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
public interface IotGroupService extends IService<IotGroup> {

    /**
     * 获取设备分组分页
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    Page<IotGroup> page(IotGroupPageParam iotGroupPageParam);

    /**
     * 添加设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    void add(IotGroupAddParam iotGroupAddParam);

    /**
     * 编辑设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    void edit(IotGroupEditParam iotGroupEditParam);

    /**
     * 删除设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    void delete(List<IotGroupIdParam> iotGroupIdParamList);

    /**
     * 获取设备分组详情
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    IotGroup detail(IotGroupIdParam iotGroupIdParam);

    /**
     * 获取设备分组详情
     *
     * @author mayike
     * @date  2024/08/06 08:55
     **/
    IotGroup queryEntity(String id);
}
