package com.example.matos.project1.Users;

import android.util.Patterns;

import java.lang.reflect.Array;

public class CheckValues {

    static boolean checkUsername = false;
    static boolean checkEmail = false;
    static boolean checkPassword = false;
    static String[] suffix = {".com",".org",".net",".int",".edu",".gov",".AD",".AE",".AF",".AG",".AI",".AL",".AM",".AN",".AO",".AQ",".AR",".AS",".AT",".AU",".AW",".AZ",".BA",".BB",".BD",".BE",".BF",".BG",".BH",".BI",".BJ",".BM",".BN",".BO",".BR",".BS",".BT",".BV",".BW",".BT",".TP",".TR",".TT",".TV",".TW",".TZ",".UA",".UG",".UK",".UM",".US",".UY",".UZ",".VA",".VC",".VE",".VG",".VI",".VN",".VU","WF",".WS",".YE",".YT",".YU",".ZA",".ZM",".ZR",".ZW"};

    public static boolean checkUsername(String username){
        int length = username.length();

        if(length < 21 && length > 3){
            checkUsername = true;
        }else {
            checkUsername =  false;
            return checkUsername;
        }
        boolean isValid = isSpecialCharacter(username);
        if(!isValid){
            checkUsername = true;
        }else{
            checkUsername =  false;
            return checkUsername;
        }

        return checkUsername;
    }

    public static boolean checkEmail(String email){
        int length = email.length();
        if(length < 100 && length > 6){
            checkEmail = true;
        }else{
            return false;
        }
        checkEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return checkEmail;
    }

    public static boolean checkPassword(String password){

        int length = password.length();
        if(length > 7 && length < 21){
            checkPassword = true;
        }else{
            checkPassword = false;
            return checkPassword;
        }

        boolean isValid = isSpecialCharacter(password);

        if(!isValid){
            checkPassword = true;
        } else{
            checkPassword = false;
            return checkPassword;
        }

        return checkPassword;
    }

    private static boolean isSpecialCharacter(String string){
        boolean legal = false;
        for(int i = 0; i < string.length(); i++){
            System.out.println("run number : " + i);
            legal = string.substring(i,i+1).matches("[^a-zA-Z0-9<>,;.:_!#¤%&/()=?+*£$€{}]");
            System.out.println(" This is substring letter : "+string.substring(i,i+1) +"This is Legal status : " +legal);
           if(legal){
               return legal;
           }

        }

        return legal;
    }




}

