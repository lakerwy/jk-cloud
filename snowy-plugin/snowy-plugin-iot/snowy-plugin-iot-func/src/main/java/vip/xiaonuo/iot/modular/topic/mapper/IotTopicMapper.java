package vip.xiaonuo.iot.modular.topic.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;

/**
 * 物理网-主题topic 表Mapper接口
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
public interface IotTopicMapper extends BaseMapper<IotTopic> {
    Page<IotTopic> pageList(Page<IotTopic> page, @Param(Constants.WRAPPER) Wrapper<IotTopic> queryWrapper);
}
