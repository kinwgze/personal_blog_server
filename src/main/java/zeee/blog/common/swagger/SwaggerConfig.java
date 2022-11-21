package zeee.blog.common.swagger;

import io.swagger.annotations.Api;
import io.swagger.models.Contact;
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
 * @author wz
 * @date 2022/11/8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                /* 扫描的包路径
                    RequestHandlerSelectors.any()   为任何接口生成API文档
                    RequestHandlerSelectors.none()  不生成任何接口文档
                    RequestHandlerSelectors.basePackage("com.neutech.swagger2study")  只为这个包下生成接口文档
                    RequestHandlerSelectors.withClassAnnotation(Api.class)  为有@Api注解的Controller生成API文档
                    RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class) 为有@ApiOperation注解的方法生成API文档
                 */
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // PathSelectors.ant("/api/**") 过滤路径 只扫描/api/**的接口 生成接口文档
                .paths(PathSelectors.any())
                .build();
    }


    //swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger中文文档")
                .description("zeeew")
                .version("1.0.0")
                .build();
    }
}
