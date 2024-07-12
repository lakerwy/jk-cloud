package vip.xiaonuo.oa.modular.attendance.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.rule.enums.AdjustmentTypeEnum;

import java.beans.Transient;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/11 15:15
 * 补卡规则
 */
public class AdjustmentRule extends BaseRule{
    @ApiModelProperty(value = "补卡次数", position = 1)
    private int correctNum = 3;

    @ApiModelProperty(value = "补卡起算日期", position = 2)
    private int correctStartTime;

    @ApiModelProperty(value = "补卡类型(1 缺卡 2 迟到 3 早退)", position = 3)
    private int[] correctTypes;

    @ApiModelProperty(value = "补卡时效，如补最近一天的", position = 4)
    private int correctApplyTime;

    /**
     * 补卡次数 文字拼接  供前端展示使用
     */
    @JsonIgnore
    private String adjustmentNumValue;

    /**
     * 补卡时效 文字拼接  供前端展示使用
     */
    @JsonIgnore
    private String adjustmentTime;

    /**
     * 补卡类型 文字拼接  供前端展示使用
     */
    @JsonIgnore
    private String adjustmentType;

    public int getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getCorrectStartTime() {
        return correctStartTime;
    }

    public void setCorrectStartTime(int correctStartTime) {
        this.correctStartTime = correctStartTime;
    }

    public int[] getCorrectTypes() {
        return correctTypes;
    }

    public void setCorrectTypes(int[] correctTypes) {
        this.correctTypes = correctTypes;
    }

    public int getCorrectApplyTime() {
        return correctApplyTime;
    }

    public void setCorrectApplyTime(int correctApplyTime) {
        this.correctApplyTime = correctApplyTime;
    }

    @JsonProperty("adjustmentTime")
    public String getAdjustmentTime() {
        this.adjustmentTime = String.format("补卡时限：可申请最近%s个自然日内的补卡",this.getCorrectApplyTime());
        return adjustmentTime;
    }

    @JsonProperty("adjustmentType")
    public String getAdjustmentType() {
        this.adjustmentType = String.format("补卡类型：%s", AdjustmentTypeEnum.fromTypes(this.getCorrectTypes()));
        return adjustmentType;
    }

    @JsonProperty("adjustmentNumValue")
    public String getAdjustmentNumValue() {
        this.adjustmentNumValue = String.format("补卡次数：每个月可提交%s次;每月的%s日起算",this.getCorrectApplyTime(),this.getCorrectStartTime());
        return adjustmentNumValue;
    }
}
