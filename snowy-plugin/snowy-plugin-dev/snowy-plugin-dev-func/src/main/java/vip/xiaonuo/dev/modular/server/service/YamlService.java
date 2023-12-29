package vip.xiaonuo.dev.modular.server.service;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vip.xiaonuo.dev.modular.server.param.YamlProperties;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/28 22:39
 */
@Service
@Slf4j
public class YamlService {
    @Resource
    TemplateEngine templateEngine;

    private final String YML_TEMPLATE = "application";

//    @Value("${output.file.path}")
//    private String outputFilePath;

//    public YamlService(TemplateEngine templateEngine) {
//        this.templateEngine = templateEngine;
//    }

    public void generateYamlFile(YamlProperties yamlProperties) {
        // 创建 Thymeleaf 上下文
        Context context = new Context();
        context.setVariable("rabbit.topic.exchange",yamlProperties.getRabbitTopicExchange());
//        TemplateEngine templateEngine = SpringUtil.getBean("templateEngine");
        // 渲染模板
        String renderedYaml = templateEngine.process("YML_TEMPLATE", context);

        // 写入文件
        try {
            File outputFile = new File("AA");
            FileCopyUtils.copy(renderedYaml.getBytes(StandardCharsets.UTF_8), outputFile);
        } catch (IOException e) {
            // 处理异常...
        }
    }
}

