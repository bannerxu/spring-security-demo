package top.banner.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title("前端API")
                .description("前端API相关接口文档")
                .contactName("Banner Xu")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
