package vip.xiaonuo.biz.modular.emp.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;

/**
 * @author yong.ye
 * @date 2023/9/21 15:18
 *
 * 绩效考评 统计结果
 */


@Getter
@Setter
public class BizEmpPerformanceVo {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 2)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 3)
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 4)
    private String orgId;

    @ApiModelProperty(value = "机构名称", position = 10)
    private String orgName;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 5)
    private String year;

    /** 员工第一季度-评估得分 */
    @ApiModelProperty(value = "员工第一季度-评估得分", position = 16)
    private Double SeasonScore1;

    /** 员工第一季度-绩效等级 */
    @ApiModelProperty(value = "员工第一季度-绩效等级", position = 17)
    private Double SeasonLevel1;

    /** 员工第二季度-评估得分 */
    @ApiModelProperty(value = "员工第二季度-评估得分", position = 16)
    private Double SeasonScore2;

    /** 员工第二季度-绩效等级 */
    @ApiModelProperty(value = "员工第二季度-绩效等级", position = 17)
    private Double SeasonLevel2;

    /** 员工第三季度-评估得分 */
    @ApiModelProperty(value = "员工第三季度-评估得分", position = 16)
    private Double SeasonScore3;

    /** 员工第三季度-绩效等级 */
    @ApiModelProperty(value = "员工第三季度-绩效等级", position = 17)
    private Double SeasonLevel3;

    /** 员工第四季度-评估得分 */
    @ApiModelProperty(value = "员工第四季度-评估得分", position = 16)
    private Double SeasonScore4;

    /** 员工第四季度-绩效等级 */
    @ApiModelProperty(value = "员工第四季度-绩效等级", position = 17)
    private Double SeasonLevel4;
}
