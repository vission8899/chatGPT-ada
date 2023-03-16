package com.vission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({"com.vission.*"})
@EnableSwagger2
@EnableOpenApi
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
