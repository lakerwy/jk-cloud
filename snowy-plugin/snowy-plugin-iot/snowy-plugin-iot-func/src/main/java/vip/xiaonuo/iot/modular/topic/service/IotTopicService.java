package vip.xiaonuo.iot.modular.topic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;
import vip.xiaonuo.iot.modular.topic.param.IotTopicAddParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicEditParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicIdParam;
import vip.xiaonuo.iot.modular.topic.param.IotTopicPageParam;

import java.util.List;

/**
 * 物理网-主题topic 表Service接口
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
public interface IotTopicService extends IService<IotTopic> {

    /**
     * 获取物理网-主题topic 表分页
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    Page<IotTopic> page(IotTopicPageParam iotTopicPageParam);

    /**
     * 添加物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    void add(IotTopicAddParam iotTopicAddParam);

    /**
     * 编辑物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    void edit(IotTopicEditParam iotTopicEditParam);

    /**
     * 删除物理网-主题topic 表
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    void delete(List<IotTopicIdParam> iotTopicIdParamList);

    /**
     * 获取物理网-主题topic 表详情
     *
     * @author mayike
     * @date  2024/08/05 10:26
     */
    IotTopic detail(IotTopicIdParam iotTopicIdParam);

    /**
     * 获取物理网-主题topic 表详情
     *
     * @author mayike
     * @date  2024/08/05 10:26
     **/
    IotTopic queryEntity(String id);

    String saveOrUpdate(String topicPrex, String clientId);
}
