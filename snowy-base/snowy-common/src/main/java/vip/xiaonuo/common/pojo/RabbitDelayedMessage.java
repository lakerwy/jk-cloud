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
    private int limit;
    private String taskKey;

    /**
     * 流程设置：通知次数
     */
    private Integer limitCount;
    /**
     * 流程设置：通知次数  本次是第几次通知
     */
    private Integer limitNum;
}
