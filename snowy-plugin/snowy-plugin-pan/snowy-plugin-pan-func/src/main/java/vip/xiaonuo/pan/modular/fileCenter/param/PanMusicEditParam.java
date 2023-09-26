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
package vip.xiaonuo.pan.modular.fileCenter.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 云盘-音乐实体类编辑参数
 *
 * @author mayike
 * @date  2023/09/08 14:13
 **/
@Getter
@Setter
public class PanMusicEditParam {

    /** ID */
    @ApiModelProperty(value = "ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** ALBUM */
    @ApiModelProperty(value = "ALBUM", position = 3)
    private String album;

    /** ALBUM_ARTIST */
    @ApiModelProperty(value = "ALBUM_ARTIST", position = 4)
    private String albumArtist;

    /** ALBUM_IMAGE */
    @ApiModelProperty(value = "ALBUM_IMAGE", position = 5)
    private String albumImage;

    /** ARTIST */
    @ApiModelProperty(value = "ARTIST", position = 6)
    private String artist;

    /** COMMENT */
    @ApiModelProperty(value = "COMMENT", position = 7)
    private String comment;

    /** COMPOSER */
    @ApiModelProperty(value = "COMPOSER", position = 8)
    private String composer;

    /** COPYRIGHT */
    @ApiModelProperty(value = "COPYRIGHT", position = 9)
    private String copyright;

    /** ENCODER */
    @ApiModelProperty(value = "ENCODER", position = 10)
    private String encoder;

    /** 文件id */
    @ApiModelProperty(value = "文件id", position = 11)
    private String fileId;

    /** GENRE */
    @ApiModelProperty(value = "GENRE", position = 12)
    private String genre;

    /** 歌词 */
    @ApiModelProperty(value = "歌词", position = 13)
    private String lyrics;

    /** ORIGINAL_ARTIST */
    @ApiModelProperty(value = "ORIGINAL_ARTIST", position = 14)
    private String originalArtist;

    /** PUBLICER */
    @ApiModelProperty(value = "PUBLICER", position = 15)
    private String publicer;

    /** TITLE */
    @ApiModelProperty(value = "TITLE", position = 16)
    private String title;

    /** TRACK */
    @ApiModelProperty(value = "TRACK", position = 17)
    private String track;

    /** TRACK_LENGTH */
    @ApiModelProperty(value = "TRACK_LENGTH", position = 18)
    private Float trackLength;

    /** URL */
    @ApiModelProperty(value = "URL", position = 19)
    private String url;

    /** YEAR */
    @ApiModelProperty(value = "YEAR", position = 20)
    private String year;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 21)
    private String extJson;

}
