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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnelDegree;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubPersonnelDegreeMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelDegreeAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelDegreeEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelDegreeIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubPersonnelDegreePageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelDegreeService;

import java.util.List;

/**
 * HR-分公司-学历信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/21 11:17
 **/
@Service
public class SubPersonnelDegreeServiceImpl extends ServiceImpl<SubPersonnelDegreeMapper, SubPersonnelDegree> implements SubPersonnelDegreeService {

    @Override
    public Page<SubPersonnelDegree> page(SubPersonnelDegreePageParam subPersonnelDegreePageParam) {
        QueryWrapper<SubPersonnelDegree> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subPersonnelDegreePageParam.getDegree())) {
            queryWrapper.lambda().eq(SubPersonnelDegree::getDegree, subPersonnelDegreePageParam.getDegree());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelDegreePageParam.getDegreeClass())) {
            queryWrapper.lambda().eq(SubPersonnelDegree::getDegreeClass, subPersonnelDegreePageParam.getDegreeClass());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelDegreePageParam.getGraduateSchool())) {
            queryWrapper.lambda().like(SubPersonnelDegree::getGraduateSchool, subPersonnelDegreePageParam.getGraduateSchool());
        }
        if(ObjectUtil.isNotEmpty(subPersonnelDegreePageParam.getMajor())) {
            queryWrapper.lambda().like(SubPersonnelDegree::getMajor, subPersonnelDegreePageParam.getMajor());
        }
        if(ObjectUtil.isAllNotEmpty(subPersonnelDegreePageParam.getSortField(), subPersonnelDegreePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subPersonnelDegreePageParam.getSortOrder());
            queryWrapper.orderBy(true, subPersonnelDegreePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subPersonnelDegreePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubPersonnelDegree::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubPersonnelDegreeAddParam subPersonnelDegreeAddParam) {
        SubPersonnelDegree subPersonnelDegree = BeanUtil.toBean(subPersonnelDegreeAddParam, SubPersonnelDegree.class);
        this.save(subPersonnelDegree);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubPersonnelDegreeEditParam subPersonnelDegreeEditParam) {
        SubPersonnelDegree subPersonnelDegree = this.queryEntity(subPersonnelDegreeEditParam.getId());
        BeanUtil.copyProperties(subPersonnelDegreeEditParam, subPersonnelDegree);
        this.updateById(subPersonnelDegree);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubPersonnelDegreeIdParam> subPersonnelDegreeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subPersonnelDegreeIdParamList, SubPersonnelDegreeIdParam::getId));
    }

    @Override
    public SubPersonnelDegree detail(SubPersonnelDegreeIdParam subPersonnelDegreeIdParam) {
        return this.queryEntity(subPersonnelDegreeIdParam.getId());
    }

    @Override
    public SubPersonnelDegree queryEntity(String id) {
        SubPersonnelDegree subPersonnelDegree = this.getById(id);
        if(ObjectUtil.isEmpty(subPersonnelDegree)) {
            throw new CommonException("HR-分公司-学历信息不存在，id值为：{}", id);
        }
        return subPersonnelDegree;
    }
}
