package com.music.musicauth.controller;

import com.music.musicauth.entity.Admin;
import com.music.musicauth.service.AdminService;
import com.music.musiccommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
public class AdminController {
    @Autowired
    private AdminService userService;

    @PostMapping("/getUser")
    public R getUser(@RequestBody List<Integer>list){
        System.out.println(list);
        System.out.println(list.getClass());
        System.out.println("ok");
        return R.ok("test ok");
    }

    /**
     * 注册接口
     * @param admin
     * @return
     */
    @PostMapping("/regist")
    public Object regist(@RequestBody Admin admin){
        return userService.regist(admin);
    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Object login(@RequestBody Admin user){
        System.out.println("进入login方法");
        System.out.println(user);
        R res = (R) userService.login1(user);
        if (Objects.isNull(res)){
            return R.error();
        }
        return res;
    }

    /**
     * 权限测试
     * @return
     */
    @PreAuthorize("hasAuthority('system:dept:list')")
    @GetMapping("/login1")
    public Object login1(){
        return R.ok();
    }

    //不给PreAuthorize的话默认就是都可以访问
    @PreAuthorize("hasAuthority('system:dept:list1')")
    @GetMapping("/logout")
    public R logout(@RequestHeader(value = "token")String token){
//        System.out.println("aaa"+request.getAttribute("token"));
        return (R) userService.logout(token);
    }

}
