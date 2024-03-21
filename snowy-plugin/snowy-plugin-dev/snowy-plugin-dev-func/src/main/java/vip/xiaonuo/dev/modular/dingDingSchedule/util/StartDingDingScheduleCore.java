package vip.xiaonuo.dev.modular.dingDingSchedule.util;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.service.DingDingScheduleService;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
import vip.xiaonuo.dev.modular.job.task.CommonHandleUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StartDingDingScheduleCore {

    private static final String DD_CATEGORY = "DingDing";

    @Resource
    IDingDingService iDingDingService;
    @Resource
    AuthThirdUserService authThirdUserService;
    @Resource
    DingDingScheduleService dingDingScheduleService;

    public String taskAddSchedule(String id,String appKey,String appSecret){
        String scheduleId = "";
        if(StringUtils.isEmpty(id)){return scheduleId;}
        try{
            String sql = "SELECT ID,TASK_NAME,TASK_REMARK,TASK_START_TIME,TASK_END_TIME,BELONGER,CREATE_USER,SN FROM BIZ_TASK_INFO " +
                    " WHERE ID = '"+id+"'";
            Map<String,Object> map= CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
            if(ObjectUtil.isEmpty(map)){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("BELONGER"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_START_TIME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_END_TIME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_NAME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("CREATE_USER"))){return scheduleId;}

            List<DingDingScheduleParam> dingDingScheduleParamList = new ArrayList<>();
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            dbsApi.changeDataSource("master");
            String sqlUser = "select  NAME,ACCOUNT from SYS_USER  WHERE ID = '"+map.get("BELONGER").toString()+"'";
            Map<String,Object> userMap= CommonHandleUtil.handleSqlQuery(sqlUser, Boolean.TRUE);
            //SysUser sysUser = sysUserService.getById(map.get("BELONGER").toString());
            DingDingScheduleParam dingDingScheduleParam = new DingDingScheduleParam();
            dingDingScheduleParam.setUserId(map.get("BELONGER").toString());
            dingDingScheduleParam.setUserName(ObjectUtil.isNotEmpty(userMap.get("NAME")) ? userMap.get("NAME").toString() : "");
            dingDingScheduleParam.setPhone(ObjectUtil.isNotEmpty(userMap.get("ACCOUNT")) ? userMap.get("ACCOUNT").toString() : "");
            dingDingScheduleParamList.add(dingDingScheduleParam);
            if(ObjectUtil.isEmpty(userMap.get("NAME")) || ObjectUtil.isEmpty(userMap.get("ACCOUNT"))){return scheduleId;}

            DingDingScheduleVo dingDingScheduleVo = new DingDingScheduleVo();
            dingDingScheduleVo.setDingDingScheduleParamList(dingDingScheduleParamList);
            dingDingScheduleVo.setStartTime(map.get("TASK_START_TIME").toString());
            dingDingScheduleVo.setEndTime(map.get("TASK_END_TIME").toString());
            dingDingScheduleVo.setSummary(map.get("TASK_NAME").toString());
            dingDingScheduleVo.setDescription(ObjectUtil.isNotEmpty(map.get("TASK_REMARK")) ? map.get("TASK_REMARK").toString() : "");
            dingDingScheduleVo.setId(map.get("ID").toString());
            dingDingScheduleVo.setCreateUser(StpUtil.getLoginIdAsString());
            scheduleId =  addDingDingSchedule(dingDingScheduleVo, appKey, appSecret);
        }catch (Exception e){
            log.error("任务创建日程失败！e:"+e.getMessage());
        }
        return scheduleId;
    }

    /**   钉钉日程   */
    public String addDingDingSchedule( DingDingScheduleVo dingDingScheduleVo,String appKey,String appSecret){

        if(StringUtils.isEmpty(appKey) || StringUtils.isEmpty(appSecret)){
            log.error("appKey和appSecret不能为空！");
            throw new CommonException("appKey和appSecret不能为空！");
        }
        if(StringUtils.isEmpty(dingDingScheduleVo.getId())){
            log.error("id表示不能为空 - (可以是任何可反查的唯一标识)！");
            return "";
        }
        if(CollectionUtil.isEmpty(dingDingScheduleVo.getDingDingScheduleParamList())){
            log.error("无日程参与人！id:"+dingDingScheduleVo.getId());
            return "";
        }
        if(isLackParam(dingDingScheduleVo)){
            log.error("开始时间，结束时间和日程标题为必传！");
            return "";
        }
        try{
            String xAcsDingtalkAccessToken = iDingDingService.getDingToken(appKey,appSecret);//获取token

            List<AuthThirdUser> authThirdUserList = authThirdUserService.list(new LambdaQueryWrapper<AuthThirdUser>()
                    .eq(AuthThirdUser::getCategory,DD_CATEGORY).in(AuthThirdUser::getUserId,dingDingScheduleVo.getDingDingScheduleParamList().stream().map(param -> param.getUserId()).collect(Collectors.toList())));
            Map<String,AuthThirdUser> authMap = new HashMap<>();
            authThirdUserList.forEach(authThird -> authMap.put(authThird.getUserId(),authThird));//此处逻辑防止在auth表中无third数据
            List<String> unionIds = getUnionIds(dingDingScheduleVo, authMap, xAcsDingtalkAccessToken);
            if(CollectionUtil.isEmpty(unionIds)){
                log.error("未找到钉钉unionId数据！id:"+dingDingScheduleVo.getId());
                return "";
            }
            String unionCreateId = authThirdUserService.getUnionIdByUserId(dingDingScheduleVo.getCreateUser(),DD_CATEGORY);
            if(StringUtils.isEmpty(unionCreateId)){
                unionCreateId = unionIds.get(0);//不存在，则默认第一个
            }
            dingDingScheduleVo.setUnionCreateId(unionCreateId);
            dingDingScheduleVo.setUnionIds(unionIds);
            dingDingScheduleVo.setXAcsDingtalkAccessToken(xAcsDingtalkAccessToken);
            dingDingScheduleVo.setReminderMinutes(480);//暂定前一天五点提醒
            return dingDingScheduleService.putHttDingDingSchedule(dingDingScheduleVo);
        }catch (Exception e){
            throw new CommonException("新建日程失败！e:"+e.getMessage()+",id:"+dingDingScheduleVo.getId());
        }
    }
    /**  获取钉钉unionId */
    private List<String> getUnionIds(DingDingScheduleVo dingDingScheduleVo,Map<String,AuthThirdUser> authMap,String xAcsDingtalkAccessToken) throws Exception{
        List<String> unionIds = new ArrayList<>();
        dingDingScheduleVo.getDingDingScheduleParamList().forEach(param -> {
            if(ObjectUtil.isEmpty(authMap.get(param.getUserId())) && StringUtils.isNotEmpty(param.getPhone())){//无auth数据
                try{
                    OapiV2UserGetbymobileResponse response = iDingDingService.getByMobile(param.getPhone());
                    String thirdId = response.getResult().getUserid();
                    OapiV2UserGetResponse dingUserInfo = CommonDingDingUtil.getUnionId(xAcsDingtalkAccessToken,thirdId);

                    String unionId = ObjectUtil.isNotEmpty(dingUserInfo) &&  ObjectUtil.isNotEmpty(dingUserInfo.getResult())
                            && StringUtils.isNotEmpty(dingUserInfo.getResult().getUnionid())? dingUserInfo.getResult().getUnionid() : "";
                    authThirdUserService.updateAuthThird(param.getUserId(),thirdId,DD_CATEGORY,param.getUserName(),unionId);
                    unionIds.add(unionId);
                }catch (Exception e){
                    log.error("查询钉钉用户失败-无auth数据！e:"+e.getMessage()+",userId:"+param.getUserId());
                }
            }else if(StringUtils.isEmpty(authMap.get(param.getUserId()).getUnionId())){//有thirdId数据，但无unionId数据
                try{
                    OapiV2UserGetResponse dingUserInfo = CommonDingDingUtil.getUnionId(xAcsDingtalkAccessToken,authMap.get(param.getUserId()).getThirdId());
                    String unionId = ObjectUtil.isNotEmpty(dingUserInfo) &&  ObjectUtil.isNotEmpty(dingUserInfo.getResult())
                            && StringUtils.isNotEmpty(dingUserInfo.getResult().getUnionid())? dingUserInfo.getResult().getUnionid() : "";
                    authThirdUserService.updateAuthThird(param.getUserId(),authMap.get(param.getUserId()).getThirdId(),DD_CATEGORY,param.getUserName(),unionId);
                    unionIds.add(unionId);
                }catch (Exception e){
                    log.error("查询钉钉用户失败-无unionId！e:"+e.getMessage()+",userId:"+param.getUserId());
                }
            }else if(StringUtils.isNotEmpty(authMap.get(param.getUserId()).getUnionId())){//都存在
                unionIds.add(authMap.get(param.getUserId()).getUnionId());
            }
        });
        return unionIds;
    }

    private boolean isLackParam(DingDingScheduleVo dingDingScheduleVo){
        if(StringUtils.isEmpty(dingDingScheduleVo.getStartTime())){return true;}
        if(StringUtils.isEmpty(dingDingScheduleVo.getEndTime())){return true;}
        if(StringUtils.isEmpty(dingDingScheduleVo.getSummary())){return true;}
        return false;
    }

    public void  deleteScheduleForUserId(String appKey,String appSecret,String scheduleId,String userId) throws Exception{
        List<AuthThirdUser> authThirdUserList = authThirdUserService.list(new LambdaQueryWrapper<AuthThirdUser>()
                .eq(AuthThirdUser::getCategory,DD_CATEGORY).eq(AuthThirdUser::getUserId,userId));
        String xAcsDingtalkAccessToken = iDingDingService.getDingToken(appKey,appSecret);//获取token
        for(AuthThirdUser authThirdUser : authThirdUserList){
            if(StringUtils.isNotEmpty(authThirdUser.getUnionId())){
                deleteScheduleCore(xAcsDingtalkAccessToken,authThirdUser.getUnionId(),scheduleId);
            }
        }
    }

    public void  deleteScheduleCore(String token,String unionId,String scheduleId) throws Exception{
        dingDingScheduleService.deleteDingDingSchedule(token,unionId,scheduleId);
    }
}
