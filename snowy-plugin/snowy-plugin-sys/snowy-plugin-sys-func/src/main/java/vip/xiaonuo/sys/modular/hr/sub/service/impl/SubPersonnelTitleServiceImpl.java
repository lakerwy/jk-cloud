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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnelTitle;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubPersonnelTitleMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelTitleAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelTitleEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelTitleIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelTitlePageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelTitleService;

import java.util.List;

/**
 * HR-分公司-职称信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/21 11:19
 **/
@Service
public class SubPersonnelTitleServiceImpl extends ServiceImpl<SubPersonnelTitleMapper, SubPersonnelTitle> implements SubPersonnelTitleService {

    @Override
    public Page<SubPersonnelTitle> page(SubPersonnelTitlePageParam subPersonnelTitlePageParam) {
        QueryWrapper<SubPersonnelTitle> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subPersonnelTitlePageParam.getName())) {
            queryWrapper.lambda().like(SubPersonnelTitle::getName, subPersonnelTitlePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelTitlePageParam.getTitleProfession())) {
            queryWrapper.lambda().like(SubPersonnelTitle::getTitleProfession, subPersonnelTitlePageParam.getTitleProfession());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelTitlePageParam.getTitleLever())) {
            queryWrapper.lambda().eq(SubPersonnelTitle::getTitleLever, subPersonnelTitlePageParam.getTitleLever());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelTitlePageParam.getTitleNo())) {
            queryWrapper.lambda().like(SubPersonnelTitle::getTitleNo, subPersonnelTitlePageParam.getTitleNo());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelTitlePageParam.getReviewOrgan())) {
            queryWrapper.lambda().like(SubPersonnelTitle::getReviewOrgan, subPersonnelTitlePageParam.getReviewOrgan());
        }
        if(ObjectUtil.isAllNotEmpty(subPersonnelTitlePageParam.getSortField(), subPersonnelTitlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subPersonnelTitlePageParam.getSortOrder());
            queryWrapper.orderBy(true, subPersonnelTitlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subPersonnelTitlePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubPersonnelTitle::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubPersonnelTitleAddParam subPersonnelTitleAddParam) {
        SubPersonnelTitle subPersonnelTitle = BeanUtil.toBean(subPersonnelTitleAddParam, SubPersonnelTitle.class);
        this.save(subPersonnelTitle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubPersonnelTitleEditParam subPersonnelTitleEditParam) {
        SubPersonnelTitle subPersonnelTitle = this.queryEntity(subPersonnelTitleEditParam.getId());
        BeanUtil.copyProperties(subPersonnelTitleEditParam, subPersonnelTitle);
        this.updateById(subPersonnelTitle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubPersonnelTitleIdParam> subPersonnelTitleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subPersonnelTitleIdParamList, SubPersonnelTitleIdParam::getId));
    }

    @Override
    public SubPersonnelTitle detail(SubPersonnelTitleIdParam subPersonnelTitleIdParam) {
        return this.queryEntity(subPersonnelTitleIdParam.getId());
    }

    @Override
    public SubPersonnelTitle queryEntity(String id) {
        SubPersonnelTitle subPersonnelTitle = this.getById(id);
        if(ObjectUtil.isEmpty(subPersonnelTitle)) {
            throw new CommonException("HR-分公司-职称信息不存在，id值为：{}", id);
        }
        return subPersonnelTitle;
    }
}
