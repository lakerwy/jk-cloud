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
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.BizTaskGroupFeignClient;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonSnUtil;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.util.StartDingDingScheduleCore;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.rez.enums.ConferenceRezEnum;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceWorkArrangeMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeAddParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeEditParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangeIdParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceWorkArrangePageParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceWorkArrangeService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceWorkArrangeAdd;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.WorkTaskInfoVo;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;
import com.alibaba.fastjson.JSONArray;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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
    @Resource
    BizTaskGroupFeignClient bizTaskGroupFeignClient;

    @Override
    public Page<ConferenceWorkArrange> page(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam) {
        List<ConferenceWorkArrange> conferenceWorkArrangeList = new ArrayList<>();
        try{
            JSONArray taskInfoListJson = bizTaskGroupFeignClient.getTaskInfoPage(conferenceWorkArrangePageParam.getRezId(),ConferenceRezEnum.PRO_TYPE.getValue());
            cn.hutool.json.JSONArray jsonArray = new cn.hutool.json.JSONArray(taskInfoListJson);
            List<WorkTaskInfoVo> bizWorkTaskInfoVoList = jsonArray.toList(WorkTaskInfoVo.class);
            bizWorkTaskInfoVoList.forEach(vo -> {
                ConferenceWorkArrange conferenceWorkArrange = new ConferenceWorkArrange();
                conferenceWorkArrange.setId(vo.getId());
                conferenceWorkArrange.setStartTime(vo.getTaskStartTime());
                conferenceWorkArrange.setFinishTime(vo.getTaskEndTime());
                conferenceWorkArrange.setWorkArrange(vo.getTaskName());
                conferenceWorkArrange.setRezId(vo.getProId());
                conferenceWorkArrange.setDutyUserId(vo.getBelonger());
                conferenceWorkArrange.setDutyOrgId(vo.getBelongerOrg());
                conferenceWorkArrangeList.add(conferenceWorkArrange);
            });
        }catch (Exception e){
            log.error("查询任务数据失败！");
        }
        Page<ConferenceWorkArrange> page = new Page<ConferenceWorkArrange>();
        page.setRecords(conferenceWorkArrangeList);
        return page;
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
        boolean isSaveTrue = true;
        if(CollectionUtil.isNotEmpty(conferenceWorkArrangeList)){
            isSaveTrue = this.saveBatch(conferenceWorkArrangeList);
        }
        isSaveTrue = addTaskInfoForRez(conferenceWorkArrangeAddParam.getWorkArrangeAddList(),conferenceRez);
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
        List<ConferenceWorkArrange> conferenceWorkArrangeList = this.list(new LambdaQueryWrapper<ConferenceWorkArrange>()
                .eq(ConferenceWorkArrange::getRezId,conferenceWorkArrangeEditParam.getRezId()));
        Map<String,ConferenceWorkArrange> oldMap = new HashMap<>();
        conferenceWorkArrangeList.forEach(work -> oldMap.put(work.getId(),work));

        List<ConferenceWorkArrange> updateOrSaveWork = new ArrayList<>();
        List<ConferenceWorkArrange> createScheduleList = new ArrayList<>();
        conferenceWorkArrangeEditParam.getConferenceWorkArrangeEditList().forEach(param -> {
            if(StringUtils.isEmpty(param.getId())){
                ConferenceWorkArrange conferenceWorkArrange = BeanUtil.toBean(param, ConferenceWorkArrange.class);
                updateOrSaveWork.add(conferenceWorkArrange);
                createScheduleList.add(conferenceWorkArrange);
            }else if(ObjectUtil.isNotEmpty(oldMap.get(param.getId()))){
                ConferenceWorkArrange conferenceWorkArrange = oldMap.get(param.getId());
                BeanUtil.copyProperties(param, conferenceWorkArrange);
                updateOrSaveWork.add(conferenceWorkArrange);
            }
        });

        Map<String,String> newMap = new HashMap<>();
        conferenceWorkArrangeEditParam.getConferenceWorkArrangeEditList().forEach(param -> {
            if(StringUtils.isEmpty(param.getId())){ newMap.put(param.getId(),param.getId());}
        });
        List<ConferenceWorkArrange> deleteWorkList = new ArrayList<>();
        conferenceWorkArrangeList.forEach(work -> {
            if(ObjectUtil.isEmpty(newMap.get(work.getId()))){deleteWorkList.add(work);}
        });
        this.saveOrUpdateBatch(updateOrSaveWork);
        this.removeBatchByIds(deleteWorkList.stream().map(work -> work.getId()).collect(Collectors.toList()));

        /**  新建日程 */
        createScheduleList.forEach(schedule -> asyCreateSchedule(schedule));
        /** 删除日程 */
        deleteWorkList.forEach(delete -> {
            if(StringUtils.isNotEmpty(delete.getScheduleId()) && StringUtils.isNotEmpty(delete.getDutyUserId())){
                try{
                    startDingDingScheduleCore.deleteScheduleForUserId( appKey, appSecret,delete.getDutyUserId(), delete.getScheduleId());
                }catch (Exception e){
                    log.error("删除日程失败！e:"+e.getMessage());
                }
            }else{
                log.error("日程ID和责任人ID不能为空值！arrageId："+delete.getId());
            }
        });
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

    private Boolean addTaskInfoForRez(List<ConferenceWorkArrangeAdd> workArrangeAddList,ConferenceRez conferenceRez){
        List<WorkTaskInfoVo> workTaskInfoVoList = new ArrayList<>();
        workArrangeAddList.forEach(work -> {
            WorkTaskInfoVo workTaskInfoVo = new WorkTaskInfoVo();
            workTaskInfoVo.setProId(conferenceRez.getId());//第三方ID
            workTaskInfoVo.setProType(ConferenceRezEnum.PRO_TYPE.getValue());//第三方类别
            workTaskInfoVo.setTaskRemark(conferenceRez.getTheme());//任务备注 - 会议主题
            workTaskInfoVo.setTaskName(work.getWorkArrange());//任务名称 - 任务部署
            workTaskInfoVo.setBelonger(work.getDutyUserId());//任务执行者
            workTaskInfoVo.setBelongerOrg(work.getDutyOrgId());//任务执行部门
            workTaskInfoVo.setLeadId(work.getDutyUserId()); //责任人
            workTaskInfoVo.setLeadOrg(work.getDutyOrgId());//责任部门
            workTaskInfoVo.setTaskStatus("1");//任务进行中
            workTaskInfoVo.setTaskStartTime(work.getStartTime());
            workTaskInfoVo.setTaskEndTime(work.getFinishTime());
            String sn = CommonSnUtil.GenerateSn("RW", "", CommonSnFormatEnum.SNYYYYMM0001.getValue());
            workTaskInfoVo.setSn(sn);
            workTaskInfoVo.setTaskPriority(StringUtils.isNotEmpty(work.getTaskPriority()) ? work.getTaskPriority() : "0");//任务优先级(默认为最低 0)：0 最低，1 较低，2 普通，3 较高，4 最高
            workTaskInfoVo.setTaskParticipants(work.getDutyUserId());//协办人员
            workTaskInfoVoList.add(workTaskInfoVo);
        });
        if(CollectionUtil.isNotEmpty(workTaskInfoVoList)){
            JSONArray jsonArray = (JSONArray) JSON.toJSON(workTaskInfoVoList);
            return bizTaskGroupFeignClient.addTaskInfo(jsonArray.toString());
        }
        return false;
    }
}
