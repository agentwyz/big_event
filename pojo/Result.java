package com.fnmain.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<DataType> {
    private Integer stateCode;
    private String message;
    private DataType dataType;


    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    public static Result success() {
        return new Result(0, "操作成功", null);
    }


    public static Result error(String message) {
        return new Result(1, message, null);
    }

}
