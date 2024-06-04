package vip.xiaonuo.dev.modular.es.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/31 10:57
 */
@Data
@Document(indexName = "file_data")
public class FileData {
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "文件ID", position = 1)
    @Id
    private String fileId;

    /** 存储引擎 */
    @ApiModelProperty(value = "存储引擎", position = 2)
    @Field(type = FieldType.Keyword)
    private String engine;

    /** 存储桶 */
    @ApiModelProperty(value = "存储桶", position = 3)
    @Field(type = FieldType.Keyword)
    private String bucket;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 4)
    @Field(type = FieldType.Text)
    private String name;

    /** 文件后缀 */
    @ApiModelProperty(value = "文件后缀", position = 5)
    @Field(type = FieldType.Keyword)
    private String suffix;

    /** 文件的对象名（唯一名称） */
    @ApiModelProperty(value = "文件的对象名（唯一名称）", position =6)
    @Field(type = FieldType.Keyword)
    private String objName;

    /** 文件存储路径 */
    @ApiModelProperty(value = "文件存储路径", position = 7)
    @Field(type = FieldType.Keyword)
    private String storagePath;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 8)
    @Field(type = FieldType.Keyword)
    private String extJson;

    @Field(name = "attachment.content",type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "文件内容", position = 9)
    @TableField(exist = false)
    private String data;

    /**
     * 创建时间
     */
    private Date createTime;


    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 1001)
    @Field(type = FieldType.Keyword)
    private String createUser;

    @ApiModelProperty(value = "创建人", position = 1002)
    @Field(type = FieldType.Text)
    private String createUserName;
}
