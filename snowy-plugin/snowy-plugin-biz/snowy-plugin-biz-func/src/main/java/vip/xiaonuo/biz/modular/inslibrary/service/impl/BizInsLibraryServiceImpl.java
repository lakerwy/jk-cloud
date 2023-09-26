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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsLinkman;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsRecord;
import vip.xiaonuo.biz.modular.inslibrary.service.BizInsLinkmanService;
import vip.xiaonuo.biz.modular.inslibrary.service.BizInsRecordService;
import vip.xiaonuo.biz.modular.inslibrary.vo.BizInsLibraryVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsLibrary;
import vip.xiaonuo.biz.modular.inslibrary.mapper.BizInsLibraryMapper;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLibraryAddParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLibraryEditParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLibraryIdParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsLibraryPageParam;
import vip.xiaonuo.biz.modular.inslibrary.service.BizInsLibraryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-产业生态链-资源库Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/15 15:23
 **/

@DS("slave_1")
@Service
public class BizInsLibraryServiceImpl extends ServiceImpl<BizInsLibraryMapper, BizInsLibrary> implements BizInsLibraryService {

    @Resource
    private BizInsLinkmanService bizInsLinkmanService;
    @Resource
    private BizInsRecordService bizInsRecordService;
    @Override
    public Page<BizInsLibrary> page(BizInsLibraryPageParam bizInsLibraryPageParam) {
        QueryWrapper<BizInsLibrary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizInsLibrary::getEnterpriseName,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getAddress,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getJkDept,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getJkContactor,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getExplainMemo,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getMainFeatures,bizInsLibraryPageParam.getSearchKey())
                    .or().like(BizInsLibrary::getOrgCode,bizInsLibraryPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getIndustryCategory())) {
            queryWrapper.lambda().eq(BizInsLibrary::getIndustryCategory, bizInsLibraryPageParam.getIndustryCategory());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getSubCategory())) {
            queryWrapper.lambda().eq(BizInsLibrary::getSubCategory, bizInsLibraryPageParam.getSubCategory());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getEnterpriseName())) {
            queryWrapper.lambda().like(BizInsLibrary::getEnterpriseName, bizInsLibraryPageParam.getEnterpriseName());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getOrgCode())) {
            queryWrapper.lambda().like(BizInsLibrary::getOrgCode, bizInsLibraryPageParam.getOrgCode());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getRelationType())) {
            queryWrapper.lambda().eq(BizInsLibrary::getRelationType, bizInsLibraryPageParam.getRelationType());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getJkContactor())) {
            queryWrapper.lambda().like(BizInsLibrary::getJkContactor, bizInsLibraryPageParam.getJkContactor());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getJkDept())) {
            queryWrapper.lambda().like(BizInsLibrary::getJkDept, bizInsLibraryPageParam.getJkDept());
        }
        if(ObjectUtil.isNotEmpty(bizInsLibraryPageParam.getIsHz())) {
            queryWrapper.lambda().eq(BizInsLibrary::getIsHz, bizInsLibraryPageParam.getIsHz());
        }
        if(ObjectUtil.isAllNotEmpty(bizInsLibraryPageParam.getSortField(), bizInsLibraryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizInsLibraryPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizInsLibraryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizInsLibraryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizInsLibrary::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizInsLibraryAddParam bizInsLibraryAddParam) {
        BizInsLibrary bizInsLibrary = BeanUtil.toBean(bizInsLibraryAddParam, BizInsLibrary.class);
        this.save(bizInsLibrary);
        //保存联络人
        List<BizInsLinkman> linkmanList = BeanUtil.copyToList(bizInsLibraryAddParam.getLinkMans(),BizInsLinkman.class);
        if (CollUtil.isNotEmpty(linkmanList)) {
            linkmanList.forEach(x->x.setLibraryId(bizInsLibrary.getId()));
            bizInsLinkmanService.saveBatch(linkmanList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizInsLibraryEditParam bizInsLibraryEditParam) {
        BizInsLibrary bizInsLibrary = this.queryEntity(bizInsLibraryEditParam.getId());
        BeanUtil.copyProperties(bizInsLibraryEditParam, bizInsLibrary);
        this.updateById(bizInsLibrary);
        //保存联络人
        List<BizInsLinkman> linkmanList = BeanUtil.copyToList(bizInsLibraryEditParam.getLinkMans(),BizInsLinkman.class);
        //待更新
        List<BizInsLinkman> update = linkmanList.stream().filter(x->StrUtil.isNotEmpty(x.getId())).collect(Collectors.toList());
        //待新增
        List<BizInsLinkman> add = linkmanList.stream().filter(x->StrUtil.isEmpty(x.getId())).collect(Collectors.toList());
        add.forEach( x-> { x.setDeleteFlag("NOT_DELETE"); x.setLibraryId(bizInsLibraryEditParam.getId()); } );
        //待删除
        List<BizInsLinkman> old = bizInsLinkmanService.list(new QueryWrapper<BizInsLinkman>().lambda().eq(BizInsLinkman::getLibraryId,bizInsLibraryEditParam.getId()));
        List<String> ids = old.stream().map(x->x.getId()).collect(Collectors.toList());
        ids.removeAll(update.stream().map(x->x.getId()).collect(Collectors.toList()));
        if(CollUtil.isNotEmpty(add)){
            bizInsLinkmanService.saveBatch(add);
        }
        if(CollUtil.isNotEmpty(update)){
            bizInsLinkmanService.updateBatchById(update);
        }
        if(CollUtil.isNotEmpty(ids)){
            bizInsLinkmanService.removeByIds(ids);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizInsLibraryIdParam> bizInsLibraryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizInsLibraryIdParamList, BizInsLibraryIdParam::getId));
        // 删除联络人
        List<String> libraryIds = CollStreamUtil.toList(bizInsLibraryIdParamList, BizInsLibraryIdParam::getId);
        bizInsLinkmanService.remove(new QueryWrapper<BizInsLinkman>().lambda().in(BizInsLinkman::getLibraryId,libraryIds));
        // 删除拜访记录
        bizInsRecordService.remove(new QueryWrapper<BizInsRecord>().lambda().in(BizInsRecord::getLibraryId,libraryIds));
    }

    @Override
    public BizInsLibraryVo detail(BizInsLibraryIdParam bizInsLibraryIdParam) {
        BizInsLibraryVo bizInsLibraryVo = new BizInsLibraryVo();
        BizInsLibrary bizInsLibrary = this.queryEntity(bizInsLibraryIdParam.getId());
        BeanUtil.copyProperties(bizInsLibrary,bizInsLibraryVo);
        bizInsLibraryVo.setLinkMans(bizInsLinkmanService.list(new QueryWrapper<BizInsLinkman>().lambda().eq(BizInsLinkman::getLibraryId,bizInsLibraryIdParam.getId()).orderByDesc(BizInsLinkman::getCreateTime)));
        bizInsLibraryVo.setRecords(bizInsRecordService.list(new QueryWrapper<BizInsRecord>().lambda().eq(BizInsRecord::getLibraryId,bizInsLibraryIdParam.getId()).orderByDesc(BizInsRecord::getVisitorTime).orderByDesc(BizInsRecord::getCreateTime)));
        return bizInsLibraryVo;
    }

    @Override
    public BizInsLibrary queryEntity(String id) {
        BizInsLibrary bizInsLibrary = this.getById(id);
        if(ObjectUtil.isEmpty(bizInsLibrary)) {
            throw new CommonException("业务表-产业生态链-资源库不存在，id值为：{}", id);
        }
        return bizInsLibrary;
    }
}
