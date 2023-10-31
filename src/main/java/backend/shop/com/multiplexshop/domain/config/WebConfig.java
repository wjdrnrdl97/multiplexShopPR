package backend.shop.com.multiplexshop.domain.config;

import backend.shop.com.multiplexshop.domain.config.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final FileProperties fileProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/error","/img/**","/js/**","/"
                        ,"/login","/logout","/join","/api/**", "/uploadfile/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadfile/**").addResourceLocations(fileProperties.getWebpath());
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
