package com.lemutugi;

import com.lemutugi.audit.AuditorWareImpl;
import com.lemutugi.config.AppProperties;
import com.lemutugi.config.ApplicationContextProvider;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@OpenAPIDefinition(info = @Info(title = "Lemutugi API", version = "2.0", description = "Lemutugi Rest API Documentation"))
@SecurityScheme(name = "lemutugi-api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class Lemutugi {
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorWareImpl();
    }

    @Bean
    public static ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lemutugi.class, args);
    }

}
