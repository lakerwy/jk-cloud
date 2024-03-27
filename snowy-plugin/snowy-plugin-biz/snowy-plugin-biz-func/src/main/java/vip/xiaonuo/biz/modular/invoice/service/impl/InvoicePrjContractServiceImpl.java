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
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.invoice.vo.InvoicePrjContractVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoicePrjContractMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoicePrjContractService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 财务系统-项目回款录入申请-销售合同Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/27 11:30
 **/
@Service
@DS("slave_1")
public class InvoicePrjContractServiceImpl extends ServiceImpl<InvoicePrjContractMapper, InvoicePrjContract> implements InvoicePrjContractService {

    @Resource
    private InvoicePrjContractMapper invoicePrjContractMapper;

    @Override
    public Page<InvoicePrjContractVo> page(InvoicePrjContractPageParam invoicePrjContractPageParam) {
        QueryWrapper<InvoicePrjContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("b.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoicePrjContractPageParam.getHtProjectName())) {
            queryWrapper.like("a.HT_PROJECT_NAME", invoicePrjContractPageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjContractPageParam.getHtContractNo())) {
            queryWrapper.like("a.HT_CONTRACT_NO", invoicePrjContractPageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjContractPageParam.getHtCounterparty())) {
            queryWrapper.like("a.HT_COUNTERPARTY", invoicePrjContractPageParam.getHtCounterparty());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjContractPageParam.getRecordSn())) {
            queryWrapper.like("b.RECORD_SN", invoicePrjContractPageParam.getRecordSn());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjContractPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.HT_PROJECT_NAME",invoicePrjContractPageParam.getSearchKey())
                    .or().like("a.HT_CONTRACT_NO",invoicePrjContractPageParam.getSearchKey())
                    .or().like("a.HT_COUNTERPARTY",invoicePrjContractPageParam.getSearchKey())
                    .or().like("b.RECORD_SN",invoicePrjContractPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(invoicePrjContractPageParam.getSortField(), invoicePrjContractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoicePrjContractPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoicePrjContractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoicePrjContractPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return invoicePrjContractMapper.getPrjContractPage(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoicePrjContractAddParam invoicePrjContractAddParam) {
        InvoicePrjContract invoicePrjContract = BeanUtil.toBean(invoicePrjContractAddParam, InvoicePrjContract.class);
        this.save(invoicePrjContract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoicePrjContractEditParam invoicePrjContractEditParam) {
        InvoicePrjContract invoicePrjContract = this.queryEntity(invoicePrjContractEditParam.getId());
        BeanUtil.copyProperties(invoicePrjContractEditParam, invoicePrjContract);
        this.updateById(invoicePrjContract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoicePrjContractIdParam> invoicePrjContractIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoicePrjContractIdParamList, InvoicePrjContractIdParam::getId));
    }

    @Override
    public InvoicePrjContract detail(InvoicePrjContractIdParam invoicePrjContractIdParam) {
        return this.queryEntity(invoicePrjContractIdParam.getId());
    }

    @Override
    public InvoicePrjContract queryEntity(String id) {
        InvoicePrjContract invoicePrjContract = this.getById(id);
        if(ObjectUtil.isEmpty(invoicePrjContract)) {
            throw new CommonException("财务系统-项目回款录入申请-销售合同不存在，id值为：{}", id);
        }
        return invoicePrjContract;
    }
}
