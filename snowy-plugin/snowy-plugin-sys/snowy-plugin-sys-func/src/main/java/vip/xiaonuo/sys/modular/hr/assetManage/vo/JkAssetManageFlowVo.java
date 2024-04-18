package vip.xiaonuo.sys.modular.hr.assetManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageFlow;

import java.util.List;

@Getter
@Setter
public class JkAssetManageFlowVo extends JkAssetManageFlow {
    /** 申请的资产信息 */
    @ApiModelProperty(value = "申请的资产信息", position = 100)
    private List<JkAssetManageRelationInfo> jkAssetManageRelationList;
}
