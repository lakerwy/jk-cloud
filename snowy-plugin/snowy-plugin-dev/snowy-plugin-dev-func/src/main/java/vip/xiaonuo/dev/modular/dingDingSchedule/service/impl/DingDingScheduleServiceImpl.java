package vip.xiaonuo.dev.modular.dingDingSchedule.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkcalendar_1_0.models.CreateEventResponse;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaPair;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.service.DingDingScheduleService;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;

import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@DS("slave_1")
public class DingDingScheduleServiceImpl implements DingDingScheduleService {

    private static final String appKey = "dingdpzuakabx5swx1xz";
    private static final String appSecret = "wLyIifhR9Xi8OY9phul0OpS32A2Mksj4p5nbgDirva0ZnRBn7ecJWM7SVnOTLjYN";

    private static final String DD_CATEGORY = "DingDing";
    private static final String TIME_ZONE = "Asia/Shanghai";
    private static final String REMINDER_TYPE = "dingtalk";

    @Resource
    IDingDingService iDingDingService;
    @Resource
    AuthThirdUserService authThirdUserService;

    @Override
    public void addDingDingSchedule(List<DingDingScheduleParam> dingDingScheduleParamList, DingDingScheduleVo dingDingScheduleVo,String createUser){
        try{
            String xAcsDingtalkAccessToken = iDingDingService.getDingToken(appKey,appSecret);//获取token
            List<String> unionIds = new ArrayList<>();

            /**  该遍历逻辑后续优化，优化改动较大，暂时用遍历 * */
            dingDingScheduleParamList.forEach(param -> {
                String unionId = authThirdUserService.getUnionIdByUserId(param.getUserId(),DD_CATEGORY);
                if(StringUtils.isEmpty(unionId)){
                    try{
                        unionId = getUnionId(param.getUserId(),param.getUserName(),param.getPhone(),xAcsDingtalkAccessToken);
                    }catch (Exception e){
                        log.error("获取getUnionId失败！e"+e.getMessage());
                    }
                }
                unionIds.add(unionId);
            });
            /**  日程创建人 */
            String unionCreateId = authThirdUserService.getUnionIdByUserId(createUser,DD_CATEGORY);
            if(StringUtils.isEmpty(unionCreateId)){
                unionCreateId = unionIds.get(0);//不存在，则默认第一个
            }
            dingDingScheduleVo.setUnionCreateId(unionCreateId);
            dingDingScheduleVo.setUnionIds(unionIds);
            dingDingScheduleVo.setXAcsDingtalkAccessToken(xAcsDingtalkAccessToken);
            dingDingScheduleVo.setReminderMinutes(480);
            putHttDingDingSchedule(dingDingScheduleVo);
        }catch (Exception e){
            log.error("创建日程失败！e"+e.getMessage());
        }
    }

    /**
     * 获取钉钉用户的unionId
     * @param userId 用户ID
     * @param token
     * */
    @Override
    public String getUnionId(String userId,String userName,String phone,String token) throws Exception{
        String thirdId = authThirdUserService.getThirdIdByUserId(userId,DD_CATEGORY);

        if(StringUtils.isEmpty(thirdId)){
            OapiV2UserGetbymobileResponse response = iDingDingService.getByMobile(phone);
            thirdId = response.getResult().getUserid();
        }
        OapiV2UserGetResponse dingUserInfo = CommonDingDingUtil.getUnionId(token,thirdId);
        String unionId = ObjectUtil.isNotEmpty(dingUserInfo) &&  ObjectUtil.isNotEmpty(dingUserInfo.getResult())
                && StringUtils.isNotEmpty(dingUserInfo.getResult().getUnionid())? dingUserInfo.getResult().getUnionid() : "";
        authThirdUserService.updateAuthThird(userId,thirdId,DD_CATEGORY,userName,unionId);
        return unionId;
    }

    @Override
    public String putHttDingDingSchedule(DingDingScheduleVo dingDingScheduleVo) throws Exception{

        com.aliyun.dingtalkcalendar_1_0.Client client = CommonDingDingUtil.createScheduleClient();
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventHeaders createEventHeaders = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventHeaders();
        createEventHeaders.xAcsDingtalkAccessToken = dingDingScheduleVo.getXAcsDingtalkAccessToken();

        java.util.Map<String, String> extra = TeaConverter.buildMap(
                new TeaPair("noChatNotification", "true"),
                new TeaPair("noPushNotification","true")
        );
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestReminders reminders0 = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestReminders()
                .setMethod(REMINDER_TYPE)
                .setMinutes(dingDingScheduleVo.getReminderMinutes());//日程提醒：dingtalk代表应用内体系，体系时间可做配置
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestLocation location = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestLocation()
                .setDisplayName(dingDingScheduleVo.getLocation());//日程地点
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestAttendees attendees0 = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestAttendees();
        dingDingScheduleVo.getUnionIds().forEach(unionId -> {
            attendees0.setId(unionId).setIsOptional(false);//参与者
        });

        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestStart start = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestStart()
                .setDateTime(dateTimeFormatConversion(dingDingScheduleVo.getStartTime(),0))
                //.setDateTime("2024-03-07T10:15:30+08:00")
                .setTimeZone(TIME_ZONE);//开始时间
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestEnd end = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest.CreateEventRequestEnd()
                .setDateTime(dateTimeFormatConversion(dingDingScheduleVo.getEndTime(),1))
                //.setDateTime("2024-03-07T11:16:30+08:00")
                .setTimeZone(TIME_ZONE);//结束时间
        com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest createEventRequest = new com.aliyun.dingtalkcalendar_1_0.models.CreateEventRequest()
                .setSummary(dingDingScheduleVo.getSummary())
                .setDescription(dingDingScheduleVo.getDescription())
                .setStart(start)
                .setEnd(end)
                .setIsAllDay(false)
                .setAttendees(java.util.Arrays.asList(attendees0))
                .setLocation(location)
                .setReminders(java.util.Arrays.asList(reminders0))
                .setExtra(extra);
        try {
            //userId :日程组织者的unionId     calendarId：固定值 primary
            CreateEventResponse createEventResponse = client.createEventWithOptions(dingDingScheduleVo.getUnionCreateId(), "primary",
                    createEventRequest, createEventHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            log.info("创建日程成功！code:"+createEventResponse.getStatusCode());
            return createEventResponse.getBody().getId();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("TeaException:生成钉钉日历失败，err-code:"+err.code+",err-message:"+err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("Exception:生成钉钉日历失败，err-code:"+err.code+",err-message:"+err.message);
            }
        }
        return "";
    }

    @Override
    public String dateTimeFormatConversion(String standardDateTimeStr,Integer flag){
        standardDateTimeStr = standardDateTimeStr.substring(0,10); // 解析日期部分
        LocalDate localDate = LocalDate.parse(standardDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 解析时间部分，如果flag有特殊需求，则覆盖时间
        LocalTime localTime;
        if (flag == 0) {
            localTime = LocalTime.of(0, 0);
        } else if (flag == 1) {
            localTime = LocalTime.of(23, 59);
        } else {
            localTime = LocalTime.parse(standardDateTimeStr.split("T")[1], DateTimeFormatter.ofPattern("HH:mm"));
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);// 合并日期和时间
        ZoneId targetZone = ZoneId.of("Asia/Shanghai");// 获取东八区（中国标准时间）的ZoneId
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, targetZone);// 将目标LocalDateTime转换为ZonedDateTime
        DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;// 创建ISO-8601格式的DateTimeFormatter
        return zonedDateTime.format(outputFormatter);// 格式化为ISO-8601格式的字符串
    }

    @Override
    public void deleteDingDingSchedule(String token,String unionId,String scheduleId) throws Exception{
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(unionId) || StringUtils.isEmpty(scheduleId)){
            log.error("deleteDingDingSchedule：参数不能为空！");
            return;
        }
        com.aliyun.dingtalkcalendar_1_0.Client client = CommonDingDingUtil.createScheduleClient();
        com.aliyun.dingtalkcalendar_1_0.models.DeleteEventHeaders deleteEventHeaders = new com.aliyun.dingtalkcalendar_1_0.models.DeleteEventHeaders();
        deleteEventHeaders.xAcsDingtalkAccessToken = token;
        try {
            client.deleteEventWithOptions(unionId, "primary", scheduleId, deleteEventHeaders, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("TeaException:删除钉钉日历失败，err-code:"+err.code+",err-message:"+err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("Exception:删除钉钉日历失败，err-code:"+err.code+",err-message:"+err.message);

            }
        }
    }
}
