package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.sub.entity.*;

import java.util.List;

@Getter
@Setter
public class SubPersonnelVo extends SubPersonnel {

    /** 签约信息集合 */
    @ApiModelProperty(value = "签约信息集合")
    private List<SubPersonnelRegister> subRegisters;

    /** 职称信息集合 */
    @ApiModelProperty(value = "职称信息集合")
    private List<SubPersonnelTitle> subTitles;

    /** 证书信息集合 */
    @ApiModelProperty(value = "证书信息集合")
    private List<SubPersonnelCert> subCerts;

    /** 学历信息集合 */
    @ApiModelProperty(value = "学历信息集合")
    private List<SubPersonnelDegree> subDegrees;
}
