package vip.xiaonuo.biz.modular.epc.safety.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelLedger;

/**
 * @author yong.ye
 * @date 2023/9/5 17:25
 */

@Getter
@Setter
public class EpcPersonnelLedgerVo extends EpcPersonnelLedger {

    /** 持证情况 */
    @ApiModelProperty(value = "持证情况", position = 25)
    private String certificateStage;
}
