package net.kaczmarzyk.example;

import java.util.ArrayList;
import java.util.List;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;


@Configuration
@ComponentScan(basePackages="net.kaczmarzyk")
@EnableJpaRepositories
@EnableAutoConfiguration
public class Application extends RepositoryRestMvcConfiguration {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
    
    @Override
    protected List<HandlerMethodArgumentResolver> defaultMethodArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.addAll(super.defaultMethodArgumentResolvers());
        resolvers.add(new SpecificationArgumentResolver());
        return resolvers;
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

