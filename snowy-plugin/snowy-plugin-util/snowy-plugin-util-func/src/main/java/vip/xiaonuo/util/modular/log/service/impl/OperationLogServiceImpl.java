package vip.xiaonuo.util.modular.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.OperationLogEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.util.modular.log.entity.OperationLogEntity;
import vip.xiaonuo.util.modular.log.mapper.OperationLogMapper;
import vip.xiaonuo.util.modular.log.param.OperationLogAddParam;
import vip.xiaonuo.util.modular.log.param.OperationLogEditParam;
import vip.xiaonuo.util.modular.log.param.OperationLogIdParam;
import vip.xiaonuo.util.modular.log.param.OperationLogPageParam;
import vip.xiaonuo.util.modular.log.service.OperationLogService;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 操作日志Service接口实现类
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLogEntity> implements OperationLogService {

    @Override
    public Page<OperationLogEntity> page(OperationLogPageParam operationLogPageParam) {
        QueryWrapper<OperationLogEntity> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(operationLogPageParam.getCategory())) {
            queryWrapper.lambda().eq(OperationLogEntity::getCategory, operationLogPageParam.getCategory());
        }
        if(ObjectUtil.isNotEmpty(operationLogPageParam.getName())){
            queryWrapper.lambda().eq(OperationLogEntity::getName,operationLogPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(operationLogPageParam.getStartTime()) && ObjectUtil.isNotEmpty(operationLogPageParam.getEndTime())) {
            queryWrapper.lambda().between(OperationLogEntity::getOpTime,operationLogPageParam.getStartTime(),operationLogPageParam.getEndTime());
        }
        if(ObjectUtil.isNotEmpty(operationLogPageParam.getBusinessId())) {
            queryWrapper.lambda().eq(OperationLogEntity::getBusinessId, operationLogPageParam.getBusinessId());
        }
        if(ObjectUtil.isNotEmpty(operationLogPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(OperationLogEntity::getCategory, operationLogPageParam.getSearchKey())
                    .or().like(OperationLogEntity::getMemo,operationLogPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(operationLogPageParam.getSortField(), operationLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(operationLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, operationLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(operationLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(OperationLogEntity::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OperationLogAddParam operationLogAddParam) {
        OperationLogEntity operationLogEntity = BeanUtil.toBean(operationLogAddParam, OperationLogEntity.class);
        this.save(operationLogEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OperationLogEditParam operationLogEditParam) {
        OperationLogEntity operationLogEntity = this.queryEntity(operationLogEditParam.getId());
        BeanUtil.copyProperties(operationLogEditParam, operationLogEntity);
        this.updateById(operationLogEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OperationLogIdParam> operationLogIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(operationLogIdParamList, OperationLogIdParam::getId));
    }

    @Override
    public OperationLogEntity detail(OperationLogIdParam operationLogIdParam) {
        return this.queryEntity(operationLogIdParam.getId());
    }

    @Override
    public OperationLogEntity queryEntity(String id) {
        OperationLogEntity operationLogEntity = this.getById(id);
        if(ObjectUtil.isEmpty(operationLogEntity)) {
            throw new CommonException("操作日志不存在，id值为：{}", id);
        }
        return operationLogEntity;
    }

    @Override
    public void saveLog(OperationLogEnum operationLogEnum,Object param,String businessId) {
        JSONObject json = JSONUtil.parseObj(param);
        // 创建日志对象
        OperationLogEntity log = new OperationLogEntity();
        log.setCategory(operationLogEnum.getCategory());
        log.setName(operationLogEnum.getName());
        log.setOpTime(DateTime.now());
        log.setOpUser(StpLoginUserUtil.getLoginUser().getName());
        log.setBusinessId(businessId);
        log.setParamJson(json.toString());
        this.save(log);
    }

    @Override
    public void saveLog(OperationLogEnum operationLogEnum,Object param, Object obj1, Object obj2, String[] excludeFields) {
        JSONObject json = JSONUtil.parseObj(param);
        convertToDateTime(obj1);
        convertToDateTime(obj2);
        JSONObject jsonObject = compareObj(obj1, obj2,excludeFields);
        // 创建日志对象
        OperationLogEntity log = new OperationLogEntity();
        log.setCategory(operationLogEnum.getCategory());
        log.setName(operationLogEnum.getName());
        log.setBusinessId(json.getStr("id"));
        log.setOpTime(DateTime.now());
        log.setOpUser(StpLoginUserUtil.getLoginUser().getName());
        log.setParamJson(json.toString());
        log.setUpdateJson(jsonObject.toString());
        this.save(log);
    }

    private void convertToDateTime(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.getType() == Date.class) {
                field.setAccessible(true);
                try {
                    Date date = (Date) field.get(obj);
                    field.set(obj, DateUtil.date(date));
                } catch (Exception e) {

                }
            }
        }
    }

    private static JSONObject compareObj(Object obj1, Object obj2, String[] excludeFields){
        // 比较字段值
        Map<String, String> originalValues = null;
        Map<String, String> updatedValues = null;
        boolean exclude = excludeFields != null && excludeFields.length > 0;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            originalValues = BeanUtils.describe(obj1);
            updatedValues = BeanUtils.describe(obj2);
            for (Map.Entry<String, String> entry : originalValues.entrySet()) {
                String fieldName = entry.getKey();
                // 需要排除的字段
                if(exclude && Arrays.asList(excludeFields).contains(fieldName)) {
                    continue;
                }
                String originalValue = entry.getValue();
                String updatedValue = updatedValues.get(fieldName);
                // 使用Objects.equals比较，避免空指针
                if (!Objects.equals(originalValue, updatedValue)) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.set("fieldName",  fieldName);
                    // 显式处理 null 值，避免显示为字符串 "null"
                    jsonObj.set("originalValue",  originalValue != null ? originalValue : "NULL");
                    jsonObj.set("updatedValue",  updatedValue != null ? updatedValue : "NULL");
                    jsonArray.add(jsonObj);
                }
            }
        } catch (Exception e) {

        }
        jsonObject.set("updatedFields", jsonArray);
        return jsonObject;
    }
}