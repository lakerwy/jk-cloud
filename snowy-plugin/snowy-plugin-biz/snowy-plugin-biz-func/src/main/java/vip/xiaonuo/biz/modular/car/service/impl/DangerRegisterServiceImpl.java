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
package vip.xiaonuo.biz.modular.car.service.impl;

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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.DangerRegister;
import vip.xiaonuo.biz.modular.car.mapper.DangerRegisterMapper;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterAddParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterEditParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterIdParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterPageParam;
import vip.xiaonuo.biz.modular.car.service.DangerRegisterService;

import java.util.Date;
import java.util.List;

/**
 * CAR-出险登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 15:49
 **/

@DS("slave_1")
@Service
public class DangerRegisterServiceImpl extends ServiceImpl<DangerRegisterMapper, DangerRegister> implements DangerRegisterService {

    @Override
    public Page<DangerRegister> page(DangerRegisterPageParam dangerRegisterPageParam) {
        QueryWrapper<DangerRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(dangerRegisterPageParam.getSn())) {
            queryWrapper.lambda().like(DangerRegister::getSn, dangerRegisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(dangerRegisterPageParam.getLicenseNo())) {
            queryWrapper.lambda().like(DangerRegister::getLicenseNo, dangerRegisterPageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(dangerRegisterPageParam.getOptName())) {
            queryWrapper.lambda().like(DangerRegister::getOptName, dangerRegisterPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(dangerRegisterPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(dangerRegisterPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(DangerRegister::getOptDate, dangerRegisterPageParam.getStartOptDate(), dangerRegisterPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(dangerRegisterPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(DangerRegister::getSn,dangerRegisterPageParam.getSearchKey())
                    .or().like(DangerRegister::getOptName,dangerRegisterPageParam.getSearchKey())
                    .or().like(DangerRegister::getLicenseNo,dangerRegisterPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(dangerRegisterPageParam.getSortField(), dangerRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(dangerRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, dangerRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(dangerRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DangerRegister::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DangerRegisterAddParam dangerRegisterAddParam) {
        DangerRegister dangerRegister = BeanUtil.toBean(dangerRegisterAddParam, DangerRegister.class);
        this.save(dangerRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DangerRegisterEditParam dangerRegisterEditParam) {
        DangerRegister dangerRegister = this.queryEntity(dangerRegisterEditParam.getId());
        BeanUtil.copyProperties(dangerRegisterEditParam, dangerRegister);
        this.updateById(dangerRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DangerRegisterIdParam> dangerRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(dangerRegisterIdParamList, DangerRegisterIdParam::getId));
    }

    @Override
    public DangerRegister detail(DangerRegisterIdParam dangerRegisterIdParam) {
        return this.queryEntity(dangerRegisterIdParam.getId());
    }

    @Override
    public DangerRegister queryEntity(String id) {
        DangerRegister dangerRegister = this.getById(id);
        if(ObjectUtil.isEmpty(dangerRegister)) {
            throw new CommonException("CAR-出险登记不存在，id值为：{}", id);
        }
        return dangerRegister;
    }
}
