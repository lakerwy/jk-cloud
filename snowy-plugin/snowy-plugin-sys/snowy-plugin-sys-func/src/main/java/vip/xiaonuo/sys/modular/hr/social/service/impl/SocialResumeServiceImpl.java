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
package vip.xiaonuo.sys.modular.hr.social.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonUrlUtil;
import vip.xiaonuo.dev.api.DevSmsApi;
import vip.xiaonuo.sys.core.config.SmsConfig;
import vip.xiaonuo.sys.modular.hr.social.entity.SocialInterview;
import vip.xiaonuo.sys.modular.hr.social.entity.SocialResume;
import vip.xiaonuo.sys.modular.hr.social.mapper.SocialResumeMapper;
import vip.xiaonuo.sys.modular.hr.social.param.*;
import vip.xiaonuo.sys.modular.hr.social.service.SocialInterviewService;
import vip.xiaonuo.sys.modular.hr.social.service.SocialResumeService;
import vip.xiaonuo.sys.modular.hr.social.vo.InterviewVo;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialResumeVo;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 社招 - （应聘者）简历库信息Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/01 09:35
 **/
@Service
public class SocialResumeServiceImpl extends ServiceImpl<SocialResumeMapper, SocialResume> implements SocialResumeService {
    @Resource
    private SocialInterviewService socialInterviewService;
    @Resource
    private SmsConfig smsConfig;
    @Resource
    private DevSmsApi devSmsApi;

    /* 云片短信：入职短信模板 */
    private static final String YUN_PIAN_TPL_CODE_ID = "5970264";
    /* 长链接替换部分 */
    private static final String REPLACE_LONG_URL = "jianli/#/social?id=" ;

    @Override
    public Page<SocialResumeVo> page(SocialResumePageParam socialResumePageParam) {
        QueryWrapper<SocialResume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getName())) {
            queryWrapper.lambda().like(SocialResume::getName, socialResumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getSex())) {
            queryWrapper.lambda().eq(SocialResume::getSex, socialResumePageParam.getSex());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getIdNumber())) {
            queryWrapper.lambda().like(SocialResume::getIdNumber, socialResumePageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getPhone())) {
            queryWrapper.lambda().like(SocialResume::getPhone, socialResumePageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getInfoResource())) {
            queryWrapper.lambda().like(SocialResume::getInfoResource, socialResumePageParam.getInfoResource());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getApplyPost())) {
            queryWrapper.lambda().like(SocialResume::getApplyPost, socialResumePageParam.getApplyPost());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getNeedDept())) {
            queryWrapper.lambda().like(SocialResume::getNeedDept, socialResumePageParam.getNeedDept());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getIsSend())) {
            queryWrapper.lambda().eq(SocialResume::getIsSend, socialResumePageParam.getIsSend());
        }
        if(ObjectUtil.isNotEmpty(socialResumePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SocialResume::getName,socialResumePageParam.getSearchKey())
                    .or().like(SocialResume::getIdNumber,socialResumePageParam.getSearchKey())
                    .or().like(SocialResume::getPhone,socialResumePageParam.getSearchKey())
                    .or().like(SocialResume::getApplyPost,socialResumePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(socialResumePageParam.getSortField(), socialResumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(socialResumePageParam.getSortOrder());
            queryWrapper.orderBy(true, socialResumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(socialResumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SocialResume::getCreateTime);
        }
        Page<SocialResume> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<SocialResumeVo> records = BeanUtil.copyToList(result.getRecords(), SocialResumeVo.class);
        if(CollUtil.isNotEmpty(records)){
            records.forEach(item->{
                LambdaQueryWrapper<SocialInterview> interviewQueryWrapper = new QueryWrapper<SocialInterview>().lambda()
                        .eq(SocialInterview::getHrSocialResumeId,item.getId())
                        .orderByDesc(SocialInterview::getSn);
                List<SocialInterview> interviewList = socialInterviewService.list(interviewQueryWrapper);
                List<InterviewVo> interviewVos = BeanUtil.copyToList(interviewList, InterviewVo.class);
                item.setInterviewList(interviewVos);
            });
        }
        Page<SocialResumeVo> newResult = new Page<>();
        newResult.setRecords(records);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SocialResumeAddParam socialResumeAddParam) {
        SocialResume socialResume = BeanUtil.toBean(socialResumeAddParam, SocialResume.class);
        this.save(socialResume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SocialResumeEditParam socialResumeEditParam) {
        SocialResume socialResume = this.queryEntity(socialResumeEditParam.getId());
        BeanUtil.copyProperties(socialResumeEditParam, socialResume);
        this.updateById(socialResume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SocialResumeIdParam> socialResumeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(socialResumeIdParamList, SocialResumeIdParam::getId));
    }

    @Override
    public SocialResumeVo detail(SocialResumeIdParam socialResumeIdParam) {
        SocialResume socialResume = this.queryEntity(socialResumeIdParam.getId());
        SocialResumeVo socialResumeVo = BeanUtil.copyProperties(socialResume, SocialResumeVo.class);
        LambdaQueryWrapper<SocialInterview> interviewQueryWrapper = new QueryWrapper<SocialInterview>().lambda()
                .eq(SocialInterview::getHrSocialResumeId,socialResume.getId())
                .orderByDesc(SocialInterview::getSn);
        List<SocialInterview> interviewList = socialInterviewService.list(interviewQueryWrapper);
        List<InterviewVo> interviewVos = BeanUtil.copyToList(interviewList, InterviewVo.class);
        socialResumeVo.setInterviewList(interviewVos);
        return socialResumeVo;
    }

    @Override
    public SocialResume queryEntity(String id) {
        SocialResume socialResume = this.getById(id);
        if(ObjectUtil.isEmpty(socialResume)) {
            throw new CommonException("社招 - （应聘者）简历库信息不存在，id值为：{}", id);
        }
        return socialResume;
    }

    @Override
    public void save(UploadParam uploadParam) {
        if(CollUtil.isEmpty(uploadParam.getFileList())) {
            throw new CommonException("请上传简历，注意文件命名格式");
        }
        List<SocialResume> resumeList = new ArrayList<>();
        uploadParam.getFileList().forEach(file->{
            SocialResume socialResume = getInfoByFileName(file.getName());
            socialResume.setResumeAttach(file.getFileId());
            resumeList.add(socialResume);
        });
        if(CollUtil.isNotEmpty(resumeList)){
            this.saveBatch(resumeList);
        }
    }

    @Override
    public void send(SocialResumeIdParam idParam) {
        SocialResume socialResume = this.queryEntity(idParam.getId());
        if(StrUtil.isEmpty(socialResume.getPhone())){
            throw new CommonException("手机号缺失，无法发送短信");
        }
        if(!PhoneUtil.isMobile(socialResume.getPhone())) {
            throw new CommonException("手机号码：{}格式错误", socialResume.getPhone());
        }
        if(smsConfig.getIsSend()) {
            // TODO 使用云片执行发送验证码，将验证码作为短信内容的参数变量放入，
            String shortUrl = socialResume.getShortUrl();
            if(ObjectUtil.isEmpty(shortUrl)) {
                String longUrl = StrUtil.concat(true, smsConfig.getBaseUrl(), REPLACE_LONG_URL, socialResume.getId());
                shortUrl = getShortUrl(longUrl);
            }
            String tpl_Value = URLEncoder.encode("#url#") + "="  + URLEncoder.encode(shortUrl);
//            devSmsApi.sendSmsYunPian("", socialResume.getPhone(), YUN_PIAN_TPL_CODE_ID, tpl_Value);
            socialResume.setShortUrl(shortUrl);
            socialResume.setIsSend("1");
            this.updateById(socialResume);
        }
    }

    @Override
    public void submit(ResumeParam resumeParam) {
        SocialResume socialResume = new SocialResume();
        if(ObjectUtil.isNotEmpty(resumeParam.getId())){
            socialResume = this.queryEntity(resumeParam.getId());
        }
        BeanUtil.copyProperties(resumeParam,socialResume);
        this.saveOrUpdate(socialResume);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if(StrUtil.isEmpty(shortUrl)){
            throw new CommonException("短链接不能为空");
        }
        SocialResume one = this.getOne(new QueryWrapper<SocialResume>().lambda().eq(SocialResume::getShortUrl, shortUrl));
        if(ObjectUtil.isEmpty(one)){
            throw new CommonException("短链接已失效或不存在");
        }
        return StrUtil.concat(true,smsConfig.getBaseUrl(),REPLACE_LONG_URL,one.getId());
    }

    /**
     * 支持 - _ + —— 符合分割  分别取 姓名_手机号_应聘岗位
     * @param fileName
     * @return
     */
    public SocialResume getInfoByFileName(String fileName){
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex); // 输出：姓名_手机号_应聘岗位
        }
        String[] splitRegex = new String[]{"_","-","——","+"};
        for (String regex : splitRegex) {
            String[] split = fileName.split(regex);
            if(split.length>=3){
                SocialResume fileInfo = new SocialResume();
                fileInfo.setName(split[0]);
                fileInfo.setPhone(split[1]);
                fileInfo.setApplyPost(split[2]);
                return fileInfo;
            }
        }
        //解析失败，返回全名
        SocialResume fileInfo = new SocialResume();
        fileInfo.setName(fileName);
        fileInfo.setPhone(fileName);
        fileInfo.setApplyPost(fileName);
        return fileInfo;
    }


    /**
     * 生成短链接 6 位
     * 1. md5 加密算法生成
     * 2. 判断是否已存在
     * 3. 随机生成
     * @param longUrl
     * @return
     */
    public String getShortUrl(String longUrl){
        String tmpShortUrl = CommonUrlUtil.shortUrl(longUrl);
        LambdaQueryWrapper<SocialResume> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SocialResume::getShortUrl).and(q->q.isNotNull(SocialResume::getShortUrl).ne(SocialResume::getShortUrl,""));
        List<String> oldShortUrls = this.list(lambdaQueryWrapper).stream().map(x->x.getShortUrl()).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(oldShortUrls)) {
            while (oldShortUrls.contains(tmpShortUrl)) {
                tmpShortUrl = CommonUrlUtil.randomShortUrl(6);
            }
        }
        return tmpShortUrl;
    }
}
