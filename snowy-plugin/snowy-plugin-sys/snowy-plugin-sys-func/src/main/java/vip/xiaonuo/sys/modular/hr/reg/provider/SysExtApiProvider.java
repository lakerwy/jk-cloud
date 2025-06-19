package vip.xiaonuo.sys.modular.hr.reg.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.SysExtApi;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageService;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyResultService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysExtApiProvider implements SysExtApi {

    @Resource
    private RegSubsidyResultService regSubsidyResultService;

    @Resource
    private IcManageService icManageService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 注册补贴 审批 通过 处理结果反馈
     * @param paramMap
     * @return
     */
    @Override
    public Boolean saveRegSubsidyResult(Map<String,String> paramMap) {
        try {
            //获取流程中参数
            String personnelId = paramMap.get("personnelId");
            String idCardNumber = paramMap.get("idCardNumber");
            String name = paramMap.get("name");
            String resultJson = paramMap.get("resultJson");
            String regSubsidyId = paramMap.get("regSubsidyId");
            //将resultJson转换成实体(保留最新，历史删除)
            regSubsidyResultService.remove(new LambdaQueryWrapper<RegSubsidyResult>().eq(RegSubsidyResult::getPersonnelId, personnelId));
            List<RegSubsidyResult> addList = JSONUtil.toList(resultJson, RegSubsidyResult.class);
            if (CollUtil.isNotEmpty(addList)) {
                addList.forEach(item -> {
                    item.setId(null);
                    item.setPersonnelId(personnelId);
                    item.setIdCardNumber(idCardNumber);
                    item.setName(name);
                    item.setRegSubsidyId(regSubsidyId);
                });
                regSubsidyResultService.saveBatch(addList);
            }
            return true;
        }catch (Exception e){
            log.error("注册补贴 审批 通过 处理结果反馈数据 -- 异常 --" + e.getMessage());
            return false;
        }
    }



    /**
     * 工商信息 新增|变更|注销 审批 通过 处理
     * @param object
     * @return
     */
    @Override
    public Boolean dealIcManage(JSONObject object) {
        return icManageService.dealIcManage(object);
    }

    @Override
    public String getUserIdByName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getName,userName);
        queryWrapper.orderByDesc(SysUser::getUserStatus);
        queryWrapper.last(" limit 1 ");
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(sysUser)){
            return sysUser.getId();
        }
        return null;
    }

    @Override
    public String getOrgIdByName(String orgName) {
        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrg::getName,orgName);
        queryWrapper.last(" limit 1 ");
        SysOrg sysOrg = sysOrgService.getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(sysOrg)){
            return sysOrg.getId();
        }
        return null;
    }

}
