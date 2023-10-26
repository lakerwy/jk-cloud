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
package vip.xiaonuo.pan.modular.fileRight.param;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 网盘权限配置表编辑参数
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 **/
@Getter
@Setter
public class PanFileRightEditParam {

    /** 权限配置详情 集合 */
    @ApiModelProperty(value = "权限配置详情 集合 ", position = 2)
    private List<FileRightEdit> objectRightList;

    /** 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "用户文件Id（UserFile.Id）", position = 1)
    private String userFileId;
}

@Getter
@Setter
class FileRightEdit{
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 4)
    private String objectId;

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 5)
    private Long rightValue;
}
