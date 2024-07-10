package vip.xiaonuo.im.modular.tencent.service;

import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.im.modular.tencent.param.AccountParam;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/9 15:37
 */
public interface TencentImService {

    /**
     * 创建IM签名
     * @param userId
     * @return
     */
    String createUserSignByUserId(String userId);

    void initUserSign();

    CommonResult<String> accountImport(AccountParam accountParam);

    CommonResult<Object> multiAccountImport(List<AccountParam> accountParams);

    CommonResult<Object> accountDelete(List<AccountParam> accountParams);

    CommonResult<Object> accountCheck(List<AccountParam> accountParams);
}
