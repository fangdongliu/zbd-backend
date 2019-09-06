package cn.fdongl.authority;

import cn.fdongl.authority.tool.AppUserDetailArgumentResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@MapperScan("cn.fdongl.authority.dao")
public class AuthorityApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AppUserDetailArgumentResolver());
    }
}