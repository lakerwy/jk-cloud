package vip.xiaonuo.oa.core.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.oa.modular.attendance.client.WebServerClient;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayRule;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayBalanceService;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayRuleService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/5 11:05
 * 初始化员工假期余额 task
 */
@Component
@Slf4j
public class HolidayBalanceTask extends AbstractTask{

    @Resource
    private WebServerClient webServerClient;

    @Resource
    private HolidayBalanceService holidayBalanceService;

    @Resource
    private HolidayRuleService holidayRuleService;

    public HolidayBalanceTask() {
        super("HolidayBalanceTask");
    }

//    @PostConstruct
//    public void init() {
//       scheduleTask();
//    }

    @Override
    protected void doTask(){
        List<HolidayRule> holidayRules = holidayRuleService.list();
        DateTime date = DateUtil.date();
        int year = date.year();
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("parentId",0);
        String org = webServerClient.getOrgByMap(mapParam);
        JSONObject jsonObject = JSONUtil.parseObj(org);
        String data = jsonObject.getStr("data");
        JSONObject sysOrg = JSONUtil.parseObj(data);
        String childrenUser = webServerClient.getChildrenUser(sysOrg.getStr("id"));
        JSONObject userJson = JSONUtil.parseObj(childrenUser);
        JSONArray jsonArray = userJson.getJSONArray("data");
        for (Object object : jsonArray) {
            JSONObject user = (JSONObject) object;
            for (HolidayRule rule : holidayRules) {
                HolidayBalance holidayBalance = new HolidayBalance();
                holidayBalance.setUserId(user.getStr("id"));
                holidayBalance.setHolidayTypeId(rule.getId());
                holidayBalance.setBalance(BigDecimal.valueOf(-1));
                holidayBalance.setYear(year);
                holidayBalanceService.save(holidayBalance);
            }
        }
    }

    @Scheduled(cron = "0 0 1 01 01 ?") // 每年1月1日凌晨1点
    public void scheduleTask() {
        execute();
    }
}
