package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目产值台账 - 收款提醒添加参数
 *
 * @author yy
 * @date  2025/06/18 16:13
 **/
@Getter
@Setter
public class PorMsgAddParam {

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 业务模块（回款计划|业主结算） */
    @ApiModelProperty(value = "业务模块（回款计划|业主结算）", position = 3)
    private String modelType;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 4)
    private String hetongId;

    /** 业务 - 主键Id */
    @ApiModelProperty(value = "业务 - 主键Id", position = 5)
    private String objId;

    /** 是否同步发送钉钉通知 */
    @ApiModelProperty(value = "是否同步发送钉钉通知", position = 6)
    private String isDing;

    /** 主题 */
    @ApiModelProperty(value = "主题", position = 7)
    private String subjectTitle;

    /** 站内信分类 */
    @ApiModelProperty(value = "站内信分类", position = 8)
    private String msgType;

    /** 正文 */
    @ApiModelProperty(value = "正文", position = 9)
    private String content;

    /** 接收人 */
    @ApiModelProperty(value = "接收人", position = 10)
    private String userJson;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 15)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 16)
    private String attach;

    /** 接收人 - userid 集合 */
    @ApiModelProperty(value = "接收人 - userid 集合", position = 20)
    private List<String> userIdList;
}
