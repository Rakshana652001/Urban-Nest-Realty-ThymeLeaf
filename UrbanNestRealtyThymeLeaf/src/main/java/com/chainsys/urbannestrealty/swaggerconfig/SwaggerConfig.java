//package com.chainsys.urbannestrealty.swaggerconfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.common.base.Predicate;
//
//import springfox.documentation.RequestHandler;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig
//{
//    @Bean
//    public Docket api()
//    {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis((Predicate<RequestHandler>) RequestHandlerSelectors
//                        .basePackage("com.chainsys.urbannestrealty.controller"))
//                .paths((Predicate<String>) PathSelectors.any()).build();
//    }
//}