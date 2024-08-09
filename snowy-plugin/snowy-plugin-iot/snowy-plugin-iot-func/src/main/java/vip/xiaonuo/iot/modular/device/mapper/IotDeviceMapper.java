package vip.xiaonuo.iot.modular.device.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;

/**
 * 物联网-设备表Mapper接口
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
public interface IotDeviceMapper extends BaseMapper<IotDevice> {
    Page<IotDevice> pageList(Page<IotDevice> page, @Param(Constants.WRAPPER) Wrapper<IotDevice> queryWrapper);
}
