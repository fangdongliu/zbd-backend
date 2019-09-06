package cn.fdongl.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author zm
 * @Date 2019/9/6 12:26
 * @Version 1.0
 **/
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("cn.fdongl"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build().pathMapping("/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("指标点计算系统")
                .description("后端接口API文档")
                .contact("zm")
                .version("1.0.0")
                .build();
    }
}