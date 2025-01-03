package vip.xiaonuo.sys.modular.dingtalk.service;

import vip.xiaonuo.sys.modular.dingtalk.param.SyncDeptParam;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncUserParam;

/**
 * 钉钉 - 数据 - 同步  服务
 */
public interface DingDingSyncService {

    /**
     * 同步 - 钉钉 - 组织架构
     *
     * 说明：
     *    1. 处理组织架构同步
     *    2. 处理人员相关信息同步
     */
    void sync();

    /**
     * 同步 指定 用户信息
     * @param syncUserParam
     */
    void syncUser(SyncUserParam syncUserParam);

    /**
     * 同步 指定 部门信息
     * @param syncDeptParam
     */
    void syncDept(SyncDeptParam syncDeptParam);
}
