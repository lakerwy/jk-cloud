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
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.entity.UserPay;
import vip.xiaonuo.bp.modular.platform.enums.PayTypeEnum;
import vip.xiaonuo.bp.modular.platform.enums.SignStatusEnum;
import vip.xiaonuo.bp.modular.platform.enums.YesOrNoEnum;
import vip.xiaonuo.bp.modular.platform.mapper.BpUserMapper;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.bp.modular.platform.service.ClarifyService;
import vip.xiaonuo.bp.modular.platform.service.NoticeService;
import vip.xiaonuo.bp.modular.platform.service.UserPayService;
import vip.xiaonuo.bp.modular.platform.vo.UserSupplierVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.UserSign;
import vip.xiaonuo.bp.modular.platform.mapper.UserSignMapper;
import vip.xiaonuo.bp.modular.platform.service.UserSignService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 招采平台 - 用户 - （招采项目）报名记录表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/10 10:49
 **/
@Service
@DS("slave_1")
public class UserSignServiceImpl extends ServiceImpl<UserSignMapper, UserSign> implements UserSignService {

    @Resource
    private BpUserMapper bpUserMapper;
    @Resource
    private UserPayService userPayService;
    @Resource
    private UserSignService userSignService;
    @Resource
    private UserSignMapper userSignMapper;
    @Resource
    private NoticeService noticeService;
    @Resource
    private ClarifyService clarifyService;


    @Override
    public Page<UserSign> page(UserSignPageParam userSignPageParam) {
        QueryWrapper<UserSign> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userSignPageParam.getBmStatus())) {
            queryWrapper.lambda().eq(UserSign::getBmStatus, userSignPageParam.getBmStatus());
        }
        if(ObjectUtil.isAllNotEmpty(userSignPageParam.getSortField(), userSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, userSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userSignPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserSign::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserSignAddParam userSignAddParam) {
        UserSign userSign = BeanUtil.toBean(userSignAddParam, UserSign.class);
        this.save(userSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserSignEditParam userSignEditParam) {
        UserSign userSign = this.queryEntity(userSignEditParam.getId());
        BeanUtil.copyProperties(userSignEditParam, userSign);
        this.updateById(userSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserSignIdParam> userSignIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(userSignIdParamList, UserSignIdParam::getId));
    }

    @Override
    public UserSign detail(UserSignIdParam userSignIdParam) {
        return this.queryEntity(userSignIdParam.getId());
    }

    @Override
    public UserSign queryEntity(String id) {
        UserSign userSign = this.getById(id);
        if(ObjectUtil.isEmpty(userSign)) {
            throw new CommonException("招采平台 - 用户 - （招采项目）报名记录表不存在，id值为：{}", id);
        }
        return userSign;
    }

    @Override
    public void sign(SignParam signParam) {
        //1. 校验是否供应商入库 -> 校验供应商入库审批是否通过
        UserSupplierVo userSupplierVo = bpUserMapper.getBpUserSupplier(signParam.getBpUserId());
        if(ObjectUtil.isEmpty(userSupplierVo)){
            throw new CommonException("1001","用户异常");
        }
        if(ObjectUtil.isEmpty(userSupplierVo.getPrvId())){
            throw new CommonException("1002","请先操作供应商入库");
        }
        if(FlwStatusEnum.ACTIVE_STATUS.getValue().equals(userSupplierVo.getPrvStatus())){
            throw new CommonException("1003","供应商入库审批中");
        }
        //2. 校验是否已缴纳附件查看费用
        List<UserPay> userPayList = userPayService.list(new QueryWrapper<UserPay>().lambda()
                .eq(UserPay::getBpUserId,signParam.getBpUserId())
                .eq(UserPay::getNoticeId,signParam.getNoticeId())
                .eq(UserPay::getType, PayTypeEnum.PAY_FOR_ATTACH.getValue()));
        if(CollUtil.isEmpty(userPayList)){
            throw new CommonException("1004","请支付附件查看费用");
        }
        //3. 校验是否已报名
        List<UserSign> userSignList = userSignService.list(new QueryWrapper<UserSign>().lambda().eq(UserSign::getBpUserId,signParam.getBpUserId()).eq(UserSign::getNoticeId,signParam.getNoticeId()));
        if(CollUtil.isNotEmpty(userSignList)){
            throw new CommonException("1005","请勿重复报名");
        }
        //4. 报名记录
        UserSignAddParam userSignAddParam = new UserSignAddParam();
        userSignAddParam.setBpUserId(signParam.getBpUserId());
        userSignAddParam.setNoticeId(signParam.getNoticeId());
        userSignAddParam.setBmStatus(SignStatusEnum.SIGN_ING.getValue());
        String memo =  DateUtil.now() + " : 报名；";
        userSignAddParam.setMemo(memo);
        userSignService.add(userSignAddParam);
    }

    @Override
    public void upload(UploadParam uploadParam) {
        UserSign userSign = this.queryEntity(uploadParam.getId());
        userSign.setAttach(uploadParam.getAttach());
        userSign.setBmStatus(SignStatusEnum.SIGN_UP.getValue());
        String memo = userSign.getMemo() + DateUtil.now()+ " : 上传报名文件；";
        userSign.setMemo(memo);
        this.updateById(userSign);
    }

    @Override
    public Page<UserSignVo> mySignList(UserSignPageParam userSignPageParam) {
        QueryWrapper<UserSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(userSignPageParam.getBmStatus())) {
            queryWrapper.eq("a.BM_STATUS", userSignPageParam.getBmStatus());
        }
        if(ObjectUtil.isNotEmpty(userSignPageParam.getType())){
            queryWrapper.eq("b.TYPE",userSignPageParam.getType());
        }
        //当前用户id
        queryWrapper.eq("a.BP_USER_ID",userSignPageParam.getBpUserId());

        if(ObjectUtil.isAllNotEmpty(userSignPageParam.getSortField(), userSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, userSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userSignPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        Page<UserSignVo> result = userSignMapper.getMySignList(CommonPageRequest.defaultPage(), queryWrapper);
        if(CollUtil.isNotEmpty(result.getRecords())){
            result.getRecords().forEach(item->{
                item.setNoticeInfo(noticeService.getById(item.getNoticeId()));
                item.setClarifyList(clarifyService.list(new QueryWrapper<Clarify>().lambda().eq(Clarify::getNoticeId,item.getNoticeId())));
                UserPay userPay = userPayService.getOne(new QueryWrapper<UserPay>().lambda()
                        .eq(UserPay::getNoticeId,item.getNoticeId())
                        .eq(UserPay::getBpUserId,item.getBpUserId())
                        .eq(UserPay::getType,PayTypeEnum.PAY_FOR_BOND.getValue()));
                item.setUserPayInfo(userPay);
                item.setIsPayForBond(ObjectUtil.isNotEmpty(userPay) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
                LocalDateTime bondEndTime = DateUtil.toLocalDateTime(item.getCreateTime()).plusDays(Long.getLong(item.getNoticeBidDeadline()));
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                item.setNoticeBondEndTime(bondEndTime.format(dateTimeFormatter));
            });
        }
        return result;
    }
}
