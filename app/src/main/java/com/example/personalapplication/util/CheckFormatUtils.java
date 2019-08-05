package com.example.personalapplication.util;

public class CheckFormatUtils {

    public static boolean checkUsername(String username) {
        //用户名以字母开头，只能包含数字、字母、下划线，长度为6-16位
        String regex = "^[a-zA-z][0-9a-zA-Z_]{5,15}$";
        return username.matches(regex);
    }

    public static boolean checkPassword(String password) {
        //密码 6-16位数字和字母的组合
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return password.matches(regex);
    }
}
