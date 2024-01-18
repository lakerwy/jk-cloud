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
package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.sub.entity.HrSubTransfer;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnel;
import vip.xiaonuo.sys.modular.hr.sub.mapper.HrSubTransferMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.HrSubTransferService;

import javax.annotation.Resource;
import java.util.List;

/**
 * HR-分公司-工资社保转入|转出总部Service接口实现类
 *
 * @author yong.ye
 * @date  2024/01/18 09:41
 **/
@Service
public class HrSubTransferServiceImpl extends ServiceImpl<HrSubTransferMapper, HrSubTransfer> implements HrSubTransferService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HrSubTransfer> page(HrSubTransferPageParam hrSubTransferPageParam) {
        QueryWrapper<HrSubTransfer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(HrSubTransfer::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getType())) {
            queryWrapper.lambda().eq(HrSubTransfer::getType, hrSubTransferPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getApplyUnit())) {
            queryWrapper.lambda().like(HrSubTransfer::getApplyUnit, hrSubTransferPageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getRegionManager())) {
            queryWrapper.lambda().like(HrSubTransfer::getRegionManager, hrSubTransferPageParam.getRegionManager());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getTransferUnit())) {
            queryWrapper.lambda().like(HrSubTransfer::getTransferUnit, hrSubTransferPageParam.getTransferUnit());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getTransferMonth())) {
            queryWrapper.lambda().like(HrSubTransfer::getTransferMonth, hrSubTransferPageParam.getTransferMonth());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getAuditItems())) {
            queryWrapper.lambda().like(HrSubTransfer::getAuditItems, hrSubTransferPageParam.getAuditItems());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getDeptId())) {
            queryWrapper.lambda().eq(HrSubTransfer::getDeptId, hrSubTransferPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getName())) {
            queryWrapper.lambda().like(HrSubTransfer::getName, hrSubTransferPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getIdNumber())) {
            queryWrapper.lambda().like(HrSubTransfer::getIdNumber, hrSubTransferPageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getBankNo())) {
            queryWrapper.lambda().like(HrSubTransfer::getBankNo, hrSubTransferPageParam.getBankNo());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getBankName())) {
            queryWrapper.lambda().like(HrSubTransfer::getBankName, hrSubTransferPageParam.getBankName());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getSn())) {
            queryWrapper.lambda().like(HrSubTransfer::getSn, hrSubTransferPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getStatus())) {
            queryWrapper.lambda().eq(HrSubTransfer::getStatus, hrSubTransferPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(hrSubTransferPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HrSubTransfer::getSn,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getName,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getApplyUnit,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getBankNo,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getBankName,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getIdNumber,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getPhone,hrSubTransferPageParam.getSearchKey())
                    .or().like(HrSubTransfer::getTransferUnit,hrSubTransferPageParam.getSearchKey())
            );
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(HrSubTransfer::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(HrSubTransfer::getCreateUser, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(hrSubTransferPageParam.getSortField(), hrSubTransferPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrSubTransferPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrSubTransferPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrSubTransferPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HrSubTransfer::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrSubTransferAddParam hrSubTransferAddParam) {
        HrSubTransfer hrSubTransfer = BeanUtil.toBean(hrSubTransferAddParam, HrSubTransfer.class);
        //走审批流程
        String processInstanceId = hrSubTransferAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            hrSubTransfer.setId(id);
            hrSubTransfer.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            hrSubTransfer.setModelId(modelId);
            hrSubTransfer.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_SUB_TRANSFER.getBizTable(),id,processInstanceId,dbName);
        }else{
            hrSubTransfer.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        hrSubTransfer.setExtJson(JsonUtils.object2json(hrSubTransfer));
        this.save(hrSubTransfer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrSubTransferEditParam hrSubTransferEditParam) {
        String id = hrSubTransferEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(hrSubTransferEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(hrSubTransferEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                hrSubTransferEditParam.setId(id);
            }
        }
        HrSubTransfer hrSubTransfer = this.queryEntity(hrSubTransferEditParam.getId());
        BeanUtil.copyProperties(hrSubTransferEditParam, hrSubTransfer);
        this.updateById(hrSubTransfer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrSubTransferIdParam> hrSubTransferIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrSubTransferIdParamList, HrSubTransferIdParam::getId));
    }

    @Override
    public HrSubTransfer detail(HrSubTransferIdParam hrSubTransferIdParam) {
        return this.queryEntity(hrSubTransferIdParam.getId());
    }

    @Override
    public HrSubTransfer queryEntity(String id) {
        HrSubTransfer hrSubTransfer = this.getById(id);
        if(ObjectUtil.isEmpty(hrSubTransfer)) {
            throw new CommonException("HR-分公司-工资社保转入|转出总部不存在，id值为：{}", id);
        }
        return hrSubTransfer;
    }
}
