package com.example.bcp.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * swagger配置类
 *
 *
 * @since 2024/11/30
 */
@Configuration
//@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("BCP文档")
                        .contact(new Contact())
                        .description("BCP项目接口文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0许可").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("github项目链接")
                        .url("https://github.com/Seriendipity/BCP"));
    }
}
