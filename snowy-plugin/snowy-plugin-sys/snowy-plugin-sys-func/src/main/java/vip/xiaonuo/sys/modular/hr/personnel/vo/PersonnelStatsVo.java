package vip.xiaonuo.sys.modular.hr.personnel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 人事档案报表统计
 */
@Getter
@Setter
public class PersonnelStatsVo {

    @ApiModelProperty(value = "部门iD", position = 1)
    private String deptId;

    @ApiModelProperty(value = "部门/分公司名称", position = 2)
    private String dept;

    @ApiModelProperty(value = "总人数", position = 3)
    private int countNum;

    @ApiModelProperty(value = "男总人数", position = 4)
    private int countBoy;

    @ApiModelProperty(value = "女总人数", position = 5)
    private int countGirl;

    @ApiModelProperty(value = "博士总人数", position = 6)
    private int countDoctor;

    @ApiModelProperty(value = "硕士总人数", position = 7)
    private int countMaster;

    @ApiModelProperty(value = "本科总人数", position = 8)
    private int countBachelor;

    @ApiModelProperty(value = "专科及以下总人数", position = 9)
    private int countAssociate;

    @ApiModelProperty(value = "28岁以下总人数", position = 10)
    private int countAge1;

    @ApiModelProperty(value = "28-35岁总人数", position = 11)
    private int countAge2;

    @ApiModelProperty(value = "35-45岁总人数", position = 12)
    private int countAge3;

    @ApiModelProperty(value = "45岁以上总人数", position = 13)
    private int countAge4;

    @ApiModelProperty(value = "入职一年以下总人数", position = 14)
    private int countWorkAge1;

    @ApiModelProperty(value = "入职1-3年总人数", position = 15)
    private int countWorkAge2;

    @ApiModelProperty(value = "入职3-5年总人数", position = 16)
    private int countWorkAge3;

    @ApiModelProperty(value = "入职5年及以上总人数", position = 17)
    private int countWorkAge4;

}
