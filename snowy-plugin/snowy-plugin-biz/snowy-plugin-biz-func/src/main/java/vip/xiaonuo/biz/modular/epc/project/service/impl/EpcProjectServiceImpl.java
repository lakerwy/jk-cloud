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
package vip.xiaonuo.biz.modular.epc.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.project.entity.EpcProject;
import vip.xiaonuo.biz.modular.epc.project.mapper.EpcProjectMapper;
import vip.xiaonuo.biz.modular.epc.project.param.*;
import vip.xiaonuo.biz.modular.epc.project.service.EpcProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-项目管理表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:55
 **/
@Service
public class EpcProjectServiceImpl extends ServiceImpl<EpcProjectMapper, EpcProject> implements EpcProjectService {

    @Override
    public Page<EpcProject> page(EpcProjectPageParam epcProjectPageParam) {
        QueryWrapper<EpcProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcProjectPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcProject::getTenantId, epcProjectPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcProjectPageParam.getProjectName())) {
            queryWrapper.lambda().like(EpcProject::getProjectName, epcProjectPageParam.getProjectName());
        }
        if(ObjectUtil.isAllNotEmpty(epcProjectPageParam.getSortField(), epcProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcProject::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcProjectAddParam epcProjectAddParam) {
        EpcProject epcProject = BeanUtil.toBean(epcProjectAddParam, EpcProject.class);
        this.save(epcProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcProjectEditParam epcProjectEditParam) {
        EpcProject epcProject = this.queryEntity(epcProjectEditParam.getId());
        BeanUtil.copyProperties(epcProjectEditParam, epcProject);
        this.updateById(epcProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcProjectIdParam> epcProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcProjectIdParamList, EpcProjectIdParam::getId));
    }

    @Override
    public EpcProject detail(EpcProjectIdParam epcProjectIdParam) {
        return this.queryEntity(epcProjectIdParam.getId());
    }

    @Override
    public EpcProject queryEntity(String id) {
        EpcProject epcProject = this.getById(id);
        if(ObjectUtil.isEmpty(epcProject)) {
            throw new CommonException("EPC-项目管理表不存在，id值为：{}", id);
        }
        return epcProject;
    }
}
