package vip.xiaonuo.oa.core.constant;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/4 10:01
 * 接口常量定义
 */
public class BaseConstants {
    /*魔点接口***/
    public final static String BASE_URL_MAGIC = "https://oapi.moredian.com";

    //获取appToken
    public final static String APP_TOKEN = "/app/getAppToken";

    //获取accessToken（ISV应用）
    public final static String ACCESS_TOKEN = "/app/getOrgAccessToken";

    //获取accessToken（内部应用）
    public final static String ORG_ACCESS_TOKEN = "/org/getOrgAccessToken";


    public final static String POSITION_TYPE_BAIDU = "baidu"; //百度地图

    public final static String POSITION_TYPE_AMAP = "amap"; //高度地图


    /*------打卡状态------*/

    public final static String CLOCK_TYPE_UP_WORK = "1"; //上班

    public final static String CLOCK_TYPE_DOWN_WORK = "2";
}
