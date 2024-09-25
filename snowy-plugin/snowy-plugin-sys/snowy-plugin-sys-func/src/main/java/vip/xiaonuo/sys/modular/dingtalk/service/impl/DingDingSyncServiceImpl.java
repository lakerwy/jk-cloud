package vip.xiaonuo.sys.modular.dingtalk.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.enums.SysOrgCategoryEnum;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉 - 数据 - 同步  服务实现类
 */

@Service
public class DingDingSyncServiceImpl implements DingDingSyncService {

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private SysOrgService sysOrgService;


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
        CommonDingDingUtil.execRecursionDept(access_token,1L,dingDeptList);

        //3. 获取建科云组织信息
        List<SysOrg> sysOrgList = sysOrgService.getAllOrgList();

//        //6. 同步钉钉组织架构到建科云
//        dingDeptList.forEach(dingDept->{
//            //根据部门名称匹配 add|update|delete
//
//
//
//        });
//        //3. 获取钉钉组织架构用户(递归)
//        List<OapiV2UserListResponse.ListUserResponse> dingUserList = new ArrayList<>();
//        dingDeptList.forEach(item->CommonDingDingUtil.execRecursionDeptUser(access_token,item.getDeptId(),0L,50L,dingUserList));
//
//        //4. 同步部门


    }



    public void syncDept(List<OapiV2DepartmentListsubResponse.DeptBaseResponse> dingDeptList){
        if(CollUtil.isEmpty(dingDeptList)) {
            return;
        }
        //同步组织架构（DEPT级别）
        List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getCategory, SysOrgCategoryEnum.DEPT.getValue()));
        /**
         * 同步规则：
         * 1. 若已存在dingDeptId（已有对应关系），关注部门是否调整
         * 2. 若不存在dingDeptId（暂无对应关系），根据部门名称匹配：
         *    若不存在，则新增
         *    若已存在，则关注部门信息是否调整
         *    若已删除，则移除
         */
        //1. 维护对应关系 dingDeptId
    }



}
