package com.fnmain.service.impl;

import com.fnmain.pojo.Time;

import java.time.LocalDateTime;

public class TimeUtils {
    public static <T extends Time> void setTime(T t) {
        t.setCreateTime(LocalDateTime.now());
        t.setUpdateTime(LocalDateTime.now());
    }
}
