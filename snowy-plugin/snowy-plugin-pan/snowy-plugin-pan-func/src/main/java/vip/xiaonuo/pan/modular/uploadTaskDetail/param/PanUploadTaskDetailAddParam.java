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
package vip.xiaonuo.pan.modular.uploadTaskDetail.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 云盘-上传任务详情表添加参数
 *
 * @author mayike
 * @date  2023/09/08 16:35
 **/
@Getter
@Setter
public class PanUploadTaskDetailAddParam {

    /** 当前分片数 */
    @ApiModelProperty(value = "当前分片数", position = 3)
    private Integer chunkNumber;

    /** 当前分片大小 */
    @ApiModelProperty(value = "当前分片大小", position = 4)
    private Long chunkSize;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 5)
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 6)
    private String filePath;

    /** md5唯一标识 */
    @ApiModelProperty(value = "md5唯一标识", position = 7)
    private String identifer;

    /** 文件相对路径 */
    @ApiModelProperty(value = "文件相对路径", position = 8)
    private String relativePath;

    /** 文件总分片数 */
    @ApiModelProperty(value = "文件总分片数", position = 9)
    private Integer totalChunks;

    /** 文件总大小 */
    @ApiModelProperty(value = "文件总大小", position = 10)
    private Long totalSize;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;

}
