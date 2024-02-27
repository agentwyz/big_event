package com.fnmain.intercepors;

import com.fnmain.utils.JwtUtil;
import com.fnmain.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //令牌的验证
        String token = request.getHeader("Authorization");

        try {
            //从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            //表示一个值
            String redisToken = operations.get(token);

            if (redisToken == null) {
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);
            //将业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //移除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
