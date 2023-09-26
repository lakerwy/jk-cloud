package vip.xiaonuo.biz.modular.crm.major.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.majorleader.entity.Majorleader;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/4 10:41
 */

@Getter
@Setter
public class MajorInfoVo {
    /** 主键ID（历史表Major.Id） */
    @TableId
    @ApiModelProperty(value = "主键ID（历史表Major.Id）", position = 1)
    private String id;

    /** 专业名称（历史表Major.MajorName） */
    @ApiModelProperty(value = "专业名称（历史表Major.MajorName）", position = 2)
    private String majorName;

    /** 专业编号（历史表Major.MajorNo） */
    @ApiModelProperty(value = "专业编号（历史表Major.MajorNo）", position = 3)
    private String majorNo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 4)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 7)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "专业-负责人", position = 9)
    private List<Majorleader> majorleaders;
}
