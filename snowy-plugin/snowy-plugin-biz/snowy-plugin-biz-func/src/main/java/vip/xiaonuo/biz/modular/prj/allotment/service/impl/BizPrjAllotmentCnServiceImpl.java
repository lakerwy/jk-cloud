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
package vip.xiaonuo.biz.modular.prj.allotment.service.impl;

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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotmentCn;
import vip.xiaonuo.biz.modular.prj.allotment.mapper.BizPrjAllotmentCnMapper;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentCnAddParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentCnEditParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentCnIdParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentCnPageParam;
import vip.xiaonuo.biz.modular.prj.allotment.service.BizPrjAllotmentCnService;

import java.util.List;

/**
 * 业务表-项目款拨付申请单-出纳支付信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/20 11:13
 **/

@DS("slave_1")
@Service
public class BizPrjAllotmentCnServiceImpl extends ServiceImpl<BizPrjAllotmentCnMapper, BizPrjAllotmentCn> implements BizPrjAllotmentCnService {

    @Override
    public Page<BizPrjAllotmentCn> page(BizPrjAllotmentCnPageParam bizPrjAllotmentCnPageParam) {
        QueryWrapper<BizPrjAllotmentCn> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentCnPageParam.getUnitCode())) {
            queryWrapper.lambda().like(BizPrjAllotmentCn::getUnitCode, bizPrjAllotmentCnPageParam.getUnitCode());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentCnPageParam.getUnitName())) {
            queryWrapper.lambda().like(BizPrjAllotmentCn::getUnitName, bizPrjAllotmentCnPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentCnPageParam.getContractNo())) {
            queryWrapper.lambda().like(BizPrjAllotmentCn::getContractNo, bizPrjAllotmentCnPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentCnPageParam.getContractName())) {
            queryWrapper.lambda().like(BizPrjAllotmentCn::getContractName, bizPrjAllotmentCnPageParam.getContractName());
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjAllotmentCnPageParam.getSortField(), bizPrjAllotmentCnPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjAllotmentCnPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjAllotmentCnPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjAllotmentCnPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjAllotmentCn::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjAllotmentCnAddParam bizPrjAllotmentCnAddParam) {
        BizPrjAllotmentCn bizPrjAllotmentCn = BeanUtil.toBean(bizPrjAllotmentCnAddParam, BizPrjAllotmentCn.class);
        this.save(bizPrjAllotmentCn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjAllotmentCnEditParam bizPrjAllotmentCnEditParam) {
        BizPrjAllotmentCn bizPrjAllotmentCn = this.queryEntity(bizPrjAllotmentCnEditParam.getId());
        BeanUtil.copyProperties(bizPrjAllotmentCnEditParam, bizPrjAllotmentCn);
        this.updateById(bizPrjAllotmentCn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjAllotmentCnIdParam> bizPrjAllotmentCnIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizPrjAllotmentCnIdParamList, BizPrjAllotmentCnIdParam::getId));
    }

    @Override
    public BizPrjAllotmentCn detail(BizPrjAllotmentCnIdParam bizPrjAllotmentCnIdParam) {
        return this.queryEntity(bizPrjAllotmentCnIdParam.getId());
    }

    @Override
    public BizPrjAllotmentCn queryEntity(String id) {
        BizPrjAllotmentCn bizPrjAllotmentCn = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjAllotmentCn)) {
            throw new CommonException("业务表-项目款拨付申请单-出纳支付信息不存在，id值为：{}", id);
        }
        return bizPrjAllotmentCn;
    }
}
