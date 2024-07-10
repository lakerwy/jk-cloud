package vip.xiaonuo.oa.core.task;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayRule;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayRuleService;


import javax.annotation.Resource;


/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/15 13:11
 * 日期规则
 */
@Component
@Slf4j
public class HolidayRuleTask extends AbstractTask{

    @Resource
    private HolidayRuleService holidayRuleService;

//    @PostConstruct
//    public void init() {
//       scheduleTask();
//    }

    public HolidayRuleTask() {
        super("HolidayRuleTask");
    }

    /**
     * 初始化一些“基本假期类型”
     * @throws Exception
     */
    @Override
    protected void doTask() throws Exception {
        /***
         * 基础假期 ：1、年假  2、事假 3、病假 4、婚假 5、产假 6、陪产假 7、丧假 8、调休
         */
        String[] holidayNames = {"年假", "事假", "病假", "婚假", "产假", "陪产假", "丧假", "调休"};

        for (String holidayName : holidayNames) {
            HolidayRule holidayRule = new HolidayRule();
            holidayRule.setHolidayName(holidayName);
            holidayRule.setLeaveUnit("day");
            holidayRule.setDayType(0);
            holidayRule.setExpireFlag(true);
            holidayRule.setSetWay(0);
            holidayRule.setExpireLimit(0);

            // 查询是否已存在该假期类型
            HolidayRule existingRule = holidayRuleService.getOne(new LambdaQueryWrapper<HolidayRule>().eq(HolidayRule::getHolidayName, holidayName));
            // 如果不存在则保存
            if (existingRule == null) {
                holidayRuleService.save(holidayRule);
            }
        }
    }

    @Scheduled(initialDelay = 300000, fixedDelay = Long.MAX_VALUE) // 延迟5分钟执行，仅执行一次
    public void scheduleTask() {
        execute();
    }
}
