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
package vip.xiaonuo.dev.modular.supervisionPlatform.service.impl;

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
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBPersonPerFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.mapper.TBPersonPerFormanceMapper;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBPersonPerFormanceAddParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBPersonPerFormanceEditParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBPersonPerFormanceIdParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBPersonPerFormancePageParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBPersonPerFormanceService;

import java.util.ArrayList;
import java.util.List;

/**
 * 市场监管公共服务平台-个人业绩信息表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/11 10:58
 **/
@Service
public class TBPersonPerformanceServiceImpl extends ServiceImpl<TBPersonPerFormanceMapper, TBPersonPerFormance> implements TBPersonPerFormanceService {

    @Override
    public Page<TBPersonPerFormance> page(TBPersonPerFormancePageParam tbpersonperformancePageParam) {
        QueryWrapper<TBPersonPerFormance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tbpersonperformancePageParam.getPersonPerfNum())) {
            queryWrapper.lambda().eq(TBPersonPerFormance::getPersonPerfNum, tbpersonperformancePageParam.getPersonPerfNum());
        }
        if(ObjectUtil.isNotEmpty(tbpersonperformancePageParam.getPerfNum())) {
            queryWrapper.lambda().eq(TBPersonPerFormance::getPerfNum, tbpersonperformancePageParam.getPerfNum());
        }
        if(ObjectUtil.isNotEmpty(tbpersonperformancePageParam.getPersonIdCard())) {
            queryWrapper.lambda().eq(TBPersonPerFormance::getPersonIdCard, tbpersonperformancePageParam.getPersonIdCard());
        }
        if(ObjectUtil.isAllNotEmpty(tbpersonperformancePageParam.getSortField(), tbpersonperformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tbpersonperformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, tbpersonperformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tbpersonperformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TBPersonPerFormance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TBPersonPerFormanceAddParam tbpersonperformanceAddParam) {
        TBPersonPerFormance tbpersonperformance = BeanUtil.toBean(tbpersonperformanceAddParam, TBPersonPerFormance.class);
        this.save(tbpersonperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void personAddBatch(List<TBPersonPerFormanceAddParam> tbpersonperformanceAddParamList) {
        try{
            List<TBPersonPerFormance> tbPersonPerFormanceList = new ArrayList<>();
            tbpersonperformanceAddParamList.forEach(param -> tbPersonPerFormanceList.add(BeanUtil.toBean(param, TBPersonPerFormance.class)));
            this.saveBatch(tbPersonPerFormanceList);
        }catch (Exception e){
            log.error("批量导入失败！e:"+e.toString());
            throw new CommonException("批量导入失败！"+e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TBPersonPerFormanceEditParam tbpersonperformanceEditParam) {
        TBPersonPerFormance tbpersonperformance = this.queryEntity(tbpersonperformanceEditParam.getId());
        BeanUtil.copyProperties(tbpersonperformanceEditParam, tbpersonperformance);
        this.updateById(tbpersonperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TBPersonPerFormanceIdParam> TBPersonPerFormanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(TBPersonPerFormanceIdParamList, TBPersonPerFormanceIdParam::getId));
    }

    @Override
    public TBPersonPerFormance detail(TBPersonPerFormanceIdParam tbpersonperformanceIdParam) {
        return this.queryEntity(tbpersonperformanceIdParam.getId());
    }

    @Override
    public TBPersonPerFormance queryEntity(String id) {
        TBPersonPerFormance tbpersonperformance = this.getById(id);
        if(ObjectUtil.isEmpty(tbpersonperformance)) {
            throw new CommonException("市场监管公共服务平台-个人业绩信息表不存在，id值为：{}", id);
        }
        return tbpersonperformance;
    }
}
