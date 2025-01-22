package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商信息实体
 *
 * @author pzh
 * @date  2025/01/22 15:52
 **/
@Getter
@Setter
public class SupplierVo {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 3)
    private String companyName;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date establishTime;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 5)
    private String legalRepresentative;

    /** 法人身份证号 */
    @ApiModelProperty(value = "法人身份证号", position = 6)
    private String legalIdNum;

    /** 注册资本(万元) */
    @ApiModelProperty(value = "注册资本(万元)", position = 7)
    private BigDecimal registeredCapital;

    /** 企业组织形式 */
    @ApiModelProperty(value = "企业组织形式", position = 8)
    private String companyType;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 9)
    private String corpCode;

    /** 公司邮箱 */
    @ApiModelProperty(value = "公司邮箱", position = 10)
    private String mailbox;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 11)
    private String businessScope;

    /** 住所地址（省市） */
    @ApiModelProperty(value = "住所地址（省市）", position = 12)
    private String home;

    /** 住所地址（详细） */
    @ApiModelProperty(value = "住所地址（详细）", position = 13)
    private String homeAddress;

    /** 办公地址（省市） */
    @ApiModelProperty(value = "办公地址（省市）", position = 14)
    private String office;

    /** 办公地址（详细） */
    @ApiModelProperty(value = "办公地址（详细）", position = 15)
    private String officeAddress;

    /** 服务地区（省市） */
    @ApiModelProperty(value = "服务地区（省市）", position = 16)
    private String serviceArea;

    /** 不涉及服务城区/区县 */
    @ApiModelProperty(value = "不涉及服务城区/区县", position = 17)
    private String nonServiceArea;

    /** 法人身份证扫描件 */
    @ApiModelProperty(value = "法人身份证扫描件", position = 18)
    private String legalIdImage;

    /** 营业执照扫描件 */
    @ApiModelProperty(value = "营业执照扫描件", position = 19)
    private String businessLicenseImage;

    /** 公司logo */
    @ApiModelProperty(value = "公司logo", position = 20)
    private String companyLogo;

    /** 管理员姓名 */
    @ApiModelProperty(value = "管理员姓名", position = 21)
    private String adminName;

    /** 管理员手机号 */
    @ApiModelProperty(value = "管理员手机号", position = 22)
    private String adminPhone;

    /** 管理员身份证号 */
    @ApiModelProperty(value = "管理员身份证号", position = 23)
    private String adminIdNum;

    /** 备用管理员手机号 */
    @ApiModelProperty(value = "备用管理员手机号", position = 24)
    private String standbyAdminPhone;

    /** 管理员照片 */
    @ApiModelProperty(value = "管理员照片", position = 25)
    private String adminImage;

    /** 备用管理员照片 */
    @ApiModelProperty(value = "备用管理员照片", position = 26)
    private String standbyAdminImage;

    /** 印章管理员姓名 */
    @ApiModelProperty(value = "印章管理员姓名", position = 27)
    private String signetAdminName;

    /** 印章管理员手机号 */
    @ApiModelProperty(value = "印章管理员手机号", position = 28)
    private String signetAdminPhone;

    /** 印章管理员身份证号 */
    @ApiModelProperty(value = "印章管理员身份证号", position = 29)
    private String signetAdminIdNum;

    /** 印章管理员邮箱 */
    @ApiModelProperty(value = "印章管理员邮箱", position = 30)
    private String signetAdminMailbox;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名", position = 31)
    private String businessAdminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号", position = 32)
    private String businessAdminPhone;

    /** 业务负责人微信号 */
    @ApiModelProperty(value = "业务负责人微信号", position = 33)
    private String businessAdminWechat;

    /** 业务负责人邮箱 */
    @ApiModelProperty(value = "业务负责人邮箱", position = 34)
    private String businessAdminMailbox;

    /** 投标专用手机号 */
    @ApiModelProperty(value = "投标专用手机号", position = 35)
    private String dedicatedPhone;

    /** 业务负责人身份证扫描件 */
    @ApiModelProperty(value = "业务负责人身份证扫描件", position = 36)
    private String businessAdminIdImage;

    /** 账户名称 */
    @ApiModelProperty(value = "账户名称", position = 37)
    private String accountName;

    /** 银行账号 */
    @ApiModelProperty(value = "银行账号", position = 38)
    private String bankAccount;

    /** 开户行名称 */
    @ApiModelProperty(value = "开户行名称", position = 39)
    private String bankName;

    /** 联行号 */
    @ApiModelProperty(value = "联行号", position = 40)
    private String interbankNum;

    /** 开户许可证扫描件 */
    @ApiModelProperty(value = "开户许可证扫描件", position = 41)
    private String bankLicenseImage;

    /** 开户行地址 */
    @ApiModelProperty(value = "开户行地址", position = 42)
    private String bankAddress;

    /** 合作承诺函 */
    @ApiModelProperty(value = "合作承诺函", position = 43)
    private String commitmentLetter;

    /** 宣读视频 */
    @ApiModelProperty(value = "宣读视频", position = 44)
    private String video;

    /** 黑名单标记(1-是黑名单，0-不是黑名单) */
    @ApiModelProperty(value = "黑名单标记(1-是黑名单，0-不是黑名单)", position = 45)
    private String isBlacklist;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因", position = 46)
    private String blacklistReason;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 47)
    private String memo;

    /** 来源类型（1-内部推荐，2-自行申报） */
    @ApiModelProperty(value = "来源类型（1-内部推荐，2-自行申报）", position = 48)
    private String source;

    /** 是否优质供应商（1-是，0-否） */
    @ApiModelProperty(value = "是否优质供应商（1-是，0-否）", position = 49)
    private String isGood;

    /** 是否首页展示（1-是，0-否） */
    @ApiModelProperty(value = "是否首页展示（1-是，0-否）", position = 50)
    private String isShow;

    /** 入库时间 */
    @ApiModelProperty(value = "入库时间", position = 51)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvedTime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 52)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 53)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 54)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 55)
    private String updateUser;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 56)
    private String deleteFlag;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 57)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 58)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 59)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 60)
    private String status;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 61)
    private String dataScopeId;
}
