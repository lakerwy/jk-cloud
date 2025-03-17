package vip.xiaonuo.sys.modular.dingtalk.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.*;
import com.fhs.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.config.DingAttendSyncProperties;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.dingtalk.enums.DingSyncTagEnum;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncAttendDataParam;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncDeptParam;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncUserParam;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;
import vip.xiaonuo.sys.modular.dingtalk.utils.SyncUtils;
import vip.xiaonuo.sys.modular.hr.emp.entity.AttendWhiteList;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttendDingding;
import vip.xiaonuo.sys.modular.hr.emp.enums.AttendWhiteListTypeEnum;
import vip.xiaonuo.sys.modular.hr.emp.service.AttendWhiteListService;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendDingdingService;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;

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

    @Resource
    private DingAttendSyncProperties dingAttendSyncProperties;

    /**
     * 钉钉组织架构顶层部门id
     */
    private static final Long DING_TOP_DEPT_ID = 1L;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private HrEmpAttendDingdingService hrEmpAttendDingdingService;

    private static final AtomicInteger syncAttendCount = new AtomicInteger(0);

    @Resource
    private AttendWhiteListService attendWhiteListService;

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
            CompletableFuture.runAsync(() -> {
                long start = System.currentTimeMillis();
                log.info("钉钉组织架构同步 -- 开始 -- ");
                //1. 获取钉钉access_token
                String access_token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());

                //2. 获取钉钉组织架构部门(递归-不含顶级1L部门)
                List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList = new ArrayList<>();
                CommonDingDingUtil.execRecursionDept(access_token, DING_TOP_DEPT_ID, dingDeptList);

                //3. 获取钉钉组织架构用户(递归)
                List<OapiV2UserListResponse.ListUserResponse> dingUserList = new ArrayList<>();
                CommonDingDingUtil.execRecursionDeptUser(access_token, DING_TOP_DEPT_ID, 0L, 50L, dingUserList); //集团下人员
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
                syncDeptUser(dingDeptList, sysOrgList, dingUserList, sysUserList, access_token);
                log.info("钉钉组织架构同步 -- 结束 -- 耗时：" + (System.currentTimeMillis() - start));
            });
        }
    }

    @Override
    public void syncUser(SyncUserParam syncUserParam) {
        if(CollUtil.isEmpty(syncUserParam.getUserList())){
            throw new CommonException("参数缺失");
        }

        //待同步用户中，已存在
        List<SysUser> existSysUserList = new ArrayList<>();
        List<String> mobileList = syncUserParam.getUserList().stream().map(x->x.getMobile()).distinct().collect(Collectors.toList());
        if(CollUtil.isNotEmpty(mobileList)) {
            existSysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getAccount,mobileList));
        }

        List<SysUser> updateUserList = new ArrayList<>();
        List<String> addDingUserIds = new ArrayList<>();
        //钉钉token
        String access_Token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());
        //本次需要同步的用户相关信息
        for(SyncUserParam.UserParam item : syncUserParam.getUserList()){
            //获取钉钉信息
            OapiV2UserGetbymobileResponse.UserGetByMobileResponse byMobile = CommonDingDingUtil.getByMobile(access_Token, item.getMobile());
            if(ObjectUtil.isEmpty(byMobile)){
                throw new CommonException("钉钉不存在手机号为:{} 的用户",item.getMobile());
            }
            SysUser existSysUser = existSysUserList.stream().filter(x -> item.getMobile().equals(x.getAccount()) && item.getName().equals(x.getName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(existSysUser)){
                existSysUser.setDingUserId(byMobile.getUserid());
                existSysUser.setDingSyncTag(DingSyncTagEnum.UPDATE.getValue());
                updateUserList.add(existSysUser);
            }else{
                addDingUserIds.add(byMobile.getUserid());
            }
        }

        //建科 系统组织架构信息
        List<SysOrg> jkSysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().isNotNull(SysOrg::getDingDeptId));
        //建科 系统用户信息（携带钉钉id）
        List<SysUser> jkSysUserList = sysUserService.getDingSysUser();
        //新增
        if(CollUtil.isNotEmpty(addDingUserIds)){
            List<SysUser> addUserList = new ArrayList<>();
            List<AuthThirdUser> addThirdUserList = new ArrayList<>();
            //循环增员
            for (String dingUserId : addDingUserIds){
                OapiV2UserGetResponse.UserGetResponse dingUser = CommonDingDingUtil.getUserById(access_Token, dingUserId);
                //新增SysUser
                SysUser tmpAddUser = SyncUtils.addUser(dingUserId,dingUser.getManagerUserid(),dingUser.getMobile(),dingUser.getName(),dingUser.getMobile(),jkSysOrgList,dingUser.getDeptIdList());
                addUserList.add(tmpAddUser);
                //添加第三方库
                AuthThirdUser tmpAddThirdUser = SyncUtils.addThirdUser(dingUser.getName(),tmpAddUser.getId(),dingUser.getUserid(),dingUser.getUnionid());
                addThirdUserList.add(tmpAddThirdUser);
            }
            jkSysUserList.addAll(addUserList);  //避免新增的员工中有主管

            //处理其他信息
            if(CollUtil.isNotEmpty(addUserList)){
                addUserList.forEach(user->{
                    user.setDingSyncMemo(DateUtil.now()+" 操作：【手动触发接口同步指定用户】 - 新增 ");
                    user.setUpdateTime(new Date());
                    try {
                        if (ObjectUtil.isNotEmpty(user.getDingDirectorId())) {
                            SysUser tmpSysUser = jkSysUserList.stream().filter(x -> user.getDingDirectorId().equals(x.getDingUserId())).findFirst().orElse(null);
                            if(ObjectUtil.isNotEmpty(tmpSysUser)) user.setDirectorId(tmpSysUser.getId());
                        }
                        sysUserService.addAndGrantRole2(user);
                    }catch (Exception e){
                        log.error("【手动触发接口同步指定用户："+ user.getAccount() +"】 -- 操作数据库 异常 --"+e.getMessage());
                    }
                });
            }
            if(CollUtil.isNotEmpty(addThirdUserList)){
                authThirdUserService.saveBatch(addThirdUserList);
            }
        }
        //更新
        if(CollUtil.isNotEmpty(updateUserList)){
            for(SysUser user : updateUserList){
                //更新信息
                OapiV2UserGetResponse.UserGetResponse dingUser = CommonDingDingUtil.getUserById(access_Token, user.getDingUserId());
                user.setOrgId(SyncUtils.getOrgId(jkSysOrgList,dingUser.getDeptIdList()));
                user.setName(dingUser.getName());
                user.setEmail(dingUser.getEmail());
                user.setDingDirectorId(dingUser.getManagerUserid());
                user.setPhone(dingUser.getMobile());
                //找到直属主管 - 对应建科云用户id
                if(ObjectUtil.isNotEmpty(dingUser.getManagerUserid())) {
                    SysUser tmpSysUser = jkSysUserList.stream().filter(x -> dingUser.getManagerUserid().equals(x.getDingUserId())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpSysUser)) user.setDirectorId(tmpSysUser.getId());
                }
                user.setDingSyncMemo(DateUtil.now()+" 操作：【手动触发接口同步指定用户】 - 更新 ");
                user.setUpdateTime(new Date());
            }
            sysUserService.updateBatchById(updateUserList);
        }
    }

    @Override
    public void syncDept(SyncDeptParam syncDeptParam) {
        SysOrg parentDept = sysOrgService.queryEntity(syncDeptParam.getParentDeptId());
        if(ObjectUtil.isEmpty(parentDept.getDingDeptId())){
            throw new CommonException("参数异常，上级部门dingDeptId缺失");
        }

        //钉钉token
        String access_Token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());
        //获取parentDingDeptId下子部门
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> listSub = CommonDingDingUtil.getListSub(access_Token, parentDept.getDingDeptId());
        if(CollUtil.isEmpty(listSub)){
            throw new CommonException("检查钉钉是否存在该上级部门");
        }
        //找到对应钉钉部门
        OapiV2DepartmentListsubResponse.DeptBaseResponse matchDingDept = listSub.stream().filter(x -> syncDeptParam.getDeptName().equals(x.getName())).findFirst().orElse(null);
        if(ObjectUtil.isEmpty(matchDingDept)){
            throw new CommonException("钉钉该上级部门下不存在部门名称：{}",syncDeptParam.getDeptName());
        }
        //对应钉钉部门详情
        OapiV2DepartmentGetResponse.DeptGetResponse dingDept = CommonDingDingUtil.getDeptById(access_Token, matchDingDept.getDeptId());

        //判断当前待同步的部门是否已存在【有可能修改过部门名称】
        SysOrg existSysOrg = sysOrgService.getOne(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getParentId,syncDeptParam.getParentDeptId())
                .eq(SysOrg::getCategory,SysOrgCategoryEnum.DEPT.getValue())
                .and(q->q.eq(SysOrg::getName,syncDeptParam.getDeptName()).or().eq(SysOrg::getDingDeptId,dingDept.getDeptId()))
                .last(" limit 1 "));

        //建科 系统用户信息（携带钉钉id）
        List<SysUser> jkSysUserList = sysUserService.getDingSysUser();
        //已存在，更新，否则，新增
        if(ObjectUtil.isNotEmpty(existSysOrg)){
            existSysOrg.setName(dingDept.getName());
            existSysOrg.setParentId(syncDeptParam.getParentDeptId());
            if(ObjectUtil.isNotEmpty(dingDept.getDeptManagerUseridList())) {
                existSysOrg.setDingDirectorId(JsonUtils.object2json(dingDept.getDeptManagerUseridList()));
                existSysOrg.setDirectorId(SyncUtils.getManageId(jkSysUserList, dingDept.getDeptManagerUseridList()));
            }
            existSysOrg.setDingSyncMemo(DateUtil.now()+" 操作：【手动触发接口同步指定用户】 - 更新 ");
            existSysOrg.setUpdateTime(new Date());
            sysOrgService.updateById(existSysOrg);

            /********************************************* 清理缓存 **********************************************************/
            commonCacheOperator.removeKeys(ORG_FIRST_CODE_CACHE_KEY);

        }else {
            SysOrg tmpAddOrg = SyncUtils.addOrg(dingDept.getName(),dingDept.getDeptId(),dingDept.getParentId());
            if(CollUtil.isNotEmpty(dingDept.getDeptManagerUseridList())) {
                tmpAddOrg.setDingDirectorId(JsonUtils.object2json(dingDept.getDeptManagerUseridList()));
                tmpAddOrg.setDirectorId(SyncUtils.getManageId(jkSysUserList, dingDept.getDeptManagerUseridList()));
            }
            tmpAddOrg.setParentId(syncDeptParam.getParentDeptId());
            tmpAddOrg.setDingSyncMemo(DateUtil.now()+" 操作：【手动触发接口同步指定用户】 - 新增 ");
            tmpAddOrg.setUpdateTime(new Date());
            sysOrgService.save(tmpAddOrg);
        }
    }


    /**
     * 同步规则： dingDeptList 钉钉没有返回DeptId=1
     */

    @DSTransactional
    public void syncDeptUser(List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList,List<SysOrg> sysOrgList,
                             List<OapiV2UserListResponse.ListUserResponse> dingUserList,List<SysUser> sysUserList,String access_Token){
        if(CollUtil.isEmpty(dingDeptList)) return;
        if(CollUtil.isEmpty(dingUserList)) return;

        /********************************************* 同步组织 - 基础数据准备 **********************************************************/
        log.info("钉钉组织架构同步 -- 部门 -- ");
        //获取DingDeptId = 1L
        SysOrg tmpTopSysOrg = sysOrgList.stream().filter(x -> DING_TOP_DEPT_ID.equals(x.getDingDeptId())).findFirst().orElse(null);
        //同步组织架构（DEPT级别 - 不包括 DingDeptId = 1）
        List<SysOrg> tmpSysOrgList = sysOrgList.stream().filter(x -> SysOrgCategoryEnum.DEPT.getValue().equals(x.getCategory())).collect(Collectors.toList());
        //待操作的sysOrg【DEPT级别 - 新增|删除|更新】
        List<SysOrg> orgData = BeanUtil.copyToList(tmpSysOrgList, SysOrg.class);
        //定义一个全组织架构[用于组织查询 - 含顶级]
        List<SysOrg> tmpAllOrgData = new ArrayList<>();
        tmpAllOrgData.add(tmpTopSysOrg);

        //1.1 维护部门对应关系 dingDeptId(为旧数据打上标记 - 更新|删除)
        if (CollUtil.isNotEmpty(orgData)) {
            List<Long> dingDeptIds = dingDeptList.stream().map(x -> x.getDeptId()).collect(Collectors.toList());
            for (SysOrg org : orgData) {
                //匹配对应关系 DingDeptId DingParentId
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

        //1.2 判断需要新增的（钉钉中存在，但sysOrg中不存在）
        List<Long> existDingDeptIds = orgData.stream().filter(x -> ObjectUtil.isNotEmpty(x.getDingDeptId())).map(x -> x.getDingDeptId()).collect(Collectors.toList());
        dingDeptList.forEach(dingDept -> {
            if (!existDingDeptIds.contains(dingDept.getDeptId())) {
                SysOrg tempAdd = SyncUtils.addOrg(dingDept.getName(),dingDept.getDeptId(),dingDept.getParentId());
                orgData.add(tempAdd);
            }
        });

        //1.3 【***】添加组织资源
        tmpAllOrgData.addAll(orgData);

        /********************************************* 同步用户 - 基础数据准备 **********************************************************/
        log.info("钉钉组织架构同步 -- 用户 -- ");
        //获取已存在的 钉钉对应关系
        LambdaQueryWrapper<AuthThirdUser> thirdUserLambdaQueryWrapper = new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getCategory, AuthThirdCategoryEnum.DingDing.getValue());
        List<AuthThirdUser> thirdUserList = authThirdUserService.list(thirdUserLambdaQueryWrapper);
        //待操作的SysUser（新增|删除|更新）
        List<SysUser> userData = BeanUtil.copyToList(sysUserList,SysUser.class);
        //待新增的对应关系
        List<AuthThirdUser> thirdUserData = new ArrayList<>();

        //2.1 维护用户对应关系，并标记
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
                        thirdUserData.add(SyncUtils.addThirdUser(user.getName(),user.getId(),tmpDingUser.getUserid(),tmpDingUser.getUnionid()));
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
                    user.setOrgId(SyncUtils.getOrgId(tmpAllOrgData,tmpDingUser.getDeptIdList()));
                }
            }
        }

        //2.2 判断是否新增用户
        List<String> existDingUserIds = userData.stream().filter(x -> ObjectUtil.isNotEmpty(x.getDingUserId())).map(x -> x.getDingUserId()).collect(Collectors.toList());
        dingUserList.forEach(dingUser->{
            if(!existDingUserIds.contains(dingUser.getUserid())){
                SysUser tmpSysUser = SyncUtils.addUser(dingUser.getUserid(),null,dingUser.getMobile(), dingUser.getName(), dingUser.getMobile(), tmpAllOrgData, dingUser.getDeptIdList());
                userData.add(tmpSysUser);
                //添加第三方库
                thirdUserData.add(SyncUtils.addThirdUser(dingUser.getName(), tmpSysUser.getId(), dingUser.getUserid(), dingUser.getUnionid()));
            }
        });

        /********************************************* 同步用户&组织 - 特殊字段处理 **********************************************************/
        //3.1 处理用户的上级主管
        log.info("钉钉组织架构同步 -- 用户 - 直属主管 -- ");
        if(CollUtil.isNotEmpty(userData)){
            userData.stream().filter(x->!DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).forEach(user->{
                //找到钉钉用户
                OapiV2UserGetResponse.UserGetResponse tmpDingUser = CommonDingDingUtil.getUserById(access_Token, user.getDingUserId());
                if(ObjectUtil.isNotEmpty(tmpDingUser) && ObjectUtil.isNotEmpty(tmpDingUser.getManagerUserid())) {
                    user.setDingDirectorId(tmpDingUser.getManagerUserid());
                    //找到直属主管 - 对应建科云用户id
                    SysUser tmpSysUser = userData.stream().filter(x -> tmpDingUser.getManagerUserid().equals(x.getDingUserId())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpSysUser)) user.setDirectorId(tmpSysUser.getId());
                }
            });
        }
        log.info("钉钉组织架构同步 -- 部门 - 上级组织|部门主管 -- ");
        //3.2 处理组织的上级组织 | 组织的部门主管
        if (CollUtil.isNotEmpty(orgData)) {
            orgData.stream().filter(x -> !DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).forEach(org -> {
                //上级组织
                SysOrg tmpSysOrg = tmpAllOrgData.stream().filter(x -> org.getDingParentId().equals(x.getDingDeptId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpSysOrg)) org.setParentId(tmpSysOrg.getId());
                //处理部门主管
                OapiV2DepartmentGetResponse.DeptGetResponse tmpDingDept = CommonDingDingUtil.getDeptById(access_Token, org.getDingDeptId());
                if(ObjectUtil.isNotEmpty(tmpDingDept) && CollUtil.isNotEmpty(tmpDingDept.getDeptManagerUseridList())){
                    org.setDingDirectorId(JsonUtils.object2json(tmpDingDept.getDeptManagerUseridList()));
                    org.setDirectorId(SyncUtils.getManageId(userData,tmpDingDept.getDeptManagerUseridList()));
                }
            });
        }

        /********************************************* 同步用户&组织 - 同步更新 **********************************************************/
        //4.1 更新部门信息
        log.info("钉钉组织架构同步 -- 部门 - 操作数据库 -- ");
        orgData.forEach(item->{
            item.setDingSyncMemo(DateUtil.now()+" 操作：手动同步 "+item.getDingSyncTag());
            item.setUpdateTime(new Date());
        });
        List<SysOrg> addOrgList = orgData.stream().filter(x -> DingSyncTagEnum.ADD.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        List<SysOrg> updateOrgList = orgData.stream().filter(x -> DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        List<SysOrg> removeOrgList = orgData.stream().filter(x -> DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(addOrgList)) {
            sysOrgService.saveBatch(addOrgList);
        }
        if (CollUtil.isNotEmpty(updateOrgList)) {
            sysOrgService.updateBatchById(updateOrgList);
        }
        if (CollUtil.isNotEmpty(removeOrgList)) {
            sysOrgService.updateBatchById(removeOrgList); //修改备注
            sysOrgService.removeBatchByIds(removeOrgList);
        }

        //4.2 更新用户信息
        log.info("钉钉组织架构同步 -- 用户 - 操作数据库 -- ");
        userData.forEach(item->{
            item.setDingSyncMemo(DateUtil.now()+" 操作：手动同步 "+item.getDingSyncTag());
            item.setUpdateTime(new Date());
        });
        List<SysUser> addUserList = userData.stream().filter(x->DingSyncTagEnum.ADD.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        List<SysUser> updateUserList = userData.stream().filter(x->DingSyncTagEnum.UPDATE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        List<SysUser> removeUserList = userData.stream().filter(x->DingSyncTagEnum.REMOVE.getValue().equals(x.getDingSyncTag())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(addUserList)){
            addUserList.forEach(user -> {
                try {
                    sysUserService.addAndGrantRole2(user);
                }catch (Exception e){
                    log.error("钉钉组织架构同步"+e.getMessage());
                }
             }
            );
        }
        if(CollUtil.isNotEmpty(thirdUserData)){
            authThirdUserService.saveBatch(thirdUserData);
        }
        if(CollUtil.isNotEmpty(updateUserList)){
            sysUserService.updateBatchById(updateUserList);
        }
        if(CollUtil.isNotEmpty(removeUserList)){
            removeUserList.forEach(x->x.setUserStatus(SysUserStatusEnum.DISABLED.getValue()));
            sysUserService.updateBatchById(removeUserList);
        }

        /********************************************* 清理缓存 **********************************************************/
        commonCacheOperator.removeKeys(ORG_FIRST_CODE_CACHE_KEY);

    }

    @Override
    public void syncAttendData(SyncAttendDataParam syncAttendDataParam, String type) {
        log.info("----- 钉钉考勤数据同步 --- 开始 -----");
        long startDealTime = System.currentTimeMillis();
        // 给定的年月，例如202501
        String yearMonthStr = syncAttendDataParam.getYM();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 解析给定的字符串为YearMonth对象
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, formatter);
        // 获取该月的第一天和最后一天，并格式化为字符串
        String firstDay = yearMonth.atDay(1).format(outputFormatter);
        String lastDay = yearMonth.atEndOfMonth().format(outputFormatter);
        log.info("----- 钉钉考勤数据同步 --- 同步时间范围：{}-{} -----",firstDay,lastDay);
        // 获取钉钉用户列表
        List<SysUser> dingSysUserList = sysUserService.getDingUser();
//        List<HrEmpAttendDingding> existList = hrEmpAttendDingdingService.list(new LambdaQueryWrapper<HrEmpAttendDingding>().eq(HrEmpAttendDingding::getYearMonth, yearMonthStr));
        hrEmpAttendDingdingService.remove(new LambdaQueryWrapper<HrEmpAttendDingding>().eq(HrEmpAttendDingding::getYearMonth, yearMonthStr));
        // 测试用，只取10个用户
//        dingSysUserList = dingSysUserList.subList(0,10);
//        dingSysUserList = dingSysUserList.stream().filter(x->"01234221013126077624".equals(x.getDingUserId())).collect(Collectors.toList());
        if(CollUtil.isEmpty(dingSysUserList)){
            log.info("----- 钉钉考勤数据同步 --- 无需同步 -----");
            return;
        }
        // 获取钉钉token
        String access_Token = CommonDingDingUtil.getToken(dingAttendSyncProperties.getAppKey(), dingAttendSyncProperties.getAppSecret());
        // 获取考勤报表列定义
        OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo attcolumns = CommonDingDingUtil.getAttcolumns(access_Token);
        if(ObjectUtil.isEmpty(attcolumns) || CollUtil.isEmpty(attcolumns.getColumns())){
            log.info("----- 钉钉考勤数据同步 --- 获取考勤报表列为空 -----");
            return;
        }
        // 筛选需要的列，并填充列ID
        Map<String, Long> columnMap = SyncUtils.getAttendColumnMap();
        List<String> columnNames = new ArrayList<>(columnMap.keySet());
        List<Long> columnIdList = attcolumns.getColumns().stream()
            .filter(x -> {
                if(columnNames.contains(x.getName())){
                    columnMap.put(x.getName(), x.getId());
                    return true;
                }
                return false;
            })
            .map(OapiAttendanceGetattcolumnsResponse.ColumnForTopVo::getId).collect(Collectors.toList());
        // 列ID转为字符串，用逗号分隔
        String column_id_list = columnIdList.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(","));
        List<HrEmpAttendDingding> hrEmpAttendDingdingList = new ArrayList<>();
        // 提前获取白名单信息，用于后续比对
        List<AttendWhiteList> attendWhiteListList = attendWhiteListService.list();
        for (SysUser sysUser : dingSysUserList) {
            log.info("----- 钉钉考勤数据同步 --- 当前同步用户：{} -----",sysUser.getName());
            HrEmpAttendDingding hrEmpAttendDingding = new HrEmpAttendDingding();
            String dingUserId = sysUser.getDingUserId();
            // 获取考勤报表列值
            OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo columnValVo = CommonDingDingUtil.getColumnval(access_Token, dingUserId, column_id_list,
                    firstDay + " 00:00:00", lastDay +" 23:59:59");
            // 考勤报表列值为空
            if(ObjectUtil.isEmpty(columnValVo) || CollUtil.isEmpty(columnValVo.getColumnVals())){
                log.info("----- 钉钉考勤数据同步 --- 获取考勤报表列值为空 -----");
                continue;
            }
            // 应出勤天数
            List<OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal> requiredDayVals = new ArrayList<>();
            // 出勤天数
            List<OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal> realDayVals = new ArrayList<>();
            // 考勤结果
            List<OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal> attendResVals = new ArrayList<>();
            // key,value反转
            Map<Long,String> reversedMap = columnMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            for(OapiAttendanceGetcolumnvalResponse.ColumnValForTopVo column_vals: columnValVo.getColumnVals()){
                Long id = column_vals.getColumnVo().getId();
                String value = reversedMap.get(id);
                switch (value){
                    case "应出勤天数":
                        requiredDayVals = column_vals.getColumnVals();
                        float requiredDay = 0.0f;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            requiredDay = column_vals.getColumnVals().stream().map(item -> new BigDecimal(item.getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();
                        }
                        hrEmpAttendDingding.setRequiredDay(requiredDay);
                        break;
                    case "出勤天数":
                        realDayVals = column_vals.getColumnVals();
                        // 出勤天数不取钉钉数据，这里统计一下是为了判断返聘员工的出勤
                        float realDay = 0.0f;
                        if(CollUtil.isNotEmpty(realDayVals)){
                            realDay = realDayVals.stream().map(item -> new BigDecimal(item.getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();
                        }
                        hrEmpAttendDingding.setRealDay(realDay);
                        break;
                    case "上班缺卡次数":
                        Integer upMissNum = 0;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            upMissNum = column_vals.getColumnVals().stream().mapToInt(item -> Integer.parseInt(item.getValue())).sum();
                        }
                        hrEmpAttendDingding.setUpMissNum(upMissNum);
                        break;
                    case "下班缺卡次数":
                        Integer downMissNum = 0;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            downMissNum = column_vals.getColumnVals().stream().mapToInt(item -> Integer.parseInt(item.getValue())).sum();
                        }
                        hrEmpAttendDingding.setDownMissNum(downMissNum);
                        break;
                    case "加班总时长":
                        float overtimeHour = 0.0f;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            overtimeHour = column_vals.getColumnVals().stream().map(item -> new BigDecimal(item.getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();
                        }
                        hrEmpAttendDingding.setOvertimeHour(overtimeHour);
                        break;
                    case "旷工天数":
                        float minerDay = 0.0f;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            minerDay = column_vals.getColumnVals().stream().map(item -> new BigDecimal(item.getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();
                        }
                        hrEmpAttendDingding.setMinerDay(minerDay);
                        break;
                    case "迟到次数":
                        Integer lateNum = 0;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            lateNum = column_vals.getColumnVals().stream().mapToInt(item -> Integer.parseInt(item.getValue())).sum();
                        }
                        hrEmpAttendDingding.setLateNum(lateNum);
                        break;
                    case "严重迟到次数":
                        Integer extremelyLateNum = 0;
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            extremelyLateNum = column_vals.getColumnVals().stream().mapToInt(item -> Integer.parseInt(item.getValue())).sum();
                        }
                        hrEmpAttendDingding.setExtremelyLateNum(extremelyLateNum);
                        break;
                    case "考勤结果":
                        // 迟到A,B,C类
                        Integer lateNumA = 0;
                        Integer lateNumB = 0;
                        Integer lateNumC = 0;
                        Integer lateNumD = 0;// 迟到超过45分钟的次数
                        // 实扣未打卡(上班、下班)
                        Integer realUpMissNum = 0;
                        Integer realDownMissNum = 0;
                        Integer realUpMissNumExclude = 0;
                        Integer realDownMissNumExclude = 0;
                        attendResVals = column_vals.getColumnVals();
                        if(CollUtil.isNotEmpty(column_vals.getColumnVals())){
                            for (OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal item : column_vals.getColumnVals()) {
                                realUpMissNum += item.getValue().contains("上班1缺卡") ? 1 : 0;
                                realDownMissNum += item.getValue().contains("下班2缺卡") ? 1 : 0;
                                realUpMissNumExclude += item.getValue().contains("上班1缺卡")
                                        && !item.getValue().contains("出差") && !item.getValue().contains("外出")? 1 : 0;
                                realDownMissNumExclude += item.getValue().contains("下班2缺卡")
                                        && !item.getValue().contains("出差") && !item.getValue().contains("外出")? 1 : 0;
                                Integer lateMins = SyncUtils.getLateMins(item.getValue());
                                if (ObjectUtil.isNotNull(lateMins)) {
                                    if (lateMins <= 15) {
                                        lateNumA++;
                                    } else if (lateMins <= 30) {
                                        lateNumB++;
                                    } else if (lateMins <= 45) {
                                        lateNumC++;
                                    }else if(lateMins > 45){
                                        lateNumD ++;
                                    }
                                }
                            }
                        }
                        hrEmpAttendDingding.setRealUpMissNum(realUpMissNum);
                        hrEmpAttendDingding.setRealDownMissNum(realDownMissNum);
                        hrEmpAttendDingding.setRealUpMissNumExclude(realUpMissNumExclude);
                        hrEmpAttendDingding.setRealDownMissNumExclude(realDownMissNumExclude);
                        hrEmpAttendDingding.setLateNumA(lateNumA);
                        hrEmpAttendDingding.setLateNumB(lateNumB);
                        hrEmpAttendDingding.setLateNumC(lateNumC);
                        hrEmpAttendDingding.setLateNumD(lateNumD);
                        break;
                    default:
                        break;
                }
            }
            // 需要扣除的天数
            Integer invalidDays = 0;
            // 异地打卡人员：当日有签到记录即可记为当天全勤
            if(SyncUtils.isOutCheckPersonnel(sysUser.getOrgId())){
                for(OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal requireDayVal : requiredDayVals) {
                    if(new BigDecimal(requireDayVal.getValue()).compareTo(BigDecimal.ZERO) <= 0){
                        continue;
                    }
                    // 获取当天的应出勤信息
                    OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal realDayVal = realDayVals.stream()
                            .filter(x -> x.getDate().getTime() == requireDayVal.getDate().getTime()).findFirst().orElse(null);
                    if(new BigDecimal(realDayVal.getValue()).compareTo(BigDecimal.ZERO) > 0) {
                        continue;
                    }
                    // 查询当天签到信息
                    Long startTime = requireDayVal.getDate().getTime();
                    Long endTime = DateUtil.offsetSecond(DateUtil.offsetDay(requireDayVal.getDate(), 1),-1).getTime();
                    OapiCheckinRecordGetResponse.PageResult checkinRecord = CommonDingDingUtil.getCheckinRecord(access_Token, dingUserId, startTime, endTime, 0L, 100L);
                    // 无签到记录
                    if (ObjectUtil.isEmpty(checkinRecord) || CollUtil.isEmpty(checkinRecord.getPageList())) {
                        invalidDays ++;
                    }
                }
            }else {
                // 考勤结果中包含出差或者外出，需要查询签到情况，有签到记录算当天全勤,如果没有签到记录，有打卡记录，那打卡需要早晚上下班正常打卡两次算全勤
                for(OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal attendResVal : attendResVals) {
                    if (attendResVal.getValue().contains("出差") || attendResVal.getValue().contains("外出")) {
                        // 获取当天的应出勤信息
                        OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal requiredDayVal = requiredDayVals.stream()
                                .filter(x -> x.getDate().getTime() == attendResVal.getDate().getTime()).findFirst().orElse(null);
                        // 当天不需要出勤
                        if(new BigDecimal(requiredDayVal.getValue()).compareTo(BigDecimal.ZERO) <= 0) {
                            continue;
                        }
                        // 查询当天签到信息
                        Long startTime = attendResVal.getDate().getTime();
                        Long endTime = DateUtil.offsetSecond(DateUtil.offsetDay(attendResVal.getDate(), 1),-1).getTime();
                        OapiCheckinRecordGetResponse.PageResult checkinRecord = CommonDingDingUtil.getCheckinRecord(access_Token, dingUserId, startTime, endTime, 0L, 100L);
                        boolean isValidCheck = false; // 是否为有效打卡
                        // 是否签到
                        if (ObjectUtil.isNotEmpty(checkinRecord) && CollUtil.isNotEmpty(checkinRecord.getPageList())) {
                            isValidCheck = true;
                        } else {
                            // 查询当天打卡记录
                            String workDateFrom = DateUtil.format(attendResVal.getDate(), DatePattern.NORM_DATETIME_PATTERN);
                            String workDateTo = DateUtil.format(DateUtil.offsetSecond(DateUtil.offsetDay(attendResVal.getDate(), 1),-1), DatePattern.NORM_DATETIME_PATTERN);
                            List<OapiAttendanceListResponse.Recordresult> attendanceList = CommonDingDingUtil.getAttendanceList(access_Token, dingUserId, workDateFrom, workDateTo, 0L, 50L);
                            if(CollUtil.isNotEmpty(attendanceList)){
                                // 上班打卡
                                List<OapiAttendanceListResponse.Recordresult> onDutyList = attendanceList.stream()
                                        .filter(attendance -> "OnDuty".equals(attendance.getCheckType()) && !"NotSigned".equals(attendance.getTimeResult()))
                                        .collect(Collectors.toList());
                                // 下班打卡
                                List<OapiAttendanceListResponse.Recordresult> offDutyList = attendanceList.stream()
                                        .filter(attendance -> "OffDuty".equals(attendance.getCheckType()) && !"NotSigned".equals(attendance.getTimeResult()))
                                        .collect(Collectors.toList());
                                if(CollUtil.isNotEmpty(onDutyList) && CollUtil.isNotEmpty(offDutyList)){
                                    Date onDutyBaseTime = DateUtil.offsetHour(attendResVal.getDate(),12);// 获取当天12点
                                    Date offDutyBaseTime = DateUtil.offsetHour(attendResVal.getDate(),14);// 获取当天14点
                                    // 当天12点前有上班打卡 且 当天14点后有下班打卡 记为有效打卡
                                    List<OapiAttendanceListResponse.Recordresult> onDutyCheckList = onDutyList.stream().filter(x -> x.getUserCheckTime().before(onDutyBaseTime))
                                            .collect(Collectors.toList());
                                    List<OapiAttendanceListResponse.Recordresult> offDutyCheckList = offDutyList.stream().filter(x -> x.getUserCheckTime().after(offDutyBaseTime))
                                            .collect(Collectors.toList());
                                    if(CollUtil.isNotEmpty(onDutyCheckList) && CollUtil.isNotEmpty(offDutyCheckList)){
                                        isValidCheck = true;
                                    }
                                }
                            }
//                        String value = isValidCheck ? "1.0":"0.0";
//                        realDayVals.stream().filter(x -> x.getDate().getTime() == attendResVal.getDate().getTime())
//                                .forEach(x -> x.setValue(value));
                        }
                        if(!isValidCheck){
                            invalidDays ++;
                        }
                    }
                }
            }
            hrEmpAttendDingding.setInvalidDays(invalidDays); // 需要扣除的天数
            // 需要的列（假期）
            String leave_names = "事假,病假,婚假,产假,丧假,年假,调休";
            OapiAttendanceGetleavetimebynamesResponse.ColumnValListForTopVo leaveTimeVo = CommonDingDingUtil.getLeavetimebynames(access_Token, dingUserId, leave_names,
                    firstDay + " 00:00:00", lastDay + " 23:59:59");
            if(ObjectUtil.isEmpty(leaveTimeVo) || CollUtil.isEmpty(leaveTimeVo.getColumns())){
                log.info("----- 钉钉考勤数据同步 --- 获取考勤报表假期数据为空 -----");
                continue;
            }
            for(OapiAttendanceGetleavetimebynamesResponse.ColumnValForTopVo column_vals:leaveTimeVo.getColumns()){
                String columnName = column_vals.getColumnvo().getName();
                float value = 0.0f;
                if(CollUtil.isNotEmpty(column_vals.getColumnvals())){
                    value = column_vals.getColumnvals().stream().map(item -> new BigDecimal(item.getValue()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();
                }
                switch (columnName) {
                    case "事假":
                        hrEmpAttendDingding.setWorkDay(value);
                        break;
                    case "病假":
                        hrEmpAttendDingding.setSickDay(value);
                        break;
                    case "婚假":
                        hrEmpAttendDingding.setMarryDay(value);
                        break;
                    case "产假":
                        hrEmpAttendDingding.setMaterDay(value);
                        break;
                    case "丧假":
                        hrEmpAttendDingding.setFunDay(value);
                        break;
                    case "年假":
                        hrEmpAttendDingding.setYearDay(value);
                        break;
                    case "调休":
                        hrEmpAttendDingding.setAdjustHour(value);
                        break;
                    default:
                        break;
                }
            }
            hrEmpAttendDingding.setYearMonth(yearMonthStr);
            hrEmpAttendDingding.setUserName(sysUser.getName());
            hrEmpAttendDingding.setIdCardNumber(sysUser.getIdCardNumber());
            String firstOrgName = ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + sysUser.getOrgId()));
            hrEmpAttendDingding.setFirstOrgName(firstOrgName);
            hrEmpAttendDingding.setOrgName(sysUser.getOrgName());
            hrEmpAttendDingding.setUserId(sysUser.getId());
            hrEmpAttendDingding.setPersonnelId(sysUser.getPersonnelId());
            if(CollUtil.isNotEmpty(attendWhiteListList)){
                AttendWhiteList attendWhiteList = attendWhiteListList.stream()
                        .filter(x -> sysUser.getId().equals(x.getUserId())).findFirst().orElse(null);
                hrEmpAttendDingding.setType(ObjectUtil.isNotEmpty(attendWhiteList) ? attendWhiteList.getType():null);
            }
            hrEmpAttendDingdingList.add(hrEmpAttendDingding);
        }
        Float maxRequiredDay = hrEmpAttendDingdingList.stream()
                .map(HrEmpAttendDingding::getRequiredDay)
                .filter(Objects::nonNull)   // 过滤空值
                .max(Comparator.naturalOrder())
                .orElse(null);
        hrEmpAttendDingdingList.forEach(x -> {
            x.setNote(String.format(DateUtil.now()+"：钉钉同步考勤数据【%s】",type));
            // 设置应计薪天数为所有数据中的最大值
            x.setRequiredDay(maxRequiredDay);
            // 员工类型是否为高管或返聘人员
            // 高管仅统计事假、病假考勤，其他情形皆算全勤
            if(AttendWhiteListTypeEnum.LEADER.getValue().equals(x.getType())){
                BigDecimal realDayValue = BigDecimal.valueOf(maxRequiredDay)
                        .subtract(BigDecimal.valueOf(x.getSickDay()))
                        .subtract(BigDecimal.valueOf(x.getWorkDay()));
                x.setRealDay(realDayValue.floatValue());
            }else {
                Integer lateNumD = x.getLateNumD();
                // 迟到45分钟以上算半天事假
                BigDecimal workDayValue = BigDecimal.valueOf(lateNumD).multiply(BigDecimal.valueOf(0.5));
                x.setWorkDay(BigDecimal.valueOf(x.getWorkDay()).add(workDayValue).floatValue());
                // 未打卡次数计算出的实际需扣除的天数
                BigDecimal missNumExclude = BigDecimal.valueOf(x.getRealUpMissNumExclude()).add(BigDecimal.valueOf(x.getRealDownMissNumExclude()))
                        .divide(BigDecimal.valueOf(2),6,RoundingMode.HALF_UP);
                // 实勤天数=应计薪天数-旷工-事假-病假-产假-实扣未打卡次数之和/2
                BigDecimal realDayValue = BigDecimal.valueOf(maxRequiredDay)
                        .subtract(BigDecimal.valueOf(x.getMinerDay()))
                        .subtract(BigDecimal.valueOf(x.getWorkDay()))
                        .subtract(BigDecimal.valueOf(x.getSickDay()))
                        .subtract(BigDecimal.valueOf(x.getMaterDay()))
                        .subtract(missNumExclude)
                        .subtract(BigDecimal.valueOf(x.getInvalidDays()));
                x.setRealDay(realDayValue.floatValue() < 0 ? 0:realDayValue.floatValue());
            }
            // 返聘员工当月有打卡记录即为当月全勤 TODO
            if(AttendWhiteListTypeEnum.REHIRE.getValue().equals(x.getType())){
                if (x.getRealDay() > 0) {
                    x.setRealDay(maxRequiredDay);
                }
            }
            isFull(x);// 是否全勤
        });
        hrEmpAttendDingdingService.saveBatch(hrEmpAttendDingdingList);
        long endDealTime = System.currentTimeMillis();
        log.info("----- 钉钉考勤数据同步 --- 完成耗时：{}ms，同步用户数量：{} -----", endDealTime-startDealTime , hrEmpAttendDingdingList.size());
    }

    // 是否全勤
    private void isFull(HrEmpAttendDingding entity){
        if(AttendWhiteListTypeEnum.LEADER.getValue().equals(entity.getType())){
            if(entity.getWorkDay() > 0 || entity.getSickDay() > 0){
                entity.setIsFull("否");
            }else{
                entity.setIsFull("是");
            }
            return;
        }
        if(AttendWhiteListTypeEnum.REHIRE.getValue().equals(entity.getType())){
            if(entity.getRealDay() > 0){
                entity.setIsFull("是");
            }else{
                entity.setIsFull("否");
            }
            return;
        }
        if(entity.getWorkDay() > 0 || entity.getSickDay() > 0 || entity.getMarryDay() > 0
                || entity.getMaterDay() > 0 || entity.getFunDay() > 0 || entity.getYearDay() > 0
                || entity.getAdjustHour() > 0 || entity.getLateNum() > 0
                || entity.getExtremelyLateNum() > 0 || entity.getUpMissNum() > 0
                || entity.getDownMissNum() > 0 || entity.getMinerDay() > 0){
            entity.setIsFull("否");
        }else{
            entity.setIsFull("是");
        }
    }

    @Override
    public void syncAttendAsync(SyncAttendDataParam syncAttendDataParam) {
        if (syncAttendStatus()) {
            throw new CommonException("上次同步暂未结束，请稍后再试");
        }
        syncAttendCount.incrementAndGet();
        CompletableFuture.runAsync(() -> {
            try {
                syncAttendData(syncAttendDataParam, "手动触发");
            }catch (Exception e){
                log.error("钉钉考勤同步异常",e);
            }finally {
                decrementSyncAttendCountSafely();
            }
        });
    }

    private void decrementSyncAttendCountSafely() {
        if (syncAttendCount.get() > 0) {
            syncAttendCount.decrementAndGet();
        }
    }

    @Override
    public Boolean syncAttendStatus() {
        int i = syncAttendCount.get();
        return i > 0;
    }

}
