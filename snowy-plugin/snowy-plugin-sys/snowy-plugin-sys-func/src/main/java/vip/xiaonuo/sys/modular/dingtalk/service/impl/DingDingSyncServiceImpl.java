package vip.xiaonuo.sys.modular.dingtalk.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.enums.DingSyncTagEnum;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;
import vip.xiaonuo.sys.modular.user.param.SysUserAddParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 钉钉 - 数据 - 同步  服务实现类
 */

@Service
public class DingDingSyncServiceImpl implements DingDingSyncService {

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private AuthThirdUserService authThirdUserService;

    /**
     * 钉钉组织架构顶层部门id
     */
    private static final Long DING_TOP_DEPT_ID = 1L;

    /**
     * 同步逻辑说明 【暂停】：
     *        1. 获取钉钉所有部门列表
     *        2. 获取钉钉所有用户列表
     *        3. 同步建科云组织架构 - 维护建科云与钉钉关系
     *        4. 同步建科云用户信息 - 维护建科云与钉钉关系
     */
    @Override
    public void sync() {
        //1. 获取钉钉access_token
        String access_token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(),dingTalkProperties.getAppSecret());

        //2. 获取钉钉组织架构部门(递归)
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList = new ArrayList<>();
        CommonDingDingUtil.execRecursionDept(access_token,DING_TOP_DEPT_ID,dingDeptList);

        //3. 获取钉钉组织架构用户(递归)
        List<OapiV2UserListResponse.ListUserResponse> dingUserList = new ArrayList<>();
        dingDeptList.forEach(item->CommonDingDingUtil.execRecursionDeptUser(access_token,item.getDeptId(),0L,50L,dingUserList));

        //4. 获取建科云需要处理的组织信息【框定范围:DEPT+1L】
        List<SysOrg> sysOrgList = new ArrayList<>();
        LambdaQueryWrapper<SysOrg> sysOrgLambdaQueryWrapper = new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getCategory,SysOrgCategoryEnum.DEPT.getValue()).or().eq(SysOrg::getDingDeptId,DING_TOP_DEPT_ID);
        sysOrgList = sysOrgService.list(sysOrgLambdaQueryWrapper);

        //5. 获取建科云需要处理的用户信息【框定范围:DEPT+1L下】
        List<SysUser> sysUserList = new ArrayList<>();
        if(CollUtil.isNotEmpty(sysOrgList)){
            List<String> orgIds = sysOrgList.stream().map(x->x.getId()).collect(Collectors.toList());
            LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId,orgIds).eq(SysUser::getUserStatus, SysUserStatusEnum.ENABLE.getValue());
            sysUserList = sysUserService.list(sysUserLambdaQueryWrapper);
        }

        syncDeptUser(dingDeptList,sysOrgList,dingUserList,sysUserList);
    }


    /**
     * 同步规则：
     *          * 1. 若已存在dingDeptId（已有对应关系），关注部门是否调整
     *          * 2. 若不存在dingDeptId（暂无对应关系），根据部门名称匹配：
     *          *    若不存在，则新增
     *          *    若已存在，则关注部门信息是否调整
     *          *    若已删除，则移除
     * @param dingDeptList 钉钉没有返回DeptId=1
     */

    @Transactional
    public void syncDeptUser(List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList,List<SysOrg> sysOrgList,
                             List<OapiV2UserListResponse.ListUserResponse> dingUserList,List<SysUser> sysUserList){
        /********************************************* 同步组织 **********************************************************/
        List<SysOrg> newOrgList = new ArrayList<>();
        if(CollUtil.isNotEmpty(dingDeptList)) {
            //获取DingDeptId = 1L
            SysOrg tmpTopSysOrg = sysOrgList.stream().filter(x -> x.getDingDeptId() == DING_TOP_DEPT_ID).findFirst().orElse(null);

            //同步组织架构（DEPT级别 - 不包括 DingDeptId = 1）
            List<SysOrg> tmpSysOrgList = sysOrgList.stream().filter(x -> SysOrgCategoryEnum.DEPT.getValue().equals(x.getCategory())).collect(Collectors.toList());

            //待操作的sysOrg【DEPT级别】
            List<SysOrg> orgData = BeanUtil.copyToList(tmpSysOrgList, SysOrg.class);

            //1. 维护部门对应关系 dingDeptId(为旧数据打上标记 - 更新|删除)
            if (CollUtil.isNotEmpty(orgData)) {
                List<Long> dingDeptIds = dingDeptList.stream().map(x -> x.getDeptId()).collect(Collectors.toList());
                for (SysOrg org : orgData) {
                    //匹配对应关系 DingDeptId
                    if (ObjectUtil.isEmpty(org.getDingDeptId())) {
                        OapiV2DepartmentListsubResponse.DeptBaseResponse tmpDingDept = dingDeptList.stream().filter(obj -> obj.getName().equals(org.getName())).findFirst().orElse(null);
                        if (ObjectUtil.isNotEmpty(tmpDingDept)) org.setDingDeptId(tmpDingDept.getDeptId());
                    }
                    //判断需要移除的（DingDeptId为空|DingDeptId不在DingDeptList中）
                    if (ObjectUtil.isEmpty(org.getDingDeptId()) || !dingDeptIds.contains(org.getDingDeptId())) {
                        org.setDingSyncTag(DingSyncTagEnum.REMOVE.getValue());
                    }
                    //判断需要更新的（DingDeptId不为空，且标记不为remove）
                    if (ObjectUtil.isNotEmpty(org.getDingDeptId()) && ObjectUtil.isEmpty(org.getDingSyncTag())) {
                        org.setDingSyncTag(DingSyncTagEnum.UPDATE.getValue());
                    }
                }
            }

            //2. 判断需要新增的（钉钉中存在，但sysOrg中不存在）
            List<Long> existDingDeptIds = orgData.stream().filter(x -> !DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).map(x -> x.getDingDeptId()).collect(Collectors.toList());
            dingDeptList.forEach(dingDept -> {
                if (!existDingDeptIds.contains(dingDept.getDeptId())) {
                    SysOrg tempAdd = new SysOrg();
                    tempAdd.setId(IdWorker.getIdStr());
                    tempAdd.setCategory(SysOrgCategoryEnum.DEPT.getValue());
                    tempAdd.setName(dingDept.getName());
                    tempAdd.setDingSyncTag(DingSyncTagEnum.ADD.getValue());
                    tempAdd.setDingDeptId(dingDept.getDeptId());
                    tempAdd.setCode(RandomUtil.randomString(10));
                    tempAdd.setSortCode(99);
                    tempAdd.setDeleteFlag("NOT_DELETE");
                    tempAdd.setCreateTime(new Date());
                    tempAdd.setCreateUser(StpLoginUserUtil.getLoginUser().getId());
                    orgData.add(tempAdd);
                }
            });

            //3. 维护数据（新增|更新） - 部门名称|上级parentId
            if (CollUtil.isNotEmpty(orgData)) {
                orgData.stream().filter(x -> DingSyncTagEnum.ADD.getValue().equals(x.getDingSyncTag()) || DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).forEach(item -> {
                    OapiV2DepartmentListsubResponse.DeptBaseResponse tmpDingDept = dingDeptList.stream().filter(obj -> item.getDingDeptId() == obj.getDeptId()).findFirst().orElse(null);
                    if (ObjectUtil.isNotEmpty(tmpDingDept)) {
                        item.setName(tmpDingDept.getName());
                        if (tmpDingDept.getParentId() == DING_TOP_DEPT_ID) {
                            item.setParentId(tmpTopSysOrg.getId());
                        } else {
                            SysOrg tmpSysOrg = orgData.stream().filter(org -> org.getDingDeptId() == tmpDingDept.getParentId()).findFirst().orElse(null);
                            if (ObjectUtil.isNotEmpty(tmpSysOrg)) item.setParentId(tmpSysOrg.getId());
                        }
                    }
                });
            }

            //4. 更新部门信息
            List<SysOrg> addList = orgData.stream().filter(x -> DingSyncTagEnum.ADD.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            List<SysOrg> updateList = orgData.stream().filter(x -> DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            List<SysOrg> removeList = orgData.stream().filter(x -> DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(addList)) {
                sysOrgService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                sysOrgService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                sysOrgService.removeBatchByIds(removeList);
            }

            newOrgList = orgData;
        }

        /********************************************* 同步用户 **********************************************************/
        if(CollUtil.isNotEmpty(dingUserList)){
            //获取已存在的 钉钉对应关系
            LambdaQueryWrapper<AuthThirdUser> thirdUserLambdaQueryWrapper = new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getCategory, AuthThirdCategoryEnum.DingDing.getValue());
            List<AuthThirdUser> thirdUserList = authThirdUserService.list(thirdUserLambdaQueryWrapper);

            //待操作的SysUser
            List<SysUser> userData = BeanUtil.copyToList(sysUserList,SysUser.class);

            List<SysOrg> finalNewOrgList = newOrgList;
            //1. 维护用户对应关系，并标记
            if(CollUtil.isNotEmpty(userData)){
                List<String> dingUserIds = dingUserList.stream().map(x->x.getUserid()).collect(Collectors.toList());
                for (SysUser user:userData) {
                    //配置钉钉userId
                    AuthThirdUser tmpThirdUser = thirdUserList.stream().filter(x -> x.getUserId().equals(user.getId())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpThirdUser)) {
                        user.setDingUserId(tmpThirdUser.getThirdId());
                    }else{
                        OapiV2UserListResponse.ListUserResponse tmpDingUser = dingUserList.stream().filter(x -> x.getMobile().equals(user.getAccount()) && x.getName().equals(user.getName())).findFirst().orElse(null);
                        if(ObjectUtil.isNotEmpty(tmpDingUser)){
                            user.setDingUserId(tmpDingUser.getUserid());
                        }
                    }
                    //删除的
                    if(ObjectUtil.isEmpty(user.getDingUserId()) || !dingUserIds.contains(user.getDingUserId())){
                        user.setDingSyncTag(DingSyncTagEnum.REMOVE.getValue());
                    }
                    //更新的
                    if(ObjectUtil.isNotEmpty(user.getDingUserId()) && ObjectUtil.isEmpty(user.getDingSyncTag())){
                        user.setDingSyncTag(DingSyncTagEnum.UPDATE.getValue());
                        OapiV2UserListResponse.ListUserResponse tmpDingUser = dingUserList.stream().filter(x -> x.getUserid().equals(user.getDingUserId())).findFirst().orElse(null);
                        for (Long deptId:tmpDingUser.getDeptIdList()) {
                            SysOrg tmpOrg = finalNewOrgList.stream().filter(x -> x.getDingDeptId() == deptId).findFirst().orElse(null);
                            if(ObjectUtil.isNotEmpty(tmpOrg)) {
                                user.setOrgId(tmpOrg.getId());
                                break;
                            }
                        }
                    }
                }
            }

            //2. 新增用户 - 直接操作数据库
            List<String> existDingUserIds = userData.stream().filter(x -> !DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).map(x -> x.getDingUserId()).collect(Collectors.toList());
            dingUserList.forEach(dingUser->{
                if(!existDingUserIds.contains(dingUser.getUserid())){
                    SysUserAddParam sysUserAddParam = new SysUserAddParam();
                    sysUserAddParam.setAccount(dingUser.getMobile());
                    sysUserAddParam.setName(dingUser.getName());
                    sysUserAddParam.setPhone(dingUser.getMobile());
                    for (Long deptId:dingUser.getDeptIdList()) {
                        SysOrg tmpOrg = finalNewOrgList.stream().filter(x -> x.getDingDeptId() == deptId).findFirst().orElse(null);
                        if(ObjectUtil.isNotEmpty(tmpOrg)) {
                            sysUserAddParam.setOrgId(tmpOrg.getId());
                            break;
                        }
                    }
                    String userId = sysUserService.addAndGrantRole(sysUserAddParam);
                    //添加第三方库
                    AuthThirdUser tmpAdd = new AuthThirdUser();
                    tmpAdd.setName(dingUser.getName());
                    tmpAdd.setCategory(AuthThirdCategoryEnum.DingDing.getValue());
                    tmpAdd.setUserId(userId);
                    tmpAdd.setThirdId(dingUser.getUserid());
                    tmpAdd.setUnionId(dingUser.getUnionid());
                    tmpAdd.setDeleteFlag("NOT_DELETE");
                    tmpAdd.setCreateTime(new Date());
                    authThirdUserService.save(tmpAdd);
                }
            });

            // 3. 更新
            List<SysUser> updateList = userData.stream().filter(x->DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(updateList)){
                sysUserService.updateBatchById(updateList);
            }

        }
    }

}
