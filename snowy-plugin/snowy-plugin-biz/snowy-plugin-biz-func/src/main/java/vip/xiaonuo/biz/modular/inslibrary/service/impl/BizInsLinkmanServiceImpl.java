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
package vip.xiaonuo.biz.modular.inslibrary.service.impl;

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
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsLinkman;
import vip.xiaonuo.biz.modular.inslibrary.mapper.BizInsLinkmanMapper;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLinkmanAddParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLinkmanEditParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLinkmanIdParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLinkmanPageParam;
import vip.xiaonuo.biz.modular.inslibrary.service.BizInsLinkmanService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-产业生态链-资源库-联络人Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/15 15:27
 **/

@DS("slave_1")
@Service
public class BizInsLinkmanServiceImpl extends ServiceImpl<BizInsLinkmanMapper, BizInsLinkman> implements BizInsLinkmanService {

    @Override
    public Page<BizInsLinkman> page(BizInsLinkmanPageParam bizInsLinkmanPageParam) {
        QueryWrapper<BizInsLinkman> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizInsLinkmanPageParam.getSortField(), bizInsLinkmanPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizInsLinkmanPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizInsLinkmanPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizInsLinkmanPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizInsLinkman::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizInsLinkmanAddParam bizInsLinkmanAddParam) {
        BizInsLinkman bizInsLinkman = BeanUtil.toBean(bizInsLinkmanAddParam, BizInsLinkman.class);
        this.save(bizInsLinkman);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizInsLinkmanEditParam bizInsLinkmanEditParam) {
        BizInsLinkman bizInsLinkman = this.queryEntity(bizInsLinkmanEditParam.getId());
        BeanUtil.copyProperties(bizInsLinkmanEditParam, bizInsLinkman);
        this.updateById(bizInsLinkman);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizInsLinkmanIdParam> bizInsLinkmanIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizInsLinkmanIdParamList, BizInsLinkmanIdParam::getId));
    }

    @Override
    public BizInsLinkman detail(BizInsLinkmanIdParam bizInsLinkmanIdParam) {
        return this.queryEntity(bizInsLinkmanIdParam.getId());
    }

    @Override
    public BizInsLinkman queryEntity(String id) {
        BizInsLinkman bizInsLinkman = this.getById(id);
        if(ObjectUtil.isEmpty(bizInsLinkman)) {
            throw new CommonException("业务表-产业生态链-资源库-联络人不存在，id值为：{}", id);
        }
        return bizInsLinkman;
    }
}
