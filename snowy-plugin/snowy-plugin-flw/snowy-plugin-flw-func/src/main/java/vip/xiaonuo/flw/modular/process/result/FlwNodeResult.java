package vip.xiaonuo.flw.modular.process.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点 查询结果
 */
@Data
public class FlwNodeResult {

    /** 后续流程节点是否有条件分支  默认：false */
    @ApiModelProperty(value = "后续流程节点是否有条件分支  默认：false", position = 1)
    private Boolean hasConditionNode = Boolean.FALSE;

    /** 后续流程节点集合 */
    @ApiModelProperty(value = "后续流程节点集合", position = 2)
    private List<FlwNodeVo> nextFlwNodeList;

    public FlwNodeResult(){
        this.nextFlwNodeList = new ArrayList<>();
    }

    @Data
    public class FlwNodeVo {

        /**
         * Id
         */
        private String id;

        /**
         * 类型
         */
        private String type;

        /**
         * 标题
         */
        private String title;

        /**
         * 内容
         */
        private String content;

        private Boolean dataLegal;

        /**
         * 参与人信息
         */
        private List<FlwNodeParticipateProp> participateInfo;
    }


    /** 参与人属性类 */
    @Data
    public class FlwNodeParticipateProp {

        /** 参与人键 */
        private String key;

        /** 参与人文字 */
        private String label;

        /** 参与人值 */
        private String value;

        /** 参与人值附加信息 */
        private String extJson;
    }
}
