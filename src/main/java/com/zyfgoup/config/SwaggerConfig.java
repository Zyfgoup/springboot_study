package com.zyfgoup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Zyfgoup
 * @Date 2020/5/29 14:20
 * @Description
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {

    @Bean
    public Docket docket(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zyfgoup.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //显示的信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("Zyfgoup","Zyfgoup.github.io","619122012@qq.com");

        return new ApiInfoBuilder()
                .title("测试Swagger2")
                .description("这是描述")
                .termsOfServiceUrl("这是什么？")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
