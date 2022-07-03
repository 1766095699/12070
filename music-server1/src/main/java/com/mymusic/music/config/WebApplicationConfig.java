//package com.mymusic.music.config;
//
//import com.mymusic.music.interceptor.MyInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ClassName WebApplicationConfig
// * @Description 拦截器的配置
// * @Author 86183
// * @Date2022-01-0710:43
// * @Version 1.0
// **/
//@Configuration
//public class WebApplicationConfig implements WebMvcConfigurer {
//    private static List<String> list = new ArrayList<>();
//    @Bean
//    public MyInterceptor myInterceptor(){
//        return new MyInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        list.add("/user/friends");
//        list.add("/user/login");
////        list.add("/user/refreshToken/**");
//
//        registry.addInterceptor(myInterceptor()).addPathPatterns("/user/**","/**/*").excludePathPatterns(list);
//    }
//}
