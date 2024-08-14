package vip.xiaonuo.iot.modular.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    CommonCacheOperator commonCacheOperator;

    public static String IOT_CMD_PRE = "IOT_CMD:";


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
        List<MqttDevice> mqttDevices = new ArrayList<>();
        MqttDevice mqttDevice = BeanUtil.copyProperties(iotDevice, MqttDevice.class);
        mqttDevices.add(mqttDevice);
        operate(iotDevice.getDeviceTopicId(), mqttDevices);
        updateById(iotDevice);
    }

    public void operate(String topicId,List<MqttDevice> mqttDevices) {
        Message message = new Message();
        IotTopic iotTopic = iotTopicService.queryEntity(topicId);
        message.setCmd(CmdConstant.CMD_208);
        message.setClientId(iotTopic.getClientId());
        MqttData data = new MqttData();
        data.setId(getId(CmdConstant.CMD_208));
        data.setDevices(mqttDevices);
        message.setData(data);
        if(JSON.toJSONString(message, SerializerFeature.WriteMapNullValue.toString()).length()>1000){
            log.error("长度大于1000");
        }
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
        if (CollUtil.isNotEmpty(iotDeviceOperateParam.getGroupIds())) {
            iotDevices.addAll(list(new LambdaQueryWrapper<IotDevice>().in(IotDevice::getGroupId, iotDeviceOperateParam.getGroupIds())));
        }
        if (CollUtil.isNotEmpty(iotDeviceOperateParam.getIds())) {
            iotDevices.addAll(list(new LambdaQueryWrapper<IotDevice>().in(IotDevice::getId, iotDeviceOperateParam.getIds())));
        }
        Map<String, List<IotDevice>> collect = iotDevices.stream().collect(Collectors.groupingBy(IotDevice::getDeviceTopicId));

        int switchType = iotDeviceOperateParam.getSwitchType();
        List<MqttDevice> mqttDevices = new ArrayList<>();
        int maxBatchLength = 800; // 字符串长度限制
        int currentBatchLength = 0;

        for (Map.Entry<String, List<IotDevice>> entry : collect.entrySet()) {
            List<IotDevice> devices = entry.getValue();
            String topicId = null;

            Iterator<IotDevice> iterator = devices.iterator();
            while (iterator.hasNext()) {
                IotDevice iotDevice = iterator.next();
                topicId = iotDevice.getDeviceTopicId();
                MqttDevice mqttDevice = buildFh(iotDevice, mqttDevices, switchType);
                String deviceString = JSON.toJSONString(mqttDevice); // 使用 Fastjson2 序列化

                int deviceLength = deviceString.length();

                // 检查是否会超过限制
                if ((currentBatchLength + deviceLength) > maxBatchLength) {
                    mqttDevices.remove(mqttDevice);
                    // 执行操作并清空列表
                    operate(topicId, mqttDevices);
                    mqttDevices.clear();
                    currentBatchLength = 0;
                    devices.add(iotDevice);
                    // 休眠 1 秒
                    try {
                        Thread.sleep(mqttProperties.getSleepTime());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Thread was interrupted during sleep", e);
                    }
                } else {
                    currentBatchLength += deviceLength;
                }
            }
            // 处理剩余未达到8个设备且字符串总长度未超过500的设备
            if (!mqttDevices.isEmpty() && topicId != null) {
                operate(topicId, mqttDevices);
                mqttDevices.clear();
                currentBatchLength = 0;

                // 休眠 1 秒
                try {
                    Thread.sleep(mqttProperties.getSleepTime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Thread was interrupted during sleep", e);
                }
            }
        }

        // 最终处理剩余的设备
        if (!mqttDevices.isEmpty()) {
            operate(iotDevices.get(0).getDeviceTopicId(), mqttDevices); // 假设所有设备的 topicId 相同
            mqttDevices.clear();
        }

        updateBatchById(iotDevices);
    }




    @Override
    public void scheduling(int switchType) {
        if(!mqttProperties.isEnable()){
            testDevice(switchType);
        }else {
            List<IotDevice> list = list();
            List<String> ids = list.stream().map(IotDevice::getId).collect(Collectors.toList());
            IotDeviceOperateParam iotDeviceOperateParam = new IotDeviceOperateParam();
            iotDeviceOperateParam.setIds(ids);
            iotDeviceOperateParam.setSwitchType(switchType);
            batchOperate(iotDeviceOperateParam);
        }
    }

    private void testDevice(int switchType){
        String deviceCode = "083b7c19";
        IotDevice one = getOne(new LambdaQueryWrapper<IotDevice>().eq(IotDevice::getDeviceCode, deviceCode));
        if(ObjectUtil.isNotEmpty(one)) {
            IotDeviceOperateParam iotDeviceOperateParam = new IotDeviceOperateParam();
            List<String> ids = new ArrayList<>();
            ids.add(one.getId());
            iotDeviceOperateParam.setIds(ids);
            iotDeviceOperateParam.setSwitchType(switchType);
            batchOperate(iotDeviceOperateParam);
        }
    }

//    private void operate(String deviceId,int switchType,int deviceType){
//        Integer switchPower1 = switchType;
//        Integer switchPower2 = null;
//        Integer switchPower3 = null;
//        if(deviceType==9){
//            switchPower2 = switchType;
//        }else if(deviceType==10){
//            switchPower2 = switchType;
//            switchPower3 = switchType;
//        }
//        operate(deviceId,switchPower1,switchPower2,switchPower3);
//    }

    private MqttDevice buildFh(IotDevice iotDevice, List<MqttDevice> mqttDevices, Integer switchType) {
        Integer deviceType = iotDevice.getDeviceType();
        iotDevice.setSwitchPower1(switchType);
        if (deviceType == 9) {
            iotDevice.setSwitchPower2(switchType);
        } else if (deviceType == 10) {
            iotDevice.setSwitchPower2(switchType);
            iotDevice.setSwitchPower3(switchType);
        }
        //MqttDevice mqttDevice = new MqttDevice();
        JSONObject jsonObject = JSONUtil.parseObj(iotDevice, true);
        MqttDevice mqttDevice = JSONUtil.toBean(jsonObject, MqttDevice.class,true);
        //MqttDevice mqttDevice = JSON.to(MqttDevice.class, iotDevice);
        // 使用 Hutool 的 BeanUtil.copyProperties 时过滤掉 null 字段
        //BeanUtil.copyProperties(iotDevice, mqttDevice, CopyOptions.create().setIgnoreNullValue(false));
        mqttDevices.add(mqttDevice);
        return mqttDevice;
    }


    @Override
    public String getId(int cmd){
        Object o = commonCacheOperator.get(IOT_CMD_PRE + cmd);
        int i = 1;
        if(ObjectUtil.isNotNull(o)){
            i = Integer.parseInt(String.valueOf(o)) + 1;
        }
        commonCacheOperator.put(IOT_CMD_PRE + cmd, i);
        return String.valueOf(i);
    }
}
