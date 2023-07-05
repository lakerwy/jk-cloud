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
package vip.xiaonuo.biz.modular.crm.rollinout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.fpinformations.entity.Fpinformations;
import vip.xiaonuo.biz.modular.crm.fpinformations.service.FpinformationsService;
import vip.xiaonuo.biz.modular.crm.rollinout.param.*;
import vip.xiaonuo.biz.modular.crm.rollinout.vo.RollinoutInfoVo;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.rollinout.entity.Rollinout;
import vip.xiaonuo.biz.modular.crm.rollinout.mapper.RollinoutMapper;
import vip.xiaonuo.biz.modular.crm.rollinout.service.RollinoutService;
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-CRM-合作伙伴人员转入转出Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:56
 **/

@DS("slave_1")
@Service
public class RollinoutServiceImpl extends ServiceImpl<RollinoutMapper, Rollinout> implements RollinoutService {

    @Resource
    private FpinformationsService fpinformationsService;

    @Resource
    private RollinoutMapper rollinoutMapper;

    @Override
    public Page<Rollinout> page(RollinoutPageParam rollinoutPageParam) {
        QueryWrapper<Rollinout> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getFpId())) {
            queryWrapper.lambda().eq(Rollinout::getFpId, rollinoutPageParam.getFpId());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getSn())) {
            queryWrapper.lambda().like(Rollinout::getSn, rollinoutPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getOpt())) {
            queryWrapper.lambda().like(Rollinout::getOpt, rollinoutPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(rollinoutPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Rollinout::getOptDate, rollinoutPageParam.getStartOptDate(), rollinoutPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getOperate())) {
            queryWrapper.lambda().eq(Rollinout::getOperate, rollinoutPageParam.getOperate());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getName())) {
            queryWrapper.lambda().like(Rollinout::getName, rollinoutPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getDept())) {
            queryWrapper.lambda().like(Rollinout::getDept, rollinoutPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(rollinoutPageParam.getRank())) {
            queryWrapper.lambda().like(Rollinout::getRank, rollinoutPageParam.getRank());
        }
        if(ObjectUtil.isAllNotEmpty(rollinoutPageParam.getSortField(), rollinoutPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rollinoutPageParam.getSortOrder());
            queryWrapper.orderBy(true, rollinoutPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rollinoutPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Rollinout::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RollinoutAddParam rollinoutAddParam) {
        Rollinout rollinout = BeanUtil.toBean(rollinoutAddParam, Rollinout.class);
        //处理Sn编号
        Rollinout tmp = this.getOne(new QueryWrapper<Rollinout>().lambda().orderByDesc(Rollinout::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(rollinoutAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        rollinout.setSn(newSn);
        this.save(rollinout);

        //反更新人员信息的社保+公积金情况
        Fpinformations fpinformations = fpinformationsService.queryEntity(rollinout.getFpId());
        fpinformations.setSocial(rollinout.getSocial());
        fpinformations.setFund(rollinout.getFund());
        fpinformationsService.updateById(fpinformations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RollinoutEditParam rollinoutEditParam) {
        Rollinout rollinout = this.queryEntity(rollinoutEditParam.getId());
        BeanUtil.copyProperties(rollinoutEditParam, rollinout);
        this.updateById(rollinout);

        //反更新人员信息的社保+公积金情况
        Fpinformations fpinformations = fpinformationsService.queryEntity(rollinout.getFpId());
        fpinformations.setSocial(rollinout.getSocial());
        fpinformations.setFund(rollinout.getFund());
        fpinformationsService.updateById(fpinformations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RollinoutIdParam> rollinoutIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(rollinoutIdParamList, RollinoutIdParam::getId));
    }

    @Override
    public Rollinout detail(RollinoutIdParam rollinoutIdParam) {
        return this.queryEntity(rollinoutIdParam.getId());
    }

    @Override
    public Rollinout queryEntity(String id) {
        Rollinout rollinout = this.getById(id);
        if(ObjectUtil.isEmpty(rollinout)) {
            throw new CommonException("业务表-CRM-合作伙伴人员转入转出不存在，id值为：{}", id);
        }
        return rollinout;
    }

    @Override
    public IPage<RollinoutInfoVo> pageList(RollinoutInfoPageParam rollinoutInfoPageParam) {
        QueryWrapper<Rollinout> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(rollinoutInfoPageParam.getSn())) {
            queryWrapper.like("a.SN", rollinoutInfoPageParam.getSn());
        }
        if(ObjectUtil.isAllNotEmpty(rollinoutInfoPageParam.getSortField(), rollinoutInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rollinoutInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, rollinoutInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rollinoutInfoPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.SN");
        }
        return rollinoutMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public RollinoutInfoVo detailById(RollinoutIdParam rollinoutIdParam) {
        return rollinoutMapper.getDetailById(rollinoutIdParam.getId());
    }
}
