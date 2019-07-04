package com.wqyp.framework.web.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

    /**
     * 全局参数
     * @return
     */
    private List<Parameter> parameter() {
        List<Parameter> params = new ArrayList<>();
        Parameter parameter = new ParameterBuilder().name("Authorization")
                .description("Authorization Bearer token")
                .modelRef(new ModelRef("wqyp-access-token"))
                .parameterType("header")
                .required(false).build();
        params.add(parameter);
        return params;

    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wqyp"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameter());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微券优品服务接口")
                .description("来自基础服务配置")
                .termsOfServiceUrl("")
                .contact("backend@www.wqyp.shop")
                .version("1.0")
                .build();
    }
}
