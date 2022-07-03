package com.music.musicauth;

import com.music.musicauth.dao.AdminDao;
import com.music.musicauth.dao.SysMenuDao;
import com.music.musicauth.entity.Admin;
import com.music.musicauth.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName MapperTest
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1115:25
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MusicAuthApplication.class)
public class MapperTest {
    @Autowired
    private SysMenuDao menuDao;
    @Test
    public void test1(){
        System.out.println("11");
        System.out.println(menuDao.selectPermsByUserId(7L));
    }
    @Autowired
    private AdminService adminService;
    @Test
    public void test111(){
        Date date = new Date();
        System.out.println(date);
    }
    @Test
    public void addUser(){

        for(int i=0;i<40000000;i++){
            String username = getRandomCharacter();
            String password = getRandomCharacter();
            System.out.println(username);
            System.out.println(password);
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            adminService.regist(admin);
            if(i%1000==0) System.out.println(i);
        }

    }
    private static String getRandomCharacter(){
        int len = (int)(Math.random()*20+1);
        if(len<4)len = 4;
        StringBuilder username = new StringBuilder();
        for(int i=0;i<len;i++){
            int c= (int)(Math.random()*26);
            int now  = (int)(Math.random()*100)+1;
            if(now%3==0)
                username.append((char)(c+'a'));
            else if(now%3==1)
                username.append((char)(c+'A'));
            else
                username.append(((int)(Math.random()*10)));
        }
        return username.toString();

    }

    public static void main(String[] args) {
        System.out.println(getRandomCharacter());
    }

    //随机生成汉字
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }
}
