package com.music.thirdpart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MessageController
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1916:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("message")
public class MessageController {
    @GetMapping("getMessage")
    public String getMessage() throws InterruptedException {
        Thread.sleep(2000);
        return "apfo";
    }
}
