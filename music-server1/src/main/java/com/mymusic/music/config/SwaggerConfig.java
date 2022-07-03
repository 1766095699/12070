package com.mymusic.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author 86183
 * @Date2021-09-2012:13
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
//    @Value("${swagger.enable}")
    private boolean enable = true;
    @Bean
    public Docket createDocket(){
        List<Parameter> parameterList = new ArrayList<Parameter>();
        ParameterBuilder accessTokenBuilder = new ParameterBuilder();
        ParameterBuilder refreshTokenBuilder = new ParameterBuilder();
//        accessTokenBuilder.name("authorization").description("swagger调试用 authorization").modelRef(new ModelRef("String"))
//                .parameterType("header").required(false);
//        refreshTokenBuilder.name("refreshToken").description("swagger调试 refreshToken").modelRef(new ModelRef("String"))
//                .parameterType("header").required(false);
//        parameterList.add(accessTokenBuilder.build());//加到List
//        parameterList.add(refreshTokenBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.mymusic.music.controller"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(parameterList)
                .enable(enable);
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("网络课设测试文档")
                .description("网络课设测试文档测试文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}
