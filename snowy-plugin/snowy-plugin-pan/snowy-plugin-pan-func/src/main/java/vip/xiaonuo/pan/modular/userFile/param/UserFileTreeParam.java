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
package vip.xiaonuo.pan.modular.userFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 文件主表添加参数
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
@Setter
public class UserFileTreeParam {

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID", required = true ,position = 1)
    private String id;

    @ApiModelProperty(value = "目录类型（0 个人网盘 1 群组网盘  2 epc特有网盘（仅在epc中使用））",  required = true,position = 2)
    private Integer foldType;

    /** 文件夹类型 */
    @ApiModelProperty(value = "文件夹类型(0 个人文件夹 1、群组文件夹 2 初始化模板文件夹)集合 前端不用传",position = 3)
    private List<Integer> foldTypes;

    @ApiModelProperty(value = "用户id 前端不用传", position = 4)
    private String userId;

    @ApiModelProperty(value = "组织id 前端不用传", position = 5)
    private List<String> orgIds;

}
