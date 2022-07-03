package com.music.musicauth.security.handler;

import com.alibaba.fastjson.JSON;
import com.music.musicauth.utils.WebUtils;
import com.music.musiccommon.utils.R;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1115:43
 * @Version 1.0
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        R res = R.error(HttpStatus.SC_UNAUTHORIZED,"用户认证失败请查询登录");
        String json = JSON.toJSONString(res);
        WebUtils.renderString(httpServletResponse, json);

    }
}
