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
package vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.impl;

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
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.BizMapRegisterService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.mapper.BizMapRegisterMapper;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterPageParam;

import java.util.List;

/**
 * 业务表-出图登记记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/07/03 11:46
 **/
@Service
@DS("slave_1")
public class BizMapRegisterServiceImpl extends ServiceImpl<BizMapRegisterMapper, BizMapRegister> implements BizMapRegisterService {

    @Override
    public Page<BizMapRegister> page(BizMapRegisterPageParam bizMapRegisterPageParam) {
        QueryWrapper<BizMapRegister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizMapRegisterPageParam.getSortField(), bizMapRegisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizMapRegisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizMapRegisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizMapRegisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizMapRegister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizMapRegisterAddParam bizMapRegisterAddParam) {
        BizMapRegister bizMapRegister = BeanUtil.toBean(bizMapRegisterAddParam, BizMapRegister.class);
        this.save(bizMapRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizMapRegisterEditParam bizMapRegisterEditParam) {
        BizMapRegister bizMapRegister = this.queryEntity(bizMapRegisterEditParam.getId());
        BeanUtil.copyProperties(bizMapRegisterEditParam, bizMapRegister);
        this.updateById(bizMapRegister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizMapRegisterIdParam> bizMapRegisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizMapRegisterIdParamList, BizMapRegisterIdParam::getId));
    }

    @Override
    public BizMapRegister detail(BizMapRegisterIdParam bizMapRegisterIdParam) {
        return this.queryEntity(bizMapRegisterIdParam.getId());
    }

    @Override
    public BizMapRegister queryEntity(String id) {
        BizMapRegister bizMapRegister = this.getById(id);
        if(ObjectUtil.isEmpty(bizMapRegister)) {
            throw new CommonException("业务表-出图登记记录表不存在，id值为：{}", id);
        }
        return bizMapRegister;
    }
}
