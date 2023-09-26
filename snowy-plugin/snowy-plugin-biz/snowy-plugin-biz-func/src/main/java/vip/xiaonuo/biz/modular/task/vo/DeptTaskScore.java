package vip.xiaonuo.biz.modular.task.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.task.entity.BizTask;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/10 10:53
 */
@Getter
@Setter
public class DeptTaskScore {

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 2)
    private Double totalScore;

    @ApiModelProperty(value = "年度任务集合", position = 3)
    private List<BizTask> bizTaskList;

}
