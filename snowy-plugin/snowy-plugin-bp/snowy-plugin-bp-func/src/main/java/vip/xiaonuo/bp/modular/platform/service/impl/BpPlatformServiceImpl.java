package vip.xiaonuo.bp.modular.platform.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.bp.modular.platform.entity.*;
import vip.xiaonuo.bp.modular.platform.enums.PayTypeEnum;
import vip.xiaonuo.bp.modular.platform.enums.SignStatusEnum;
import vip.xiaonuo.bp.modular.platform.enums.UserStatusEnum;
import vip.xiaonuo.bp.modular.platform.enums.YesOrNoEnum;
import vip.xiaonuo.bp.modular.platform.mapper.BpUserMapper;
import vip.xiaonuo.bp.modular.platform.mapper.ClarifyMapper;
import vip.xiaonuo.bp.modular.platform.mapper.UserSignMapper;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticePageParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeTzPageParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointIdParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointPageParam;
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.bp.modular.platform.param.userSign.*;
import vip.xiaonuo.bp.modular.platform.service.*;
import vip.xiaonuo.bp.modular.platform.util.LoginBpUserUtil;
import vip.xiaonuo.bp.modular.platform.vo.*;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.mapper.BpSupplierMapper;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.bp.modular.recruit.supplier.service.BpSupplierService;
import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;
import vip.xiaonuo.bp.modular.platform.vo.UserNoticeVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;
import vip.xiaonuo.bp.modular.platform.vo.UserSupplierVo;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.CommonEmailUtil;
import vip.xiaonuo.dev.api.DevSmsApi;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@DS("slave_1")
public class BpPlatformServiceImpl implements BpPlatformService {

    private static final String BP_VALID_CODE_CACHE_KEY = "bp-validCode:";

    /* 云片短信：验证码短信模板 */
    private static final String SMS_YUNPIAN_TPL_CODE_ID = "5620650";

    /* 设备类型：PC */
    private static final String SA_TOKEN_DEVICE = "PC";

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private DevSmsApi devSmsApi;
    @Resource
    private BpUserService bpUserService;
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
    @Resource
    private NoticeTzService noticeTzService;
    @Resource
    private BpSupplierService bpSupplierService;
    @Resource
    private ClarifyMapper clarifyMapper;
    @Resource
    private BpSupplierMapper bpSupplierMapper;
    @Resource
    private BpPointService bpPointService;


    @Override
    public String register(RegisterParam registerParam) {
        //获取密码
        String password = "";
        String passwordSure = "";
        try{
            password = CommonCryptogramUtil.doSm2Decrypt(registerParam.getPassword());
            passwordSure = CommonCryptogramUtil.doSm2Decrypt(registerParam.getPasswordSure());
        }catch (Exception e){
            throw new CommonException("密码解密失败");
        }
        //1. 密码 - 确认密码一致
        if(!password.equals(passwordSure)){
            throw new CommonException("密码与确认密码不一致！");
        }
        //2. 短信验证码是否一致
        String phone = registerParam.getPhone();
        validPhoneValidCodeParam(phone, registerParam.getValidCode(), registerParam.getValidCodeReqNo());
        //3. 判断用户是否存在
        List<BpUser> bpUserList = bpUserService.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,phone));
        if(CollUtil.isNotEmpty(bpUserList)){
            throw new CommonException("账号已存在！");
        }
        //4. 创建账号
        BpUser bpUser = new BpUser();
        bpUser.setAccount(phone);
        bpUser.setName(registerParam.getName());
        bpUser.setPhone(phone);
        // SM2解密后，获得前端传来的密码哈希值
        String passwordHash = CommonCryptogramUtil.doHashValue(password);
        bpUser.setPassword(passwordHash);
        bpUser.setUserStatus(UserStatusEnum.ENABLE.getValue());
        bpUser.setMemo(DateUtil.now()+" : 招采平台-注册；");
        bpUserService.save(bpUser);
        //5. 执行登录
        BpLoginUser bpLoginUser = BeanUtil.copyProperties(bpUser,BpLoginUser.class);
        return execLogin(bpLoginUser,SA_TOKEN_DEVICE);
    }


    @Override
    public PicValidCodeResult getPicCaptcha() {
        // 生成验证码，随机4位字符
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 38, 4, 10);
        // 定义返回结果
        PicValidCodeResult picValidCodeResult = new PicValidCodeResult();
        // 获取验证码的值
        String validCode = circleCaptcha.getCode();
        // 获取验证码的base64
        String validCodeBase64 = circleCaptcha.getImageBase64Data();
        // 生成请求号
        String validCodeReqNo = IdWorker.getIdStr();
        // 将base64返回前端
        picValidCodeResult.setValidCodeBase64(validCodeBase64);
        // 将请求号返回前端
        picValidCodeResult.setValidCodeReqNo(validCodeReqNo);
        // 将请求号作为key，验证码的值作为value放到redis，用于校验，5分钟有效
        commonCacheOperator.put(BP_VALID_CODE_CACHE_KEY + validCodeReqNo, validCode, 5 * 60L);
        return picValidCodeResult;

    }

    @Override
    public String getPhoneValidCode(GetPhoneValidCodeParam getPhoneValidCodeParam) {
        // 手机号
        String phone = getPhoneValidCodeParam.getPhone();
        // 账号校验
        checkAccount(phone);
        // 验证码
        String validCode = getPhoneValidCodeParam.getValidCode();
        // 验证码请求号
        String validCodeReqNo = getPhoneValidCodeParam.getValidCodeReqNo();
        // 校验参数
        validPhoneValidCodeParam(null, validCode, validCodeReqNo);
        // 生成手机验证码的值，随机6为数字
        String phoneValidCode = RandomUtil.randomNumbers(6);
        // 生成手机验证码的请求号
        String phoneValidCodeReqNo = IdWorker.getIdStr();

        // TODO 使用云片执行发送验证码，将验证码作为短信内容的参数变量放入，
        String tpl_Id = SMS_YUNPIAN_TPL_CODE_ID;
        String tpl_Value = URLEncoder.encode("#code#") + "="  + URLEncoder.encode(phoneValidCode);
        devSmsApi.sendSmsYunPian("", phone, tpl_Id, tpl_Value);

        // 将请求号作为key，验证码的值作为value放到redis，用于校验，10分钟有效
        commonCacheOperator.put(BP_VALID_CODE_CACHE_KEY + phone + StrUtil.UNDERLINE + phoneValidCodeReqNo, phoneValidCode, 10 * 60L);
        // 返回请求号
        return phoneValidCodeReqNo;
    }

    @Override
    public String doLogin(AccountPasswordLoginParam accountPasswordLoginParam) {
        // 获取账号
        String account = accountPasswordLoginParam.getAccount();
        // SM2解密并获得前端传来的密码哈希值
        String passwordHash;
        try {
            // 解密，并做哈希值
            String pwd = CommonCryptogramUtil.doSm2Decrypt(accountPasswordLoginParam.getPassword());
            passwordHash = CommonCryptogramUtil.doHashValue(pwd);
        } catch (Exception e) {
            throw new CommonException("密码解密失败");
        }
        // 账号校验
        BpLoginUser bpLoginUser = checkAccount(account);
        if(!passwordHash.equals(bpLoginUser.getPassword())){
            throw new CommonException("密码错误");
        }
        //执行登录
        return execLogin(bpLoginUser,SA_TOKEN_DEVICE);
    }

    @Override
    public String doLoginByPhone(PhoneValidCodeLoginParam phoneValidCodeLoginParam) {
        // 手机号
        String phone = phoneValidCodeLoginParam.getPhone();
        // 校验参数
        validPhoneValidCodeParam(phone, phoneValidCodeLoginParam.getValidCode(), phoneValidCodeLoginParam.getValidCodeReqNo());
        // 根据手机号(账号)校验
        BpLoginUser bpLoginUser = checkAccount(phone);
        //执行登录
        return execLogin(bpLoginUser,SA_TOKEN_DEVICE);
    }

    @Override
    public BpLoginUser getLoginUser() {
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }
        bpLoginUser.setPassword(null);
        return bpLoginUser;
    }


    /**
     * 校验手机号与验证码等参数
     *
     **/
    private void validPhoneValidCodeParam(String phoneOrEmail, String validCode, String validCodeReqNo) {
        // 验证码正确则校验手机号格式
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            // 执行校验验证码
            validValidCode(null, validCode, validCodeReqNo);
        } else {
            if(!PhoneUtil.isMobile(phoneOrEmail) && !CommonEmailUtil.isEmail(phoneOrEmail)) {
                throw new CommonException("手机号格式错误");
            }
            // 执行校验验证码
            validValidCode(phoneOrEmail, validCode, validCodeReqNo);
        }
    }


    /**
     * 校验验证码方法
     *
     **/
    private void validValidCode(String phoneOrEmail, String validCode, String validCodeReqNo) {
        // 依据请求号，取出缓存中的验证码进行校验
        Object existValidCode;
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            existValidCode = commonCacheOperator.get(BP_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            existValidCode = commonCacheOperator.get(BP_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 为空则直接验证码错误
        if(ObjectUtil.isEmpty(existValidCode)) {
            throw new CommonException("验证码错误");
        }
        // 移除该验证码
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            commonCacheOperator.remove(BP_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            commonCacheOperator.remove(BP_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 不一致则直接验证码错误
        if (!validCode.equals(Convert.toStr(existValidCode).toLowerCase())) {
            throw new CommonException("验证码错误");
        }
    }



    /**
     * 执行 招采平台-前台 端登录
     *
     **/
    private String execLogin(BpLoginUser bpLoginUser, String device) {
        // 校验状态
        if(!UserStatusEnum.ENABLE.getValue().equals(bpLoginUser.getUserStatus())) {
            throw new CommonException(1000,"账号不可用");
        }
        // 执行登录
        StpUtil.login(bpLoginUser.getId(), new SaLoginModel().setDevice(device).setExtra("name", bpLoginUser.getName()));
        // 缓存用户信息，此处使用TokenSession为了指定时间内无操作则自动下线
        StpUtil.getTokenSession().set("bpLoginUser", bpLoginUser);
        // 返回token
        return StpUtil.getTokenInfo().tokenValue;
    }


    @Override
    public void sign(SignParam signParam) {
        //当前用户信息
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }
        //1. 校验是否供应商入库 -> 校验供应商入库审批是否通过
        UserSupplierVo userSupplierVo = bpUserMapper.getBpUserSupplier(bpLoginUser.getId());
        if(ObjectUtil.isEmpty(userSupplierVo)){
            throw new CommonException(1001,"用户异常");
        }
        if(ObjectUtil.isEmpty(userSupplierVo.getPrvId())){
            throw new CommonException(1002,"请先操作供应商入库");
        }
        if(FlwStatusEnum.ACTIVE_STATUS.getValue().equals(userSupplierVo.getPrvStatus())){
            throw new CommonException(1003,"供应商入库审批中");
        }
        //2. 校验是否已缴纳附件查看费用
        List<UserPay> userPayList = userPayService.list(new QueryWrapper<UserPay>().lambda()
                .eq(UserPay::getBpUserId,bpLoginUser.getId())
                .eq(UserPay::getNoticeId,signParam.getNoticeId())
                .eq(UserPay::getType, PayTypeEnum.PAY_FOR_ATTACH.getValue()));
        if(CollUtil.isEmpty(userPayList)){
            throw new CommonException(1004,"请支付附件查看费用");
        }
        //3. 校验是否已报名
        List<UserSign> userSignList = userSignService.list(new QueryWrapper<UserSign>().lambda().eq(UserSign::getBpUserId,bpLoginUser.getId()).eq(UserSign::getNoticeId,signParam.getNoticeId()));
        if(CollUtil.isNotEmpty(userSignList)){
            throw new CommonException(1005,"请勿重复报名");
        }
        //4. 报名记录
        UserSignAddParam userSignAddParam = new UserSignAddParam();
        userSignAddParam.setBpUserId(bpLoginUser.getId());
        userSignAddParam.setNoticeId(signParam.getNoticeId());
        userSignAddParam.setBmStatus(SignStatusEnum.SIGN_ING.getValue());
        String memo =  DateUtil.now() + " : 报名；";
        userSignAddParam.setMemo(memo);
        userSignService.add(userSignAddParam);
    }


    @Override
    public void signPay(PayParam payParam) {
        //当前用户信息
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }
        //1. 判断是否已缴纳
        List<UserPay> userPayList = userPayService.list(new QueryWrapper<UserPay>().lambda()
                .eq(UserPay::getBpUserId,bpLoginUser.getId())
                .eq(UserPay::getNoticeId,payParam.getNoticeId())
                .eq(UserPay::getType,payParam.getType()));
        if(CollUtil.isNotEmpty(userPayList)){
            throw new CommonException(1006,"请勿重复缴费");
        }
        //2. 新增缴费记录
        UserPayAddParam userPayAddParam = new UserPayAddParam();
        userPayAddParam.setBpUserId(bpLoginUser.getId());
        userPayAddParam.setNoticeId(payParam.getNoticeId());
        userPayAddParam.setType(payParam.getType());
        userPayAddParam.setAmount(payParam.getAmount());
        userPayAddParam.setMemo(DateUtil.now()+" : 缴纳 【"+payParam.getType()+"】 "+payParam.getAmount() +"；");
        userPayService.add(userPayAddParam);
    }

    @Override
    public void signUpload(UploadParam uploadParam) {
        UserSign userSign = userSignService.queryEntity(uploadParam.getId());
        userSign.setAttach(uploadParam.getAttach());
        userSign.setBmStatus(SignStatusEnum.SIGN_UP.getValue());
        String memo = userSign.getMemo() + DateUtil.now()+ " : 上传报名文件；";
        userSign.setMemo(memo);
        userSignService.updateById(userSign);
    }

    @Override
    public Page<UserSignVo> mySignList(UserSignPageParam userSignPageParam) {
        //当前用户信息
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }
        QueryWrapper<UserSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(userSignPageParam.getBmStatus())) {
            queryWrapper.eq("a.BM_STATUS", userSignPageParam.getBmStatus());
        }
        if(ObjectUtil.isNotEmpty(userSignPageParam.getType())){
            queryWrapper.eq("b.TYPE",userSignPageParam.getType());
        }
        queryWrapper.eq("a.BP_USER_ID",bpLoginUser.getId());

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
                if(ObjectUtil.isNotEmpty(item.getNoticeBidDeadline())) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String tmpCreateTime = DateUtil.format(item.getCreateTime(),dateTimeFormatter);
                    LocalDateTime tmpTime = LocalDateTime.parse(tmpCreateTime,dateTimeFormatter);
                    LocalDateTime bondEndTime = tmpTime.plusDays(Long.parseLong(item.getNoticeBidDeadline()));
                    item.setNoticeBondEndTime(bondEndTime.format(dateTimeFormatter));
                }
            });
        }
        return result;
    }


    @Override
    public UserNoticeVo noticeDetail(NoticeIdParam noticeIdParam) {
        Notice notice = noticeService.queryEntity(noticeIdParam.getId());
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
            }else{
                noticeVo.setBmStatus(SignStatusEnum.SIGN_NO.getValue());
            }
        }
        /**
         * 尝试获取 当前登录用户信息|若没有登录，则不携带个人相关信息
         */
        try {
            //当前用户信息
            BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
            //用户已登录
            if (ObjectUtil.isNotEmpty(bpLoginUser)) {
                //已登录用户 - 是否已报名
                List<UserSign> userSignList = userSignService.list(new QueryWrapper<UserSign>().lambda().eq(UserSign::getBpUserId, bpLoginUser.getId()).eq(UserSign::getNoticeId, noticeIdParam.getId()));
                noticeVo.setIsSign(CollUtil.isNotEmpty(userSignList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
                //已登录用户 - 是否已缴纳查看附件费用
                List<UserPay> userPayList = userPayService.list(new QueryWrapper<UserPay>().lambda()
                        .eq(UserPay::getBpUserId, bpLoginUser.getId())
                        .eq(UserPay::getNoticeId, noticeIdParam.getId())
                        .eq(UserPay::getType, PayTypeEnum.PAY_FOR_ATTACH.getValue()));
                noticeVo.setIsPayForAttach(CollUtil.isNotEmpty(userPayList) ? YesOrNoEnum.YES.getValue() : YesOrNoEnum.NO.getValue());
            }
        }catch (Exception e){
        }
        return noticeVo;
    }

    @Override
    public BpSupplier getSupplierInfo() {
        //当前用户信息
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }
        //获取供应商入库信息
        BpUser bpUser = bpUserService.queryEntity(bpLoginUser.getId());
        if(ObjectUtil.isNotEmpty(bpUser) && ObjectUtil.isNotEmpty(bpUser.getSupplierId())){
            BpSupplier bpSupplier = bpSupplierService.getOne(new QueryWrapper<BpSupplier>().lambda()
                    .eq(BpSupplier::getId,bpUser.getSupplierId())
                    .in(BpSupplier::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue()));
            return bpSupplier;
        }
        return null;
    }

    @Override
    public Page<NoticeTz> noticeTzPage(NoticeTzPageParam noticeTzPageParam) {
        return noticeTzService.page(noticeTzPageParam);
    }

    @Override
    public NoticeTz noticeTzDetail(NoticeTzIdParam noticeTzIdParam) {
        return noticeTzService.detail(noticeTzIdParam);
    }

    @Override
    public Page<NoticeVo> noticePage(NoticePageParam noticePageParam) {
        return noticeService.page(noticePageParam);
    }

    @Override
    public void addSupplier(BpSupplierAddParam bpSupplierAddParam) {
        //当前用户信息
        BpLoginUser bpLoginUser = LoginBpUserUtil.getLoginUser();
        if(ObjectUtil.isEmpty(bpLoginUser)){
            throw new CommonException("token 异常，获取用户信息失败");
        }

        //调用供应商入库审批
        bpSupplierAddParam.setBpUserId(bpLoginUser.getId());
        bpSupplierService.add(bpSupplierAddParam);

        //通过招采用户id查询供应商对应数据
        BpUser bpUser = bpUserService.getById(bpSupplierAddParam.getBpUserId());
        if(ObjectUtil.isNotEmpty(bpUser)){
            //填充对应用户的供应商id
            bpUser.setSupplierId(bpSupplierAddParam.getId());
            bpUserService.updateById(bpUser);
        }
    }

    @Override
    public Page<BpSupplierVo> getBpSupplierPageList(BpSupplierPageParam bpSupplierPageParam) {
        //调用供应商查询
        return bpSupplierService.page(bpSupplierPageParam);
    }

    @Override
    public List<ClarifyVo> noticeClarify(String noticeId) {
        return clarifyMapper.getClarifyByNoticeId(noticeId);
    }

    @Override
    public String supplierRegister(BpSupplierAddParam bpSupplierAddParam) {
        String account = "";
        //1. 获取作为账号的信息
        if(CollUtil.isNotEmpty(bpSupplierAddParam.getRelationAddParamList())){
            account = bpSupplierAddParam.getRelationAddParamList().get(0).getBusinessadminPhone();
        }
        //2. 账号不为空
        if(ObjectUtil.isNotEmpty(account)){
            //判断用户是否存在
            List<BpUser> bpUserList = bpUserService.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,account));
            if(CollUtil.isNotEmpty(bpUserList)){
                throw new CommonException("账号{}已存在！",account);
            }
        }
        //3. 新增供应商信息
        bpSupplierService.add(bpSupplierAddParam);
        return "供应商注册成功";
    }

    @Override
    public Boolean check(BpSupplierAddParam bpSupplierAddParam) {
        String account = "";
        //1. 获取作为账号的信息
        if(CollUtil.isNotEmpty(bpSupplierAddParam.getRelationAddParamList())){
            account = bpSupplierAddParam.getRelationAddParamList().get(0).getBusinessadminPhone();
        }
        //2. 账号不为空
        if(ObjectUtil.isNotEmpty(account)){
            //判断用户是否存在
            List<BpUser> bpUserList = bpUserService.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,account));
            if(CollUtil.isNotEmpty(bpUserList)){
                throw new CommonException("账号{}已存在！",account);
            }
            //供应商是否提交过注册信息
            List<BpSupplier> bpSuppliers = bpSupplierMapper.getSupplierByPhone(account);
            if(CollUtil.isNotEmpty(bpSuppliers)){
                throw new CommonException("账号{}已存在供应商注册信息，请勿重复提交",account);
            }
        }
        return true;
    }

    @Override
    public Page<BpPoint> pointPage(BpPointPageParam bpPointPageParam) {
        return bpPointService.page(bpPointPageParam);
    }

    @Override
    public BpPoint pointDetail(BpPointIdParam bpPointIdParam) {
        return bpPointService.detail(bpPointIdParam);
    }

    /**
     * 系统以手机号作为账号，需要判断是否存在
     *   若异常，则报错
     *   若无误，返回用户信息
     * @param account
     * @return
     */
    public BpLoginUser checkAccount(String account){
        List<BpUser> bpUserList = bpUserService.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,account));
        if(CollUtil.isEmpty(bpUserList)){
            throw new CommonException("账号不存在");
        }else if(bpUserList.size() > 1){
            throw new CommonException("账号异常，请联系管理员");
        }
        BpLoginUser bpLoginUser = BeanUtil.copyProperties(bpUserList.get(0), BpLoginUser.class);
        return bpLoginUser;
    }

}
