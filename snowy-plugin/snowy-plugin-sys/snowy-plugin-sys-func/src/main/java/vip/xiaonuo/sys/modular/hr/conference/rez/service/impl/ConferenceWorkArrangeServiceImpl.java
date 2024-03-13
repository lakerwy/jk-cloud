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
package vip.xiaonuo.sys.modular.hr.conference.rez.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.util.StartDingDingScheduleCore;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceWorkArrangeMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeAddParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeEditParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeIdParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangePageParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceWorkArrangeService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.annotation.Resource;

/**
 * 会议模块-工作部署Service接口实现类
 *
 * @author ljy
 * @date  2023/12/07 17:43
 **/
@Service
public class ConferenceWorkArrangeServiceImpl extends ServiceImpl<ConferenceWorkArrangeMapper, ConferenceWorkArrange> implements ConferenceWorkArrangeService {

    @Value("${dingtalkstream.clientId}")
    private String appKey;
    @Value("${dingtalkstream.clientSecret}")
    private String appSecret;

    @Resource
    StartDingDingScheduleCore startDingDingScheduleCore;
    @Resource
    ConferenceRezService conferenceRezService;
    @Resource
    SysUserService sysUserService;

    @Override
    public Page<ConferenceWorkArrange> page(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam) {
        QueryWrapper<ConferenceWorkArrange> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getRezId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getRezId, conferenceWorkArrangePageParam.getRezId());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getDutyOrgId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getDutyOrgId, conferenceWorkArrangePageParam.getDutyOrgId());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getDutyUserId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getDutyUserId, conferenceWorkArrangePageParam.getDutyUserId());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getWorkArrange())) {
            queryWrapper.lambda().like(ConferenceWorkArrange::getWorkArrange, conferenceWorkArrangePageParam.getWorkArrange());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceWorkArrangePageParam.getSortField(), conferenceWorkArrangePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceWorkArrangePageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceWorkArrangePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceWorkArrangePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceWorkArrange::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceWorkArrangeAddParam conferenceWorkArrangeAddParam) {
        if(CollectionUtil.isEmpty(conferenceWorkArrangeAddParam.getWorkArrangeAddList())){
            throw new CommonException("任务数据不能为空");
        }
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceWorkArrangeAddParam.getRezId());
        List<ConferenceWorkArrange> conferenceWorkArrangeList = new ArrayList<>();
        conferenceWorkArrangeAddParam.getWorkArrangeAddList().forEach(workArrangeParam -> {
            ConferenceWorkArrange conferenceWorkArrange = BeanUtil.toBean(workArrangeParam, ConferenceWorkArrange.class);
            if(ObjectUtil.isEmpty(workArrangeParam.getStartTime())){
                conferenceWorkArrange.setStartTime(new Date());
            }
            conferenceWorkArrange.setRezTheme(conferenceRez.getTheme());
            conferenceWorkArrange.setRezId(conferenceWorkArrangeAddParam.getRezId());
            conferenceWorkArrange.setIsSchedule(0);//未建日程
            conferenceWorkArrangeList.add(conferenceWorkArrange);
        });
        boolean isSaveTrue = false;
        if(CollectionUtil.isNotEmpty(conferenceWorkArrangeList)){
            isSaveTrue = this.saveBatch(conferenceWorkArrangeList);
        }
        if(isSaveTrue){
            conferenceWorkArrangeList.forEach(conferenceWorkArrange -> asyCreateSchedule(conferenceWorkArrange));
        }
    }
    /**  异步创建日程 */
    private Future<String> asyCreateSchedule(ConferenceWorkArrange conferenceWorkArrange) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                createSchedule(conferenceWorkArrange);
            } catch (Exception e) {
                log.error("创建日程失败");
            }
            return "异步创建日程";
        });
        return future;
    }
    private void createSchedule(ConferenceWorkArrange conferenceWorkArrange){
        List<DingDingScheduleParam> dingDingScheduleParamList = new ArrayList<>();
        SysUser user = sysUserService.getById(conferenceWorkArrange.getDutyUserId());
        DingDingScheduleParam dingDingScheduleParam = new DingDingScheduleParam();
        dingDingScheduleParam.setUserId(user.getId());
        dingDingScheduleParam.setUserName(user.getName());
        dingDingScheduleParam.setPhone(user.getAccount());
        dingDingScheduleParamList.add(dingDingScheduleParam);

        DingDingScheduleVo dingDingScheduleVo = new DingDingScheduleVo();
        dingDingScheduleVo.setDingDingScheduleParamList(dingDingScheduleParamList);
        dingDingScheduleVo.setStartTime(DateUtil.format(conferenceWorkArrange.getStartTime(), DatePattern.NORM_DATETIME_PATTERN));
        dingDingScheduleVo.setEndTime(DateUtil.format(conferenceWorkArrange.getFinishTime(), DatePattern.NORM_DATETIME_PATTERN));
        dingDingScheduleVo.setSummary(conferenceWorkArrange.getWorkArrange());
        dingDingScheduleVo.setDescription(conferenceWorkArrange.getRezTheme());
        dingDingScheduleVo.setId(conferenceWorkArrange.getId());
        String scheduleId = startDingDingScheduleCore.addDingDingSchedule(dingDingScheduleVo, appKey, appSecret);
        if(StringUtils.isNotEmpty(scheduleId)){
            conferenceWorkArrange.setScheduleId(scheduleId);
            conferenceWorkArrange.setIsSchedule(1);//已建日程
            this.saveOrUpdate(conferenceWorkArrange);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceWorkArrangeEditParam conferenceWorkArrangeEditParam) {
        ConferenceWorkArrange conferenceWorkArrange = this.queryEntity(conferenceWorkArrangeEditParam.getId());
        BeanUtil.copyProperties(conferenceWorkArrangeEditParam, conferenceWorkArrange);
        this.updateById(conferenceWorkArrange);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceWorkArrangeIdParam> conferenceWorkArrangeIdParamList) {
        List<ConferenceWorkArrange> conferenceWorkArrangeList = this.list(new LambdaQueryWrapper<ConferenceWorkArrange>().in(ConferenceWorkArrange::getId,conferenceWorkArrangeIdParamList));
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceWorkArrangeIdParamList, ConferenceWorkArrangeIdParam::getId));
        /**  开始删除日程 */
        conferenceWorkArrangeList.forEach(arrange -> {
            if(StringUtils.isNotEmpty(arrange.getScheduleId()) && StringUtils.isNotEmpty(arrange.getDutyUserId())){
                try{
                    startDingDingScheduleCore.deleteScheduleForUserId( appKey, appSecret,arrange.getDutyUserId(), arrange.getScheduleId());
                }catch (Exception e){
                    log.error("删除日程失败！e:"+e.getMessage());
                }
            }
        });
    }

    @Override
    public ConferenceWorkArrange detail(ConferenceWorkArrangeIdParam conferenceWorkArrangeIdParam) {
        return this.queryEntity(conferenceWorkArrangeIdParam.getId());
    }

    @Override
    public ConferenceWorkArrange queryEntity(String id) {
        ConferenceWorkArrange conferenceWorkArrange = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceWorkArrange)) {
            throw new CommonException("会议模块-工作部署不存在，id值为：{}", id);
        }
        return conferenceWorkArrange;
    }
}
