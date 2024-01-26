package vip.xiaonuo.web.core.provider.sys.applyattendance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.sys.api.SysAttendanceApi;
import vip.xiaonuo.sys.feign.SysHrAttendanceFeign;

/**
 * 补卡Feign提供者
 *
 * @author ljy
 * @date 2023/09/20 15:19
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SysHrAttendanceFeignProvider implements SysHrAttendanceFeign {
    private final SysAttendanceApi attendanceApi;


    /**
     * 处理考勤申请的业务
     *
     * @author ljy
     * @date 2023/9/20 15:19
     **/
    @Override
    @RequestMapping("/feign/sys/applyattendance/dealApplyAttendance")
    public void dealApplyAttendance(@RequestParam(value = "processInstanceId",required = true) String processInstanceId,@RequestParam(value = "applyType",required = true) String applyType){
        attendanceApi.resubmitAttendance(processInstanceId,applyType);
    }

    @Override
    @RequestMapping("/feign/sys/applyattendance/attendanceStatJobHandle")
    public void attendanceStatJobHandle(){
        attendanceApi.attendanceStatJobHandle();
    }

    @Override
    @RequestMapping("/feign/sys/applyattendance/calculateHolidayJobHandle")
    public void calculateHolidayJobHandle(String processInstanceId){
        //
        attendanceApi.calculateHolidayJobHandle(processInstanceId);
    }

    @Override
    @RequestMapping("/feign/sys/applyattendance/rezToRuanLianJobHandle")
    public void rezToRuanLianJobHandle(){
        attendanceApi.rezToRuanLianJobHandle();
    }

}
