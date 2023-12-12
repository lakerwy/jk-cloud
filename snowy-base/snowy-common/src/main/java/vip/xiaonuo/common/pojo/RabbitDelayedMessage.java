package vip.xiaonuo.common.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/8 16:04
 */
@Setter
@Getter
public class RabbitDelayedMessage {
    private RabbitMsg rabbitMsg;
    private Double limit;
    private String taskKey;
}
