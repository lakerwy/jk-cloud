package vip.xiaonuo.sys.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * 系统考勤申请模块综合Feign
 *
 * @author dongxiayu
 * @date 20223/09/20 15:29
 */
@FeignClient(name= FeignConstant.WEB_APP, contextId = "SysHrAttendanceFeign")
public interface SysHrAttendanceFeign {

    /**
     * 处理考勤申请的业务
     *
     * @author ljy
     * @date 2023/9/20 15:19
     **/
    @RequestMapping("/feign/sys/applyattendance/dealApplyAttendance")
    void dealApplyAttendance(@RequestParam(value = "processInstanceId",required = true) String processInstanceId,@RequestParam(value = "applyType",required = true) String applyType);

    /**
     * 处理考勤申请的业务
     *
     * @author ljy
     * @date 2023/9/20 15:19
     **/
    @RequestMapping("/feign/sys/applyattendance/attendanceStatJobHandle")
    void attendanceStatJobHandle();

    /**
     * 处理请假之后的流程
     *
     * @author ljy
     * @date 2023/10/27 10:19
     **/
    @RequestMapping("/feign/sys/applyattendance/calculateHolidayJobHandle")
    void calculateHolidayJobHandle(String processInstanceId);

    @RequestMapping("/feign/sys/applyattendance/rezToRuanLianJobHandle")
    void rezToRuanLianJobHandle();
}
