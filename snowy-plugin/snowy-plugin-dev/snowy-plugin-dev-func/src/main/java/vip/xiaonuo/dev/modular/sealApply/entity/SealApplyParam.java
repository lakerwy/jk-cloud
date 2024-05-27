package vip.xiaonuo.dev.modular.sealApply.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 云玺申请单用印请求参数实体
 * @author hxw
 * @date 2024/5/23 9:00
 */
@Setter
@Getter
public class SealApplyParam {

    /** 申请单id **/
    @ApiModelProperty(value = "申请单id")
    private String id;

    /** 申请标题 **/
    @ApiModelProperty(value = "申请标题")
    private String title;

    /** 申请内容 **/
    @ApiModelProperty(value = "申请内容")
    private String content;

    /** 设备标识 **/
    @ApiModelProperty(value = "设备标识")
    private String uuid;

    /** 合同份数 **/
    @ApiModelProperty(value = "合同份数")
    private Integer contractCopiesNum;

    /** 用印次数 **/
    @ApiModelProperty(value = "用印次数")
    private Integer contractCopiesCount;

    /** 申请文件类型id **/
    @ApiModelProperty(value = "申请文件类型id")
    private Integer typeId;


    /** 申请文件类型标识 **/
    @ApiModelProperty(value = "申请文件类型标识")
    private String typeCode;

    /** 申请人id **/
    @ApiModelProperty(value = "申请人id")
    private String applicantId;

    /** 用印人id **/
    @ApiModelProperty(value = "用印人id")
    private String usePeopleId;
}
