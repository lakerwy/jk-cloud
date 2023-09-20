package vip.xiaonuo.biz.core.context.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.SysAttendanceApi;
import vip.xiaonuo.sys.feign.SysHrAttendanceFeign;

/**
 * 用户Api上下文Bean
 *
 * @author ljy
 * @date 2023/9/20 16:29
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SysAttendanceApiContextBean implements SysAttendanceApi {

    private final SysHrAttendanceFeign sysHrAttendanceFeign;

    @Override
    public void  resubmitAttendance(String processInstanceId, String applyType){
        sysHrAttendanceFeign.dealApplyAttendance(processInstanceId,applyType);
    }
}
