package vip.xiaonuo.sys.modular.hr.magic.magicattendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Date;

@Setter
@Getter
public class MagicMemberParam {

    @ApiModelProperty(value = "显示人脸图片文件", position = 1)
    private File showFace;

    @ApiModelProperty(value = "识别人脸图片文件-人脸图片中人脸的像素大小至少为200*200，并非整体图片像素大小", position = 2)
    private File verifyFace;
    @ApiModelProperty(value = "手机号", position = 3)
    private String mobile;
    @ApiModelProperty(value = "入职时间-Long型时间戳，毫秒级", position = 4)
    private Date enterTime;
    @ApiModelProperty(value = "电话号码", position = 5)
    private String telephone;
    @ApiModelProperty(value = "职位", position = 6)
    private String post;
    @ApiModelProperty(value = "成员姓名", position = 7)
    private String memberName;
    @ApiModelProperty(value = "第三方ID", position = 8)
    private String tpId;
    @ApiModelProperty(value = "昵称", position = 9)
    private String nickName;
    @ApiModelProperty(value = "工号", position = 10)
    private String jobNum;
    @ApiModelProperty(value = "性别 -- 0：未知，1：男，2：女", position = 11)
    private Integer sex;
    @ApiModelProperty(value = "生日", position = 12)
    private Date birthday;
    @ApiModelProperty(value = "邮箱", position = 13)
    private String email;
    @ApiModelProperty(value = "建科成员ID", position = 14)
    private String userId;
    @ApiModelProperty(value = "成员ID", position = 15)
    private Long memberId;

}
