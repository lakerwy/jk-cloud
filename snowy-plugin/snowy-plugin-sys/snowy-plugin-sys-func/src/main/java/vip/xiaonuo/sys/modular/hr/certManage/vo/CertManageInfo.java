package vip.xiaonuo.sys.modular.hr.certManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;

@Getter
@Setter
public class CertManageInfo extends CertManage {

    /** （证书是否失效，总部|分公司人员离职即证书失效，外部人员证书是否失效由页面自行维护） 状态  0 :有效  1：失效 */
    @ApiModelProperty(value = "（证书是否失效，总部|分公司人员离职即证书失效，外部人员证书是否失效由页面自行维护） 状态  0 :有效  1：失效", position = 100)
    private String userStatus;

}
