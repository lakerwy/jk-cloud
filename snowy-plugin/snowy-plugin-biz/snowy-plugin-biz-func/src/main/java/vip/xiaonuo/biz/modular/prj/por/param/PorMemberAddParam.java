package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理添加参数
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
@Getter
@Setter
public class PorMemberAddParam {

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String userName;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 4)
    private String userId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 5)
    private String phone;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 6)
    private String orgName;

    /** 所属部门Id */
    @ApiModelProperty(value = "所属部门Id", position = 7)
    private String orgId;

    /** 担任角色 */
    @ApiModelProperty(value = "担任角色", position = 8)
    private String roleName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memberMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 14)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 15)
    private String attach;

}
