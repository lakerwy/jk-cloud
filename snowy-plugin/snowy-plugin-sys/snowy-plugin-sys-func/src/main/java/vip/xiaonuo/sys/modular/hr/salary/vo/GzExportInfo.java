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

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 2)
    private String yearMonth;

    /** 所属年 */
    @ApiModelProperty(value = "所属年", position = 3)
    private String year;

    /** 所属月 */
    @ApiModelProperty(value = "所属月", position = 4)
    private String month;

    /** 模板文件路径 */
    @ApiModelProperty(value = "模板文件路径", position = 5)
    private String templatePath;

    /** 表头 - 标题 */
    @ApiModelProperty(value = "表头 - 标题", position = 6)
    private String title;

    /** 每个sheet打印条数 */
    @ApiModelProperty(value = "每个sheet打印条数", position = 7)
    private Integer pageSize;

    public GzExportInfo(String company, String yearMonth, String year, String month, String templatePath, String title,Integer pageSize) {
        this.company = company;
        this.yearMonth = yearMonth;
        this.year = year;
        this.month = month;
        this.templatePath = templatePath;
        this.title = title;
        this.pageSize = pageSize;
    }
}
