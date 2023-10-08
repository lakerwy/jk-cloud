package vip.xiaonuo.biz.modular.rule.service;

import vip.xiaonuo.biz.modular.rule.entity.BizRuleManage;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeAddParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeEditParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeIdParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangePageParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BizRuleManageService extends IService<BizRuleManage> {
    Page<BizRuleManage> page(BizRuleMangePageParam bizRuleMangePageParam);


    void add(BizRuleMangeAddParam bizRuleMangeAddParam);

    BizRuleManage queryEntity(String id);

    void edit(BizRuleMangeEditParam bizRuleMangeEditParam);


    void delete(List<BizRuleMangeIdParam> bizRuleMangeIdParamList);

    BizRuleManage detail(BizRuleMangeIdParam bizRuleMangeIdParam);
}