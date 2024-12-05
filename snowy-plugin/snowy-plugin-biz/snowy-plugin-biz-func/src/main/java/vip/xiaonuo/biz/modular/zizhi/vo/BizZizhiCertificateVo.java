package vip.xiaonuo.biz.modular.zizhi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.zizhi.entity.BizZizhiCertificate;

@Getter
@Setter
public class BizZizhiCertificateVo extends BizZizhiCertificate {

    /** 证书有效期标识（正常|已过期|临近六个月） */
    @ApiModelProperty(value = "证书有效期标识（正常|已过期|临近六个月）", position = 20)
    private String certStage;
}
