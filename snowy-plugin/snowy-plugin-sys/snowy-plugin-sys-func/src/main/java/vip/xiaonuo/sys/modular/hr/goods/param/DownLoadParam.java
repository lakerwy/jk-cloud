package vip.xiaonuo.sys.modular.hr.goods.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 物资领用申请 - 下载
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
@Getter
@Setter
public class DownLoadParam {

    /** 领用部门 */
    @ApiModelProperty(value = "领用部门", position = 3)
    private String deptName;

    /** 领用人 */
    @ApiModelProperty(value = "领用人", position = 5)
    private String optName;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /** SN */
    @ApiModelProperty(value = "SN", position = 10)
    private String sn;

    /** 领用部门负责人 */
    @ApiModelProperty(value = "领用部门负责人", position = 11)
    private String deptLeader;

    /** 行政管理部负责人 */
    @ApiModelProperty(value = "行政管理部负责人", position = 12)
    private String xzLeader;


    /** 物资清单 */
    @ApiModelProperty(value = "物资清单", position = 100)
    private List<GoodsItemEditParam> goodsApplyList;

}
