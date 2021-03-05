package br.com.luizalabs.clientsapi.configuration.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


private const val BASE_PACKAGE = "br.com.luizalabs.clients"

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration::class)
class SwaggerConfiguration {
    
    @Bean
    fun swaggerClientsApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("clients-api")
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(PathSelectors.regex("/api/.*"))
            .build()
            .apiInfo(
                ApiInfoBuilder()
                    .version("base-version")
                    .title("Clients API")
                    .description("Clients API documentation").build()
            )
    }
}