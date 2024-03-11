package vip.xiaonuo.dev.modular.dingDingSchedule.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.dingDingSchedule.service.DingDingScheduleService;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StartDingDingScheduleCore {

    //@Value("${dingschedulestream.appKey}")
    private String appKey;

    //@Value("${dingschedulestream.appSecret}")
    private String appSecret;


    private static final String DD_CATEGORY = "DingDing";

    @Resource
    IDingDingService iDingDingService;
    @Resource
    AuthThirdUserService authThirdUserService;
    @Resource
    DingDingScheduleService dingDingScheduleService;

    /**   钉钉日程   */
    public void addDingDingSchedule( DingDingScheduleVo dingDingScheduleVo){
        try{
            if(StringUtils.isEmpty(dingDingScheduleVo.getId())){
                log.error("id表示不能为空 - (可以是任何可反查的唯一标识)！");
                return;
            }
            if(CollectionUtil.isEmpty(dingDingScheduleVo.getDingDingScheduleParamList())){
                log.error("无日程参与人！id:"+dingDingScheduleVo.getId());
                return;
            }
            if(isLackParam(dingDingScheduleVo)){
                log.error("开始时间，结束时间和日程标题为必传！");
                return;
            }
            String xAcsDingtalkAccessToken = iDingDingService.getDingToken(appKey,appSecret);//获取token

            List<AuthThirdUser> authThirdUserList = authThirdUserService.list(new LambdaQueryWrapper<AuthThirdUser>()
                    .eq(AuthThirdUser::getCategory,DD_CATEGORY).in(AuthThirdUser::getUserId,dingDingScheduleVo.getDingDingScheduleParamList().stream().map(param -> param.getUserId()).collect(Collectors.toList())));
            Map<String,AuthThirdUser> authMap = new HashMap<>();
            authThirdUserList.forEach(authThird -> authMap.put(authThird.getUserId(),authThird));//此处逻辑防止在auth表中无third数据
            List<String> unionIds = getUnionIds(dingDingScheduleVo, authMap, xAcsDingtalkAccessToken);
            if(CollectionUtil.isEmpty(unionIds)){
                log.error("未找到钉钉unionId数据！id:"+dingDingScheduleVo.getId());
                return;
            }
            String unionCreateId = authThirdUserService.getUnionIdByUserId(dingDingScheduleVo.getCreateUser(),DD_CATEGORY);
            if(StringUtils.isEmpty(unionCreateId)){
                unionCreateId = unionIds.get(0);//不存在，则默认第一个
            }
            dingDingScheduleVo.setUnionCreateId(unionCreateId);
            dingDingScheduleService.putHttDingDingSchedule(dingDingScheduleVo);
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
}
