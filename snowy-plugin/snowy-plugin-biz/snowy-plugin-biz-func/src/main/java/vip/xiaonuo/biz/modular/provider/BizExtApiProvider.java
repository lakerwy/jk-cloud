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
package vip.xiaonuo.biz.modular.provider;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.api.BizExtApi;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.service.PorProjectService;
import vip.xiaonuo.biz.modular.user.service.BizUserService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class BizExtApiProvider implements BizExtApi {

    @Resource
    private BizCompanyGoalService bizCompanyGoalService;

    @Resource
    private PorProjectService porProjectService;

    @Resource
    private BizOrgService bizOrgService;

    @Resource
    private BizUserService bizUserService;

    @Override
    public void updateCompanyGoal(JSONObject jsonObject) {
        log.info("更新-绩效考核-目标登记，参数值 jsonObject:{}", jsonObject);
        JSONArray jsonArray = new JSONArray();
        String goalId = null;
        try {
            goalId = jsonObject.getStr("id");
            jsonArray = jsonObject.getJSONArray("kpiList");
        } catch (Exception e){
            log.error("更新-绩效考核-目标登记时,参数转换异常");
        }
        List<BizCompanyGoalKpi> kpiList = JSONUtil.toList(jsonArray, BizCompanyGoalKpi.class);
        log.info("更新-绩效考核-目标登记，参数值goalId:{}，kpiList:{}", goalId, kpiList);
        bizCompanyGoalService.updateCompanyGoal(goalId, kpiList);
    }

    @Override
    public void doWarn(String warnType) {
        porProjectService.doWarn(warnType);
    }

    @Override
    public void savePorProject(String bizProjectId, String areaLeader, String areaLeaderId, String dept, String deptId) {
        if(ObjectUtil.isNotEmpty(areaLeader) && ObjectUtil.isEmpty(areaLeaderId)){
            areaLeaderId = bizUserService.getUserIdByName(areaLeader);
        }
        if(ObjectUtil.isNotEmpty(dept) && ObjectUtil.isEmpty(deptId)){
            deptId = bizOrgService.getOrgIdByName(dept);
        }
        PorProject porProject = new PorProject();
        porProject.setProjectId(bizProjectId);
        porProject.setDutyDeptId(deptId);
        porProject.setDutyDeptName(dept);
        porProject.setDutyId(areaLeaderId);
        porProject.setDutyName(areaLeader);
        porProject.setProjectStatus("未开始");
        porProject.setMemo("立项审批通过-系统自动同步");
        porProjectService.save(porProject);
    }
}
