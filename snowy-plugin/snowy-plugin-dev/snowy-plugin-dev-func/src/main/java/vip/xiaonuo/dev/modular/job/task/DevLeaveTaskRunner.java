/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1、离职在最后工作日+7天后删除账号
 * 2、转正日期到后，更改员工为正式员工
 * 3、调岗通过后、生效日期后，组织架构换到新的部门中去
 *
 * @author mayike
 * @date 2023/8/24 15:52
 **/
@Slf4j
@Component
public class DevLeaveTaskRunner implements CommonTimerTaskRunner {

    @Override
    public void action() {
        try {
            //1、离职在最后工作日+7天后删除账号
            String sql = "SELECT b.PHONE,b.ID FROM (SELECT FILE_ID FROM PERSONNEL_FILE_MOVES WHERE MOVE_TYPE= '2' AND STATUS = '1' AND TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'),LAST_DATE) = 0 ) a   LEFT JOIN PERSONNEL b ON a.FILE_ID = b.ID";
            log.info("离职人员查询----"+sql);
            List<Map<String, Object>> mapList = CommonHandleUtil.handleSqlQuery2(sql, Boolean.TRUE);
            if(CollUtil.isNotEmpty(mapList)){
                String fileId = mapList.stream().map(result -> "'" + result.get("PHONE") + "'").collect(Collectors.joining(","));
                String ids = mapList.stream().map(result -> "'" + result.get("ID") + "'").collect(Collectors.joining(","));
                if(StrUtil.isNotEmpty(fileId)){
                    sql = String.format("UPDATE SYS_USER SET `DELETE_FLAG` = 'DELETED'  WHERE ACCOUNT IN  (%s) ",fileId);
                    log.info("离职人员账户更新----"+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
//                    if(StrUtil.isNotEmpty(ids)){
//                        sql = String.format("UPDATE PERSONNEL SET `STATUS` = '1' where ID IN  (%s) ",ids);
//                        log.info("离职人员花名册更新----"+sql);
//                        CommonHandleUtil.handleSqlExecution(sql);
//                    }
                }
            }
            //2 转正日期到后，更改员工为正式员工
            sql = "SELECT FILE_ID FROM PERSONNEL_FILE_MOVES WHERE MOVE_TYPE= '0' AND STATUS = '1' AND TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'),POSITIVE_TIME) = 0 ";
            log.info("转正人员账户查询----"+sql);
            mapList = CommonHandleUtil.handleSqlQuery2(sql, Boolean.TRUE);
            if(CollUtil.isNotEmpty(mapList)){
                String fileId = mapList.stream().map(result -> "'" + result.get("FILE_ID") + "'").collect(Collectors.joining(","));
                if(StrUtil.isNotEmpty(fileId)){
                    sql = String.format("UPDATE PERSONNEL SET `EMPLOY_NATURE` = '正式' where ID IN  (%s) ",fileId);
                    log.info("转正人员账户更新---"+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
                }
            }

            //调岗后岗位调动
            sql = "SELECT b.PHONE,a.NOW_DEPT_ID,a.NOW_DUTY_ID, a.FILE_ID FROM (SELECT FILE_ID,NOW_DEPT_ID,NOW_DUTY_ID FROM PERSONNEL_FILE_MOVES WHERE MOVE_TYPE= '1' AND STATUS = '1' AND TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'),OPT_DATE) = 0 ) a   LEFT JOIN PERSONNEL b ON a.FILE_ID = b.ID";
            log.info("调岗账户查询----"+sql);
            mapList = CommonHandleUtil.handleSqlQuery2(sql, Boolean.TRUE);
            if(CollUtil.isNotEmpty(mapList)){
                for (Map<String, Object> map : mapList) {
                    sql = "UPDATE SYS_USER SET `ORG_ID` = '" + map.get("NOW_DEPT_ID") +"' ,POSITION_ID = '"+ map.get("NOW_DUTY_ID") +"' WHERE `ACCOUNT` = '" + map.get("PHONE")+"'";
                    log.info("调岗账户更新----"+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
                    sql = "UPDATE PERSONNEL SET `DEPT_ID` = '" +map.get("NOW_DEPT_ID") + "' ,`DUTY_ID` = '" + map.get("NOW_DUTY_ID") +"' where ID = '" + map.get("FILE_ID") + "'";
                    log.info("调岗账户人事档案个更新----"+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
                }
            }

            log.info("人员移动情况......");
        }catch (Exception e)
        {
            log.info("人员移动情况......"+e.getMessage());
        }
    }
}
