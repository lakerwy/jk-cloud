package vip.xiaonuo.procure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import vip.xiaonuo.common.runner.AppStartupListener;

@Slf4j
@RestController
@EnableSwagger2WebMvc
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "vip.xiaonuo")
@SpringBootApplication(scanBasePackages = {"vip.xiaonuo"})
@EnableScheduling
public class SnowyIotApp {

    /* 解决druid 日志报错：discard long time none received connection:xxx */
    static {
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SnowyIotApp.class);
        springApplication.run(args);
        log.info(">>> {}", SnowyIotApp.class.getSimpleName().toUpperCase() + " STARTING SUCCESS");
    }

    /**
     * 首页
     *
     * @author xuyuxiang
     * @date 2022/7/8 14:22
     **/
    @GetMapping("/")
    public String index() {
        return "WELCOME";
    }

    @Bean
    public AppStartupListener appStartupListener(){
        return new AppStartupListener();
    }

}
