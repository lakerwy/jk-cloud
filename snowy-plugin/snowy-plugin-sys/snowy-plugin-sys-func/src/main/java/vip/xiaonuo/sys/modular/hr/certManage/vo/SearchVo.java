package vip.xiaonuo.sys.modular.hr.certManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 综合搜索 - 结果集 - 分三个结果栏【注册类|培训类|职称类】
 */

@Getter
@Setter
public class SearchVo {

    /**
     * 注册类证书集合
     */
    @ApiModelProperty(value = "注册类证书集合", position = 1)
    public List<CertManageVo> reCertList;

    /**
     * 培训类证书集合
     */
    @ApiModelProperty(value = "培训类证书集合", position = 2)
    public List<CertManageVo> trCertList;

    /**
     * 职称类证书集合
     */
    @ApiModelProperty(value = "职称类证书集合", position = 3)
    public List<CertManageVo> otherCertList;
}
