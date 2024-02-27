package com.fnmain.validation;

import com.fnmain.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param s 将来需要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false, 表示校验不通过, 如果返回true, 则校验通过
     */

    //在这个方法中提供一个校验规则
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        //提供校验规则
        if (s == null) return false;
        if (s.equals("已发布") || s.equals("草稿")) return true;

        return false;
    }
}
