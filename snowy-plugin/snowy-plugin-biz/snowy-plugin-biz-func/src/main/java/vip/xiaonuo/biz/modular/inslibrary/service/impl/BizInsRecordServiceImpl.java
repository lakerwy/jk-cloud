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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsRecord;
import vip.xiaonuo.biz.modular.inslibrary.mapper.BizInsRecordMapper;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordAddParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordEditParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordIdParam;
import vip.xiaonuo.biz.modular.inslibrary.param.BizInsRecordPageParam;
import vip.xiaonuo.biz.modular.inslibrary.service.BizInsRecordService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-产业生态链-资源库-拜访记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/15 15:27
 **/

@DS("slave_1")
@Service
public class BizInsRecordServiceImpl extends ServiceImpl<BizInsRecordMapper, BizInsRecord> implements BizInsRecordService {

    @Override
    public Page<BizInsRecord> page(BizInsRecordPageParam bizInsRecordPageParam) {
        QueryWrapper<BizInsRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizInsRecordPageParam.getLibraryId())){
            queryWrapper.lambda().eq(BizInsRecord::getLibraryId,bizInsRecordPageParam.getLibraryId());
        }
        if(ObjectUtil.isAllNotEmpty(bizInsRecordPageParam.getSortField(), bizInsRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizInsRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizInsRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizInsRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizInsRecord::getLibraryId).orderByDesc(BizInsRecord::getVisitorTime).orderByDesc(BizInsRecord::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizInsRecordAddParam bizInsRecordAddParam) {
        BizInsRecord bizInsRecord = BeanUtil.toBean(bizInsRecordAddParam, BizInsRecord.class);
        this.save(bizInsRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizInsRecordEditParam bizInsRecordEditParam) {
        BizInsRecord bizInsRecord = this.queryEntity(bizInsRecordEditParam.getId());
        BeanUtil.copyProperties(bizInsRecordEditParam, bizInsRecord);
        this.updateById(bizInsRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizInsRecordIdParam> bizInsRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizInsRecordIdParamList, BizInsRecordIdParam::getId));
    }

    @Override
    public BizInsRecord detail(BizInsRecordIdParam bizInsRecordIdParam) {
        return this.queryEntity(bizInsRecordIdParam.getId());
    }

    @Override
    public BizInsRecord queryEntity(String id) {
        BizInsRecord bizInsRecord = this.getById(id);
        if(ObjectUtil.isEmpty(bizInsRecord)) {
            throw new CommonException("业务表-产业生态链-资源库-拜访记录不存在，id值为：{}", id);
        }
        return bizInsRecord;
    }
}
