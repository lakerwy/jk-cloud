package vip.xiaonuo.biz.modular.inslibrary.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsLinkman;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsRecord;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/15 17:06
 */
@Getter
@Setter
public class BizInsLibraryVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 所属行业门类 */
    @ApiModelProperty(value = "所属行业门类", position = 2)
    private String industryCategory;

    /** 所属行业大类 */
    @ApiModelProperty(value = "所属行业大类", position = 3)
    private String subCategory;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称", position = 4)
    private String enterpriseName;

    /** 组织机构代码 */
    @ApiModelProperty(value = "组织机构代码", position = 5)
    private String orgCode;

    /** 企业地址 */
    @ApiModelProperty(value = "企业地址", position = 6)
    private String address;

    /** 关系类型 */
    @ApiModelProperty(value = "关系类型", position = 7)
    private String relationType;

    /** 建科联系人 */
    @ApiModelProperty(value = "建科联系人", position = 8)
    private String jkContactor;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 9)
    private String jkDept;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10)
    private String phone;

    /** 主要特色 */
    @ApiModelProperty(value = "主要特色", position = 11)
    private String mainFeatures;

    /** 与我司互补说明 */
    @ApiModelProperty(value = "与我司互补说明", position = 12)
    private String explainMemo;

    /** 与我司是否合作 */
    @ApiModelProperty(value = "与我司是否合作", position = 13)
    private String isHz;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 15)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 17)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 18)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 19)
    private String updateUser;

    /** 联络人 */
    @ApiModelProperty(value = "联络人", position = 20)
    private List<BizInsLinkman> linkMans;

    /** 拜访记录 */
    @ApiModelProperty(value = "拜访记录", position = 21)
    private List<BizInsRecord> records;
}
