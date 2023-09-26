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
package vip.xiaonuo.biz.modular.epc.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcProviderAss;
import vip.xiaonuo.biz.modular.epc.provider.mapper.EpcProviderAssMapper;
import vip.xiaonuo.biz.modular.epc.provider.param.*;
import vip.xiaonuo.biz.modular.epc.provider.service.EpcProviderAssService;
import vip.xiaonuo.biz.modular.epc.provider.vo.ProviderAssVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EPC 供应商库-考核表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/25 16:26
 **/
@Service
public class EpcProviderAssServiceImpl extends ServiceImpl<EpcProviderAssMapper, EpcProviderAss> implements EpcProviderAssService {

    @Resource
    private EpcProviderAssMapper epcProviderAssMapper;

    @Override
    public Page<EpcProviderAss> page(EpcProviderAssPageParam epcProviderAssPageParam) {
        QueryWrapper<EpcProviderAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getPrvId())) {
            queryWrapper.lambda().eq(EpcProviderAss::getPrvId, epcProviderAssPageParam.getPrvId());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getYear())) {
            queryWrapper.lambda().eq(EpcProviderAss::getYear, epcProviderAssPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getSeason())) {
            queryWrapper.lambda().eq(EpcProviderAss::getSeason, epcProviderAssPageParam.getSeason());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getAssType())) {
            queryWrapper.lambda().eq(EpcProviderAss::getAssType, epcProviderAssPageParam.getAssType());
        }
        if(ObjectUtil.isAllNotEmpty(epcProviderAssPageParam.getSortField(), epcProviderAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcProviderAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcProviderAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcProviderAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcProviderAss::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcProviderAssAddParam epcProviderAssAddParam) {
        EpcProviderAss epcProviderAss = BeanUtil.toBean(epcProviderAssAddParam, EpcProviderAss.class);
        this.save(epcProviderAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcProviderAssEditParam epcProviderAssEditParam) {
        EpcProviderAss epcProviderAss = this.queryEntity(epcProviderAssEditParam.getId());
        BeanUtil.copyProperties(epcProviderAssEditParam, epcProviderAss);
        this.updateById(epcProviderAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcProviderAssIdParam> epcProviderAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcProviderAssIdParamList, EpcProviderAssIdParam::getId));
    }

    @Override
    public EpcProviderAss detail(EpcProviderAssIdParam epcProviderAssIdParam) {
        return this.queryEntity(epcProviderAssIdParam.getId());
    }

    @Override
    public EpcProviderAss queryEntity(String id) {
        EpcProviderAss epcProviderAss = this.getById(id);
        if(ObjectUtil.isEmpty(epcProviderAss)) {
            throw new CommonException("EPC 供应商库-考核表不存在，id值为：{}", id);
        }
        return epcProviderAss;
    }

    @Override
    public Boolean check(EpcProviderAssCheckParam epcProviderAssCheckParam) {
        QueryWrapper<EpcProviderAss> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EpcProviderAss::getPrvId,epcProviderAssCheckParam.getPrvId());
        queryWrapper.lambda().eq(EpcProviderAss::getAssType,epcProviderAssCheckParam.getAssType());
        queryWrapper.lambda().eq(EpcProviderAss::getYear,epcProviderAssCheckParam.getYear());
        queryWrapper.lambda().eq(EpcProviderAss::getSeason,epcProviderAssCheckParam.getSeason());
        List<EpcProviderAss> epcProviderAssList = this.list(queryWrapper);
        return ObjectUtil.isNotEmpty(epcProviderAssList);
    }

    @Override
    public Page<ProviderAssVo> pageList(EpcProviderAssPageParam epcProviderAssPageParam) {
        QueryWrapper<EpcProviderAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getPrvId())) {
            queryWrapper.eq("a.PRV_ID", epcProviderAssPageParam.getPrvId());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getYear())) {
            queryWrapper.eq("a.YEAR", epcProviderAssPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getAssType())) {
            queryWrapper.eq("a.ASS_TYPE", epcProviderAssPageParam.getAssType());
        }
        if(ObjectUtil.isNotEmpty(epcProviderAssPageParam.getSearchKey())) {
            queryWrapper.eq("b.PRV_NAME", epcProviderAssPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(epcProviderAssPageParam.getSortField(), epcProviderAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcProviderAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcProviderAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcProviderAssPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.YEAR").orderByAsc("b.PRV_NAME");
        }

        //获取 供应商+年+考核类型
        Page<ProviderAssVo> pageAssList = epcProviderAssMapper.getAssList(CommonPageRequest.defaultPage(),queryWrapper);
        //有数据，处理
        if(CollUtil.isNotEmpty(pageAssList.getRecords())){
            //获取该供应商考核记录
            List<String> prvIds = pageAssList.getRecords().stream().map(x->x.getPrvId()).distinct().collect(Collectors.toList());
            List<EpcProviderAss> providerAssList = this.list(new QueryWrapper<EpcProviderAss>().lambda().in(EpcProviderAss::getPrvId,prvIds));
            //处理四季
            List<String> seasons = Arrays.asList("1","2","3","4");
            for (ProviderAssVo vo:pageAssList.getRecords()) {
                for (String season:seasons) {
                    Optional<EpcProviderAss> first = providerAssList.stream().filter(x -> x.getPrvId().equals(vo.getPrvId())
                            && x.getYear().equals(vo.getYear()) && x.getAssType().equals(vo.getAssType()) && x.getSeason().equals(season)).findFirst();
                    if(season.equals("1")){
                        vo.setAssScore1(first.isPresent() ? first.get().getAssScore() : null);
                        vo.setAssLevel1(first.isPresent() ? first.get().getAssLevel() : null);
                        vo.setAssJson1(first.isPresent() ? first.get().getAssJson() : null);
                        vo.setMemo1(first.isPresent() ? first.get().getMemo() : null);
                    }
                    if(season.equals("2")){
                        vo.setAssScore2(first.isPresent() ? first.get().getAssScore() : null);
                        vo.setAssLevel2(first.isPresent() ? first.get().getAssLevel() : null);
                        vo.setAssJson2(first.isPresent() ? first.get().getAssJson() : null);
                        vo.setMemo2(first.isPresent() ? first.get().getMemo() : null);
                    }
                    if(season.equals("3")){
                        vo.setAssScore3(first.isPresent() ? first.get().getAssScore() : null);
                        vo.setAssLevel3(first.isPresent() ? first.get().getAssLevel() : null);
                        vo.setAssJson3(first.isPresent() ? first.get().getAssJson() : null);
                        vo.setMemo3(first.isPresent() ? first.get().getMemo() : null);
                    }
                    if(season.equals("4")){
                        vo.setAssScore4(first.isPresent() ? first.get().getAssScore() : null);
                        vo.setAssLevel4(first.isPresent() ? first.get().getAssLevel() : null);
                        vo.setAssJson4(first.isPresent() ? first.get().getAssJson() : null);
                        vo.setMemo4(first.isPresent() ? first.get().getMemo() : null);
                    }
                }
            }
        }
        return pageAssList;
    }
}
