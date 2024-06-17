package vip.xiaonuo.common.consts;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/7 16:24
 */
public class RabbitConstant {
    public final static String DEFAULT_CHANNEL = "jkYunOutPut-0"; //和 application.yml 中的     stream:bindings:jkYunOutPut 一致

    public final static String DEFAULT_DELAYED_CHANNEL = "delayedMessage-out-0"; //和 application.yml 中的     stream:bindings:jkYunOutPut 一致

    public final static String RABBIT_KEY = "task_key_";

    public final static String RABBIT_MESSAGE_ID = "message_id";

    public final static String RABBIT_MESSAGE_ID_key = "message_id_key_";

    public final static String DEFAULT_EXT_CHANNEL = "ext-out-0"; //和其他外部服务通信的通道

    public final static String ROUTING_EPC_KEY = "routing-epc";

    public final static String ROUTING_SUPERV_KEY = "routing-superv";

}
