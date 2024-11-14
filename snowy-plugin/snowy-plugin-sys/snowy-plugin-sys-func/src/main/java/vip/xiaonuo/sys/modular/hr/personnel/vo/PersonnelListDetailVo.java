package vip.xiaonuo.sys.modular.hr.personnel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileCert;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonnelListDetailVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 图像 */
    @ApiModelProperty(value = "图像", position = 2)
    private String image;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 4)
    private String highestEducation;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 5)
    private String nation;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 10)
    private String idNumber;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 11)
    private String origo;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 12)
    private Integer age;

    /** 入职司龄(自动算) */
    @ApiModelProperty(value = "入职司龄(自动算)", position = 14)
    private String enterage;

    /** 现居住地址 */
    @ApiModelProperty(value = "现居住地址", position = 16)
    private String address;

    /** 工作城市 */
    /** 养老保险个人编号 */

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 18)
    private String phone;

    /** E-Mail */
    @ApiModelProperty(value = "E-Mail", position = 19)
    private String email;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 20)
    private String dept;

    /**
     * 第一学历
     */
    @ApiModelProperty(value = "第一学历", position = 30)
    private String firstDegree;

    /**
     * 学历类型
     */
    @ApiModelProperty(value = "第一学历学历类型", position = 31)
    private String firstDegreeClass;

    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "第一学历毕业时间", position = 32)
    private String firstGraduateDate;

    /**
     * 毕业院校
     */
    @ApiModelProperty(value = "第一学历毕业院校", position = 33)
    private String firstGraduateSchool;

    /**
     * 第一学历专业
     */
    @ApiModelProperty(value = "第一学历专业", position = 34)
    private String firstMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "第一学历备注", position = 35)
    private String firstNote;

    /**
     * 第二学历
     */
    @ApiModelProperty(value = "第二学历", position = 36)
    private String secondDegree;

    /**
     * 第二学历类型
     */
    @ApiModelProperty(value = "第二学历类型", position = 37)
    private String secondDegreeClass;

    /**
     * 第二学历毕业时间
     */
    @ApiModelProperty(value = "第二学历毕业时间", position = 38)
    private String secondGraduateDate;

    /**
     * 第二学历毕业院校
     */
    @ApiModelProperty(value = "第二学历毕业院校", position = 39)
    private String secondGraduateSchool;

    /**
     * 第二学历专业
     */
    @ApiModelProperty(value = "第二学历专业", position = 40)
    private String secondMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "第二学历备注", position = 41)
    private String secondNote;

    /**
     * 第三学历
     */
    @ApiModelProperty(value = "第三学历", position = 42)
    private String thirdDegree;

    /**
     * 第三学历类型
     */
    @ApiModelProperty(value = "第三学历类型", position = 43)
    private String thirdDegreeClass;

    /**
     * 第三学历毕业时间
     */
    @ApiModelProperty(value = "第三学历毕业时间", position = 44)
    private String thirdGraduateDate;

    /**
     * 第三学历毕业院校
     */
    @ApiModelProperty(value = "第三学历毕业院校", position = 45)
    private String thirdGraduateSchool;

    /**
     * 第三学历专业
     */
    @ApiModelProperty(value = "第三学历专业", position = 46)
    private String thirdMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "第三学历备注", position = 47)
    private String thirdNote;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 48)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @ApiModelProperty(value = "证书信息列表", position = 80)
    private List<PersonnelFileCert> personnelCertList;

    @ApiModelProperty(value = "职称信息列表", position = 81)
    private List<PersonnelFileTitle> personnelTitleList;
}
