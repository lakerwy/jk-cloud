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
package vip.xiaonuo.biz.modular.develop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.develop.entity.DevFormItem;
import vip.xiaonuo.biz.modular.develop.service.DevFormItemService;
import vip.xiaonuo.biz.modular.develop.vo.DevFormVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.develop.entity.DevForm;
import vip.xiaonuo.biz.modular.develop.mapper.DevFormMapper;
import vip.xiaonuo.biz.modular.develop.param.DevFormAddParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormEditParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormIdParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormPageParam;
import vip.xiaonuo.biz.modular.develop.service.DevFormService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 需求单 - 发起流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/27 16:08
 **/
@Service
@DS("slave_1")
public class DevFormServiceImpl extends ServiceImpl<DevFormMapper, DevForm> implements DevFormService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private DevFormItemService devFormItemService;

    @Override
    public Page<DevFormVo> page(DevFormPageParam devFormPageParam) {
        QueryWrapper<DevForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(DevForm::getDeleteFlag,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(devFormPageParam.getSn())){
            queryWrapper.lambda().like(DevForm::getSn,devFormPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getStatus())){
            queryWrapper.lambda().eq(DevForm::getStatus,devFormPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(DevForm::getApplyUnit, devFormPageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getApplyDept())) {
            queryWrapper.lambda().like(DevForm::getApplyDept, devFormPageParam.getApplyDept());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getActionUser())) {
            queryWrapper.lambda().like(DevForm::getActionUser, devFormPageParam.getActionUser());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getApplyName())) {
            queryWrapper.lambda().like(DevForm::getApplyName, devFormPageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getStartEstimatedStartTime()) && ObjectUtil.isNotEmpty(devFormPageParam.getEndEstimatedStartTime())) {
            queryWrapper.lambda().between(DevForm::getEstimatedStartTime, devFormPageParam.getStartEstimatedStartTime(), devFormPageParam.getEndEstimatedStartTime());
        }
        if(ObjectUtil.isNotEmpty(devFormPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(DevForm::getApplyDept,devFormPageParam.getSearchKey())
                    .or().like(DevForm::getApplyName,devFormPageParam.getSearchKey())
                    .or().like(DevForm::getApplyDesc,devFormPageParam.getSearchKey())
                    .or().like(DevForm::getSn,devFormPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(devFormPageParam.getSortField(), devFormPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFormPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFormPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFormPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DevForm::getCreateTime);
        }
        Page<DevForm> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<DevFormVo> records = BeanUtil.copyToList(result.getRecords(), DevFormVo.class);
        if(CollUtil.isNotEmpty(records)){
            records.forEach(x->{
                List<DevFormItem> devFormItems = devFormItemService.list(new QueryWrapper<DevFormItem>().lambda().eq(DevFormItem::getBizDevFormId,x.getId()));
                x.setFormItemList(devFormItems);
            });
        }
        Page<DevFormVo> newResult = new Page<>();
        newResult.setRecords(records);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevFormAddParam devFormAddParam) {
        DevForm devForm = BeanUtil.toBean(devFormAddParam, DevForm.class);
        //走审批流程
        String processInstanceId = devFormAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            devForm.setId(id);
            devForm.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            devForm.setModelId(modelId);
            devForm.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_DEV_FORM.getBizModel(),id,processInstanceId,dbName);
        }else{
            devForm.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(devForm);
        //需求拆解
        if (CollUtil.isNotEmpty(devFormAddParam.getFormItemList())) {
            List<DevFormItem> tmpList = BeanUtil.copyToList(devFormAddParam.getFormItemList(), DevFormItem.class);
            tmpList.forEach(v -> {
                v.setBizDevFormId(devForm.getId());
            });
            devFormItemService.saveBatch(tmpList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevFormEditParam devFormEditParam) {
        String id = devFormEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(devFormEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(devFormEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                devFormEditParam.setId(id);
            }
        }
        DevForm devForm = this.queryEntity(devFormEditParam.getId());
        BeanUtil.copyProperties(devFormEditParam, devForm);
        this.updateById(devForm);

        //【需求拆解】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(devFormEditParam.getFormItemList())) {
            devFormEditParam.getFormItemList().forEach(x->x.setBizDevFormId(devForm.getId()));
            //新增|修改|删除
            List<DevFormItem> oldList = devFormItemService.list(new QueryWrapper<DevFormItem>().lambda().eq(DevFormItem::getBizDevFormId, devForm.getId()));
            List<String> oldIds = oldList.stream().map(DevFormItem::getId).collect(Collectors.toList());
            //待更新的
            List<DevFormItem> updateList = devFormEditParam.getFormItemList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<DevFormItem> addList = devFormEditParam.getFormItemList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(DevFormItem::getId).collect(Collectors.toList());
            List<DevFormItem> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                devFormItemService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                devFormItemService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                devFormItemService.removeByIds(removeList);
            }
        }else{
            devFormItemService.remove(new QueryWrapper<DevFormItem>().lambda().eq(DevFormItem::getBizDevFormId, devForm.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevFormIdParam> devFormIdParamList) {
        // 执行删除
        List<String> delIds = CollStreamUtil.toList(devFormIdParamList, DevFormIdParam::getId);
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            devFormItemService.remove(new QueryWrapper<DevFormItem>().lambda().in(DevFormItem::getBizDevFormId, delIds));
        }
    }

    @Override
    public DevFormVo detail(DevFormIdParam devFormIdParam) {
        DevForm devForm = this.queryEntity(devFormIdParam.getId());
        DevFormVo devFormVo = BeanUtil.copyProperties(devForm, DevFormVo.class);
        List<DevFormItem> devFormItems = devFormItemService.list(new QueryWrapper<DevFormItem>().lambda().eq(DevFormItem::getBizDevFormId,devForm.getId()));
        devFormVo.setFormItemList(devFormItems);
        return devFormVo;
    }

    @Override
    public DevForm queryEntity(String id) {
        DevForm devForm = this.getById(id);
        if(ObjectUtil.isEmpty(devForm)) {
            throw new CommonException("需求单 - 发起流程不存在，id值为：{}", id);
        }
        return devForm;
    }
}
