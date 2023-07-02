package com.projects7.config;

import com.fasterxml.classmate.TypeResolver;
import com.projects7.dto.PersonDto;
import com.projects7.dto.PersonDtoInput;
import com.projects7.security.PersonDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(SpringDataRestConfiguration.class)
public class SwaggerUIConfig {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(PersonDto.class),
                        typeResolver.resolve(PersonDtoInput.class),
                        typeResolver.resolve(PersonDetails.class),
                        typeResolver.resolve(UsernameNotFoundException.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.projects7.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);

    }
}