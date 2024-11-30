package com.example.bcp.config;

//import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
    @Bean
    public Docket docket() {
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置基本信息
                .apiInfo(apiInfo())
                ;
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "米大傻", // 作者姓名
                "https://blog.csdn.net/xhmico?type=blog", // 作者网址
                "777777777@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("多加辣-接口文档") // 标题
                .description("众里寻他千百度，慕然回首那人却在灯火阑珊处") // 描述
                .termsOfServiceUrl("https://www.baidu.com") // 跳转连接
                .version("1.0") // 版本
                .license("Swagger-的使用(详细教程)")
                .licenseUrl("https://blog.csdn.net/xhmico/article/details/125353535")
                .contact(contact)
                .build();
    }

}
