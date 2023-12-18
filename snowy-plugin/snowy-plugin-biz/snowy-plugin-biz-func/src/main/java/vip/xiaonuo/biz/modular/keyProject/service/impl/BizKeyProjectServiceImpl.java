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
package vip.xiaonuo.biz.modular.keyProject.service.impl;

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
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.keyProject.entity.BizKeyProject;
import vip.xiaonuo.biz.modular.keyProject.mapper.BizKeyProjectMapper;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectAddParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectEditParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectIdParam;
import vip.xiaonuo.biz.modular.keyProject.param.BizKeyProjectPageParam;
import vip.xiaonuo.biz.modular.keyProject.service.BizKeyProjectService;
import vip.xiaonuo.common.util.CommonSnUtil;

import java.util.List;

/**
 * BIZ-重点项目信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/18 15:29
 **/
@DS("slave_1")
@Service
public class BizKeyProjectServiceImpl extends ServiceImpl<BizKeyProjectMapper, BizKeyProject> implements BizKeyProjectService {

    @Override
    public Page<BizKeyProject> page(BizKeyProjectPageParam bizKeyProjectPageParam) {
        QueryWrapper<BizKeyProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getName())) {
            queryWrapper.lambda().like(BizKeyProject::getName, bizKeyProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getScale())) {
            queryWrapper.lambda().like(BizKeyProject::getScale, bizKeyProjectPageParam.getScale());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getBidLimit())) {
            queryWrapper.lambda().like(BizKeyProject::getBidLimit, bizKeyProjectPageParam.getBidLimit());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getUnitName())) {
            queryWrapper.lambda().like(BizKeyProject::getUnitName, bizKeyProjectPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getSn())) {
            queryWrapper.lambda().like(BizKeyProject::getSn, bizKeyProjectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getType())) {
            queryWrapper.lambda().eq(BizKeyProject::getType, bizKeyProjectPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizKeyProjectPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizKeyProject::getName,bizKeyProjectPageParam.getSearchKey())
                    .or().like(BizKeyProject::getUnitName,bizKeyProjectPageParam.getSearchKey())
                    .or().like(BizKeyProject::getScale,bizKeyProjectPageParam.getSearchKey())
                    .or().like(BizKeyProject::getSn,bizKeyProjectPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizKeyProjectPageParam.getSortField(), bizKeyProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizKeyProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizKeyProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizKeyProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizKeyProject::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizKeyProjectAddParam bizKeyProjectAddParam) {
        BizKeyProject bizKeyProject = BeanUtil.toBean(bizKeyProjectAddParam, BizKeyProject.class);
        //处理Sn编号
        BizKeyProject tmp = this.getOne(new QueryWrapper<BizKeyProject>().lambda().orderByDesc(BizKeyProject::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn("KP", tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        bizKeyProject.setSn(newSn);
        this.save(bizKeyProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizKeyProjectEditParam bizKeyProjectEditParam) {
        BizKeyProject bizKeyProject = this.queryEntity(bizKeyProjectEditParam.getId());
        BeanUtil.copyProperties(bizKeyProjectEditParam, bizKeyProject);
        this.updateById(bizKeyProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizKeyProjectIdParam> bizKeyProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizKeyProjectIdParamList, BizKeyProjectIdParam::getId));
    }

    @Override
    public BizKeyProject detail(BizKeyProjectIdParam bizKeyProjectIdParam) {
        return this.queryEntity(bizKeyProjectIdParam.getId());
    }

    @Override
    public BizKeyProject queryEntity(String id) {
        BizKeyProject bizKeyProject = this.getById(id);
        if(ObjectUtil.isEmpty(bizKeyProject)) {
            throw new CommonException("BIZ-重点项目信息不存在，id值为：{}", id);
        }
        return bizKeyProject;
    }
}
