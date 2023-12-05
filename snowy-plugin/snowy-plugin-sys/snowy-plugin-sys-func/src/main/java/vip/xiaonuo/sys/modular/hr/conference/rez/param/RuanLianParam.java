package vip.xiaonuo.sys.modular.hr.conference.rez.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

@Setter
@Getter
public class RuanLianParam {

    /** 企业编号  */
    @ApiModelProperty(value = "企业编号")
    private String companyId;

    /** 用户id  */
    @ApiModelProperty(value = "用户id")
    private String userid;

    /** 预约主题  */
    @ApiModelProperty(value = "预约主题")
    private String ztname;

    /** 预约人姓名  */
    @ApiModelProperty(value = "预约人姓名")
    private String cjname;

    /** 预约开始时间  */
    @ApiModelProperty(value = "预约开始时间")
    private String yytimes;

    /** 预约结束时间  */
    @ApiModelProperty(value = "预约结束时间")
    private String yytimee;

    /**  场景id  */
    @ApiModelProperty(value = " 场景id")
    private String cjid;

    /** 外部邀请人  */
    @ApiModelProperty(value = "外部邀请人")
    private Json wbyqinfo;

    @ApiModelProperty(value = "详细地址")
    private String addr;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "默认不写：-1立即通知 -2不通知 >0开始前多少分钟通知")
    private String tztime;

    @ApiModelProperty(value = "会议ID")
    private String rezId;

    @ApiModelProperty(value = "面板名称")
    private String mianbanName;
}
