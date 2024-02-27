package com.fnmain.controller;

import com.fnmain.pojo.Result;
import com.fnmain.pojo.User;
import com.fnmain.service.UserService;
import com.fnmain.utils.JwtUtil;
import com.fnmain.utils.Md5Util;
import com.fnmain.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public Result register(@Pattern(regexp="^\\S{5,16}$") String username, @Pattern(regexp="^\\S{5,16}$") String password) {

        User user = userService.findByUserName(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        }

        return Result.error("用户名称已将被占用");
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp="^\\S{5,16}$") String username, @Pattern(regexp="^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);

        if (user == null) {
            return Result.error("用户不存在");
        }

        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            //登陆成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());

            String token = JwtUtil.genToken(claims);

            //将token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        return Result.success(userService.findByUserName(username));
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        System.out.println(user.getEmail());
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");


        userService.updateAvatar(avatarUrl, id);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params, @RequestHeader("Autorization") String token) {

        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        //原来的密码是否正确
        //调用userService根据用户名字拿到原密码, 再和old
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);

        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确");
        }

        //newPwd和rePwd
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }


        //2.调用server完成密码更新
        userService.updatePwd(newPwd);

        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }

}
