package com.music.musicauth.security.handler;

import com.alibaba.fastjson.JSON;
import com.music.musicauth.utils.WebUtils;
import com.music.musiccommon.utils.R;
import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenDeniedHandlerImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-06-1115:51
 * @Version 1.0
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        R res = R.error(HttpStatus.SC_FORBIDDEN,"授权失败,您的权限不足");
        String json = JSON.toJSONString(res);
        WebUtils.renderString(httpServletResponse, json);
    }
}
