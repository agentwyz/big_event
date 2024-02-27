package com.fnmain.service;

import com.fnmain.pojo.User;


public interface UserService {

    User findByUserName(String name);

    void register(String name, String password);

    void update(User user);

    void updateAvatar(String avatarUrl, Integer id);

    void updatePwd(String s);


}
