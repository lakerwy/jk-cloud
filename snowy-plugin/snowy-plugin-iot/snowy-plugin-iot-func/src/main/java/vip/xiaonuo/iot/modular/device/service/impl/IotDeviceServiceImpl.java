package vip.xiaonuo.iot.modular.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.iot.core.config.MqttProperties;
import vip.xiaonuo.iot.modular.device.constant.CmdConstant;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.mapper.IotDeviceMapper;
import vip.xiaonuo.iot.modular.device.param.*;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;
import vip.xiaonuo.iot.modular.mqtt.client.MqttSendClient;
import vip.xiaonuo.iot.modular.mqtt.param.Message;
import vip.xiaonuo.iot.modular.mqtt.param.MqttData;
import vip.xiaonuo.iot.modular.mqtt.param.MqttDevice;
import vip.xiaonuo.iot.modular.mqtt.service.IdGeneratorService;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;
import vip.xiaonuo.iot.modular.topic.service.IotTopicService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物联网-设备表Service接口实现类
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
@Service
public class IotDeviceServiceImpl extends ServiceImpl<IotDeviceMapper, IotDevice> implements IotDeviceService {

    @Resource
    private MqttSendClient mqttSendClient;

    @Resource
    private IotTopicService iotTopicService;

    @Resource
    private IdGeneratorService idGeneratorService;

    @Resource
    private MqttProperties mqttProperties;

    @Override
    public Page<IotDevice> page(IotDevicePageParam iotDevicePageParam) {
        QueryWrapper<IotDevice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(iotDevicePageParam.getSortField(), iotDevicePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(iotDevicePageParam.getSortOrder());
            queryWrapper.orderBy(true, iotDevicePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(iotDevicePageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.CREATE_TIME");
        }
        if(StrUtil.isNotEmpty(iotDevicePageParam.getSearchKey())){
            queryWrapper.eq("a.DEVICE_NAME",iotDevicePageParam.getSearchKey())
                    .or().like("b.TOPIC_DESC",iotDevicePageParam.getSearchKey())
                    .or().like("c.NAME",iotDevicePageParam.getSearchKey());
        }
        return baseMapper.pageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IotDeviceAddParam iotDeviceAddParam) {
        IotDevice iotDevice = BeanUtil.toBean(iotDeviceAddParam, IotDevice.class);
        this.save(iotDevice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IotDeviceEditParam iotDeviceEditParam) {
        IotDevice iotDevice = this.queryEntity(iotDeviceEditParam.getId());
        BeanUtil.copyProperties(iotDeviceEditParam, iotDevice);
        this.updateById(iotDevice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IotDeviceIdParam> iotDeviceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(iotDeviceIdParamList, IotDeviceIdParam::getId));
    }

    @Override
    public IotDevice detail(IotDeviceIdParam iotDeviceIdParam) {
        return this.queryEntity(iotDeviceIdParam.getId());
    }

    @Override
    public IotDevice queryEntity(String id) {
        IotDevice iotDevice = this.getById(id);
        if(ObjectUtil.isEmpty(iotDevice)) {
            throw new CommonException("物联网-设备表不存在，id值为：{}", id);
        }
        return iotDevice;
    }

    @Override
    public void operate(IotDeviceSwitchParam iotDeviceSwitchParam) {
        String id = iotDeviceSwitchParam.getId();
//        if(iotDeviceSwitchParam.getSwitchPower1()!=null){
//            int switchPower1 = iotDeviceSwitchParam.getSwitchPower1();
//        }
//        int switchPower2 = iotDeviceSwitchParam.getSwitchPower2();
//        int switchPower3 = iotDeviceSwitchParam.getSwitchPower3();
        operate(id,iotDeviceSwitchParam.getSwitchPower1(),iotDeviceSwitchParam.getSwitchPower2(),iotDeviceSwitchParam.getSwitchPower3());
        IotDevice iotDevice = queryEntity(id);
        Integer switchPower1 = iotDeviceSwitchParam.getSwitchPower1();
        if(switchPower1 != null){
            iotDevice.setSwitchPower1(switchPower1);
        }
        Integer switchPower2 = iotDeviceSwitchParam.getSwitchPower2();
        if(switchPower2 != null){
            iotDevice.setSwitchPower2(switchPower2);
        }
        Integer switchPower3 = iotDeviceSwitchParam.getSwitchPower3();
        if(switchPower3 != null){
            iotDevice.setSwitchPower3(switchPower3);
        }
        updateById(iotDevice);
    }

    public void operate(String id,Integer switchPower1,Integer switchPower2,Integer switchPower3) {
        Message message = new Message();
        IotDevice iotDevice = this.getById(id);
        if(ObjectUtil.isEmpty(iotDevice)) {
            throw new CommonException("设备不存在");
        }
        IotTopic iotTopic = iotTopicService.queryEntity(iotDevice.getDeviceTopicId());
        message.setCmd(CmdConstant.CMD_208);
        message.setClientId(iotTopic.getClientId());
        MqttData data = new MqttData();
        data.setId(idGeneratorService.generateId());
        MqttDevice mqttDevice = BeanUtil.copyProperties(iotDevice, MqttDevice.class);
        if(switchPower1 != null){
            mqttDevice.setSwitchPower1(switchPower1);
        }
        if(switchPower2 != null){
            mqttDevice.setSwitchPower2(switchPower2);
        }
        if(switchPower3 != null){
            mqttDevice.setSwitchPower3(switchPower3);
        }
        List<MqttDevice> mqttDevices = new ArrayList<>();
        mqttDevices.add(mqttDevice);
        data.setDevices(mqttDevices);
        message.setData(data);
        mqttSendClient.publish(false,iotTopic.getTopicPrex()+"/"+iotTopic.getClientId(),message);
    }

    @Override
    public void deviceUpdate(IotDevice iotDevice,String clientId){
        if(ObjectUtil.isNotEmpty(iotDevice)) {
            IotDevice one = getOne(new LambdaQueryWrapper<IotDevice>().eq(IotDevice::getDeviceCode, iotDevice.getDeviceCode()));
            String topicId = iotTopicService.saveOrUpdate(mqttProperties.getGateWayPre(), clientId);
            iotDevice.setDeviceTopicId(topicId);
            if(ObjectUtil.isEmpty(one)) {
                iotDevice.setId(null);
                save(iotDevice);
            }else {
                iotDevice.setId(one.getId());
                updateById(iotDevice);
            }
        }
    }

    @Override
    public List<IotDevice> listByGroupId(String groupId){
        return list(new LambdaQueryWrapper<IotDevice>().eq(IotDevice::getGroupId, groupId));
    }

    @Override
    public void setDeviceGroup(IotDeviceGroupParam iotDeviceGroupParam){
        update(new LambdaUpdateWrapper<IotDevice>().set(IotDevice::getGroupId, iotDeviceGroupParam.getGroupId())
                .in(IotDevice::getId,iotDeviceGroupParam.getDeviceIdList()));
    }

    @Override
    public void batchOperate(IotDeviceOperateParam iotDeviceOperateParam) {
        List<IotDevice> iotDevices = new ArrayList<>();
        if (CollUtil.isNotEmpty(iotDeviceOperateParam.getGroupIds())){
            iotDevices.addAll(list(new LambdaQueryWrapper<IotDevice>().in(IotDevice::getGroupId, iotDeviceOperateParam.getGroupIds())));

        }
        if (CollUtil.isNotEmpty(iotDeviceOperateParam.getIds())){
            iotDevices.addAll(list(new LambdaQueryWrapper<IotDevice>().in(IotDevice::getId, iotDeviceOperateParam.getIds())));
        }
        int switchType = iotDeviceOperateParam.getSwitchType();
        for (IotDevice iotDevice : iotDevices){
            Integer deviceType = iotDevice.getDeviceType();
            iotDevice.setSwitchPower1(switchType);
            if(deviceType==9){
                iotDevice.setSwitchPower2(switchType);
            }else if(deviceType==10){
                iotDevice.setSwitchPower2(switchType);
                iotDevice.setSwitchPower3(switchType);
            }
            operate(iotDevice.getId(),switchType,iotDevice.getDeviceType());
        }
        updateBatchById(iotDevices);
    }

    @Override
    public void scheduling(int switchType) {
        if(!mqttProperties.isEnable()){
            testDevice(switchType);
        }else {
            List<IotDevice> list = list();
            for (IotDevice iotDevice : list) {
                try {
                    operate(iotDevice.getId(),switchType,iotDevice.getDeviceType());
                } catch (Exception e) {
                    log.error(String.format("编号为%s设备%s失败", iotDevice.getDeviceCode(), switchType == 0 ? "关闭" : "打开"), e);
                }
            }
        }
    }

    private void testDevice(int switchType){
        String deviceCode = "083b7c19";
        IotDevice one = getOne(new LambdaQueryWrapper<IotDevice>().eq(IotDevice::getDeviceCode, deviceCode));
        if(ObjectUtil.isNotEmpty(one)) {
            operate(one.getId(),switchType,switchType,switchType);
        }
    }

    private void operate(String deviceId,int switchType,int deviceType){
        Integer switchPower1 = switchType;
        Integer switchPower2 = null;
        Integer switchPower3 = null;
        if(deviceType==9){
            switchPower2 = switchType;
        }else if(deviceType==10){
            switchPower2 = switchType;
            switchPower3 = switchType;
        }
        operate(deviceId,switchPower1,switchPower2,switchPower3);
    }
}
