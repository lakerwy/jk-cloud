package vip.xiaonuo.pan.modular.fileCenter.office.entities;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/18 10:33
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
