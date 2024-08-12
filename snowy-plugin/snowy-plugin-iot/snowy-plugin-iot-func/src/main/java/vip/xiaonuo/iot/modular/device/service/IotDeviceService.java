package vip.xiaonuo.iot.modular.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.param.*;

import java.util.List;

/**
 * 物联网-设备表Service接口
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
public interface IotDeviceService extends IService<IotDevice> {

    /**
     * 获取物联网-设备表分页
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    Page<IotDevice> page(IotDevicePageParam iotDevicePageParam);

    /**
     * 添加物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    void add(IotDeviceAddParam iotDeviceAddParam);

    /**
     * 编辑物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    void edit(IotDeviceEditParam iotDeviceEditParam);

    /**
     * 删除物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    void delete(List<IotDeviceIdParam> iotDeviceIdParamList);

    /**
     * 获取物联网-设备表详情
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    IotDevice detail(IotDeviceIdParam iotDeviceIdParam);

    /**
     * 获取物联网-设备表详情
     *
     * @author mayike
     * @date  2024/08/05 09:51
     **/
    IotDevice queryEntity(String id);

    void operate(IotDeviceSwitchParam iotDeviceSwitchParam);

    void deviceUpdate(IotDevice iotDevice, String clientId);

    List<IotDevice> listByGroupId(String groupId);

    void setDeviceGroup(IotDeviceGroupParam iotDeviceGroupParam);

    void batchOperate(IotDeviceOperateParam iotDeviceOperateParam);

    void scheduling(int switchType);

    String getId(int cmd);
}
