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
package vip.xiaonuo.biz.modular.teambition.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskProject;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskProjectMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskProjectAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskProjectEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskProjectIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskProjectPageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 任务项目表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 09:06
 **/
@Service
public class BizTaskProjectServiceImpl extends ServiceImpl<BizTaskProjectMapper, BizTaskProject> implements BizTaskProjectService {

    @Override
    public Page<BizTaskProject> page(BizTaskProjectPageParam bizTaskProjectPageParam) {
        QueryWrapper<BizTaskProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskProjectPageParam.getProName())) {
            queryWrapper.lambda().like(BizTaskProject::getProName, bizTaskProjectPageParam.getProName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskProjectPageParam.getProSourceId())) {
            queryWrapper.lambda().eq(BizTaskProject::getProSourceId, bizTaskProjectPageParam.getProSourceId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskProjectPageParam.getGroupId())) {
            queryWrapper.lambda().eq(BizTaskProject::getGroupId, bizTaskProjectPageParam.getGroupId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskProjectPageParam.getSortField(), bizTaskProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskProject::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskProjectAddParam bizTaskProjectAddParam) {
        BizTaskProject bizTaskProject = BeanUtil.toBean(bizTaskProjectAddParam, BizTaskProject.class);
        this.save(bizTaskProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskProjectEditParam bizTaskProjectEditParam) {
        BizTaskProject bizTaskProject = this.queryEntity(bizTaskProjectEditParam.getId());
        BeanUtil.copyProperties(bizTaskProjectEditParam, bizTaskProject);
        this.updateById(bizTaskProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskProjectIdParam> bizTaskProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskProjectIdParamList, BizTaskProjectIdParam::getId));
    }

    @Override
    public BizTaskProject detail(BizTaskProjectIdParam bizTaskProjectIdParam) {
        return this.queryEntity(bizTaskProjectIdParam.getId());
    }

    @Override
    public BizTaskProject queryEntity(String id) {
        BizTaskProject bizTaskProject = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskProject)) {
            throw new CommonException("任务项目表不存在，id值为：{}", id);
        }
        return bizTaskProject;
    }
}
