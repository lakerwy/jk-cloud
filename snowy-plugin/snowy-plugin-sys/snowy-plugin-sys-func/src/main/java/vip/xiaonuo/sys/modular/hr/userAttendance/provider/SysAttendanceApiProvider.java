package vip.xiaonuo.sys.modular.hr.userAttendance.provider;

import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.SysAttendanceApi;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userHolidayData.service.HrUserHolidayDataService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 11:40
 */
@Component
public class SysAttendanceApiProvider implements SysAttendanceApi {
    @Resource
    HrUserAttendanceService hrUserAttendanceService;
    @Resource
    HrUserHolidayDataService hrUserHolidayDataService;
    @Override
    public void resubmitAttendance(String processInstanceId, String applyType) {
        hrUserAttendanceService.resubmitAttendance(processInstanceId,applyType);
    }

    @Override
    public void attendanceStatJobHandle(){
        hrUserAttendanceService.attendanceStatJobHandle();
    }

    @Override
    public void calculateHolidayJobHandle(String processInstanceId){
        hrUserHolidayDataService.calculateHolidayJobHandle(processInstanceId);
    }

    @Override
    public void rezToRuanLianJobHandle(){
        hrUserAttendanceService.rezToRuanLianJobHandle();
    }
}
