package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BizChartReviewsDetailsVo  implements Serializable {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** SN值 */
    @ApiModelProperty(value = "SN值", position = 3)
    private String sn;

    /** 受理人 */
    @ApiModelProperty(value = "受理人", position = 4)
    private String opt;

    /** 受理时间 */
    @ApiModelProperty(value = "受理时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optDate;

    /** 项目(合同)编号 */
    @ApiModelProperty(value = "项目(合同)编号", position = 6)
    private String projectNo;

    /** 送审人 */
    @ApiModelProperty(value = "送审人", position = 7)
    private String sender;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 8)
    private String companyOrDepartment;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 9)
    private String projectLeader;

    /** 设计阶段 */
    @ApiModelProperty(value = "设计阶段", position = 10)
    private String designStage;

    /** 设计人 */
    @ApiModelProperty(value = "设计人", position = 11)
    private String designer;

    /** 初次出图日期 */
    @ApiModelProperty(value = "初次出图日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mapDate;

    /** 初次出图份数 */
    @ApiModelProperty(value = "初次出图份数", position = 13)
    private String mapCopies;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String note;

    /** 送审图 */
    @ApiModelProperty(value = "送审图", position = 15)
    private String reviewAttachments;

    /** 暂时未用到 */
    @ApiModelProperty(value = "暂时未用到", position = 16)
    private String attachments1;

    /** 审定图 */
    @ApiModelProperty(value = "审定图", position = 17)
    private String validatedAttachments;

    /** 其他资料 */
    @ApiModelProperty(value = "其他资料", position = 18)
    private String otherAttachments;

    /** 盖章日期 */
    @ApiModelProperty(value = "盖章日期", position = 19)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sealDate;

    /** 是否留档 */
    @ApiModelProperty(value = "是否留档", position = 20)
    private String isFile;

    /** 是否物流 */
    @ApiModelProperty(value = "是否物流", position = 21)
    private String isLogistics;

    /** 受理状态 */
    @ApiModelProperty(value = "受理状态", position = 22)
    private String status;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态", position = 23)
    private String deleteFlag;

    /** 合同编号（历史表Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Hetong.ContractNo）", position = 24)
    private String contractNo;

    /** 合同名称（历史表Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表Hetong.ProjectName）", position = 25)
    private String projectName;

    /** 工程类别（历史表Hetong.SecondLevel） */
    @ApiModelProperty(value = "工程类别（历史表Hetong.SecondLevel）", position = 26)
    private String secondLevel;

    @ApiModelProperty(value = "图审记录列表")
    private List<BizMapRegister> bizMapRegisterList;

    @ApiModelProperty(value = "校审记录列表")
    private List<BizAuditLists> bizAuditListsList;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 50)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 51)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 52)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 53)
    private String updateUser;

    /** 基本信息备注 */
    @ApiModelProperty(value = "基本信息备注", position = 53)
    private String remark;
}
