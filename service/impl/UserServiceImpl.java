package com.fnmain.service.impl;

import com.fnmain.mapper.UserMapper;
import com.fnmain.pojo.User;
import com.fnmain.service.UserService;
import com.fnmain.utils.Md5Util;
import com.fnmain.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public void register(String userName, String password) {
        //密码需要加密
        userMapper.add(userName, Md5Util.getMD5String(password));
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl, Integer id) {
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd ) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}
