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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadUserProfession;
import vip.xiaonuo.biz.modular.cad.mapper.CadUserProfessionMapper;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionPageParam;
import vip.xiaonuo.biz.modular.cad.service.CadUserProfessionService;

import java.util.List;

/**
 * CAD-用户专业关系表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:41
 **/
@DS("slave_1")
@Service
public class CadUserProfessionServiceImpl extends ServiceImpl<CadUserProfessionMapper, CadUserProfession> implements CadUserProfessionService {

    @Override
    public Page<CadUserProfession> page(CadUserProfessionPageParam cadUserProfessionPageParam) {
        QueryWrapper<CadUserProfession> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadUserProfessionPageParam.getSortField(), cadUserProfessionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadUserProfessionPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadUserProfessionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadUserProfessionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadUserProfession::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadUserProfessionAddParam cadUserProfessionAddParam) {
        CadUserProfession cadUserProfession = BeanUtil.toBean(cadUserProfessionAddParam, CadUserProfession.class);
        this.save(cadUserProfession);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadUserProfessionEditParam cadUserProfessionEditParam) {
        CadUserProfession cadUserProfession = this.queryEntity(cadUserProfessionEditParam.getId());
        BeanUtil.copyProperties(cadUserProfessionEditParam, cadUserProfession);
        this.updateById(cadUserProfession);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadUserProfessionIdParam> cadUserProfessionIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadUserProfessionIdParamList, CadUserProfessionIdParam::getId));
    }

    @Override
    public CadUserProfession detail(CadUserProfessionIdParam cadUserProfessionIdParam) {
        return this.queryEntity(cadUserProfessionIdParam.getId());
    }

    @Override
    public CadUserProfession queryEntity(String id) {
        CadUserProfession cadUserProfession = this.getById(id);
        if(ObjectUtil.isEmpty(cadUserProfession)) {
            throw new CommonException("CAD-用户专业关系表不存在，id值为：{}", id);
        }
        return cadUserProfession;
    }
}
