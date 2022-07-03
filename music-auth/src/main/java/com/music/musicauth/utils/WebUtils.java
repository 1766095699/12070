package com.music.musicauth.utils;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName WebUtils
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1115:47
 * @Version 1.0
 **/
public class WebUtils {
    public static String renderString(HttpServletResponse response,String string){
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
