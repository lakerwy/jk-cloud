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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.major.entity.Major;
import vip.xiaonuo.biz.modular.crm.notes.vo.NotesInfoVo;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
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
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private NotesMapper notesMapper;

    @Override
    public Page<Notes> page(NotesPageParam notesPageParam) {
        QueryWrapper<Notes> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(notesPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Notes::getSn, notesPageParam.getSearchKey()).or()
                    .like(Notes::getOpt, notesPageParam.getSearchKey()).or()
                    .like(Notes::getPartnerNo, notesPageParam.getSearchKey()).or()
                    .like(Notes::getContractNo, notesPageParam.getSearchKey()).or()
                    .like(Notes::getNote, notesPageParam.getSearchKey());
        }
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
            queryWrapper.lambda().orderByDesc(Notes::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(NotesAddParam notesAddParam) {
        Notes notes = BeanUtil.toBean(notesAddParam, Notes.class);
        //处理Sn编号
        Notes tmp = this.getOne(new QueryWrapper<Notes>().lambda().orderByDesc(Notes::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(notesAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        notes.setSn(newSn);
        notes.setOpt(StpLoginUserUtil.getLoginUser().getName());
        notes.setOptDate(new Date());
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

    @Override
    public IPage<NotesInfoVo> pageList(NotesPageParam notesPageParam) {
        QueryWrapper<Notes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");
        if (ObjectUtil.isNotEmpty(notesPageParam.getSearchKey())) {
            queryWrapper.like("a.SN", notesPageParam.getSearchKey()).or()
                    .like("a.CONTRACT_NO", notesPageParam.getSearchKey()).or()
                    .like("a.PARTNER_NO", notesPageParam.getSearchKey()).or()
                    .like("b.contractName", notesPageParam.getSearchKey()).or()
                    .like("b.partnerName", notesPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getSn())) {
            queryWrapper.like("a.SN", notesPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getOpt())) {
            queryWrapper.like("a.OPT", notesPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndOptDate())) {
            queryWrapper.between("a.OPT_DATE", notesPageParam.getStartOptDate(), notesPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getType())) {
            queryWrapper.eq("a.TYPE", notesPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getPartnerNo())) {
            queryWrapper.like("a.PARTNER_NO", notesPageParam.getPartnerNo());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", notesPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartApplyDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndApplyDate())) {
            queryWrapper.between("a.APPLY_DATE", notesPageParam.getStartApplyDate(), notesPageParam.getEndApplyDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartFinalDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndFinalDate())) {
            queryWrapper.between("a.FINAL_DATE", notesPageParam.getStartFinalDate(), notesPageParam.getEndFinalDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getStartSignDate()) && ObjectUtil.isNotEmpty(notesPageParam.getEndSignDate())) {
            queryWrapper.between("a.SIGN_DATE", notesPageParam.getStartSignDate(), notesPageParam.getEndSignDate());
        }
        if(ObjectUtil.isNotEmpty(notesPageParam.getCancelMark())) {
            queryWrapper.eq("a.CANCEL_MARK", notesPageParam.getCancelMark());
        }
        if(ObjectUtil.isAllNotEmpty(notesPageParam.getSortField(), notesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(notesPageParam.getSortOrder());
            queryWrapper.orderBy(true, notesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(notesPageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.SN");
        }
        return notesMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public NotesInfoVo detailById(NotesIdParam notesIdParam) {
        return notesMapper.getDetailById(notesIdParam.getId());
    }
}
