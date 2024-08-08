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
package vip.xiaonuo.flw.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.ModelInstance;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.flw.core.node.FlwNode;
import vip.xiaonuo.flw.core.parser.ProcessParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 节点信息工具类
 *
 * @author xuyuxiang
 * @date 2023/5/11 0:18
 */
public class NodeInfoUtil {

    private static final String START_TASK = "startTask";

    public static boolean isStartEvent(FlwNode flwNode) {
        return BpmnModelConstants.BPMN_ELEMENT_START_EVENT.equals(flwNode.getType());
    }

    public static boolean isStartTask(FlwNode flwNode) {
        return START_TASK.equals(flwNode.getType());
    }

    public static boolean isUserTask(FlwNode flwNode) {
        return BpmnModelConstants.BPMN_ELEMENT_USER_TASK.equals(flwNode.getType());
    }

    public static boolean isServiceTask(FlwNode flwNode) {
        return BpmnModelConstants.BPMN_ELEMENT_SERVICE_TASK.equals(flwNode.getType());
    }

    public static boolean isExclusiveGateway(FlwNode flwNode) {
        return BpmnModelConstants.BPMN_ELEMENT_EXCLUSIVE_GATEWAY.equals(flwNode.getType());
    }

    public static boolean isParallelGateway(FlwNode flwNode) {
        return BpmnModelConstants.BPMN_ELEMENT_PARALLEL_GATEWAY.equals(flwNode.getType());
    }

    public static boolean isStartEvent(ModelInstance modelInstance) {
        return BpmnModelConstants.BPMN_ELEMENT_START_EVENT.equals(modelInstance.getDocumentElement().getElementType().getTypeName());
    }

    public static boolean isUserTask(String typeName) {
        return BpmnModelConstants.BPMN_ELEMENT_USER_TASK.equals(typeName);
    }

    public static boolean isServiceTask(String typeName) {
        return BpmnModelConstants.BPMN_ELEMENT_SERVICE_TASK.equals(typeName);
    }

    public static boolean isExclusiveGateway(String typeName) {
        return BpmnModelConstants.BPMN_ELEMENT_EXCLUSIVE_GATEWAY.equals(typeName);
    }

    public static boolean isParallelGateway(String typeName) {
        return BpmnModelConstants.BPMN_ELEMENT_PARALLEL_GATEWAY.equals(typeName);
    }

    public static boolean isEndEvent(String typeName) {
        return BpmnModelConstants.BPMN_ELEMENT_END_EVENT.equals(typeName);
    }

    public static void validFlwNode(FlwNode flwNode) {
        String id = flwNode.getId();
        String type = flwNode.getType();
        String title = flwNode.getTitle();
        if(ObjectUtil.isAllEmpty(id, type, title)) {
            throw new CommonException("流程JSON解析格式错误，某节点的id、type、title均为空");
        }
        if(ObjectUtil.hasEmpty(id, type, title)) {
            throw new CommonException("流程JSON解析格式错误，存在某节点的id、type、title为空");
        }
    }

    /**
     * 根据流程节点信息获取用户节点信息集合
     *
     * @author xuyuxiang
     * @date 2023/5/19 15:56
     **/
    public static List<FlwNode> getUserTaskFlwNodeList(FlwNode flwNode) {
        return ProcessParser.buildBpmnModelInstance(JSONUtil.toJsonStr(flwNode)).getModelElementsByType(UserTask.class).stream()
                .filter(userTask -> !userTask.getName().equals("EMPTY")).map(userTask -> {
                    FlwNode tempFlwNode = new FlwNode();
                    tempFlwNode.setId(userTask.getId());
                    tempFlwNode.setTitle(userTask.getName());
                    return tempFlwNode;
                }).collect(Collectors.toList());
    }


    /**
     * 根据 流程设计 json 当前节点id 获取后续流程节点
     * @param flwJson
     * @param activityId
     * @return
     */
    public static List<FlwNode> getAllNextFlwNodes(String flwJson,String activityId){
        List<FlwNode> flwNodeList = new ArrayList<>();
        FlwNode process = JSONUtil.toBean(flwJson, FlwNode.class);
        executeFlwNodes(process,flwNodeList);
        //找到当前节点
        FlwNode currentFlwNode = flwNodeList.stream().filter(x->x.getId().equals(activityId)).findFirst().orElse(null);
        //后续节点集合
        List<FlwNode> nextFlwNodeList = new ArrayList<>();
        return executeNextFlwNodes(currentFlwNode,nextFlwNodeList);
    }

    /**
     * 递归 获取 流程设计json 所有节点信息（startEvent|startTask...）
     * @param flwNode 流程设计json
     * @param flwNodeList 节点集合
     * @return
     */
    public static List<FlwNode> executeFlwNodes(FlwNode flwNode,List<FlwNode> flwNodeList) {
        if(ObjectUtil.isEmpty(flwNode) || ObjectUtil.isEmpty(flwNode.getId())){
            return flwNodeList;
        }
        //看 ChildNode
        if(ObjectUtil.isNotEmpty(flwNode.getChildNode()) && ObjectUtil.isNotEmpty(flwNode.getChildNode().getId())){
            flwNodeList.add(flwNode.getChildNode());
            executeFlwNodes(flwNode.getChildNode(),flwNodeList);
        }
        //看 conditionNodeList 条件分支中是一个FlwNode集合
        if(CollUtil.isNotEmpty(flwNode.getConditionNodeList())){
            flwNode.getConditionNodeList().forEach(item->{
                if(ObjectUtil.isNotEmpty(item) && ObjectUtil.isNotEmpty(item.getId())){
                    flwNodeList.add(item);
                    executeFlwNodes(item,flwNodeList);
                }
            });
        }
        return flwNodeList;
    }


    /**
     * 有分支则不返回，无条件分支返回后续节点
     * 递归 获取 当前节点 所有后续节点信息（startEvent|startTask...）
     * @param flwNode 流程设计json
     * @param flwNodeList 节点集合
     * @return
     */
    public static List<FlwNode> executeNextFlwNodes(FlwNode flwNode,List<FlwNode> flwNodeList) {
        if(ObjectUtil.isEmpty(flwNode) || ObjectUtil.isEmpty(flwNode.getId())){
            return flwNodeList;
        }
        if(CollUtil.isNotEmpty(flwNode.getConditionNodeList())){
            return flwNodeList;
        }
        if(ObjectUtil.isNotEmpty(flwNode.getChildNode()) && ObjectUtil.isNotEmpty(flwNode.getChildNode().getId())){
            FlwNode tmpFlwNode = BeanUtil.copyProperties(flwNode.getChildNode(),FlwNode.class);
            tmpFlwNode.setChildNode(null);
            tmpFlwNode.setConditionNodeList(null);
            flwNodeList.add(tmpFlwNode);
            executeNextFlwNodes(flwNode.getChildNode(),flwNodeList);
        }
        return flwNodeList;
    }
}
