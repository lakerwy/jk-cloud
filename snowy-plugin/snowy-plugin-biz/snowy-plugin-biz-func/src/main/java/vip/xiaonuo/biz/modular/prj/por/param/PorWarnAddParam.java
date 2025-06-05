package vip.xiaonuo.biz.modular.prj.por.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录添加参数
 *
 * @author yy
 * @date  2025/06/03 09:41
 **/
@Getter
@Setter
public class PorWarnAddParam {

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 预警级别 */
    @ApiModelProperty(value = "预警级别", position = 3)
    private String warnLevel;

    /** 预警时间 */
    @ApiModelProperty(value = "预警时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date warnTime;

    /** 预警类型 */
    @ApiModelProperty(value = "预警类型", position = 5)
    private String warnType;

    /** 预警内容 */
    @ApiModelProperty(value = "预警内容", position = 6)
    private String warnContent;

    /** 处理时间 */
    @ApiModelProperty(value = "处理时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    /** 处理情况 */
    @ApiModelProperty(value = "处理情况", position = 8)
    private String dealStatus;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 13)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 14)
    private String attach;

}
