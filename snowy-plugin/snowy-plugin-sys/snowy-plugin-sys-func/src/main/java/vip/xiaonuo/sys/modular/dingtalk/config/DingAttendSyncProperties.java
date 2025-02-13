package vip.xiaonuo.sys.modular.dingtalk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉考勤数据同步配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "dingattend")
public class DingAttendSyncProperties {

    private Long agentId;

    private String appKey;

    private String appSecret;

}
