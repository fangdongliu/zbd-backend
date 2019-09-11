package cn.fdongl.point;

import cn.fdongl.authority.util.AppUserDetailArgumentResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@ComponentScan(value = "cn.fdongl")
@MapperScan("cn.fdongl.point.mapper")
@EnableAsync
public class PointApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        //LongAdder
        SpringApplication.run(PointApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AppUserDetailArgumentResolver());
    }
}