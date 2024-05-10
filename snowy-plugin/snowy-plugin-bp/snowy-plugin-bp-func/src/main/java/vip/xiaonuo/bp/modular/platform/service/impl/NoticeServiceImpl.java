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
package vip.xiaonuo.bp.modular.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.entity.UserPay;
import vip.xiaonuo.bp.modular.platform.entity.UserSign;
import vip.xiaonuo.bp.modular.platform.enums.PayTypeEnum;
import vip.xiaonuo.bp.modular.platform.enums.SignStatusEnum;
import vip.xiaonuo.bp.modular.platform.enums.YesOrNoEnum;
import vip.xiaonuo.bp.modular.platform.service.ClarifyService;
import vip.xiaonuo.bp.modular.platform.service.UserPayService;
import vip.xiaonuo.bp.modular.platform.service.UserSignService;
import vip.xiaonuo.bp.modular.platform.vo.NoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.UserNoticeVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.Notice;
import vip.xiaonuo.bp.modular.platform.mapper.NoticeMapper;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeAddParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeEditParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticePageParam;
import vip.xiaonuo.bp.modular.platform.service.NoticeService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 招采管理 - 竞价公告|询价公告|招标公告Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/07 16:36
 **/
@Service
@DS("slave_1")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private ClarifyService clarifyService;
    @Resource
    private UserSignService userSignService;
    @Resource
    private UserPayService userPayService;

    @Override
    public Page<NoticeVo> page(NoticePageParam noticePageParam) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(noticePageParam.getType())) {
            queryWrapper.lambda().eq(Notice::getType, noticePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getTitle())) {
            queryWrapper.lambda().like(Notice::getTitle, noticePageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getNo())) {
            queryWrapper.lambda().like(Notice::getNo, noticePageParam.getNo());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getObjName())) {
            queryWrapper.lambda().like(Notice::getObjName, noticePageParam.getObjName());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getStartStartTime()) && ObjectUtil.isNotEmpty(noticePageParam.getEndStartTime())) {
            queryWrapper.lambda().between(Notice::getStartTime, noticePageParam.getStartStartTime(), noticePageParam.getEndStartTime());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getStartEndTime()) && ObjectUtil.isNotEmpty(noticePageParam.getEndEndTime())) {
            queryWrapper.lambda().between(Notice::getEndTime, noticePageParam.getStartEndTime(), noticePageParam.getEndEndTime());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getStartPublishTime()) && ObjectUtil.isNotEmpty(noticePageParam.getEndPublishTime())) {
            queryWrapper.lambda().between(Notice::getPublishTime, noticePageParam.getStartPublishTime(), noticePageParam.getEndPublishTime());
        }
        if(ObjectUtil.isNotEmpty(noticePageParam.getPrvType())) {
            JSONArray objects = new JSONArray(noticePageParam.getPrvType());
            if(objects.size() > 0) {
                queryWrapper.lambda().and(q->{
                    for (int i = 0; i < objects.size(); i++) {
                        q.or().like(Notice::getPrvType,objects.get(i).toString());
                    }
                });
            }
        }
        if(ObjectUtil.isAllNotEmpty(noticePageParam.getSortField(), noticePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(noticePageParam.getSortOrder());
            queryWrapper.orderBy(true, noticePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(noticePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Notice::getCreateTime);
        }
        Page<Notice> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<NoticeVo> records = BeanUtil.copyToList(result.getRecords(),NoticeVo.class);
        if(CollUtil.isNotEmpty(records)){
            records.forEach(item->{
                List<Clarify> clarifyList = clarifyService.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getNoticeId,item.getId()));
                item.setHasClarify(CollUtil.isNotEmpty(clarifyList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
                item.setClarifyList(clarifyList);
                if(ObjectUtil.isNotEmpty(item.getStartTime()) && ObjectUtil.isNotEmpty(item.getEndTime())){
                    DateTime now = DateTime.now();
                    if(now.isAfter(item.getStartTime()) && now.isBefore(item.getEndTime())){
                        item.setBmStatus(SignStatusEnum.SIGN_ING.getValue());
                    }else if(now.isAfter(item.getEndTime())){
                        item.setBmStatus(SignStatusEnum.SIGN_STOP.getValue());
                    }
                }
            });
        }
        Page<NoticeVo> newResult = new Page<>();
        newResult.setRecords(records);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(NoticeAddParam noticeAddParam) {
        Notice notice = BeanUtil.toBean(noticeAddParam, Notice.class);
        notice.setPublishTime(new Date());
        //校验标题是否重复
        if(ObjectUtil.isNotEmpty(notice.getTitle()) && ObjectUtil.isNotEmpty(notice.getType())){
            int size = this.list(new QueryWrapper<Notice>().lambda().eq(Notice::getTitle,notice.getTitle()).eq(Notice::getType,notice.getType())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.save(notice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(NoticeEditParam noticeEditParam) {
        Notice notice = this.queryEntity(noticeEditParam.getId());
        BeanUtil.copyProperties(noticeEditParam, notice);
        //校验标题是否重复
        if(ObjectUtil.isNotEmpty(notice.getTitle()) && ObjectUtil.isNotEmpty(notice.getType())){
            int size = this.list(new QueryWrapper<Notice>().lambda().eq(Notice::getTitle,notice.getTitle()).eq(Notice::getType,notice.getType()).ne(Notice::getId,notice.getId())).size();
            if(size > 0){
                throw new CommonException("标题已存在！");
            }
        }
        this.updateById(notice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<NoticeIdParam> noticeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(noticeIdParamList, NoticeIdParam::getId));
    }

    @Override
    public NoticeVo detail(NoticeIdParam noticeIdParam) {
        Notice notice = this.queryEntity(noticeIdParam.getId());
        NoticeVo noticeVo = BeanUtil.copyProperties(notice,NoticeVo.class);
        List<Clarify> clarifyList = clarifyService.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getNoticeId,noticeVo.getId()));
        noticeVo.setHasClarify(CollUtil.isNotEmpty(clarifyList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
        noticeVo.setClarifyList(clarifyList);
        if(ObjectUtil.isNotEmpty(noticeVo.getStartTime()) && ObjectUtil.isNotEmpty(noticeVo.getEndTime())){
            DateTime now = DateTime.now();
            if(now.isAfter(noticeVo.getStartTime()) && now.isBefore(noticeVo.getEndTime())){
                noticeVo.setBmStatus(SignStatusEnum.SIGN_ING.getValue());
            }else if(now.isAfter(noticeVo.getEndTime())){
                noticeVo.setBmStatus(SignStatusEnum.SIGN_STOP.getValue());
            }
        }
        return noticeVo;
    }

    @Override
    public Notice queryEntity(String id) {
        Notice notice = this.getById(id);
        if(ObjectUtil.isEmpty(notice)) {
            throw new CommonException("招采管理 - 竞价公告|询价公告|招标公告不存在，id值为：{}", id);
        }
        return notice;
    }

    @Override
    public UserNoticeVo noticeDetail(NoticeIdParam noticeIdParam) {
        Notice notice = this.queryEntity(noticeIdParam.getId());
        UserNoticeVo noticeVo = BeanUtil.copyProperties(notice,UserNoticeVo.class);
        List<Clarify> clarifyList = clarifyService.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getNoticeId,noticeVo.getId()));
        noticeVo.setHasClarify(CollUtil.isNotEmpty(clarifyList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
        noticeVo.setClarifyList(clarifyList);
        if(ObjectUtil.isNotEmpty(noticeVo.getStartTime()) && ObjectUtil.isNotEmpty(noticeVo.getEndTime())){
            DateTime now = DateTime.now();
            if(now.isAfter(noticeVo.getStartTime()) && now.isBefore(noticeVo.getEndTime())){
                noticeVo.setBmStatus(SignStatusEnum.SIGN_ING.getValue());
            }else if(now.isAfter(noticeVo.getEndTime())){
                noticeVo.setBmStatus(SignStatusEnum.SIGN_STOP.getValue());
            }
        }
        //用户已登录
        if(ObjectUtil.isNotEmpty(noticeIdParam.getBpUserId())) {
            //已登录用户 - 是否已报名
            List<UserSign> userSignList = userSignService.list(new QueryWrapper<UserSign>().lambda().eq(UserSign::getBpUserId,noticeIdParam.getBpUserId()).eq(UserSign::getNoticeId,noticeIdParam.getId()));
            noticeVo.setIsSign(CollUtil.isNotEmpty(userSignList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
            //已登录用户 - 是否已缴纳查看附件费用
            List<UserPay> userPayList = userPayService.list(new QueryWrapper<UserPay>().lambda()
                    .eq(UserPay::getBpUserId,noticeIdParam.getBpUserId())
                    .eq(UserPay::getNoticeId,noticeIdParam.getId())
                    .eq(UserPay::getType, PayTypeEnum.PAY_FOR_ATTACH.getValue()));
            noticeVo.setIsPayForAttach(CollUtil.isNotEmpty(userPayList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
        }
        return noticeVo;
    }
}
