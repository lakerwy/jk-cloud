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
package vip.xiaonuo.biz.modular.crm.majorleader.service.impl;

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
import vip.xiaonuo.biz.modular.crm.major.entity.Major;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.majorleader.entity.Majorleader;
import vip.xiaonuo.biz.modular.crm.majorleader.mapper.MajorleaderMapper;
import vip.xiaonuo.biz.modular.crm.majorleader.param.MajorleaderAddParam;
import vip.xiaonuo.biz.modular.crm.majorleader.param.MajorleaderEditParam;
import vip.xiaonuo.biz.modular.crm.majorleader.param.MajorleaderIdParam;
import vip.xiaonuo.biz.modular.crm.majorleader.param.MajorleaderPageParam;
import vip.xiaonuo.biz.modular.crm.majorleader.service.MajorleaderService;

import java.util.List;

/**
 * 业务表-CRM-专业负责人Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:43
 **/

@DS("slave_1")
@Service
public class MajorleaderServiceImpl extends ServiceImpl<MajorleaderMapper, Majorleader> implements MajorleaderService {

    @Override
    public Page<Majorleader> page(MajorleaderPageParam majorleaderPageParam) {
        QueryWrapper<Majorleader> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(majorleaderPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Majorleader::getUserName, majorleaderPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(majorleaderPageParam.getMajorId())) {
            queryWrapper.lambda().eq(Majorleader::getMajorId, majorleaderPageParam.getMajorId());
        }
        if(ObjectUtil.isNotEmpty(majorleaderPageParam.getUserName())) {
            queryWrapper.lambda().like(Majorleader::getUserName, majorleaderPageParam.getUserName());
        }
        if(ObjectUtil.isAllNotEmpty(majorleaderPageParam.getSortField(), majorleaderPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(majorleaderPageParam.getSortOrder());
            queryWrapper.orderBy(true, majorleaderPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(majorleaderPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Majorleader::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(MajorleaderAddParam majorleaderAddParam) {
        Majorleader majorleader = BeanUtil.toBean(majorleaderAddParam, Majorleader.class);
        this.save(majorleader);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(MajorleaderEditParam majorleaderEditParam) {
        Majorleader majorleader = this.queryEntity(majorleaderEditParam.getId());
        BeanUtil.copyProperties(majorleaderEditParam, majorleader);
        this.updateById(majorleader);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<MajorleaderIdParam> majorleaderIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(majorleaderIdParamList, MajorleaderIdParam::getId));
    }

    @Override
    public Majorleader detail(MajorleaderIdParam majorleaderIdParam) {
        return this.queryEntity(majorleaderIdParam.getId());
    }

    @Override
    public Majorleader queryEntity(String id) {
        Majorleader majorleader = this.getById(id);
        if(ObjectUtil.isEmpty(majorleader)) {
            throw new CommonException("业务表-CRM-专业负责人不存在，id值为：{}", id);
        }
        return majorleader;
    }
}
