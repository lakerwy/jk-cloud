package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;
import vip.xiaonuo.flw.modular.flwbiz.project.service.BizProjectService;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.entity.BizProjectRegister;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.BizProjectRegisterService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;

@Slf4j
@Component
public class FlwNewProjectTaskListener implements ExecutionListener {

    @Resource
    FormRelationTableService formRelationTableService;
    @Resource
    BizProjectService bizProjectService;

    @Resource
    BizProjectRegisterService bizProjectRegisterService;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        //获取流程实例关联的业务信息
        FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
        if(ObjectUtil.isNotEmpty(info)){
            //根据businessKey 从project中获取APPROVAL_NO 单号 更新立项登记的立项情况 BIZ_PROJECT_REGISTER 的 is_Approval
            //update BIZ_PROJECT_REGISTER set is_Approval = "已立项" where id = (select APPROVAL_NO  from BIZ_PROJECT where id = businessKey)
            BizProject bizProject = bizProjectService.getById(info.getId());
            if(ObjectUtil.isAllNotEmpty(bizProject,bizProject.getInfoNo())){
                //String sql = "update BIZ_PROJECT_REGISTER set is_Approval = \"已立项\" where SN = '"+bizProject.getInfoNo()+"'";
                UpdateWrapper<BizProjectRegister> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().eq(BizProjectRegister::getSn,bizProject.getInfoNo());
                updateWrapper.lambda().set(BizProjectRegister::getIsApproval,"已立项");
                bizProjectRegisterService.update(updateWrapper);
                log.info("更新立项申请表字段is_Approval-----");
            }
        }
    }
}
