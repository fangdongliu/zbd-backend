package cn.fdongl.authority;

import cn.fdongl.authority.util.AppUserDetailArgumentResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("cn.fdongl.authority.mapper")
public class AuthorityApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AppUserDetailArgumentResolver());
    }

}