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
package vip.xiaonuo.biz.modular.crm.calocks.service.impl;

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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.calocks.entity.Calocks;
import vip.xiaonuo.biz.modular.crm.calocks.mapper.CalocksMapper;
import vip.xiaonuo.biz.modular.crm.calocks.param.CalocksAddParam;
import vip.xiaonuo.biz.modular.crm.calocks.param.CalocksEditParam;
import vip.xiaonuo.biz.modular.crm.calocks.param.CalocksIdParam;
import vip.xiaonuo.biz.modular.crm.calocks.param.CalocksPageParam;
import vip.xiaonuo.biz.modular.crm.calocks.service.CalocksService;
import vip.xiaonuo.common.util.CommonSnUtil;

import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-CA登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:35
 **/

@DS("slave_1")
@Service
public class CalocksServiceImpl extends ServiceImpl<CalocksMapper, Calocks> implements CalocksService {

    @Override
    public Page<Calocks> page(CalocksPageParam calocksPageParam) {
        QueryWrapper<Calocks> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(calocksPageParam.getOpt())) {
            queryWrapper.lambda().like(Calocks::getOpt, calocksPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(calocksPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Calocks::getOptDate, calocksPageParam.getStartOptDate(), calocksPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getSn())) {
            queryWrapper.lambda().like(Calocks::getSn, calocksPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getUnitName())) {
            queryWrapper.lambda().like(Calocks::getUnitName, calocksPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getIssuer())) {
            queryWrapper.lambda().like(Calocks::getIssuer, calocksPageParam.getIssuer());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getKeeper())) {
            queryWrapper.lambda().like(Calocks::getKeeper, calocksPageParam.getKeeper());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getApplicant())) {
            queryWrapper.lambda().like(Calocks::getApplicant, calocksPageParam.getApplicant());
        }
        if(ObjectUtil.isNotEmpty(calocksPageParam.getStatus())) {
            queryWrapper.lambda().eq(Calocks::getStatus, calocksPageParam.getStatus());
        }
        if (ObjectUtil.isNotEmpty(calocksPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(Calocks::getSn, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getOpt, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getUnitName, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getIssuer, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getKeeper, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getApplicant, calocksPageParam.getSearchKey()).or()
                    .like(Calocks::getAccount1, calocksPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(calocksPageParam.getSortField(), calocksPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(calocksPageParam.getSortOrder());
            queryWrapper.orderBy(true, calocksPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(calocksPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Calocks::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CalocksAddParam calocksAddParam) {
        Calocks calocks = BeanUtil.toBean(calocksAddParam, Calocks.class);
        //处理Sn编号
        Calocks tmp = this.getOne(new QueryWrapper<Calocks>().lambda().orderByDesc(Calocks::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(calocksAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYY001.getValue());
        calocks.setSn(newSn);
        calocks.setOpt(StpLoginUserUtil.getLoginUser().getName());
        calocks.setOptDate(new Date());
        this.save(calocks);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CalocksEditParam calocksEditParam) {
        Calocks calocks = this.queryEntity(calocksEditParam.getId());
        BeanUtil.copyProperties(calocksEditParam, calocks);
        this.updateById(calocks);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CalocksIdParam> calocksIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(calocksIdParamList, CalocksIdParam::getId));
    }

    @Override
    public Calocks detail(CalocksIdParam calocksIdParam) {
        return this.queryEntity(calocksIdParam.getId());
    }

    @Override
    public Calocks queryEntity(String id) {
        Calocks calocks = this.getById(id);
        if(ObjectUtil.isEmpty(calocks)) {
            throw new CommonException("业务表-CRM-CA登记不存在，id值为：{}", id);
        }
        return calocks;
    }
}
