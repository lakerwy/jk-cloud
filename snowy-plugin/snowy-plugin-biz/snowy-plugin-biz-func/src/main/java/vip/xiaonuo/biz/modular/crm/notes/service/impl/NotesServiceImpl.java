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
package vip.xiaonuo.biz.modular.crm.notes.service.impl;

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
import vip.xiaonuo.biz.modular.crm.notes.entity.Notes;
import vip.xiaonuo.biz.modular.crm.notes.mapper.NotesMapper;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesAddParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesEditParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesIdParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesPageParam;
import vip.xiaonuo.biz.modular.crm.notes.service.NotesService;

import java.util.List;

/**
 * 业务表-CRM-备忘录登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:49
 **/

@DS("slave_1")
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements NotesService {

    @Override
    public Page<Notes> page(NotesPageParam notesPageParam) {
        QueryWrapper<Notes> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(notesPageParam.getSn())) {
            queryWrapper.lambda().like(Notes::getSn, notesPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getOpt())) {
            queryWrapper.lambda().like(Notes::getOpt, notesPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Notes::getOptDate, notesPageParam.getStartOptDate(), notesPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getType())) {
            queryWrapper.lambda().eq(Notes::getType, notesPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getPartnerNo())) {
            queryWrapper.lambda().like(Notes::getPartnerNo, notesPageParam.getPartnerNo());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getContractNo())) {
            queryWrapper.lambda().like(Notes::getContractNo, notesPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartApplyDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndApplyDate())) {
            queryWrapper.lambda().between(Notes::getApplyDate, notesPageParam.getStartApplyDate(), notesPageParam.getEndApplyDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartFinalDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndFinalDate())) {
            queryWrapper.lambda().between(Notes::getFinalDate, notesPageParam.getStartFinalDate(), notesPageParam.getEndFinalDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartSignDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndSignDate())) {
            queryWrapper.lambda().between(Notes::getSignDate, notesPageParam.getStartSignDate(), notesPageParam.getEndSignDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getCancelMark())) {
            queryWrapper.lambda().eq(Notes::getCancelMark, notesPageParam.getCancelMark());
        }
        if(ObjectUtil.isAllNotEmpty(notesPageParam.getSortField(), notesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(notesPageParam.getSortOrder());
            queryWrapper.orderBy(true, notesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(notesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Notes::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(NotesAddParam notesAddParam) {
        Notes notes = BeanUtil.toBean(notesAddParam, Notes.class);
        this.save(notes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(NotesEditParam notesEditParam) {
        Notes notes = this.queryEntity(notesEditParam.getId());
        BeanUtil.copyProperties(notesEditParam, notes);
        this.updateById(notes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<NotesIdParam> notesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(notesIdParamList, NotesIdParam::getId));
    }

    @Override
    public Notes detail(NotesIdParam notesIdParam) {
        return this.queryEntity(notesIdParam.getId());
    }

    @Override
    public Notes queryEntity(String id) {
        Notes notes = this.getById(id);
        if(ObjectUtil.isEmpty(notes)) {
            throw new CommonException("业务表-CRM-备忘录登记不存在，id值为：{}", id);
        }
        return notes;
    }
}
