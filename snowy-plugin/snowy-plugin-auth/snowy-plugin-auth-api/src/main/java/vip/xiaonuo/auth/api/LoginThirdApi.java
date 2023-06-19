package vip.xiaonuo.auth.api;

/**
 * 定义一个通过系统UserId登录的接口（免登）
 *
 * @author yong.ye
 * @date 2023/6/15 8:41
 */
public interface LoginThirdApi {
    /**
     * 根据用户id和客户端类型登录，用于第三方免登
     *
     * @param userId 系统用户Id
     * @param device 设备类型 若传入空 默认为 PC
     * @param type 客户端类型 若传入空 默认为 B
     * @return
     */
    String doLoginThirdById(String userId, String type, String device);
}
