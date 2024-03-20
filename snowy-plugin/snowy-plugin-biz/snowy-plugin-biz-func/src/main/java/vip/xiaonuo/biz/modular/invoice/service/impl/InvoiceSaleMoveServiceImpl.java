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
package vip.xiaonuo.biz.modular.invoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
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
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceSaleMoveVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleMove;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleMoveMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMovePageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleMoveService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 财务系统-销售发票-作废|红冲异动申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:21
 **/
@Service
@DS("slave_1")
public class InvoiceSaleMoveServiceImpl extends ServiceImpl<InvoiceSaleMoveMapper, InvoiceSaleMove> implements InvoiceSaleMoveService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private InvoiceSaleMoveMapper invoiceSaleMoveMapper;

    @Override
    public Page<InvoiceSaleMoveVo> page(InvoiceSaleMovePageParam invoiceSaleMovePageParam) {
        QueryWrapper<InvoiceSaleMove> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getApplyUnit())) {
            queryWrapper.eq("a.APPLY_UNIT", invoiceSaleMovePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getApplyName())) {
            queryWrapper.like("a.APPLY_NAME", invoiceSaleMovePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getMoveType())) {
            queryWrapper.eq("a.MOVE_TYPE", invoiceSaleMovePageParam.getMoveType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getSn())) {
            queryWrapper.like("a.SN", invoiceSaleMovePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", invoiceSaleMovePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSaleMovePageParam.getSortField(), invoiceSaleMovePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSaleMovePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSaleMovePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSaleMovePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return invoiceSaleMoveMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleMoveAddParam invoiceSaleMoveAddParam) {
        InvoiceSaleMove invoiceSaleMove = BeanUtil.toBean(invoiceSaleMoveAddParam, InvoiceSaleMove.class);
        //走审批流程
        String processInstanceId = invoiceSaleMoveAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            invoiceSaleMove.setId(id);
            invoiceSaleMove.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            invoiceSaleMove.setModelId(modelId);
            invoiceSaleMove.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_INVOICE_SALE_MOVE.getBizTable(),id,processInstanceId,dbName);
        }else{
            invoiceSaleMove.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(invoiceSaleMove);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleMoveEditParam invoiceSaleMoveEditParam) {
        String id = invoiceSaleMoveEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(invoiceSaleMoveEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(invoiceSaleMoveEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                invoiceSaleMoveEditParam.setId(id);
            }
        }
        InvoiceSaleMove invoiceSaleMove = this.queryEntity(invoiceSaleMoveEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleMoveEditParam, invoiceSaleMove);
        this.updateById(invoiceSaleMove);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleMoveIdParam> invoiceSaleMoveIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleMoveIdParamList, InvoiceSaleMoveIdParam::getId));
    }

    @Override
    public InvoiceSaleMoveVo detail(InvoiceSaleMoveIdParam invoiceSaleMoveIdParam) {
        return invoiceSaleMoveMapper.getDetailById(invoiceSaleMoveIdParam.getId());
    }

    @Override
    public InvoiceSaleMove queryEntity(String id) {
        InvoiceSaleMove invoiceSaleMove = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSaleMove)) {
            throw new CommonException("财务系统-销售发票-作废|红冲异动申请不存在，id值为：{}", id);
        }
        return invoiceSaleMove;
    }
}
