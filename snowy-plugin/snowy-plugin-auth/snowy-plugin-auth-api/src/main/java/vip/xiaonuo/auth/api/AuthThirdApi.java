package vip.xiaonuo.auth.api;

import java.util.List;
import java.util.Map;

/**
 * @author yong.ye
 * @date 2023/6/14 16:54
 */
public interface AuthThirdApi {

    /**
     * 通过第三方登录类型+第三方用户Id,获取对应的系统用户Id
     * @param thirdId 第三方Id
     * @param category 类型
     * @return
     */
    String getUserIdByThirdId(String thirdId,String category);

    /**
     * 通过系统用户Id,获取三方账号
     * @param userId 系统用户Id
     * @param category 类型
     * @return
     */
    String getThirdIdByUserId(String userId,String category);

    /**
     * 通过系统用户Id,获取三方账号
     * @param userId 系统用户Id
     * @param category 类型
     * @return
     */
    String getUnionIdByUserId(String userId,String category);

    /**
     * 新增第三方用户记录
     * @param userId 系统用户Id
     * @param thirdId 第三方用户Id
     * @param category 类型
     * @param name 用户名
     * @return
     */
    Boolean addAuthThird(String userId,String thirdId,String category,String name);


    /**
     * 新增第三方用户记录
     * @param userId 系统用户Id
     * @param thirdId 第三方用户Id
     * @param category 类型
     * @param name 用户名
     * @param unionId 钉钉唯一第三方ID
     * @return
     */
    Boolean updateAuthThird(String userId,String thirdId,String category,String name,String unionId);

    /**
     * 删除第三方用户记录
     * @param userId 系统用户Id
     * @param category 类型
     * @return
     */
    Boolean delAuthThird(String userId,String category);

    /**
     * 根据类型，获取userId，ThirdId键值对
     * @param category 类型
     * @return
     */
    List<Map<String,String>> getKeyValueByCategory(String category);
}
