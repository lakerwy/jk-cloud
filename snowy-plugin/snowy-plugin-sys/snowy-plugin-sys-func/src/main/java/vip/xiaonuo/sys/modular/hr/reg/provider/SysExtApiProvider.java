package vip.xiaonuo.sys.modular.hr.reg.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.api.SysExtApi;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageService;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyResultService;

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

}
