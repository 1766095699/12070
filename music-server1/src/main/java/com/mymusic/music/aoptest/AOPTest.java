package com.mymusic.music.aoptest;

//package com.mymusic.music.aoptest;
//
//import org.springframework.boot.test.context.SpringBootTest;
//
/////**
//// * @ClassName AOPTest
//// * @Description TODO
//// * @Author 86183
//// * @Date2022-01-0615:17
//// * @Version 1.0
//// **/
////指定为Spring环境中的单元测试
//@org.junit.jupiter.api.extension.ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
////指定为SpringBoot环境的单元测试，Application为启动类
//@SpringBootTest(classes = MusicApplicationTests.class)
public class AOPTest {
    public static void main(String[] args) {
        User user1 = new User();
        user1.username = "root1";
        user1.password = "root1";

        User user2 = new User();
        user2.username = "root1";
        user2.password = "root1";
        System.out.println(user1.equals(user2));
        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
    }
}

