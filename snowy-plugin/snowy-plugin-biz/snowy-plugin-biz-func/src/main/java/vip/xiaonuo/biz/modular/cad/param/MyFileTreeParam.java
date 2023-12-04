package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 根据项目Id,当前用户Id，获取【设计区】树状目录
 */
@Getter
@Setter
public class MyFileTreeParam {

    /** 项目Id */
    @ApiModelProperty(value = "项目Id",position = 1)
    private String prjId;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 2)
    private String userId;
}
