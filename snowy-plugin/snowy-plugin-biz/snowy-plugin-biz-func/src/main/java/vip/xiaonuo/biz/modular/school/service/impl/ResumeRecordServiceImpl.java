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
package vip.xiaonuo.biz.modular.school.service.impl;

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
import vip.xiaonuo.biz.modular.school.entity.ResumeRecord;
import vip.xiaonuo.biz.modular.school.mapper.ResumeRecordMapper;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordAddParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordEditParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordIdParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordPageParam;
import vip.xiaonuo.biz.modular.school.service.ResumeRecordService;

import java.util.List;

/**
 * 校招-简历操作记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/15 08:55
 **/

@DS("slave_1")
@Service
public class ResumeRecordServiceImpl extends ServiceImpl<ResumeRecordMapper, ResumeRecord> implements ResumeRecordService {

    @Override
    public Page<ResumeRecord> page(ResumeRecordPageParam resumeRecordPageParam) {
        QueryWrapper<ResumeRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(resumeRecordPageParam.getActionType())) {
            queryWrapper.lambda().eq(ResumeRecord::getActionType, resumeRecordPageParam.getActionType());
        }
        if(ObjectUtil.isNotEmpty(resumeRecordPageParam.getOperation())) {
            queryWrapper.lambda().like(ResumeRecord::getOperation, resumeRecordPageParam.getOperation());
        }
        if(ObjectUtil.isNotEmpty(resumeRecordPageParam.getResult())) {
            queryWrapper.lambda().eq(ResumeRecord::getResult, resumeRecordPageParam.getResult());
        }
        if(ObjectUtil.isNotEmpty(resumeRecordPageParam.getType())) {
            queryWrapper.lambda().eq(ResumeRecord::getType, resumeRecordPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(resumeRecordPageParam.getSortField(), resumeRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(resumeRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, resumeRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(resumeRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ResumeRecord::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ResumeRecordAddParam resumeRecordAddParam) {
        ResumeRecord resumeRecord = BeanUtil.toBean(resumeRecordAddParam, ResumeRecord.class);
        this.save(resumeRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ResumeRecordEditParam resumeRecordEditParam) {
        ResumeRecord resumeRecord = this.queryEntity(resumeRecordEditParam.getId());
        BeanUtil.copyProperties(resumeRecordEditParam, resumeRecord);
        this.updateById(resumeRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ResumeRecordIdParam> resumeRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(resumeRecordIdParamList, ResumeRecordIdParam::getId));
    }

    @Override
    public ResumeRecord detail(ResumeRecordIdParam resumeRecordIdParam) {
        return this.queryEntity(resumeRecordIdParam.getId());
    }

    @Override
    public ResumeRecord queryEntity(String id) {
        ResumeRecord resumeRecord = this.getById(id);
        if(ObjectUtil.isEmpty(resumeRecord)) {
            throw new CommonException("校招-简历操作记录不存在，id值为：{}", id);
        }
        return resumeRecord;
    }
}
