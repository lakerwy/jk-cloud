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
package vip.xiaonuo.biz.modular.crm.equipmenttrans.service.impl;

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
import vip.xiaonuo.biz.modular.crm.equipmenttrans.entity.Equipmenttrans;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.mapper.EquipmenttransMapper;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.param.EquipmenttransAddParam;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.param.EquipmenttransEditParam;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.param.EquipmenttransIdParam;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.param.EquipmenttransPageParam;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.service.EquipmenttransService;

import java.util.List;

/**
 * 业务表-CRM-设备转移记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:39
 **/

@DS("slave_1")
@Service
public class EquipmenttransServiceImpl extends ServiceImpl<EquipmenttransMapper, Equipmenttrans> implements EquipmenttransService {

    @Override
    public Page<Equipmenttrans> page(EquipmenttransPageParam equipmenttransPageParam) {
        QueryWrapper<Equipmenttrans> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getEqId())) {
            queryWrapper.lambda().eq(Equipmenttrans::getEqId, equipmenttransPageParam.getEqId());
        }
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(equipmenttransPageParam.getEndRegisterDate())) {
            queryWrapper.lambda().between(Equipmenttrans::getRegisterDate, equipmenttransPageParam.getStartRegisterDate(), equipmenttransPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getDept())) {
            queryWrapper.lambda().like(Equipmenttrans::getDept, equipmenttransPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getUsers())) {
            queryWrapper.lambda().like(Equipmenttrans::getUsers, equipmenttransPageParam.getUsers());
        }
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getEDept())) {
            queryWrapper.lambda().like(Equipmenttrans::getEDept, equipmenttransPageParam.getEDept());
        }
        if(ObjectUtil.isNotEmpty(equipmenttransPageParam.getEUsers())) {
            queryWrapper.lambda().like(Equipmenttrans::getEUsers, equipmenttransPageParam.getEUsers());
        }
        if(ObjectUtil.isAllNotEmpty(equipmenttransPageParam.getSortField(), equipmenttransPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(equipmenttransPageParam.getSortOrder());
            queryWrapper.orderBy(true, equipmenttransPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(equipmenttransPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Equipmenttrans::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EquipmenttransAddParam equipmenttransAddParam) {
        Equipmenttrans equipmenttrans = BeanUtil.toBean(equipmenttransAddParam, Equipmenttrans.class);
        this.save(equipmenttrans);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EquipmenttransEditParam equipmenttransEditParam) {
        Equipmenttrans equipmenttrans = this.queryEntity(equipmenttransEditParam.getId());
        BeanUtil.copyProperties(equipmenttransEditParam, equipmenttrans);
        this.updateById(equipmenttrans);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EquipmenttransIdParam> equipmenttransIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(equipmenttransIdParamList, EquipmenttransIdParam::getId));
    }

    @Override
    public Equipmenttrans detail(EquipmenttransIdParam equipmenttransIdParam) {
        return this.queryEntity(equipmenttransIdParam.getId());
    }

    @Override
    public Equipmenttrans queryEntity(String id) {
        Equipmenttrans equipmenttrans = this.getById(id);
        if(ObjectUtil.isEmpty(equipmenttrans)) {
            throw new CommonException("业务表-CRM-设备转移记录不存在，id值为：{}", id);
        }
        return equipmenttrans;
    }
}
