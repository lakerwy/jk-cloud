package vip.xiaonuo.sys.modular.hr.h.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 招待事项登记编辑参数
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Getter
@Setter
public class HRegEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 填报部门Id */
    @ApiModelProperty(value = "填报部门Id", position = 2)
    private String deptId;

    /** 填报部门 */
    @ApiModelProperty(value = "填报部门", position = 3)
    private String deptName;

//    /** 填报人Id */
//    @ApiModelProperty(value = "填报人Id", position = 4)
//    private String optId;
//
//    /** 填报人 */
//    @ApiModelProperty(value = "填报人", position = 5)
//    private String optName;
//
//    /** 登记日期 */
//    @ApiModelProperty(value = "登记日期", position = 6)
//    private Date optTime;

    /** 招待日期 */
    @ApiModelProperty(value = "招待日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hDate;

    /** 招待事由 */
    @ApiModelProperty(value = "招待事由", position = 8)
    private String descMemo;

    /** 陪同人员 */
    @ApiModelProperty(value = "陪同人员", position = 9)
    private String joinJosn;

    /** 总人数 */
    @ApiModelProperty(value = "总人数", position = 10)
    private String joinNum;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 16)
    private String attachs;

    /** SN */
    @ApiModelProperty(value = "SN", position = 20)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 21)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 22)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;

}
