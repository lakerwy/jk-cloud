package vip.xiaonuo.iot.modular.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/8 14:50
 */
@Data
public class NodeVo {
    private String key;

    private String value;

    private String desc;

    /**
     * 1、项目
     * 2、网关
     * 3、设备
     */
    private int type;

    private List<NodeVo> children;
}
