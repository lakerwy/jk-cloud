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
package vip.xiaonuo.biz.modular.teambition.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.teambition.ThirdParam.ThirdTaskPageParam;
import vip.xiaonuo.biz.modular.teambition.consts.TaskConfConstant;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskFile;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskInfo;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskProject;
import vip.xiaonuo.biz.modular.teambition.enums.BizTaskGroupEnum;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskInfoMapper;
import vip.xiaonuo.biz.modular.teambition.param.*;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskFileService;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskInfoService;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskProjectService;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.entity.BizTaskComment;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.BizTaskCommentService;
import vip.xiaonuo.biz.modular.teambition.vo.BizTaskProjectVo;
import vip.xiaonuo.biz.modular.teambition.vo.TaskInfoMsg;
import vip.xiaonuo.biz.modular.teambition.vo.ThirdBizTaskInfoVo;
import vip.xiaonuo.biz.modular.user.entity.BizUser;
import vip.xiaonuo.biz.modular.user.service.BizUserService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.dev.feign.DevFeign;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 任务主表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@DS("slave_1")
@Service
@Configuration
public class BizTaskInfoServiceImpl extends ServiceImpl<BizTaskInfoMapper, BizTaskInfo> implements BizTaskInfoService {

    @Resource
    BizTaskProjectService bizTaskProjectService;
    @Resource
    BizOrgService bizOrgService;
    @Resource
    BizUserService bizUserService;
    @Resource
    BizTaskCommentService bizTaskCommentService;
    @Resource
    StreamBridge streamBridge;
    @Resource
    BizTaskFileService bizTaskFileService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    DevFeign devFeign;

    @Override
    public Page<BizTaskInfo> page(BizTaskInfoPageParam bizTaskInfoPageParam) {
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
        QueryWrapper<BizTaskInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getParentId())) {
            queryWrapper.lambda().eq(BizTaskInfo::getParentId, bizTaskInfoPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTaskInfo::getTaskName, bizTaskInfoPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskStartTime()) && ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskEndTime())) {
            queryWrapper.lambda().gt(BizTaskInfo::getCreateTime, bizTaskInfoPageParam.getTaskStartTime());
            queryWrapper.lambda().lt(BizTaskInfo::getCreateTime, bizTaskInfoPageParam.getTaskEndTime());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getProType())){
            queryWrapper.lambda().eq(BizTaskInfo::getProType,bizTaskInfoPageParam.getProType());
        }
        /**
         * 任务权限
         * 1.自己负责的任务
         * 2.自己执行的任务
         * 3.自己参与的
         * 4.自己创建的
         * 5.所属同一部门的其他人的任务(任务状态为非私有才可看)
         * 6.若为部门领导，可以看所有部门子任务(任何状态)
         * */
        if(bizTaskInfoPageParam.getInfoType() == 0){
            queryWrapper.lambda().eq(BizTaskInfo::getLeadId, saBaseLoginUser.getId())  //自己负责的任务
                    .or().eq(BizTaskInfo::getBelonger, saBaseLoginUser.getId())   //自己执行的任务
                    .or().like(BizTaskInfo::getTaskParticipants,saBaseLoginUser.getId())//自己参与的任务
                    .or().eq(BizTaskInfo::getCreateUser,saBaseLoginUser.getId())//自己创建的的任务
                    .or().eq(BizTaskInfo::getLeadOrg,saBaseLoginUser.getOrgId());//同一部门的非私有任务
            if(bizOrgService.count(new LambdaQueryWrapper<BizOrg>().eq(BizOrg::getDirectorId,saBaseLoginUser.getId())) > 0){
                List<String> childrenOrgIds = bizOrgService.getChildren(saBaseLoginUser.getOrgId()).stream().map(org -> org.getId()).collect(Collectors.toList());
                queryWrapper.lambda().or().in(BizTaskInfo::getLeadOrg,childrenOrgIds);//责任部门
                queryWrapper.lambda().or().in(BizTaskInfo::getBelongerOrg,childrenOrgIds);//执行部门
            }
        }else if(bizTaskInfoPageParam.getInfoType() == 1){
            queryWrapper.lambda().eq(BizTaskInfo::getCreateUser,saBaseLoginUser.getId());  //自己负责的任务
        }else if(bizTaskInfoPageParam.getInfoType() == 2){
            queryWrapper.lambda().eq(BizTaskInfo::getBelonger, saBaseLoginUser.getId());  //自己执行的任务
        }else if(bizTaskInfoPageParam.getInfoType() == 3){
            queryWrapper.lambda().like(BizTaskInfo::getTaskParticipants,saBaseLoginUser.getId());//自己参与的任务
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskStatus())){ //任务状态
            queryWrapper.lambda().eq(BizTaskInfo::getTaskStatus, bizTaskInfoPageParam.getTaskStatus());
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizTaskInfo::getDataScopeId, loginUserDataScope);
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q -> q.like(BizTaskInfo::getTaskName, bizTaskInfoPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskInfoPageParam.getSortField(), bizTaskInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskInfo::getId);
        }
        Page<BizTaskInfo> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        getTaskProject(page);
        getTaskComment(page);
        getTaskFile(page);
        return page;
    }
    /** 获取任务的来源详情*/
    private void getTaskProject(Page<BizTaskInfo> page){
        if(CollectionUtil.isNotEmpty(page.getRecords())){
            List<String> proIds = page.getRecords().stream().map(info ->info.getProId()).collect(Collectors.toList());
            List<BizTaskProject> taskProjectList = bizTaskProjectService.list(new LambdaQueryWrapper<BizTaskProject>().in(BizTaskProject::getId,proIds));
            Map<String,BizTaskProject> map = new HashMap<>();
            if(CollectionUtil.isNotEmpty(taskProjectList)){
                taskProjectList.forEach(task -> map.put(task.getId(),task));
            }
            page.getRecords().forEach(info ->info.setBizTaskProject(map.get(info.getProId())));
        }
    }
    /**  获取任务评论数据 */
    private void getTaskComment(Page<BizTaskInfo> page){
        if(CollectionUtil.isNotEmpty(page.getRecords())){
            List<String> taskInfoIds = page.getRecords().stream().map(info ->info.getId()).collect(Collectors.toList());
            List<BizTaskComment> bizTaskCommentList = bizTaskCommentService.list(new LambdaQueryWrapper<BizTaskComment>().in(BizTaskComment::getTaskInfoId,taskInfoIds));
            Map<String,List<BizTaskComment>> commentMap = new HashMap<>();
            if(CollectionUtil.isNotEmpty(bizTaskCommentList)){
                bizTaskCommentList.forEach(comment -> {
                    List<BizTaskComment> commentList = commentMap.get(comment.getTaskInfoId()) != null ?commentMap.get(comment.getTaskInfoId()) : new ArrayList<>();
                    commentList.add(comment);
                    commentMap.put(comment.getTaskInfoId(),commentList);
                });
            }
            page.getRecords().forEach(info -> {
                List<BizTaskComment> commentList = commentMap.get(info.getId()) != null ? commentMap.get(info.getId()) : new ArrayList<>();
                info.setBizTaskCommentList(CollectionUtil.isNotEmpty(setBizTaskCommentList(commentList)) ? setBizTaskCommentList(commentList) : new ArrayList<>());
                //info.setBizTaskCommentList(commentMap.get(info.getId()));
            }
            );
        }
    }
    /** 任务附件 */
    private void getTaskFile(Page<BizTaskInfo> page){
        if(CollectionUtil.isNotEmpty(page.getRecords())){
            List<String> taskInfoIds = page.getRecords().stream().map(info ->info.getId()).collect(Collectors.toList());
            List<BizTaskFile> bizTaskFileList = bizTaskFileService.list(new LambdaQueryWrapper<BizTaskFile>().in(BizTaskFile::getTaskInfoId,taskInfoIds));
            Map<String,List<BizTaskFile>> fileMap = new HashMap<>();
            if(CollectionUtil.isNotEmpty(bizTaskFileList)){
                bizTaskFileList.forEach(file -> {
                    List<BizTaskFile> fileList = fileMap.get(file.getTaskInfoId()) != null ?fileMap.get(file.getTaskInfoId()) : new ArrayList<>();
                    fileList.add(file);
                    fileMap.put(file.getTaskInfoId(),fileList);
                });
            }
            page.getRecords().forEach(info ->info.setBizTaskFileList(CollectionUtil.isNotEmpty(fileMap.get(info.getId())) ? fileMap.get(info.getId()) : new ArrayList<>()));
        }
    }

    /** 新增任务
     *  1.任务数据
     *  2.任务来源数据，比如会议A 的详情数据
     * */
    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskInfoAddParam bizTaskInfoAddParam) {
        String proId = null;
        String infoStatus = null;
        if(ObjectUtil.isEmpty(bizTaskInfoAddParam.getParentId()) && (ObjectUtil.isEmpty(bizTaskInfoAddParam.getBizTaskProjectVo())
                || ObjectUtil.isEmpty(bizTaskInfoAddParam.getBizTaskProjectVo().getProName()))){
            throw new CommonException("非子任务需要填写任务来源");
        }
        if(ObjectUtil.isEmpty(bizTaskInfoAddParam.getParentId()) && ObjectUtil.isNotEmpty(bizTaskInfoAddParam.getBizTaskProjectVo())
                && ObjectUtil.isNotEmpty(bizTaskInfoAddParam.getBizTaskProjectVo().getProName())){
            BizTaskProjectVo  bizTaskProjectVo = bizTaskInfoAddParam.getBizTaskProjectVo();
            BizTaskProject bizTaskProject = BeanUtil.toBean(bizTaskProjectVo, BizTaskProject.class);
            bizTaskProjectService.save(bizTaskProject);
            proId = bizTaskProject.getId();
            infoStatus = "创建了任务";
        }else{
            BizTaskInfo parentTaskInfo = this.getById(bizTaskInfoAddParam.getParentId());
            if(ObjectUtil.isEmpty(parentTaskInfo)){
                log.error("无父类任务！id:"+bizTaskInfoAddParam.getParentId());
            }else{
                proId = parentTaskInfo.getProId();
            }
            infoStatus = "创建了子任务";
        }
        BizTaskInfo bizTaskInfo = BeanUtil.toBean(bizTaskInfoAddParam, BizTaskInfo.class);
        bizTaskInfo.setProId(proId);
        initBizTaskInfo(bizTaskInfo);
        generateNo();//系统编号
        bizTaskInfo.setSn(commonCacheOperator.get(TaskConfConstant.TASK_NO_KEY).toString());
        this.save(bizTaskInfo);
        sendAddMsg(bizTaskInfo);
        putTaskComment(bizTaskInfo,infoStatus);
    }
    private void putTaskComment(BizTaskInfo bizTaskInfo,String infoStatus){
        BizTaskComment bizTaskComment = new BizTaskComment();
        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        dbsApi.changeDataSource("master");
        BizUser bizUser = bizUserService.getById(bizTaskInfo.getCreateUser());
        dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
        bizTaskComment.setInfoStatus(bizUser.getName()+" "+infoStatus);
        bizTaskCommentService.save(bizTaskComment);
    }
    //数据初始化
    private void initBizTaskInfo(BizTaskInfo bizTaskInfo){
        bizTaskInfo.setTaskStatus(BizTaskGroupEnum.ZRROR.getValue());//任务状态初始化：未完成
        if(ObjectUtil.isEmpty(bizTaskInfo.getTaskPriority())){
            bizTaskInfo.setTaskPriority(BizTaskGroupEnum.ZRROR.getValue());//任务优先级初始化：最低
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfo.getLeadId())){ //反查任务负责人的部门
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            dbsApi.changeDataSource(DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase());
            BizUser bizUser = bizUserService.getById(bizTaskInfo.getLeadId());
            dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
            bizTaskInfo.setLeadOrg(bizUser.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfo.getBelonger())){ //反查任务执行者的部门
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            dbsApi.changeDataSource(DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase());
            BizUser bizUser = bizUserService.getById(bizTaskInfo.getBelonger());
            dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
            bizTaskInfo.setBelongerOrg(bizUser.getOrgId());
        }
    }
    /** 任务名称：taskName
     * 任务创建人：createUser
     * 任务执行人：belongerUser
     * 任务执行时间：taskTime
     * 任务参与人：taskParticipants*/
    private void sendAddMsg(BizTaskInfo bizTaskInfo){
        List<String> userIds = new ArrayList<>();//执行人，创建人，参与人
        userIds.add(bizTaskInfo.getCreateUser());
        userIds.add(bizTaskInfo.getBelonger());
        List<String> taskParticipants = Arrays.asList(bizTaskInfo.getTaskParticipants().split(","));
        userIds.addAll(taskParticipants);

        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        dbsApi.changeDataSource(DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase());
        List<BizUser> bizUserList = bizUserService.list(new LambdaQueryWrapper<BizUser>().in(BizUser::getId,userIds));
        dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);

        Map<String,BizUser> userMap = new HashMap<>();
        bizUserList.forEach(user -> userMap.put(user.getId(),user));

        StringBuffer taskPartNames = new StringBuffer();
        taskParticipants.forEach(part -> {
            if(userMap.get(part) != null){ taskPartNames.append(userMap.get(part).getName()).append(",");}
        });
        Map<String,String> map = new HashMap<>();
        map.put("taskName",bizTaskInfo.getTaskName());
        map.put("createUser",userMap.get(bizTaskInfo.getCreateUser()) != null ? userMap.get(bizTaskInfo.getCreateUser()).getName() : "");
        map.put("belongerUser", StringUtils.isNotEmpty(bizTaskInfo.getBelonger()) ? userMap.get(bizTaskInfo.getBelonger()) != null ?
                userMap.get(bizTaskInfo.getBelonger()).getName() : "":"");
        map.put("taskTime", "");
        if(ObjectUtil.isNotEmpty(bizTaskInfo.getTaskStartTime()) && ObjectUtil.isNotEmpty(bizTaskInfo.getTaskEndTime())){
            map.put("taskTime",DateUtil.format(bizTaskInfo.getTaskStartTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN) +"-"
                    +DateUtil.format(bizTaskInfo.getTaskEndTime(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        }
        map.put("taskParticipants",taskPartNames.toString());
        sendMsg(map,bizUserList.stream().map(user -> user.getId()).collect(Collectors.toList()));
    }

    /**
     * 生成项目编号
     * @return
     */
    public void generateNo(){
        String day = DateUtil.format(new Date(),DatePattern.PURE_DATE_PATTERN);
        String sn = day+TaskConfConstant.TASK_NO_ONE;
        Object o = commonCacheOperator.get(TaskConfConstant.TASK_NO_KEY);
        if(ObjectUtil.isNotEmpty(o)){
            String taskNoKey = String.valueOf(commonCacheOperator.get(TaskConfConstant.TASK_NO_KEY));
            if(taskNoKey.startsWith(day)){
                commonCacheOperator.put(TaskConfConstant.TASK_NO_KEY,Integer.parseInt(taskNoKey)+1);
            }else{
                commonCacheOperator.put(TaskConfConstant.TASK_NO_KEY,sn);
            }
        }else {
            commonCacheOperator.put(TaskConfConstant.TASK_NO_KEY,sn);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskInfoEditParam bizTaskInfoEditParam) {
        String infoStatus = "";
        BizTaskInfo bizTaskInfo = this.queryEntity(bizTaskInfoEditParam.getId());
        if(!bizTaskInfoEditParam.getTaskStatus().equals(bizTaskInfo.getTaskStatus())){//修改任务状态
            String startStatus = bizTaskInfo.getTaskStatus().equals(BizTaskGroupEnum.ZRROR.getValue()) ?
                    BizTaskGroupEnum.NOT_START.getValue() :bizTaskInfo.getTaskStatus().equals(BizTaskGroupEnum.ONE.getValue()) ?
                    BizTaskGroupEnum.END.getValue() : BizTaskGroupEnum.NOT_END.getValue();
            String changeStatus = bizTaskInfoEditParam.getTaskStatus().equals(BizTaskGroupEnum.ZRROR.getValue()) ?
                    BizTaskGroupEnum.NOT_START.getValue() :bizTaskInfoEditParam.getTaskStatus().equals(BizTaskGroupEnum.ONE.getValue()) ?
                    BizTaskGroupEnum.END.getValue() : BizTaskGroupEnum.NOT_END.getValue();
            infoStatus = "将任务状态从"+startStatus+"改为"+changeStatus;
            if(bizTaskInfoEditParam.getTaskStatus().equals("2")){
                infoStatus = "任务完成";
            }
        }
        BeanUtil.copyProperties(bizTaskInfoEditParam, bizTaskInfo);
        this.updateById(bizTaskInfo);
        if(ObjectUtil.isNotEmpty(bizTaskInfoEditParam.getProId())){
            editBizTaskProject(bizTaskInfoEditParam);
        }
        putTaskComment(bizTaskInfo,infoStatus);
    }
    private void editBizTaskProject(BizTaskInfoEditParam bizTaskInfoEditParam){
        BizTaskProject bizTaskProject = bizTaskProjectService.getById(bizTaskInfoEditParam.getProId());
        if(ObjectUtil.isNotEmpty(bizTaskProject)){
            BizTaskProjectVo bizTaskProjectVo = bizTaskInfoEditParam.getBizTaskProjectVo();
            bizTaskProject.setProName(bizTaskProjectVo.getProName());
            bizTaskProject.setProDesc(bizTaskProjectVo.getProDesc());
            bizTaskProjectService.saveOrUpdate(bizTaskProject);
            return;
        }
        log.error("未找到任务来源详情学习！proId:"+bizTaskInfoEditParam.getProId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskInfoIdParam> bizTaskInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskInfoIdParamList, BizTaskInfoIdParam::getId));
    }

    @Override
    public BizTaskInfo detail(BizTaskInfoIdParam bizTaskInfoIdParam) {
        BizTaskInfo bizTaskInfo = this.queryEntity(bizTaskInfoIdParam.getId());
        BizTaskProject bizTaskProject = bizTaskProjectService.getById(bizTaskInfo.getProId());
        bizTaskInfo.setBizTaskProject(bizTaskProject);
        List<BizTaskComment> commentList = setBizTaskCommentList(bizTaskCommentService.list(new LambdaQueryWrapper<BizTaskComment>().eq(BizTaskComment::getTaskInfoId,bizTaskInfo.getId())));
        bizTaskInfo.setBizTaskCommentList(CollectionUtil.isNotEmpty(commentList) ? commentList : new ArrayList<>());
        return bizTaskInfo;
    }

    private List<BizTaskComment> setBizTaskCommentList(List<BizTaskComment> commentList){
        if(CollectionUtil.isEmpty(commentList)){
            return commentList;
        }
        List<BizTaskComment> list = new ArrayList<>();
        commentList.forEach(comment -> {
            if(comment.getParentId().equals("0")){
                for(BizTaskComment bizTaskComment : commentList){
                    if(comment.getId().equals(bizTaskComment.getParentId())){
                        List<BizTaskComment> comments = CollectionUtil.isNotEmpty(comment.getBizTaskCommentList())? comment.getBizTaskCommentList() : new ArrayList<>();
                        comments.add(bizTaskComment);
                        comment.setBizTaskCommentList(comments);
                    }
                }
                list.add(comment);
            }
        });
        return list;
    }

    @Override
    public BizTaskInfo queryEntity(String id) {
        BizTaskInfo bizTaskInfo = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskInfo)) {
            throw new CommonException("任务主表不存在，id值为：{}", id);
        }
        return bizTaskInfo;
    }

    @Override
    public void sendMsg(Map<String,String> map,List<String> receiverIdList){
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(FlwCacheConstant.TITLE,"【任务平台通知】");
        jsonObject.set("taskName",map.get("taskName"));
        jsonObject.set("createUser",map.get("createUser"));
        jsonObject.set("belongerUser",map.get("belongerUser"));
        jsonObject.set("taskTime",map.get("taskTime"));
        jsonObject.set("taskParticipants",map.get("taskParticipants"));
        jsonObject.set(FlwCacheConstant.SENDERS, String.join(StrUtil.COMMA, receiverIdList));
        TaskInfoMsg taskInfoMsg = TaskInfoMsg.buildRabbitMsg(jsonObject);
        commonCacheOperator.addToSet(RabbitConstant.RABBIT_MESSAGE_ID,taskInfoMsg.getUuid());
        streamBridge.send(RabbitConstant.DEFAULT_CHANNEL,taskInfoMsg);
    }

    private void sendChangeMsg(Map<String,String> map,List<String> receiverIdList){
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(FlwCacheConstant.TITLE,"【任务变更通知】");
        jsonObject.set("taskName",map.get("taskName"));
        jsonObject.set("createUser",map.get("createUser"));
        jsonObject.set("belongerUser",map.get("belongerUser"));
        jsonObject.set("taskTime",map.get("taskTime"));
        jsonObject.set("taskParticipants",map.get("taskParticipants"));
        jsonObject.set("taskChange",map.get("taskChange"));
        jsonObject.set(FlwCacheConstant.SENDERS, String.join(StrUtil.COMMA, receiverIdList));
        TaskInfoMsg taskInfoMsg = TaskInfoMsg.changeRabbitMsg(jsonObject);
        commonCacheOperator.addToSet(RabbitConstant.RABBIT_MESSAGE_ID,taskInfoMsg.getUuid());
        streamBridge.send(RabbitConstant.DEFAULT_CHANNEL,taskInfoMsg);
    }


    @Override
    public Page<BizTaskInfo> thirdPage(ThirdTaskPageParam thirdTaskPageParam) {
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
        QueryWrapper<BizTaskInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getParentId())) {
            queryWrapper.lambda().eq(BizTaskInfo::getParentId, thirdTaskPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTaskInfo::getTaskName, thirdTaskPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getTaskStartTime()) && ObjectUtil.isNotEmpty(thirdTaskPageParam.getTaskEndTime())) {
            queryWrapper.lambda().gt(BizTaskInfo::getCreateTime, thirdTaskPageParam.getTaskStartTime());
            queryWrapper.lambda().lt(BizTaskInfo::getCreateTime, thirdTaskPageParam.getTaskEndTime());
            /*queryWrapper.lambda().between(BizTaskInfo::getTaskStartTime, thirdTaskPageParam.getTaskStartTime(), thirdTaskPageParam.getTaskEndTime());
            queryWrapper.lambda().between(BizTaskInfo::getTaskEndTime, thirdTaskPageParam.getTaskStartTime(), thirdTaskPageParam.getTaskEndTime());*/
        }
        /**
         * 任务权限
         * 1.自己负责的任务
         * 2.自己执行的任务
         * 3.自己参与的
         * 4.自己创建的
         * 5.所属同一部门的其他人的任务(任务状态为非私有才可看)
         * 6.若为部门领导，可以看所有部门子任务(任何状态)
         * */
        if(thirdTaskPageParam.getInfoType() == 0){
            queryWrapper.lambda().eq(BizTaskInfo::getLeadId, saBaseLoginUser.getId())  //自己负责的任务
                    .or().eq(BizTaskInfo::getBelonger, saBaseLoginUser.getId())   //自己执行的任务
                    .or().like(BizTaskInfo::getTaskParticipants,saBaseLoginUser.getId())//自己参与的任务
                    .or().eq(BizTaskInfo::getCreateUser,saBaseLoginUser.getId())//自己创建的的任务
                    .or().eq(BizTaskInfo::getLeadOrg,saBaseLoginUser.getOrgId());//同一部门的非私有任务
            if(bizOrgService.count(new LambdaQueryWrapper<BizOrg>().eq(BizOrg::getDirectorId,saBaseLoginUser.getId())) > 0){
                List<String> childrenOrgIds = bizOrgService.getChildren(saBaseLoginUser.getOrgId()).stream().map(org -> org.getId()).collect(Collectors.toList());
                queryWrapper.lambda().or().in(BizTaskInfo::getLeadOrg,childrenOrgIds);//责任部门
                queryWrapper.lambda().or().in(BizTaskInfo::getBelongerOrg,childrenOrgIds);//执行部门
            }
        }else if(thirdTaskPageParam.getInfoType() == 1){
            queryWrapper.lambda().eq(BizTaskInfo::getCreateUser,saBaseLoginUser.getId());  //自己负责的任务
        }else if(thirdTaskPageParam.getInfoType() == 2){
            queryWrapper.lambda().eq(BizTaskInfo::getBelonger, saBaseLoginUser.getId());  //自己执行的任务
        }else if(thirdTaskPageParam.getInfoType() == 3){
            queryWrapper.lambda().like(BizTaskInfo::getTaskParticipants,saBaseLoginUser.getId());//自己参与的任务
        }
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getTaskStatus())){ //任务状态
            queryWrapper.lambda().eq(BizTaskInfo::getTaskStatus, thirdTaskPageParam.getTaskStatus());
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizTaskInfo::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizTaskInfo::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q -> q.like(BizTaskInfo::getTaskName, thirdTaskPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(thirdTaskPageParam.getSortField(), thirdTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(thirdTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, thirdTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(thirdTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskInfo::getId);
        }
        if(ObjectUtil.isNotEmpty(thirdTaskPageParam.getProSourceId())){//来源ID不为空
            List<String> proIds = bizTaskProjectService.list(new LambdaQueryWrapper<BizTaskProject>()
                    .eq(BizTaskProject::getThirdCode,thirdTaskPageParam.getThirdCode()).eq(BizTaskProject::getProSourceId,thirdTaskPageParam.getProSourceId()))
                    .stream().map(project -> project.getId()).collect(Collectors.toList());
            queryWrapper.lambda().in(BizTaskInfo::getProId,proIds);
        }else{
            List<String> proIds = bizTaskProjectService.list(new LambdaQueryWrapper<BizTaskProject>()
                    .eq(BizTaskProject::getThirdCode,thirdTaskPageParam.getThirdCode())).stream().map(project -> project.getId()).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(proIds)){
                queryWrapper.lambda().in(BizTaskInfo::getProId,proIds);
            }
        }
        Page<BizTaskInfo> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        getTaskProject(page);
        getTaskComment(page);
        getTaskFile(page);
        return page;
    }


    /** 非新建任务来源任务*/
    @Override
    public void thirdAdd(BizTaskInfoAddParam bizTaskInfoAddParam){
        String proId = null;
        String infoStatus = null;
        if(ObjectUtil.isEmpty(bizTaskInfoAddParam.getBizTaskProjectVo()) || StringUtils.isNotEmpty(bizTaskInfoAddParam.getBizTaskProjectVo().getProSourceId())){
            throw new CommonException("第三方来源创建任务，必须有来源ID！");
        }
        if(!(bizTaskProjectService.count(new LambdaQueryWrapper<BizTaskProject>().eq(BizTaskProject::getProSourceId,bizTaskInfoAddParam.getBizTaskProjectVo().getProSourceId()))>0)){
            //保存任务来源
            BizTaskProjectVo  bizTaskProjectVo = bizTaskInfoAddParam.getBizTaskProjectVo();
            BizTaskProject bizTaskProject = BeanUtil.toBean(bizTaskProjectVo, BizTaskProject.class);
            bizTaskProjectService.save(bizTaskProject);
            proId = bizTaskProject.getId();
            infoStatus = "创建了任务";
        }else{
            List<BizTaskProject> projectList = bizTaskProjectService.list(new LambdaQueryWrapper<BizTaskProject>()
                    .eq(BizTaskProject::getProSourceId,bizTaskInfoAddParam.getBizTaskProjectVo().getProSourceId()));
            proId = projectList.get(0).getId();
        }

        BizTaskInfo bizTaskInfo = BeanUtil.toBean(bizTaskInfoAddParam, BizTaskInfo.class);
        bizTaskInfo.setProId(proId);
        initBizTaskInfo(bizTaskInfo);
        this.save(bizTaskInfo);
        sendAddMsg(bizTaskInfo);
        putTaskComment(bizTaskInfo,infoStatus);
    }

    /**  会议新增任务 - 特殊处理 */
    @Override
    public Boolean addTaskInfo(String jsonObject){
        List<BizTaskInfo> bizTaskInfoList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonObject);
            bizTaskInfoList = jsonArray.toList(BizTaskInfo.class);
        }catch (Exception e){
            log.error("新建任务失败！e:"+e.getMessage());
            return false;
        }
        return this.saveOrUpdateBatch(bizTaskInfoList);
    }

    /** 第三方分页数据*/
    @Override
    public com.alibaba.fastjson.JSONArray thirdPage(String proId, String proType) {
        if(StringUtils.isEmpty(proId) || StringUtils.isEmpty(proType)){
            return new com.alibaba.fastjson.JSONArray();
        }
        List<BizTaskInfo> bizTaskInfoList = this.list(new LambdaQueryWrapper<BizTaskInfo>()
                .eq(BizTaskInfo::getProId,proId).eq(BizTaskInfo::getProType,proType));
        com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) JSON.toJSON(bizTaskInfoList);
        return jsonArray;
    }

    @Override
    public String editThirdWorkCore(ThirdBizTaskInfoParam thirdBizTaskInfoParam){
        List<BizTaskInfo> bizTaskInfoList = new ArrayList<>();
        if(StringUtils.isNotEmpty(thirdBizTaskInfoParam.getProId()) && StringUtils.isNotEmpty(thirdBizTaskInfoParam.getProType())){
            bizTaskInfoList = this.list(new LambdaQueryWrapper<BizTaskInfo>().eq(BizTaskInfo::getProId,thirdBizTaskInfoParam.getProId())
                    .eq(BizTaskInfo::getProType,thirdBizTaskInfoParam.getProType()));
        }
        List<BizTaskInfo> updateWorkList = new ArrayList<>();//修改
        List<BizTaskInfo> saveWorkList = new ArrayList<>();//新增
        List<BizTaskInfo> deleteWorkList = new ArrayList<>();//删除

        Map<String,String> map = new HashMap<>();
        thirdBizTaskInfoParam.getThirdBizTaskInfoVoList().forEach(vo -> {
            if(StringUtils.isEmpty(vo.getId())){
                BizTaskInfo bizTaskInfo = BeanUtil.toBean(vo,BizTaskInfo.class);
                bizTaskInfo.setProId(thirdBizTaskInfoParam.getProId());
                bizTaskInfo.setProType(thirdBizTaskInfoParam.getProType());
                saveWorkList.add(bizTaskInfo);
            }
            if(StringUtils.isNotEmpty(vo.getId())){
                BizTaskInfo bizTaskInfo = BeanUtil.toBean(vo,BizTaskInfo.class);
                updateWorkList.add(bizTaskInfo);
                map.put(vo.getId(),vo.getId());
            }
        });
        if(CollectionUtil.isNotEmpty(bizTaskInfoList)){
            bizTaskInfoList.forEach(info -> {
                if(ObjectUtil.isEmpty(map.get(info.getId()))){ deleteWorkList.add(info);}
            });
        }
        /** 新增 */
        if(CollectionUtil.isNotEmpty(saveWorkList)){
            Boolean isSaveTrue = this.saveBatch(saveWorkList);
            if(isSaveTrue){ saveWorkList.forEach(work -> asyCreateSchedule(work.getId()));}
        }
        /** 修改 */
        if(CollectionUtil.isNotEmpty(updateWorkList)){ this.saveOrUpdateBatch(updateWorkList); }

        /** 删除 */
        if(CollectionUtil.isNotEmpty(deleteWorkList)){
            Boolean isdeleteTrue = this.removeBatchByIds(deleteWorkList);
            if(isdeleteTrue){
                deleteWorkList.forEach(del -> {
                    boolean isTrue = StringUtils.isNotEmpty(del.getScheduleId()) && StringUtils.isNotEmpty(del.getBelonger());
                    if(isTrue){ asyDeleteSchedule(del.getScheduleId(),del.getBelonger());}
                });
            }
        }
        return "修改成功！";
    }

    /**  异步创建日程 */
    private Future<String> asyCreateSchedule(String id) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                devFeign.addScheduleForWork(id);
            } catch (Exception e) {
                log.error("创建日程失败!e:"+e.getMessage());
            }
            return "异步创建日程";
        });
        return future;
    }
    /**  异步删除日程 */
    private Future<String> asyDeleteSchedule(String scheduleId,String dutyUserId) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try{
                devFeign.deleteScheduleForWork(scheduleId,dutyUserId);
            }catch (Exception e){
                log.error("删除日程失败！e:"+e.getMessage());
            }
            return "异步删除日程";
        });
        return future;
    }

}
