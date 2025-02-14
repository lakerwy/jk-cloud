package vip.xiaonuo.sys.modular.hr.emp.provider;

import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.DingTalkApi;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncAttendDataParam;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 钉钉API provider
 *
 * @author pzh
 * @date 2025/02/26 14:24
 **/
@Service
public class DingTalkApiProvider implements DingTalkApi {

    @Resource
    DingDingSyncService dingDingSyncService;

    @Override
    public void syncLastMonthAttend() {
        SyncAttendDataParam syncAttendDataParam = new SyncAttendDataParam();
        // 获取上一个月，例如202501
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String lastMonth = sdf.format(calendar.getTime());
        syncAttendDataParam.setYM(lastMonth);
        dingDingSyncService.syncAttendData(syncAttendDataParam, "定时任务触发");
    }
}
