package vip.xiaonuo.dev.modular.sealMessage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 获取群杰所有的印章信息返回实体
 * @Author hxw
 * @Date 2024/5/17 9:24
 */
@Getter
@Setter
public class UserSealBaseVo {
    /** 印章id */
    @ApiModelProperty(value = "印章id")
    private String id;

    /** 用印部门 */
    @ApiModelProperty(value = "印章名称")
    private String sealName;

    /** 印章状态 (0 在用 1停用 2 已销毁) */
    @ApiModelProperty(value = "印章状态 (0 在用 1停用 2 已销毁)")
    private String sealStatus;

    /** 启用/禁止时间格式为yyyy-MM-dd ,印章不启用时为禁止时间，为印章录入时间，
     * 启用时不填则默认时间为印章录入时间，若填写的启用时间在录入时间之后则为录入时间 */
    @ApiModelProperty(value = "启用/禁止时间")
    private Date realApplyTime;

    /** 印章形状 */
    @ApiModelProperty(value = "印章形状")
    private String sealShape;

    /** 印章材质 */
    @ApiModelProperty(value = "印章材质")
    private String sealMaterial;

    /** 公司id（企业号） */
    @ApiModelProperty(value = "公司id（企业号）")
    private String companyId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /** 印章图片id */
    @ApiModelProperty(value = "印章图片id")
    private String picId;

    /** 组织代码 */
    @ApiModelProperty(value = "组织代码")
    private String orgNo;

    /** 印章分类（0：物理章；1：智能章；2：电子章）
     智能章和物理章，由deviceId是否
     有值决定，默认为0：物理章*/
    @ApiModelProperty(value = "印章分类（0：物理章；1：智能章；2：电子章）")
    private String sealCategory;

    /** 印章形式（physics：物理章；electronic：电子章）*/
    @ApiModelProperty(value = "印章形式（physics：物理章；electronic：电子章）")
    private String sealCategoryNew;

    /** 印模文件Id */
    @ApiModelProperty(value = "创建时间")
    private String sealModelFile;

    /** 印章管理员id */
    @ApiModelProperty(value = "印章管理员id")
    private String adminId;

    /** 印章管理员名称 */
    @ApiModelProperty(value = "印章管理员名称")
    private String adminName;

    /** 印章所在地址的状态值 0管理员，1印章柜，2外带人 */
    @ApiModelProperty(value = "印章所在地址的状态值 0管理员，1印章柜，2外带人")
    private String addressStatus;

    /** json对象记录addressStatus三种状态的数据 */
    @ApiModelProperty(value = "json对象记录addressStatus三种状态的数据")
    private String addressInfo;

    /** 印章图片头部 */
    @ApiModelProperty(value = "印章图片头部")
    private String imgHeader;

    /** 印章图片底部 */
    @ApiModelProperty(value = "印章图片底部")
    private String imgCenter;

    /** 设备id */
    @ApiModelProperty(value = "设备id")
    private String deviceId;

    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private String deptId;

    /** 部门组织类型 1：部门 ，2：分公司 (当id唯一时，可不填) */
    @ApiModelProperty(value = "部门组织类型 1：部门 ，2：分公司 (当id唯一时，可不填)")
    private String deptOrgType;

    /** 印章使用人 */
    @ApiModelProperty(value = "印章使用人")
    private List sealerList;

    /** 用印主体 */
    @ApiModelProperty(value = "用印主体")
    private String useSealSubject;

    /** 用印用户信息 */
    @ApiModelProperty(value = "用印用户信息")
    private String sealManager;
}
