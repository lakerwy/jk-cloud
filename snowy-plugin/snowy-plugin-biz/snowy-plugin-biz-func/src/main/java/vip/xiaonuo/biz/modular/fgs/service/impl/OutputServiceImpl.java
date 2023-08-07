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
package vip.xiaonuo.biz.modular.fgs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.fgs.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.fgs.entity.Output;
import vip.xiaonuo.biz.modular.fgs.mapper.OutputMapper;
import vip.xiaonuo.biz.modular.fgs.service.OutputService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-FGS-建筑业产值明细表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/02 10:30
 **/

@DS("slave_1")
@Service
public class OutputServiceImpl extends ServiceImpl<OutputMapper, Output> implements OutputService {

    @Override
    public Page<Output> page(OutputPageParam outputPageParam) {
        QueryWrapper<Output> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(outputPageParam.getYear())) {
            queryWrapper.lambda().eq(Output::getYear, outputPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getMonth())) {
            queryWrapper.lambda().eq(Output::getMonth, outputPageParam.getMonth());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getContractNo())) {
            queryWrapper.lambda().eq(Output::getContractNo, outputPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getProjectName())) {
            queryWrapper.lambda().like(Output::getProjectName, outputPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getCounterparty())) {
            queryWrapper.lambda().like(Output::getCounterparty, outputPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().eq(Output::getCompanyOrDepartment, outputPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getProvince())) {
            queryWrapper.lambda().eq(Output::getProvince, outputPageParam.getProvince());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getSecondLevel())) {
            queryWrapper.lambda().eq(Output::getSecondLevel, outputPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getOperator())) {
            queryWrapper.lambda().eq(Output::getOperator, outputPageParam.getOperator());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getOrgId())) {
            queryWrapper.lambda().eq(Output::getOrgId, outputPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(outputPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(outputPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Output::getSealDate, outputPageParam.getStartSealDate(), outputPageParam.getEndSealDate());
        }
        if(ObjectUtil.isAllNotEmpty(outputPageParam.getSortField(), outputPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(outputPageParam.getSortOrder());
            queryWrapper.orderBy(true, outputPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(outputPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Output::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OutputAddParam outputAddParam) {
        Output output = BeanUtil.toBean(outputAddParam, Output.class);
        output.setOperator(StpLoginUserUtil.getLoginUser().getName());
        output.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        this.save(output);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OutputEditParam outputEditParam) {
        Output output = this.queryEntity(outputEditParam.getId());
        BeanUtil.copyProperties(outputEditParam, output);
        this.updateById(output);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OutputIdParam> outputIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(outputIdParamList, OutputIdParam::getId));
    }

    @Override
    public Output detail(OutputIdParam outputIdParam) {
        return this.queryEntity(outputIdParam.getId());
    }

    @Override
    public Output queryEntity(String id) {
        Output output = this.getById(id);
        if(ObjectUtil.isEmpty(output)) {
            throw new CommonException("业务表-FGS-建筑业产值明细表不存在，id值为：{}", id);
        }
        return output;
    }

    @Override
    public void batchSave(BatchOutputParam batchOutputParam) {
        if(CollUtil.isNotEmpty(batchOutputParam.getOutputlist())) {
            List<Output> savelist = batchOutputParam.getOutputlist();
            //筛选 待更新 的集合
            List<Output> updatelist = savelist.stream().filter(x -> StrUtil.isNotBlank(x.getId())).collect(Collectors.toList());
            //筛选 待新增 的集合
            savelist.removeAll(updatelist);
            //处理数据
            if(CollUtil.isNotEmpty(updatelist)){
                this.updateBatchById(updatelist);
            }
            if(CollUtil.isNotEmpty(savelist)){
                this.saveBatch(savelist);
            }
        }
    }
}
