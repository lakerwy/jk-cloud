package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工资报表-基本信息
 */
@Data
public class GzExportInfo {
    /** 工资发放公司 */
    @ApiModelProperty(value = "工资发放公司", position = 1)
    private String company;

    /** 所属年 */
    @ApiModelProperty(value = "所属年", position = 2)
    private String year;

    /** 所属月 */
    @ApiModelProperty(value = "所属月", position = 3)
    private String month;

    /** 模板文件路径 */
    @ApiModelProperty(value = "模板文件路径", position = 4)
    private String templatePath;


    public GzExportInfo(String company, String year, String month, String templatePath) {
        this.company = company;
        this.year = year;
        this.month = month;
        this.templatePath = templatePath;
    }
}
