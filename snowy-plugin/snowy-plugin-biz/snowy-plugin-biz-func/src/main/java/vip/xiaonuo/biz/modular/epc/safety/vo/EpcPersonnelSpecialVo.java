package vip.xiaonuo.biz.modular.epc.safety.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelSpecial;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/9/6 8:26
 */

@Getter
@Setter
public class EpcPersonnelSpecialVo extends EpcPersonnelSpecial {

    /** 主键Id|编号 */
    @TableId
    @ApiModelProperty(value = "主键Id|编号", position = 1)
    private String id_p;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId_p;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 3)
    private String projectId_p;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name_p;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 5)
    private String idCard_p;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 6)
    private String gender_p;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 7)
    private String age_p;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 8)
    private String phone_p;

    /** 在职状态 */
    @ApiModelProperty(value = "在职状态", position = 9)
    private String hireStatus_p;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 10)
    private String belongCompany_p;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种", position = 11)
    private String workType_p;

    /** 进场时间 */
    @ApiModelProperty(value = "进场时间", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enterTime_p;

    /** 退场时间 */
    @ApiModelProperty(value = "退场时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outTime_p;

    /** 是否在保 */
    @ApiModelProperty(value = "是否在保", position = 14)
    private String isInsured_p;

    /** 保额（元） */
    @ApiModelProperty(value = "保额（元）", position = 15)
    private BigDecimal insureAmount_p;

    /** 保险单据 */
    @ApiModelProperty(value = "保险单据", position = 16)
    private String insureAttach_p;

    /** 是否进行了三级安全教育 */
    @ApiModelProperty(value = "是否进行了三级安全教育", position = 17)
    private String isSafety_p;

    /** 三级安全教育试卷 */
    @ApiModelProperty(value = "三级安全教育试卷", position = 18)
    private String safetyAttach_p;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo_p;

}
