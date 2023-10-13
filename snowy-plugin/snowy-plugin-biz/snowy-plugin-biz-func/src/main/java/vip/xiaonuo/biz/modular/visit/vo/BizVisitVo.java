package vip.xiaonuo.biz.modular.visit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.visit.entity.BizVisit;
import vip.xiaonuo.biz.modular.visit.entity.BizVisitList;

import java.util.List;

@Getter
@Setter
public class BizVisitVo extends BizVisit {

    @ApiModelProperty(value = "外访-计划表", position = 50)
    private List<BizVisitList> paramList;
}
