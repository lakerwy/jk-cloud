/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.jzscmohurd.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * BIZ_JZSC_MOHURD编辑参数
 *
 * @author mayike
 * @date  2024/05/20 17:18
 **/
@Getter
@Setter
public class BizJzscMohurdSyncParam {

    @ApiModelProperty(value = "白名单", position = 1)
    private List<String> whiteList;

    @ApiModelProperty(value = "人员列表", position = 2)
    private List<JzUser> users;

    @ApiModelProperty(value = "同步类型(0 组织架构 1 表格)",position = 3)
    private int type;

    @Setter
    @Getter
    public static class JzUser{
        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "身份证号")
        private String idNumber;
    }

}
