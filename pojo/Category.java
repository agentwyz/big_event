package com.fnmain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category implements Time {

    @NotNull(groups = Update.class)
    private Integer id;

    @NotEmpty
    private String categoryName;

    @NotEmpty
    private String categoryAlias;
    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    public interface Add extends Default {

    }

    public interface Update extends Default {

    }

    //如果说某个校验项, 没有指定分组默认属于Default分组
    //分组之间可以继承
    //A extends B, 那么A中

}
