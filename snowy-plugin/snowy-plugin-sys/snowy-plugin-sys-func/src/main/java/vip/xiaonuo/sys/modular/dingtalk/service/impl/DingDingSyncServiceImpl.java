package vip.xiaonuo.sys.modular.dingtalk.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.dingtalk.enums.DingSyncTagEnum;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;
import vip.xiaonuo.sys.modular.dingtalk.utils.SyncUtils;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;
import vip.xiaonuo.sys.modular.user.param.SysUserAddParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 钉钉 - 数据 - 同步  服务实现类
 */

@Service
@Slf4j
public class DingDingSyncServiceImpl implements DingDingSyncService {

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private AuthThirdUserService authThirdUserService;

    @Resource
    private DingSyncProperties dingSyncProperties;

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
        if(dingSyncProperties.getIsSync()) {
            log.info("钉钉组织架构同步 -- 开始 -- ");
            //1. 获取钉钉access_token
            String access_token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());

            //2. 获取钉钉组织架构部门(递归)
            List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList = new ArrayList<>();
            CommonDingDingUtil.execRecursionDept(access_token, DING_TOP_DEPT_ID, dingDeptList);

            //3. 获取钉钉组织架构用户(递归)
            List<OapiV2UserListResponse.ListUserResponse> dingUserList = new ArrayList<>();
            dingDeptList.forEach(item -> CommonDingDingUtil.execRecursionDeptUser(access_token, item.getDeptId(), 0L, 50L, dingUserList));

            //4. 获取建科云需要处理的组织信息【框定范围:DEPT+1L】
            LambdaQueryWrapper<SysOrg> sysOrgLambdaQueryWrapper = new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getCategory, SysOrgCategoryEnum.DEPT.getValue()).or().eq(SysOrg::getDingDeptId, DING_TOP_DEPT_ID);
            List<SysOrg> sysOrgList = sysOrgService.list(sysOrgLambdaQueryWrapper);

            //5. 获取建科云需要处理的用户信息【框定范围:DEPT+1L下】
            List<SysUser> sysUserList = new ArrayList<>();
            if (CollUtil.isNotEmpty(sysOrgList)) {
                List<String> orgIds = sysOrgList.stream().map(x -> x.getId()).collect(Collectors.toList());
                LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId, orgIds).eq(SysUser::getUserStatus, SysUserStatusEnum.ENABLE.getValue());
                sysUserList = sysUserService.list(sysUserLambdaQueryWrapper);
            }

            //6. 同步逻辑
            syncDeptUser(dingDeptList, sysOrgList, dingUserList, sysUserList);
            log.info("钉钉组织架构同步 -- 结束 -- ");
        }
    }


    /**
     * 同步规则： dingDeptList 钉钉没有返回DeptId=1
     */

    @Transactional
    public void syncDeptUser(List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList,List<SysOrg> sysOrgList,
                             List<OapiV2UserListResponse.ListUserResponse> dingUserList,List<SysUser> sysUserList){
        /********************************************* 同步组织 **********************************************************/
        List<SysOrg> newOrgList = new ArrayList<>();
        if(CollUtil.isNotEmpty(dingDeptList)) {
            log.info("钉钉组织架构同步 -- 部门 -- ");
            //获取DingDeptId = 1L
            SysOrg tmpTopSysOrg = sysOrgList.stream().filter(x -> DING_TOP_DEPT_ID.equals(x.getDingDeptId())).findFirst().orElse(null);
            //同步组织架构（DEPT级别 - 不包括 DingDeptId = 1）
            List<SysOrg> tmpSysOrgList = sysOrgList.stream().filter(x -> SysOrgCategoryEnum.DEPT.getValue().equals(x.getCategory())).collect(Collectors.toList());
            //待操作的sysOrg【DEPT级别】
            List<SysOrg> orgData = BeanUtil.copyToList(tmpSysOrgList, SysOrg.class);

            //1. 维护部门对应关系 dingDeptId(为旧数据打上标记 - 更新|删除)
            if (CollUtil.isNotEmpty(orgData)) {
                List<Long> dingDeptIds = dingDeptList.stream().map(x -> x.getDeptId()).collect(Collectors.toList());
                for (SysOrg org : orgData) {
                    //匹配对应关系 DingDeptId  DingParentId
                    OapiV2DepartmentListsubResponse.DeptBaseResponse tmpDingDept = null;
                    if (ObjectUtil.isNotEmpty(org.getDingDeptId())) {
                        tmpDingDept = dingDeptList.stream().filter(obj -> obj.getDeptId().equals(org.getDingDeptId())).findFirst().orElse(null);
                    }else{
                        tmpDingDept = dingDeptList.stream().filter(obj -> obj.getName().equals(org.getName())).findFirst().orElse(null);
                    }
                    if (ObjectUtil.isNotEmpty(tmpDingDept)) {
                        org.setDingDeptId(tmpDingDept.getDeptId());
                        org.setDingParentId(tmpDingDept.getParentId());
                        org.setName(tmpDingDept.getName());
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
            List<Long> existDingDeptIds = orgData.stream().filter(x -> ObjectUtil.isNotEmpty(x.getDingDeptId())).map(x -> x.getDingDeptId()).collect(Collectors.toList());
            dingDeptList.forEach(dingDept -> {
                if (!existDingDeptIds.contains(dingDept.getDeptId())) {
                    SysOrg tempAdd = SyncUtils.addOrg(dingDept.getName(),dingDept.getDeptId(),dingDept.getParentId());
                    orgData.add(tempAdd);
                }
            });

            //3. 维护数据（新增|更新） - 上级parentId
            if (CollUtil.isNotEmpty(orgData)) {
                orgData.stream().filter(x -> !DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).forEach(item -> {
                    if (DING_TOP_DEPT_ID.equals(item.getDingParentId())) {
                        item.setParentId(tmpTopSysOrg.getId());
                    } else {
                        SysOrg tmpSysOrg = orgData.stream().filter(org -> item.getDingParentId().equals(org.getDingDeptId())).findFirst().orElse(null);
                        if (ObjectUtil.isNotEmpty(tmpSysOrg)) item.setParentId(tmpSysOrg.getId());
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
            log.info("钉钉组织架构同步 -- 用户 -- ");
            //获取已存在的 钉钉对应关系
            LambdaQueryWrapper<AuthThirdUser> thirdUserLambdaQueryWrapper = new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getCategory, AuthThirdCategoryEnum.DingDing.getValue());
            List<AuthThirdUser> thirdUserList = authThirdUserService.list(thirdUserLambdaQueryWrapper);

            //待操作的SysUser
            List<SysUser> userData = BeanUtil.copyToList(sysUserList,SysUser.class);
            //待新增的对应关系
            List<AuthThirdUser> addThirdUserList = new ArrayList<>();
            //最新组织信息
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
                            addThirdUserList.add(SyncUtils.addThirdUser(user.getName(),user.getId(),tmpDingUser.getUserid(),tmpDingUser.getUnionid()));
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
                        user.setOrgId(SyncUtils.getOrgId(finalNewOrgList,tmpDingUser.getDeptIdList()));
                    }
                }
            }

            //2. 新增用户|更新|删除 - 直接操作数据库
            List<String> existDingUserIds = userData.stream().filter(x -> ObjectUtil.isNotEmpty(x.getDingUserId())).map(x -> x.getDingUserId()).collect(Collectors.toList());
            dingUserList.forEach(dingUser->{
                if(!existDingUserIds.contains(dingUser.getUserid())){
                    try {
                        SysUserAddParam tmpSysUser = SyncUtils.addUser(dingUser.getMobile(), dingUser.getName(), dingUser.getMobile(), finalNewOrgList, dingUser.getDeptIdList());
                        String userId = sysUserService.addAndGrantRole(tmpSysUser); //新增用户以及角色
                        //添加第三方库
                        addThirdUserList.add(SyncUtils.addThirdUser(dingUser.getName(), userId, dingUser.getUserid(), dingUser.getUnionid()));
                    }catch (Exception e){
                        log.error("钉钉组织架构同步 -- 用户 -- "+e.getMessage());
                    }
                }
            });
            //更新
            List<SysUser> updateList = userData.stream().filter(x->DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            //删除
            List<SysUser> removeList = userData.stream().filter(x->DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(updateList)){
                sysUserService.updateBatchById(updateList);
            }
            //删除
            if(CollUtil.isNotEmpty(removeList)){
                removeList.forEach(x->x.setUserStatus(SysUserStatusEnum.DISABLED.getValue()));
                sysUserService.updateBatchById(removeList);
            }
            //维护关系
            if(CollUtil.isNotEmpty(addThirdUserList)){
                authThirdUserService.saveBatch(addThirdUserList);
            }
        }
    }

}
