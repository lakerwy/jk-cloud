package vip.xiaonuo.sys.modular.info.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.info.entity.PartnerFee;
import vip.xiaonuo.sys.modular.record.entity.PartnerRecord;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PartnerInfoVo{

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 3)
    private String parentId;

    /** 主管id */
    @ApiModelProperty(value = "主管id", position = 4)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String directorId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 5)
    private String name;

    /** 编码 */
    @ApiModelProperty(value = "编码", position = 6)
    private String code;

    /** 分类 */
    @ApiModelProperty(value = "分类", position = 7)
    private String category;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", position = 8)
    private Integer sortCode;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String extJson;

    @ApiModelProperty(value = "伙伴类型", position = 10)
    private String partnerType;

    @ApiModelProperty(value = "所属大区", position = 11)
    private String region;

    @ApiModelProperty(value = "大区经理", position = 12)
    private String regionalManager;

    @ApiModelProperty(value = "伙伴性质", position = 13)
    private String partnerNature ;

    @ApiModelProperty(value = "当前管理率", position = 14)
    private String currentRates;

    /** 实体 */
    @ApiModelProperty(value = "实体", position = 15)
    private String entity;

    /** 地址 */
    @ApiModelProperty(value = "地址", position = 16)
    private String address;

    /** 电话 */
    @ApiModelProperty(value = "电话", position = 17)
    private String telNumber;

    /** 伙伴状态 */
    @ApiModelProperty(value = "伙伴状态", position = 18)
    private String partnerStatus;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码", position = 19)
    private String phoneNumber;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 20)
    private String post;

    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱", position = 21)
    private String email;

    /** 伙伴分级 */
    @ApiModelProperty(value = "伙伴分级", position = 22)
    private String partnerLevel;

    /** 重要程度 */
    @ApiModelProperty(value = "重要程度", position = 23)
    private String vitalLevel;

    /** 开户银行 */
    @ApiModelProperty(value = "开户银行", position = 24)
    private String bankName;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 25)
    private String bankNumber;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 26)
    private String receivingUnits;

    /** 邮递地址 */
    @ApiModelProperty(value = "邮递地址", position = 27)
    private String mailingAddress;
    @ApiModelProperty(value = "合作伙伴-管理费缴纳情况", position = 28)
    private List<PartnerFee> partnerFees;

    @ApiModelProperty(value = "合作伙伴-维护记录", position = 29)
    private List<PartnerRecord> partnerRecords;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 30)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 32)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 34)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 阶段 */
    @ApiModelProperty(value = "阶段", position = 34)
    private String stages;

    /** 协同客服 */
    @ApiModelProperty(value = "协同客服", position = 36)
    private String cservice;

    /** 主要联系人 */
    @ApiModelProperty(value = "主要联系人", position = 37)
    private String contact;
}
