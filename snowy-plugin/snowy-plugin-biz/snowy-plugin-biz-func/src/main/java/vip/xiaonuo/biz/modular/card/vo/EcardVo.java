package vip.xiaonuo.biz.modular.card.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.card.entity.CardImages;
import vip.xiaonuo.biz.modular.card.entity.Ecards;

import java.util.List;

/**
 * 电子名片信息+图片资源信息
 */

@Getter
@Setter
public class EcardVo extends Ecards {

    /** 图片资源集合 */
    @ApiModelProperty(value = "图片资源集合", position = 20)
    private List<CardImages> images;

}
