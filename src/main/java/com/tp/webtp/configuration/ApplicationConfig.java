package com.tp.webtp.configuration;

import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Tag;
import com.tp.webtp.model.JaxbList;
import com.tp.webtp.model.Tags;
import com.tp.webtp.resolver.JsonViewResolver;
import com.tp.webtp.resolver.XmlViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.tp.webtp")
@PropertySource(value = { "classpath:application.properties" })
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(jsonViewResolver());
        resolvers.add(jspViewResolver());
        resolvers.add(xmlViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver xmlViewResolver() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(String.class,Serie.class, Tags.class,Tag.class, JaxbList.class);

        return new XmlViewResolver(marshaller);
    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }


    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/*")
//                .addResourceLocations("classpath:/*").setCachePeriod(3000);
//    }
}
