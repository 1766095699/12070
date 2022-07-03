//package com.mymusic.music.config;
//
//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
//import lombok.extern.java.Log;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.logging.Logger;
//
///**
// * @ClassName XxlJobConfig
// * @Description TODO
// * @Author 86183
// * @Date2022-03-1615:46
// * @Version 1.0
// **/
//@Configuration
//@Slf4j
//public class XxlJobConfig {
//    @Value("${xxl.job.admin.addresses}")
//    private String adminAddresses;
//    @Value("${xxl.job.executor.appname}")
//    private String appname;
//
//    @Bean
//    public XxlJobSpringExecutor xxlJobExecutor() {
////        logger.info(">>>>>>>>>>> xxl-job config init.");
//        System.out.println("xxl-job config init.");
//        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
//        System.out.println(adminAddresses+" "+appname);
//        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
//        xxlJobSpringExecutor.setAppname(appname);
//        xxlJobSpringExecutor.setAddress("http://ljh17660.viphk.91tunnel.com");
////        xxlJobSpringExecutor.setPort(port);
////        xxlJobSpringExecutor.setAccessToken(accessToken);
////        xxlJobSpringExecutor.setLogPath(logPath);
////        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
//        return xxlJobSpringExecutor;
//    }
//}
