package vip.xiaonuo.common.consts;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/7 16:24
 */
public class RabbitConstant {
    public final static String DEFAULT_CHANNEL = "jkYunOutPut-"; //和 application.yml 中的     stream:bindings:jkYunOutPut 一致

    public final static String DEFAULT_DELAYED_CHANNEL = "delayedMessage-out-"; //和 application.yml 中的     stream:bindings:jkYunOutPut 一致

    public final static String RABBIT_KEY = "task_key_";

    public final static String RABBIT_MESSAGE_ID = "message_id";

    public final static String RABBIT_MESSAGE_ID_key = "message_id_key_";
}
