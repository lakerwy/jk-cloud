package vip.xiaonuo.sys.modular.dept.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 部门报销公告 信息集合
 */
@Getter
@Setter
public class ImportNotice {

    /** 导入集合 */
    @ApiModelProperty(value = "导入集合", position = 1)
    private List<ExpenseNoticeAddParam> importData;
}
