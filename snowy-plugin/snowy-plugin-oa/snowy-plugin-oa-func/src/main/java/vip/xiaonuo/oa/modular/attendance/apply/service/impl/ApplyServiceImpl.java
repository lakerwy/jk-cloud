package vip.xiaonuo.oa.modular.attendance.apply.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.apply.entity.Apply;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;
import vip.xiaonuo.oa.modular.attendance.apply.mapper.ApplyMapper;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyPageParam;
import vip.xiaonuo.oa.modular.attendance.apply.service.ApplyService;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.enums.AttnObjectEnum;
import vip.xiaonuo.oa.modular.attendance.group.service.GroupService;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;
import vip.xiaonuo.oa.modular.attendance.rule.service.RuleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 考勤申请管理表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Service
public class ApplyServiceImpl<T extends ApplyJson> extends ServiceImpl<ApplyMapper<T>, Apply<T>> implements ApplyService<T> {


    @Resource
    private GroupService groupService;

    @Resource
    private RuleService ruleService;

    @Override
    public Page<Apply<T>> page(ApplyPageParam applyPageParam,Class<T> clazz) {
        QueryWrapper<Apply<T>> queryWrapper = new QueryWrapper<>();
        if(applyPageParam.getOwner()){
            queryWrapper.lambda().eq(Apply::getApplyUserId,StpLoginUserUtil.getLoginUser().getId());
        }
        if(applyPageParam.getApplyType() != 0){
            queryWrapper.lambda().eq(Apply::getApplyType,applyPageParam.getApplyType());
        }
        if(ObjectUtil.isAllNotEmpty(applyPageParam.getSortField(), applyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(applyPageParam.getSortOrder());
            queryWrapper.orderBy(true, applyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(applyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Apply::getId);
        }
        Page<Apply<T>> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        // 处理 applyJson 字段的类型转换
        List<Apply<T>> records = page.getRecords();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Apply<T> apply : records) {
            String applyJsonStr = apply.getApplyJsonStr();
            if(StrUtil.isNotEmpty(applyJsonStr)){
                try {
                    T t = objectMapper.readValue(applyJsonStr, clazz);
                    apply.setApplyJson(t);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ApplyAddParam applyAddParam) {
        Apply<T> apply = BeanUtil.toBean(applyAddParam, Apply.class);
        /**
         *  先获取请假规则(依次通过 用户id -> 部门Id -> 考勤组Id -> 公司Id )
         * 0、是否在请假规则内请假，比如：补请假申请，只能补前一天的
         * 1、 判断冲突情况  请假/加班/外出/等
         * 2、检查该时间段是否有请假
         * 3、判断请假是否在休息日，要给出提示
         * 4、计算请假时长
         * 2、根据请假类型，判断假期情况。
         */
        if(ObjectUtil.isNotEmpty(applyAddParam.getApplyJson())){
            ApplyJson applyJson = applyAddParam.getApplyJson();
            String  applyJsonStr = JSON.toJSONString(applyJson);
            apply.setApplyJsonStr(applyJsonStr);
        }
        this.save(apply);
    }

    /**
     * 校验是否符合请假规则
     */
    private void checkHolidayApplyRule(ApplyAddParam applyAddParam){
        int applyType = applyAddParam.getApplyType();
        if(applyType == RuleTypeEnum.APPLY_RULE.getType()){
            //请假审批


        }
    }

    /**
     * 判断是否有其他申请
     */
    private void checkOtherApply(){
        //todo
    }

    /**
     * 检查是否为无效申请
     * 比如 请假开始时间和结束时间都在节假期内，就是无效的
     */
    private void checkIsInvalidApply(){

    }

    /**
     * 计算时长
     * @return
     */
    private double calculateDuration(){
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ApplyEditParam applyEditParam) {
        Apply apply = this.queryEntity(applyEditParam.getId());
        BeanUtil.copyProperties(applyEditParam, apply);
        if(ObjectUtil.isNotEmpty(applyEditParam.getApplyJson())){
            ApplyJson applyJson = applyEditParam.getApplyJson();
            String  applyJsonStr = JSON.toJSONString(applyJson);
            apply.setApplyJsonStr(applyJsonStr);
        }
        this.updateById(apply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ApplyIdParam> applyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(applyIdParamList, ApplyIdParam::getId));
    }

    @Override
    public Apply detail(ApplyIdParam applyIdParam,Class<T> clazz) {
        Apply apply = this.queryEntity(applyIdParam.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String applyJsonStr = apply.getApplyJsonStr();
        try {
            T t = objectMapper.readValue(applyJsonStr, clazz);
            apply.setApplyJson(t);
        }catch (Exception e){
            e.printStackTrace();
        }

        return apply;
    }

    @Override
    public Apply queryEntity(String id) {
        Apply apply = this.getById(id);
        if(ObjectUtil.isEmpty(apply)) {
            throw new CommonException("考勤申请管理表不存在，id值为：{}", id);
        }
        return apply;
    }

    /**
     * 考勤组有规则的话就按考勤组,没有的话就采用默认的,默认的没有就算了
     * @return
     */
    @Override
    public Rule<OverWorkRule> getOwnerOverWorks(){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        Group group = groupService.getGroupByUserId(loginUser.getId());
        List<String> objectIds = new ArrayList<>();
        objectIds.add(group.getId());
        List<Rule<OverWorkRule>> ownerRules = ruleService.getOwnerRules(objectIds, AttnObjectEnum.GROUP_OBJECT.getType());
        if(CollUtil.isNotEmpty(ownerRules)){
            return ownerRules.get(0);
        }
        objectIds = new ArrayList<>();
        objectIds.add(loginUser.getOrgId());
        ownerRules = ruleService.getOwnerRules(objectIds, AttnObjectEnum.DEPT_OBJECT.getType());
        if(CollUtil.isNotEmpty(ownerRules)){
            return ownerRules.get(0);
        }
        return null;
    }
}
