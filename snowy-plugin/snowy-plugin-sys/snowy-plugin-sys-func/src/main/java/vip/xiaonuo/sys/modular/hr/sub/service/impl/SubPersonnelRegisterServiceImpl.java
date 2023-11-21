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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelRegisterAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelRegisterEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelRegisterIdParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelRegisterService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnelRegister;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubPersonnelRegisterMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelRegisterPageParam;

import java.util.List;

/**
 * HR-分公司- 签约信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/21 11:18
 **/
@Service
public class SubPersonnelRegisterServiceImpl extends ServiceImpl<SubPersonnelRegisterMapper, SubPersonnelRegister> implements SubPersonnelRegisterService {

    @Override
    public Page<SubPersonnelRegister> page(SubPersonnelRegisterPageParam subPersonnelRegisterPageParam) {
        QueryWrapper<SubPersonnelRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subPersonnelRegisterPageParam.getName())) {
            queryWrapper.lambda().like(SubPersonnelRegister::getName, subPersonnelRegisterPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelRegisterPageParam.getSignUnit())) {
            queryWrapper.lambda().like(SubPersonnelRegister::getSignUnit, subPersonnelRegisterPageParam.getSignUnit());
        }
        if(ObjectUtil.isAllNotEmpty(subPersonnelRegisterPageParam.getSortField(), subPersonnelRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subPersonnelRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, subPersonnelRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subPersonnelRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubPersonnelRegister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubPersonnelRegisterAddParam subPersonnelRegisterAddParam) {
        SubPersonnelRegister subPersonnelRegister = BeanUtil.toBean(subPersonnelRegisterAddParam, SubPersonnelRegister.class);
        this.save(subPersonnelRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubPersonnelRegisterEditParam subPersonnelRegisterEditParam) {
        SubPersonnelRegister subPersonnelRegister = this.queryEntity(subPersonnelRegisterEditParam.getId());
        BeanUtil.copyProperties(subPersonnelRegisterEditParam, subPersonnelRegister);
        this.updateById(subPersonnelRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubPersonnelRegisterIdParam> subPersonnelRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subPersonnelRegisterIdParamList, SubPersonnelRegisterIdParam::getId));
    }

    @Override
    public SubPersonnelRegister detail(SubPersonnelRegisterIdParam subPersonnelRegisterIdParam) {
        return this.queryEntity(subPersonnelRegisterIdParam.getId());
    }

    @Override
    public SubPersonnelRegister queryEntity(String id) {
        SubPersonnelRegister subPersonnelRegister = this.getById(id);
        if(ObjectUtil.isEmpty(subPersonnelRegister)) {
            throw new CommonException("HR-分公司- 签约信息不存在，id值为：{}", id);
        }
        return subPersonnelRegister;
    }
}
