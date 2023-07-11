package vip.xiaonuo.biz.modular.crm.notes.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/4 15:13
 */

@Getter
@Setter
public class NotesInfoVo {

    /** 主键ID（历史表Notes.Id） */
    @ApiModelProperty(value = "主键ID（历史表Notes.Id）", position = 1)
    private String id;

    /** 流水号SN（历史表Notes.SN） */
    @ApiModelProperty(value = "流水号SN（历史表Notes.SN）", position = 2)
    private String sn;

    /** 登记人（历史表Notes.Opt） */
    @ApiModelProperty(value = "登记人（历史表Notes.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表Notes.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表Notes.OptDate）", position = 4)
    private Date optDate;

    /** 类别（历史表Notes.Type） */
    @ApiModelProperty(value = "类别（历史表Notes.Type）", position = 5)
    private String type;

    /** 伙伴编号|伙伴名称（历史表Notes.PartnerNo） */
    @ApiModelProperty(value = "伙伴编号|伙伴名称（历史表Notes.PartnerNo）", position = 6)
    private String partnerNo;

    /** 合同编号（历史表Notes.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Notes.ContractNo）", position = 7)
    private String contractNo;

    /** 申请日期（历史表Notes.ApplyDate） */
    @ApiModelProperty(value = "申请日期（历史表Notes.ApplyDate）", position = 8)
    private Date applyDate;

    /** 最后签批日期（历史表Notes.FinalDate） */
    @ApiModelProperty(value = "最后签批日期（历史表Notes.FinalDate）", position = 9)
    private Date finalDate;

    /** 财务签收日期（历史表Notes.SignDate） */
    @ApiModelProperty(value = "财务签收日期（历史表Notes.SignDate）", position = 10)
    private Date signDate;

    /** 执行注销标记（历史表Notes.CancelMark） */
    @ApiModelProperty(value = "执行注销标记（历史表Notes.CancelMark）", position = 11)
    private String cancelMark;

    /** 备注（历史表Notes.Note） */
    @ApiModelProperty(value = "备注（历史表Notes.Note）", position = 12)
    private String note;

    /** 合作人（历史表Notes.Partner） */
    @ApiModelProperty(value = "合作人（历史表Notes.Partner）", position = 13)
    private String partner;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;


    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 19)
    private String contractName;

    /** 伙伴名称 */
//    @ApiModelProperty(value = "伙伴名称", position = 20)
//    private String partnerName;

}
