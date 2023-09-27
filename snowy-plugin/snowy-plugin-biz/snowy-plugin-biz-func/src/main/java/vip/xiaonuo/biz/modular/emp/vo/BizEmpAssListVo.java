package vip.xiaonuo.biz.modular.emp.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/9/27 9:34
 */

@Getter
@Setter
public class BizEmpAssListVo {

    /** 考核对象-用户Id */
    @ApiModelProperty(value = "考核对象-用户Id", position = 8)
    private String userId;

    /** 考核对象-用户名 */
    @ApiModelProperty(value = "考核对象-用户名", position = 9)
    private String userName;

    /** 考核对象-组织Id */
    @ApiModelProperty(value = "考核对象-组织Id", position = 10)
    private String orgId;

    /** 考核对象-组织名称 */
    @ApiModelProperty(value = "考核对象-组织名称", position = 11)
    private String orgName;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 12)
    private String year;
}
