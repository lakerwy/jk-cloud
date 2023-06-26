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
package vip.xiaonuo.sys.modular.record.service.impl;

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
import vip.xiaonuo.sys.modular.record.entity.PartnerRecord;
import vip.xiaonuo.sys.modular.record.mapper.PartnerRecordMapper;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordAddParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordEditParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordIdParam;
import vip.xiaonuo.sys.modular.record.param.PartnerRecordPageParam;
import vip.xiaonuo.sys.modular.record.service.PartnerRecordService;

import java.util.List;

/**
 * 合作伙伴-维护记录Service接口实现类
 *
 * @author mayike
 * @date  2023/06/26 14:08
 **/
@Service
public class PartnerRecordServiceImpl extends ServiceImpl<PartnerRecordMapper, PartnerRecord> implements PartnerRecordService {

    @Override
    public Page<PartnerRecord> page(PartnerRecordPageParam partnerRecordPageParam) {
        QueryWrapper<PartnerRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(partnerRecordPageParam.getSortField(), partnerRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(partnerRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, partnerRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(partnerRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PartnerRecord::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PartnerRecordAddParam partnerRecordAddParam) {
        PartnerRecord partnerRecord = BeanUtil.toBean(partnerRecordAddParam, PartnerRecord.class);
        this.save(partnerRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PartnerRecordEditParam partnerRecordEditParam) {
        PartnerRecord partnerRecord = this.queryEntity(partnerRecordEditParam.getId());
        BeanUtil.copyProperties(partnerRecordEditParam, partnerRecord);
        this.updateById(partnerRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PartnerRecordIdParam> partnerRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(partnerRecordIdParamList, PartnerRecordIdParam::getId));
    }

    @Override
    public PartnerRecord detail(PartnerRecordIdParam partnerRecordIdParam) {
        return this.queryEntity(partnerRecordIdParam.getId());
    }

    @Override
    public PartnerRecord queryEntity(String id) {
        PartnerRecord partnerRecord = this.getById(id);
        if(ObjectUtil.isEmpty(partnerRecord)) {
            throw new CommonException("合作伙伴-维护记录不存在，id值为：{}", id);
        }
        return partnerRecord;
    }
}
