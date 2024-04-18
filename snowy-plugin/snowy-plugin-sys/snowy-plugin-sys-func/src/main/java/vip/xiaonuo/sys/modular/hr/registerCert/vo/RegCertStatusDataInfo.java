package vip.xiaonuo.sys.modular.hr.registerCert.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.RegCertStatusData;

/**
 * 注册补贴-人力资源-注册执业资格证书信息 【结果反馈】
 */

@Getter
@Setter
public class RegCertStatusDataInfo extends RegCertStatusData {
    /** 注册补贴流程 - 模型ID */
    @ApiModelProperty(value = "注册补贴流程 -模型ID", position = 100)
    private String modelId;

    /** 注册补贴流程 -流程实例id */
    @ApiModelProperty(value = "注册补贴流程 -流程实例id", position = 100)
    private String processInstanceId;

    /** 注册补贴流程 -SN单号 */
    @ApiModelProperty(value = "注册补贴流程 -SN单号", position = 100)
    private String sn;

    /** 注册补贴流程 -流程状态 */
    @ApiModelProperty(value = "注册补贴流程 -流程状态", position = 100)
    private String status;
}
