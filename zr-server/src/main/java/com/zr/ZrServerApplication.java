package com.zr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zr.mapper")
public class ZrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZrServerApplication.class, args);
    }

}
