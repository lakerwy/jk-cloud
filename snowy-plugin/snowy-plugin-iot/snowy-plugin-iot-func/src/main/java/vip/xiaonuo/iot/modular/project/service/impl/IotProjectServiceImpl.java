package vip.xiaonuo.iot.modular.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
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
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;
import vip.xiaonuo.iot.modular.group.entity.IotGroup;
import vip.xiaonuo.iot.modular.group.service.IotGroupService;
import vip.xiaonuo.iot.modular.project.entity.IotProject;
import vip.xiaonuo.iot.modular.project.mapper.IotProjectMapper;
import vip.xiaonuo.iot.modular.project.param.*;
import vip.xiaonuo.iot.modular.project.service.IotProjectService;
import vip.xiaonuo.iot.modular.project.vo.NodeVo;
import vip.xiaonuo.iot.modular.topic.entity.IotTopic;
import vip.xiaonuo.iot.modular.topic.service.IotTopicService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物联网-项目Service接口实现类
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Service
public class IotProjectServiceImpl extends ServiceImpl<IotProjectMapper, IotProject> implements IotProjectService {

    @Resource
    private IotTopicService iotTopicService;

    @Resource
    private IotDeviceService iotDeviceService;

    @Resource
    private IotGroupService iotGroupService;

    @Override
    public Page<IotProject> page(IotProjectPageParam iotProjectPageParam) {
        QueryWrapper<IotProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(iotProjectPageParam.getProjectName())) {
            queryWrapper.lambda().like(IotProject::getProjectName, iotProjectPageParam.getProjectName());
        }
        if(ObjectUtil.isAllNotEmpty(iotProjectPageParam.getSortField(), iotProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(iotProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, iotProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(iotProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(IotProject::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IotProjectAddParam iotProjectAddParam) {
        IotProject one = getOne(new LambdaQueryWrapper<IotProject>().eq(IotProject::getProjectName, iotProjectAddParam.getProjectName()));
        if(one != null){
            throw new CommonException("该项目名已被使用。");
        }
        IotProject iotProject = BeanUtil.toBean(iotProjectAddParam, IotProject.class);
        iotProject.setProjectCode("IOT"+ RandomUtil.randomNumbers(6));
        this.save(iotProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IotProjectEditParam iotProjectEditParam) {
        IotProject one = getOne(new LambdaQueryWrapper<IotProject>().eq(IotProject::getProjectName, iotProjectEditParam.getProjectName()));
        if(one != null && !one.getId().equals(iotProjectEditParam.getId())){
            throw new CommonException("该项目名已被使用。");
        }
        IotProject iotProject = this.queryEntity(iotProjectEditParam.getId());
        BeanUtil.copyProperties(iotProjectEditParam, iotProject);
        this.updateById(iotProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IotProjectIdParam> iotProjectIdParamList) {
        if(CollUtil.isNotEmpty(iotProjectIdParamList)){
            iotProjectIdParamList.forEach(iotProjectIdParam -> {
                List<IotTopic> list = iotTopicService.list(new LambdaQueryWrapper<IotTopic>().eq(IotTopic::getProjectId, iotProjectIdParam.getId()));
                if(CollUtil.isNotEmpty(list)){
                    throw new CommonException("项目下关联了网关，请移除网关后删除，项目ID:{}",iotProjectIdParam.getId());
                }
            });
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(iotProjectIdParamList, IotProjectIdParam::getId));
    }

    @Override
    public IotProject detail(IotProjectIdParam iotProjectIdParam) {
        return this.queryEntity(iotProjectIdParam.getId());
    }

    @Override
    public IotProject queryEntity(String id) {
        IotProject iotProject = this.getById(id);
        if(ObjectUtil.isEmpty(iotProject)) {
            throw new CommonException("物联网-项目不存在，id值为：{}", id);
        }
        return iotProject;
    }

    @Override
    public NodeVo getIotTrees(IotProjectListParam iotProjectListParam) {
        String id = iotProjectListParam.getId();
        if(StrUtil.isEmpty(id)){
            List<IotProject> list = list();
            if(CollUtil.isNotEmpty(list)){
                id = list.get(0).getId();
            }
        }
        if(StrUtil.isEmpty(id)){
            return null;
        }
        IotProject iotProject = queryEntity(id);
        NodeVo nodeVo = new NodeVo();
        nodeVo.setType(1);
        nodeVo.setKey(id);
        nodeVo.setValue(iotProject.getProjectName());
        nodeVo.setDesc(iotProject.getProjectName());
        String searchKey = iotProjectListParam.getSearchKey();
        if(iotProjectListParam.getType()==0){
            buildTopicTree(id, nodeVo,searchKey);
        }else {
            buildGroupTree(id,nodeVo,searchKey);
        }
        return nodeVo;
    }

    private void buildTopicTree(String id, NodeVo nodeVo,String searchKey) {
        List<IotTopic> iotTopics = iotTopicService.list(new LambdaQueryWrapper<IotTopic>().eq(IotTopic::getProjectId, id));
        if(CollUtil.isNotEmpty(iotTopics)){
            List<IotDevice> iotDevices = new ArrayList<>();
            if(StrUtil.isEmpty(searchKey)){
                iotDevices.addAll(iotDeviceService.list());
            }else {
                iotDevices.addAll(iotDeviceService.list(new LambdaQueryWrapper<IotDevice>().like(IotDevice::getDeviceCode, searchKey).or().like(IotDevice::getDeviceName, searchKey)));
            }
            Map<String, List<NodeVo>> iotDeviceMaps  = iotDevices.stream()
                    .collect(Collectors.groupingBy(
                            iotDevice -> StrUtil.isEmpty(iotDevice.getDeviceTopicId()) ? "-1" : iotDevice.getDeviceTopicId(),
                            Collectors.mapping(iotDevice -> {
                                NodeVo nodeVo1 = new NodeVo();
                                nodeVo1.setType(3);
                                nodeVo1.setKey(iotDevice.getId());
                                nodeVo1.setValue(iotDevice.getDeviceCode());
                                nodeVo1.setCode(iotDevice.getDeviceCode());
                                nodeVo1.setIsOnline(iotDevice.getIsOnline());
                                nodeVo1.setDesc(iotDevice.getDeviceName());
                                return nodeVo1;
                            }, Collectors.toList())
                    ));
            List<NodeVo> iotTopicNodes = new ArrayList<>();
            iotTopics.forEach(iotTopic -> {
                NodeVo nodeVo1 = new NodeVo();
                nodeVo1.setType(2);
                nodeVo1.setKey(iotTopic.getId());
                nodeVo1.setValue(iotTopic.getTopic());
                nodeVo1.setDesc(iotTopic.getTopicDesc());
                nodeVo1.setChildren(iotDeviceMaps.get(iotTopic.getId()));
                iotTopicNodes.add(nodeVo1);
            });
            List<NodeVo> nodeVos = iotDeviceMaps.get("-1");
            if(CollUtil.isNotEmpty(nodeVos)){
                NodeVo nodeVo1 = new NodeVo();
                nodeVo1.setType(2);
                nodeVo1.setKey("-1");
                nodeVo1.setValue("其他");
                nodeVo1.setDesc("其他");
                nodeVo1.setChildren(nodeVos);
                iotTopicNodes.add(nodeVo1);
            }
            nodeVo.setChildren(iotTopicNodes);
        }
    }

    private void buildGroupTree(String id, NodeVo nodeVo,String searchKey) {
        List<IotGroup> iotGroups = iotGroupService.list(new LambdaQueryWrapper<IotGroup>().eq(IotGroup::getProjectId, id));
        if(CollUtil.isNotEmpty(iotGroups)){
            List<IotDevice> iotDevices = new ArrayList<>();
            if(StrUtil.isEmpty(searchKey)){
                iotDevices.addAll(iotDeviceService.list());
            }else {
                iotDevices.addAll(iotDeviceService.list(new LambdaQueryWrapper<IotDevice>().like(IotDevice::getDeviceCode, searchKey).or().like(IotDevice::getDeviceName, searchKey)));
            }
            Map<String, List<NodeVo>> iotDeviceMaps  = iotDevices.stream()
                    .collect(Collectors.groupingBy(
                            iotDevice -> StrUtil.isEmpty(iotDevice.getGroupId()) ? "-1" : iotDevice.getGroupId(),
                            Collectors.mapping(iotDevice -> {
                                NodeVo nodeVo1 = new NodeVo();
                                nodeVo1.setType(3);
                                nodeVo1.setKey(iotDevice.getId());
                                nodeVo1.setValue(iotDevice.getDeviceCode());
                                nodeVo1.setDesc(iotDevice.getDeviceName());
                                nodeVo1.setCode(iotDevice.getDeviceCode());
                                nodeVo1.setIsOnline(iotDevice.getIsOnline());
                                return nodeVo1;
                            }, Collectors.toList())
                    ));
            List<NodeVo> iotGroupNodes = new ArrayList<>();
            iotGroups.forEach(iotGroup -> {
                NodeVo nodeVo1 = new NodeVo();
                nodeVo1.setType(2);
                nodeVo1.setKey(iotGroup.getId());
                nodeVo1.setValue(iotGroup.getName());
                nodeVo1.setDesc(iotGroup.getName());
                nodeVo1.setChildren(iotDeviceMaps.get(iotGroup.getId()));
                iotGroupNodes.add(nodeVo1);
            });
            List<NodeVo> nodeVos = iotDeviceMaps.get("-1");
            if(CollUtil.isNotEmpty(nodeVos)){
                NodeVo nodeVo1 = new NodeVo();
                nodeVo1.setType(2);
                nodeVo1.setKey("-1");
                nodeVo1.setValue("其他");
                nodeVo1.setDesc("其他");
                nodeVo1.setChildren(nodeVos);
                iotGroupNodes.add(nodeVo1);
            }
            nodeVo.setChildren(iotGroupNodes);
        }
    }
}
