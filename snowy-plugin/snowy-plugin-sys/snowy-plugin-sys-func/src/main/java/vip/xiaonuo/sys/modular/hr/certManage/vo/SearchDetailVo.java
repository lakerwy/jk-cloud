package vip.xiaonuo.sys.modular.hr.certManage.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 综合搜索列表-详情页-分四块（注册|培训|职称|花名册）
 */
@Getter
@Setter
public class SearchDetailVo {

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

    /**
     * 花名册信息
     */
    @ApiModelProperty(value = "花名册信息", position = 4)
    public UserInfo userInfo;

}
