package vip.xiaonuo.sys.modular.hr.reg.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 初次申请，携带资质管理证书信息
 */


@Getter
@Setter
public class RegCertVo {

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 3)
    private String level;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 4)
    private String type;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 5)
    private String major;

    /** 注册成功日期 */
    @ApiModelProperty(value = "注册成功日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;

    /** 花名册主键id */
    @ApiModelProperty(value = "花名册主键id", position = 13)
    private String personnelId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 14)
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 15)
    private String idCardNumber;
}
