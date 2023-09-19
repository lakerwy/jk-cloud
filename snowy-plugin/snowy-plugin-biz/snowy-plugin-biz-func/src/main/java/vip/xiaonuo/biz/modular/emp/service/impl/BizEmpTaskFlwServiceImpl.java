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
package vip.xiaonuo.biz.modular.emp.service.impl;

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
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpTaskFlwMapper;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwAddParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwEditParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwIdParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwPageParam;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;

import java.util.List;

/**
 * 业务表-员工绩效考核-季度任务流程审批单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/19 08:48
 **/


@DS("slave_1")
@Service
public class BizEmpTaskFlwServiceImpl extends ServiceImpl<BizEmpTaskFlwMapper, BizEmpTaskFlw> implements BizEmpTaskFlwService {

    @Override
    public Page<BizEmpTaskFlw> page(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam) {
        QueryWrapper<BizEmpTaskFlw> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getSn())) {
            queryWrapper.lambda().like(BizEmpTaskFlw::getSn, bizEmpTaskFlwPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskFlwPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizEmpTaskFlw::getStatus, bizEmpTaskFlwPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpTaskFlwPageParam.getSortField(), bizEmpTaskFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpTaskFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpTaskFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpTaskFlwPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizEmpTaskFlw::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizEmpTaskFlwAddParam bizEmpTaskFlwAddParam) {
        BizEmpTaskFlw bizEmpTaskFlw = BeanUtil.toBean(bizEmpTaskFlwAddParam, BizEmpTaskFlw.class);
        this.save(bizEmpTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpTaskFlwEditParam bizEmpTaskFlwEditParam) {
        BizEmpTaskFlw bizEmpTaskFlw = this.queryEntity(bizEmpTaskFlwEditParam.getId());
        BeanUtil.copyProperties(bizEmpTaskFlwEditParam, bizEmpTaskFlw);
        this.updateById(bizEmpTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpTaskFlwIdParam> bizEmpTaskFlwIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizEmpTaskFlwIdParamList, BizEmpTaskFlwIdParam::getId));
    }

    @Override
    public BizEmpTaskFlw detail(BizEmpTaskFlwIdParam bizEmpTaskFlwIdParam) {
        return this.queryEntity(bizEmpTaskFlwIdParam.getId());
    }

    @Override
    public BizEmpTaskFlw queryEntity(String id) {
        BizEmpTaskFlw bizEmpTaskFlw = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpTaskFlw)) {
            throw new CommonException("业务表-员工绩效考核-季度任务流程审批单不存在，id值为：{}", id);
        }
        return bizEmpTaskFlw;
    }
}
