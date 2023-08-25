package vip.xiaonuo.pan.modular.file.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 15:25
 */
@Api(tags = "云盘文件控制器")
@ApiSupport(author = "jk_team", order = 10)
@RestController
@Validated
public class FileController {
}
