package com.solution.naukarimanthan.utils;


/**
 * Author       : Arvindo Mondal
 * Created on   : 29-12-2018
 * Email        : arvindomondal@gmail.com
 */
public enum Validation {

    INSTANCE;

    /**
     * This will never Override nor be instanciate
     */
    Validation(){}

    private String isValidJobTitle(Object object){
        String result = "false";
        if(object != null){
            if(object instanceof String) {
                String value = (String) object;
                if(value.equals("")){
                    return result;
                }
            }
        }

        return result;
    }

    public static String validMobile(String number){
        return "ghhgh";
    }

    public static String validEmail(String email){
        return "kjkjkjj";
    }

    public static String[] validPassword(){
        return null;
    }

    public static String[] validName(){
        return null;
    }
}
