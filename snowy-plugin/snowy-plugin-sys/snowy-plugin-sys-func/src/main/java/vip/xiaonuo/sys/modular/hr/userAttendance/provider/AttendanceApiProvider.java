package vip.xiaonuo.sys.modular.hr.userAttendance.provider;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.AttendanceApi;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 11:40
 */
@Component
public class AttendanceApiProvider implements AttendanceApi {
    @Resource
    HrUserAttendanceService hrUserAttendanceService;
    @Override
    public void attendance(JSONObject hrUserAttendanceAddParam) {
        HrUserAttendanceAddParam bean = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendanceAddParam.class);
        hrUserAttendanceService.attendance(bean);
    }
}
