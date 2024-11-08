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
     * 流程设置：通知次数  未设置 【默认3次】
     */
    private Integer limitCount = 3;
    /**
     * 流程设置：通知次数  本次是第几次通知  从1开始
     */
    private Integer limitNum = 1;

    /**
     * 流程实例Id
     */
    private String processInstanceId;
}
