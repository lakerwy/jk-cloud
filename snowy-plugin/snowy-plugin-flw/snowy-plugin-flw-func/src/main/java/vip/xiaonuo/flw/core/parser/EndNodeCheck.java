package vip.xiaonuo.flw.core.parser;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.springframework.stereotype.Component;


import java.util.*;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/8 10:16
 */
@Component
public class EndNodeCheck {

    private final Map<String, FlowElement> flowElementCache = new HashMap<>();

    public boolean nextTaskIsEnd(DelegateExecution delegateExecution) {
        String currentActivityId = delegateExecution.getCurrentActivityId();

        // Check if FlowElement is already in cache
        FlowElement flowElement = flowElementCache.get(currentActivityId);

        if (flowElement == null) {
            // If not in cache, retrieve it and put it in cache
            flowElement = delegateExecution.getBpmnModelInstance().getModelElementById(currentActivityId);
            flowElementCache.put(currentActivityId, flowElement);
        }

        // Get outgoing flows
        Collection<SequenceFlow> outgoingFlows = getOutgoingFlows(delegateExecution.getBpmnModelInstance(), flowElement);

        // Check if the next node is an EndEvent
        return outgoingFlows.stream().anyMatch(outgoingFlow -> outgoingFlow.getTarget() instanceof EndEvent);
    }

    private static Collection<SequenceFlow> getOutgoingFlows(BpmnModelInstance bpmnModel, FlowElement flowElement) {
        Collection<SequenceFlow> outgoingFlows = new ArrayList<>();

        for (SequenceFlow sequenceFlow : bpmnModel.getModelElementsByType(SequenceFlow.class)) {
            if (sequenceFlow.getSource().equals(flowElement)) {
                outgoingFlows.add(sequenceFlow);
            }
        }

        return outgoingFlows;
    }
}
