/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.registerCert.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.ApplyRegCertAlwVo;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.RegAlwUserVo;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.RegCertDataVo;
import vip.xiaonuo.sys.modular.hr.registerCert.vo.RegCertStatusDataVo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 注册补贴-申请FLW流程表信息添加参数
 *
 * @author ljy
 * @date  2024/03/28 15:48
 **/
@Getter
@Setter
public class ApplyRegCertDataFlowAddParam {

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 2)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 3)
    private String processInstanceId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 4)
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 5)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

    /** 身份证号*/
    @ApiModelProperty(value = "身份证号",required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCardNumber;

    @ApiModelProperty(value = "注册补贴-本次申请信息")
    List<ApplyRegCertAlwVo> applyRegCertAlwVoList;

    @ApiModelProperty(value = "注册补贴-申请用户基本信息",required = true)
    @NotNull(message = "注册补贴-申请用户基本信息")
    private RegAlwUserVo regAlwUserVo;

    @ApiModelProperty(value = "注册补贴-已有证书")
    private List<RegCertDataVo> regCertDataList;

    @ApiModelProperty(value = "注册补贴-注册执业资格证书")
    private List<RegCertStatusDataVo> regCertStatusDataList;

}
