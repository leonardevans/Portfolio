package com.lemutugi;

import com.lemutugi.audit.AuditorWareImpl;
import com.lemutugi.config.AppProperties;
import com.lemutugi.config.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Lemutugi {
    @Bean
    public AuditorAware<Long> auditorAware(){
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
