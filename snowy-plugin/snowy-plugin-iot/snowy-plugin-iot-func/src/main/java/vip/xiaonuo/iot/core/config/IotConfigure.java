package vip.xiaonuo.iot.core.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 14:03
 * iot 物联网相关配置
 */
@Configuration
public class IotConfigure {

    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;


    /**
     * API文档分组配置
     * @return
     */
    @Bean(value = "iotDocApi")
    public Docket iotDocApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("iot功能插件")
                        .description("iot功能插件")
                        .termsOfServiceUrl("https://www.xiaonuo.vip")
                        .contact(new Contact("SNOWY_TEAM","https://yun.jianke.co", "jianke@foxmail.com"))
                        .version("2.0.0")
                        .build())
                .globalResponseMessage(RequestMethod.GET, CommonResult.responseList())
                .globalResponseMessage(RequestMethod.POST, CommonResult.responseList())
                .groupName("iot功能插件")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("vip.xiaonuo.iot"))
                .paths(PathSelectors.any())
                .build().extensions(openApiExtensionResolver.buildExtensions("iot功能插件"));
    }
}
