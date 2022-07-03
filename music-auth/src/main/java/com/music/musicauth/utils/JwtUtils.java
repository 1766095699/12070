package com.music.musicauth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
@Slf4j
public class JwtUtils {

    //常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
//    public static final long EXPIRE = 1000*40; //token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥


    //生成token字符串的方法
    public static String getJwtToken(String username){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        log.warn(new Date(System.currentTimeMillis() + EXPIRE).toString());
        return JwtToken;
    }

    //生成token字符串的方法
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)  //设置token主体部分 ，存储用户信息
                .claim("username", nickname)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
            log.warn(new Date(System.currentTimeMillis() + EXPIRE).toString());
        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    public static String getUserId(String token){
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.get("id").toString();
    }
    public static String getUsername(String token){
        String userId=null;
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.get("username").toString();
    }



    public static void main(String[] args) {
//        String token  = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2NTQ4Mjc2MjksImV4cCI6MTY1NDgyNzY2OSwiaWQiOiIxIiwibmlja25hbWUiOiJyb290In0.BusIB9zgyWy7EkLbYosemMo5ldLsGV4W7lu3ehPSlJc";
//        System.out.println(JwtUtils.getJwtToken("1", "root"));
//        System.out.println(JwtUtils.checkToken(token));//检查token是否过期
//        System.out.println(JwtUtils.getUsername(token));//根据token获取数据,假如token过期,会直接报错提示且获取数据失败
//        JwtUtils.
        long s = System.currentTimeMillis();
        PasswordEncoder pw = new BCryptPasswordEncoder();
        System.out.println(pw.encode("root"));
//        System.out.println(pw.matches("root", "$2a$10$rrHTuacXJa1dbhaVWbH1E.zN7dRZXAiLHkHZTlhyX4DOoy3sgPmJW"));
        System.out.println(System.currentTimeMillis()-s);

    }
}
