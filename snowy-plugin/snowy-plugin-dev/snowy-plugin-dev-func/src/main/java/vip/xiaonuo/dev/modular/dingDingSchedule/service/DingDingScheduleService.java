package vip.xiaonuo.dev.modular.dingDingSchedule.service;

import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;

import java.util.List;

public interface DingDingScheduleService {

    /**
     * 新增钉钉日程
     * */
    void addDingDingSchedule(List<DingDingScheduleParam> dingDingScheduleParamList, DingDingScheduleVo dingDingScheduleVo,String createUser);

    /**
     * 获取钉钉用户的unionId
     * @param token
     * @param phone 用户电话号码
     * */
    String getUnionId(String userId, String userName, String phone, String token) throws Exception;

    String dateTimeFormatConversion(String standardDateTimeStr,Integer flag);

    String putHttDingDingSchedule(DingDingScheduleVo dingDingScheduleVo) throws Exception;

    /** 删除日程 */
    void deleteDingDingSchedule(String token,String unionId,String scheduleId) throws Exception;
}
