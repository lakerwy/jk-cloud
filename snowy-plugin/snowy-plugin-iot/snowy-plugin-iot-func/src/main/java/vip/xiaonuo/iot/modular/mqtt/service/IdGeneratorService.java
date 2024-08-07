package vip.xiaonuo.iot.modular.mqtt.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/5 17:18
 */
@Service
public class IdGeneratorService {
    private final Snowflake snowflake;

    public IdGeneratorService() {
        // 第一个参数是数据中心ID，第二个参数是机器ID
        this.snowflake = IdUtil.createSnowflake(1, 1);
    }

    public String generateId() {
        return String.valueOf(snowflake.nextId());
    }
}
