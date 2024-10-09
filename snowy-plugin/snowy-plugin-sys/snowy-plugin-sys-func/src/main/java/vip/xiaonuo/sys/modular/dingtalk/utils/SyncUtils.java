package vip.xiaonuo.sys.modular.dingtalk.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.sys.modular.dingtalk.enums.DingSyncTagEnum;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;

import java.util.Date;
import java.util.List;

public class SyncUtils {

    /**
     * 构建组织
     * @param name 部门名称
     * @param dingDeptId 钉钉部门Id
     * @param dingParentId 钉钉部门父Id
     * @return
     */
    public static SysOrg addOrg(String name, Long dingDeptId, Long dingParentId){
        SysOrg tempAdd = new SysOrg();
        tempAdd.setId(IdWorker.getIdStr());
        tempAdd.setCategory(SysOrgCategoryEnum.DEPT.getValue());
        tempAdd.setName(name);
        tempAdd.setDingSyncTag(DingSyncTagEnum.ADD.getValue());
        tempAdd.setDingDeptId(dingDeptId);
        tempAdd.setCode(RandomUtil.randomString(10));
        tempAdd.setSortCode(99);
        tempAdd.setDeleteFlag("NOT_DELETE");
        tempAdd.setCreateTime(new Date());
        tempAdd.setDingParentId(dingParentId);
        return tempAdd;
    }

    /**
     * 构建三方用户
     * @param name 姓名
     * @param userId 系统用户Id
     * @param dingUserId 钉钉用户Id
     * @param unionId 钉钉用户unionId
     * @return
     */
    public static AuthThirdUser addThirdUser(String name, String userId, String dingUserId, String unionId){
        AuthThirdUser tmpAdd = new AuthThirdUser();
        tmpAdd.setName(name);
        tmpAdd.setCategory(AuthThirdCategoryEnum.DingDing.getValue());
        tmpAdd.setUserId(userId);
        tmpAdd.setThirdId(dingUserId);
        tmpAdd.setUnionId(unionId);
        tmpAdd.setDeleteFlag("NOT_DELETE");
        tmpAdd.setCreateTime(new Date());
        return tmpAdd;
    }

    /**
     * 构建增员
     * @param account
     * @param name
     * @param phone
     * @param sysOrgList
     * @param dingDeptIds
     * @return
     */
    public static SysUser addUser(String dingUserId,String dingDirectorId,String account, String name, String phone, List<SysOrg> sysOrgList, List<Long> dingDeptIds ){
        SysUser user = new SysUser();
        user.setId(IdWorker.getIdStr());
        user.setAccount(account);
        user.setName(name);
        user.setPhone(phone);
        user.setOrgId(getOrgId(sysOrgList,dingDeptIds));
        user.setDingUserId(dingUserId);
        user.setDingSyncTag(DingSyncTagEnum.ADD.getValue());
        user.setDingDirectorId(dingDirectorId);
        user.setCreateTime(new Date());
        return user;
    }

    /**
     * 【根据钉钉deptId，找到系统对应orgId】
     *
     * @param sysOrgList 建科云组织信息
     * @param dingDeptIds 钉钉用户所属部门集合 （默认取一个）
     * @return
     */
    public static String getOrgId(List<SysOrg> sysOrgList,List<Long> dingDeptIds ){
        for (Long deptId:dingDeptIds) {
            SysOrg tmpOrg = sysOrgList.stream().filter(x -> deptId.equals(x.getDingDeptId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpOrg)) {
                return tmpOrg.getId();
            }
        }
        return null;
    }


    /**
     * 【根据钉钉部门主管列表，找到系统对应userId】
     *
     * @param sysUserList 建科云用户信息
     * @param dingManagerIds 钉钉部门主管集合 （默认取一个）
     * @return
     */
    public static String getManageId(List<SysUser> sysUserList,List<String> dingManagerIds ){
        for (String managerId:dingManagerIds) {
            SysUser tmpUser = sysUserList.stream().filter(x -> managerId.equals(x.getDingUserId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpUser)) {
                return tmpUser.getId();
            }
        }
        return null;
    }
}
