package vip.xiaonuo.sys.modular.hr.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）简历库信息实体
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
@Getter
@Setter
@TableName("HR_SCHOOL_RESUME")
public class SchoolResume {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 年 */
    @ApiModelProperty(value = "年", position = 2)
    private String year;

    /** 春招|秋招 */
    @ApiModelProperty(value = "春招|秋招", position = 3)
    private String type;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 电话|手机 */
    @ApiModelProperty(value = "电话|手机", position = 5)
    private String phone;

    /** 学校 */
    @ApiModelProperty(value = "学校", position = 6)
    private String school;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 7)
    private String education;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 8)
    private String major;

    /** 应聘信息来源 */
    @ApiModelProperty(value = "应聘信息来源", position = 9)
    private String infoResource;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 10)
    private String resumeAttach;

    /** 其他附件 */
    @ApiModelProperty(value = "其他附件", position = 11)
    private String otherAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别", position = 13)
    private String postType;

    /** 应聘岗位 */
    @ApiModelProperty(value = "应聘岗位", position = 14)
    private String applyPost;

    /** 期望薪资 */
    @ApiModelProperty(value = "期望薪资", position = 15)
    private String expectSalary;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 16)
    private String idNumber;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 17)
    private String sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月", position = 18)
    private String birthDay;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 19)
    private String age;

    /** E-mail */
    @ApiModelProperty(value = "E-mail", position = 20)
    private String email;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 21)
    private String nativePlace;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 22)
    private String politicalStatus;

    /** 健康状况 */
    @ApiModelProperty(value = "健康状况", position = 23)
    private String healthStatus;

    /** 婚姻状况 */
    @ApiModelProperty(value = "婚姻状况", position = 24)
    private String maritalStatus;

    /** 近期照片 */
    @ApiModelProperty(value = "近期照片", position = 25)
    private String photo;

    /** 户口所在地 */
    @ApiModelProperty(value = "户口所在地", position = 26)
    private String placement;

    /** 现居住地址 */
    @ApiModelProperty(value = "现居住地址", position = 27)
    private String address;

    /** 住房情况 */
    @ApiModelProperty(value = "住房情况", position = 28)
    private String houseStatus;

    /** 爱好/特长 */
    @ApiModelProperty(value = "爱好/特长", position = 29)
    private String hobby;

    /** 荣誉证书 */
    @ApiModelProperty(value = "荣誉证书", position = 30)
    private String honor;

    /** 奖励/证书/外语等级 */
    @ApiModelProperty(value = "奖励/证书/外语等级", position = 31)
    private String skillLevel;

    /** 最快入职时间 */
    @ApiModelProperty(value = "最快入职时间", position = 32)
    private String entryDate;

    /** 科研/项目/研究成果 */
    @ApiModelProperty(value = "科研/项目/研究成果", position = 33)
    private String research;

    /** 教育信息(由近及远) */
    @ApiModelProperty(value = "教育信息(由近及远)", position = 34)
    private String eduJson;

    /** 实习经历(由近及远) */
    @ApiModelProperty(value = "实习经历(由近及远)", position = 35)
    private String workJson;

    /** 直系亲属之详情情况 */
    @ApiModelProperty(value = "直系亲属之详情情况", position = 36)
    private String relativeJson;

    /** 亲友姓名(如有) */
    @ApiModelProperty(value = "亲友姓名(如有)", position = 37)
    private String friendName;

    /** 部门/职位(如有) */
    @ApiModelProperty(value = "部门/职位(如有)", position = 38)
    private String friendDuty;

    /** 关系(如有) */
    @ApiModelProperty(value = "关系(如有)", position = 39)
    private String friendRelation;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 40)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 41)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 42)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 43)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 44)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 45)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 46)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 所处阶段 */
    @ApiModelProperty(value = "所处阶段", position = 47)
    private String resumeStage;

    /** 是否发送短信：0：否 1：是 */
    @ApiModelProperty(value = "是否发送短信：0：否 1：是", position = 48)
    private String isSend;

    /** url短链接 */
    @ApiModelProperty(value = "url短链接", position = 49)
    private String shortUrl;

    /** 简历状态 0 未审核 1 合格 -1 不合格 */
    @ApiModelProperty(value = "简历状态 0 未审核 1 合格 -1 不合格", position = 100)
    private String resumeStatus;
}
