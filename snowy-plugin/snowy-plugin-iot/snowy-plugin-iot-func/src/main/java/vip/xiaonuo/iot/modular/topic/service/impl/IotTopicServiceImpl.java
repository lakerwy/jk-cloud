package vip.xiaonuo.iot.modular.topic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.iot.modular.mqtt.client.MqttAcceptClient;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;
import vip.xiaonuo.iot.modular.topic.mapper.IotTopicMapper;
import vip.xiaonuo.iot.modular.topic.param.IotTopicAddParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicEditParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicIdParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicPageParam;
import vip.xiaonuo.iot.modular.topic.service.IotTopicService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物理网-主题topic 表Service接口实现类
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Service
public class IotTopicServiceImpl extends ServiceImpl<IotTopicMapper, IotTopic> implements IotTopicService {

    @Resource
    private MqttAcceptClient mqttAcceptClient;

    @Override
    public Page<IotTopic> page(IotTopicPageParam iotTopicPageParam) {
        QueryWrapper<IotTopic> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(iotTopicPageParam.getSortField(), iotTopicPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(iotTopicPageParam.getSortOrder());
            queryWrapper.orderBy(true, iotTopicPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(iotTopicPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(IotTopic::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IotTopicAddParam iotTopicAddParam) {
        IotTopic iotTopic = BeanUtil.toBean(iotTopicAddParam, IotTopic.class);
        String topicPrex = iotTopicAddParam.getTopicPrex();
        String clientId = iotTopicAddParam.getClientId();
        IotTopic one = getOne(new QueryWrapper<IotTopic>().lambda().eq(IotTopic::getClientId, clientId).eq(IotTopic::getTopicPrex, topicPrex));
        if(one != null){
            throw new CommonException("该主题已存在");
        }
        this.save(iotTopic);
        mqttAcceptClient.subscribe(iotTopic.getTopicPrex()+"/"+iotTopic.getClientId(),1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IotTopicEditParam iotTopicEditParam) {
        IotTopic iotTopic = this.queryEntity(iotTopicEditParam.getId());
        BeanUtil.copyProperties(iotTopicEditParam, iotTopic);
        String topicPrex = iotTopicEditParam.getTopicDesc();
        String clientId = iotTopicEditParam.getClientId();
        IotTopic one = getOne(new QueryWrapper<IotTopic>().lambda().eq(IotTopic::getClientId, clientId).eq(IotTopic::getTopicPrex, topicPrex));
        if(one != null){
            throw new CommonException("该主题已存在");
        }
        this.updateById(iotTopic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IotTopicIdParam> iotTopicIdParamList) {
        for (IotTopicIdParam iotTopicIdParam : iotTopicIdParamList) {
            IotTopic iotTopic = getById(iotTopicIdParam.getId());
            mqttAcceptClient.unsubscribe(iotTopic.getTopicPrex()+"/"+iotTopic.getClientId());
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(iotTopicIdParamList, IotTopicIdParam::getId));
    }

    @Override
    public IotTopic detail(IotTopicIdParam iotTopicIdParam) {
        return this.queryEntity(iotTopicIdParam.getId());
    }

    @Override
    public IotTopic queryEntity(String id) {
        IotTopic iotTopic = this.getById(id);
        if(ObjectUtil.isEmpty(iotTopic)) {
            throw new CommonException("物理网-主题topic 表不存在，id值为：{}", id);
        }
        return iotTopic;
    }

    @Override
    public String saveOrUpdate(String topicPrex,String clientId) {
        IotTopic one = getOne(new QueryWrapper<IotTopic>().lambda().eq(IotTopic::getClientId, clientId).eq(IotTopic::getTopicPrex, topicPrex));
        if(ObjectUtil.isNotEmpty(one)){
            return one.getId();
        }
        one = new IotTopic();
        one.setTopicPrex(topicPrex);
        one.setClientId(clientId);
        this.save(one);
        mqttAcceptClient.subscribe(one.getTopicPrex()+"/"+one.getClientId(),1);
        return one.getId();
    }
}
