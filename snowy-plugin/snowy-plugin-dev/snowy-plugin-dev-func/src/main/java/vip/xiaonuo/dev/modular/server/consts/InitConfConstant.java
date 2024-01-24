package vip.xiaonuo.dev.modular.server.consts;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/9 9:24
 */
public interface InitConfConstant {

    public final String SYSTEM_NO_KEY = "system:no";

    /**
     * mysql 数据库对应容器名称
     */
    public final String MYSQL_DOCKER_NAME = "MYSQL_DOCKER_NAME";

    /**
     * 数据库脚本初始化源文件位置
     */
    public final String DUMP_SQL_SOURCE_PATH = "DUMP_SQL_SOURCE_PATH";

    /**
     * 数据库脚本目标文件位置
     */
    public final String DUMP_SQL_TARGET_PATH = "DUMP_SQL_TARGET_PATH";

    /**
     * 配置文件输出路径
     */
    public final String YAML_OUT_CONFIG_PATH = "YAML_OUT_CONFIG_PATH";

    /**
     * 配置文件库路径
     */
    public final String YAML_CONFIG_PATH = "YAML_CONFIG_PATH";

    public final String COMPOSE_YML_CONFIG_PATH = "COMPOSE_YML_CONFIG_PATH";

    /**
     * 配置文件输出路径
     */
    public final String COMPOSE_YML_OUT_CONFIG_PATH = "COMPOSE_YML_OUT_CONFIG_PATH";

    /**
     * 源系统IP
     */
    public final String SYSTEM_SOURCE_IP = "SYSTEM_SOURCE_IP";

    public final String SOURCE_SERVER_NAME = "SOURCE_SERVER_NAME";

    public final String SOURCE_SERVER_PS = "SOURCE_SERVER_PS";


    /**
     * 单体源配置库绝
     */
    public final String SINGLE_SOURCE_CONFIG_PATH = "SINGLE_SOURCE_CONFIG_PATH";

    /**
     * 单体目标配置路径
     */
    public final String SINGLE_TARGET_CONFIG_PATH = "SINGLE_TARGET_CONFIG_PATH";

    public final String MYSQL_PARAMS = "?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true";

    /**
     * yml需要替换掉的key
     */

    public final String MYSQL_RUL = "spring.datasource.dynamic.datasource.master.url";

    public final String MYSQL_USERNAME = "spring.datasource.dynamic.datasource.master.username";

    public final String MYSQL_PASSWORD = "spring.datasource.dynamic.datasource.master.password";

    /**
     * rabbbitmq交换机key
     */
    public final String RABBIT_MQ_TOPIC_DESTINATION_1 = "spring.cloud.stream.bindings.topic-output-1.destination";

    public final String RABBIT_MQ_TOPIC_DESTINATION_2 = "spring.cloud.stream.bindings.sink-in-0.destination";

    public final String RABBIT_MQ_DELAYED_DESTINATION_1 = "spring.cloud.stream.bindings.delayedMessage-out-1.destination";

    public final String RABBIT_MQ_DELAYED_DESTINATION_2 = "spring.cloud.stream.bindings.delayedMessage-in-1.destination";

    /**
     * 广播交换机前缀
     */
    public final String STREAM_TOPIC_EXCHANGE_PRE = "exchange-epc-";

    /**
     * 延迟交换机前缀
     */
    public final String STREAM_DELAYED_EXCHANGE_PRE = "exchange-epc-delayed-";

    public final String SNOWY_CONFIG_COMMON_FRONT_RUL = "snowy.config.common.front-url";

    public final String SNOWY_CONFIG_COMMON_BACKEND_RUL = "snowy.config.common.backend-url";

    /**
     * docker-compose 中容器名称
     */
    public final String DOCKER_CONTAINER_NAME = "services.web.container_name";

    public final String DOCKER_PORT = "services.web.ports";

    /**
     * 配置文件目录名
     */
    public final String CONF_NAME = "conf";

    public final String APPLICATION_YML_NAME = "application.yaml";

    public final String DOCKER_YML_NAME = "docker-compose.yml";

    public final String BUILD_SH = "build.sh";

    public final String DATABASE_CODE = " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";

    public final String EPC_USER_URL = "/api/epc/third/syncJkUser";
}
