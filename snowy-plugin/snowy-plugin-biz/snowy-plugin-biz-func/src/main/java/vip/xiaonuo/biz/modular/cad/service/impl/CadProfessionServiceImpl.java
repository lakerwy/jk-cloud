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
package vip.xiaonuo.biz.modular.cad.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.CadUserProfession;
import vip.xiaonuo.biz.modular.cad.service.CadUserProfessionService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadProfession;
import vip.xiaonuo.biz.modular.cad.mapper.CadProfessionMapper;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadProfessionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * CAD-专业表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 **/
@DS("slave_1")
@Service
public class CadProfessionServiceImpl extends ServiceImpl<CadProfessionMapper, CadProfession> implements CadProfessionService {

    @Resource
    private CadUserProfessionService cadUserProfessionService;
    @Override
    public Page<CadProfession> page(CadProfessionPageParam cadProfessionPageParam) {
        QueryWrapper<CadProfession> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cadProfessionPageParam.getName())) {
            queryWrapper.lambda().like(CadProfession::getName, cadProfessionPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadProfessionPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CadProfession::getName,cadProfessionPageParam.getSearchKey())
                    .or().like(CadProfession::getMemo,cadProfessionPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cadProfessionPageParam.getSortField(), cadProfessionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadProfessionPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadProfessionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadProfessionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadProfession::getSortIndex).orderByAsc(CadProfession::getName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadProfessionAddParam cadProfessionAddParam) {
        CadProfession cadProfession = BeanUtil.toBean(cadProfessionAddParam, CadProfession.class);
        this.save(cadProfession);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadProfessionEditParam cadProfessionEditParam) {
        CadProfession cadProfession = this.queryEntity(cadProfessionEditParam.getId());
        BeanUtil.copyProperties(cadProfessionEditParam, cadProfession);
        this.updateById(cadProfession);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadProfessionIdParam> cadProfessionIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(cadProfessionIdParamList, CadProfessionIdParam::getId);
        this.removeByIds(ids);
        //删除专业时，同时删除 用户专业关系
        cadUserProfessionService.remove(new QueryWrapper<CadUserProfession>().lambda().in(CadUserProfession::getProfessionId,ids));
    }

    @Override
    public CadProfession detail(CadProfessionIdParam cadProfessionIdParam) {
        return this.queryEntity(cadProfessionIdParam.getId());
    }

    @Override
    public CadProfession queryEntity(String id) {
        CadProfession cadProfession = this.getById(id);
        if(ObjectUtil.isEmpty(cadProfession)) {
            throw new CommonException("CAD-专业表不存在，id值为：{}", id);
        }
        return cadProfession;
    }
}
