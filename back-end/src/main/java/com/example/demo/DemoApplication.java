package com.example.demo;

//import com.example.demo.config.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
//        System.out.println("start");
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Autowired
//    private JwtInterceptor jwtInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/covid")
//                .excludePathPatterns("/logout")
//                .excludePathPatterns("/kcallback")
//                .excludePathPatterns("/ncallback");
//
//    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("jwt-auth-token");
    }
}
