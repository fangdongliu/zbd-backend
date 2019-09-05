package cn.fdongl.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@SpringBootApplication
public class PointApplication {

    public static void main(String[] args) {

        SpringApplication.run(PointApplication.class, args);
    }

}
