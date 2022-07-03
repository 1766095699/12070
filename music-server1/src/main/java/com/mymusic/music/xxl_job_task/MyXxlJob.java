//package com.mymusic.music.xxl_job_task;
//
//import com.mymusic.music.config.Result;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import static com.xxl.job.core.context.XxlJobHelper.getShardTotal;
//
///**
// * @ClassName MyXxlJob
// * @Description TODO
// * @Author 86183
// * @Date2022-03-1618:02
// * @Version 1.0
// **/
////@Component
////@Slf4j
////public class MyXxlJob {
////    @XxlJob("myJobHandler")
////    public ReturnT<String> execute(String param){
////        int t = XxlJobHelper.getShardIndex()/getShardTotal();//获取分片号
////        log.info("myXXLJOB--------------------"+t);
////        return ReturnT.SUCCESS;
////    }
////}
