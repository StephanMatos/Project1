package com.example.matos.project1.Users;

import android.util.Patterns;

class CheckValues {

    private static boolean checkUsername = false;
    private static boolean checkEmail = false;
    private static boolean checkPassword = false;
    //static String[] suffix = {".com",".org",".net",".int",".edu",".gov",".AD",".AE",".AF",".AG",".AI",".AL",".AM",".AN",".AO",".AQ",".AR",".AS",".AT",".AU",".AW",".AZ",".BA",".BB",".BD",".BE",".BF",".BG",".BH",".BI",".BJ",".BM",".BN",".BO",".BR",".BS",".BT",".BV",".BW",".BT",".TP",".TR",".TT",".TV",".TW",".TZ",".UA",".UG",".UK",".UM",".US",".UY",".UZ",".VA",".VC",".VE",".VG",".VI",".VN",".VU","WF",".WS",".YE",".YT",".YU",".ZA",".ZM",".ZR",".ZW"};

    static boolean checkUsername(String username){

        return ((username.length() < 21) && (username.length() > 3)) && isSpecialCharacter(username);

    }

    static boolean checkEmail(String email){
        int length = email.length();
        if(length < 100 && length > 6){
            checkEmail = true;
        }else{
            return false;
        }
        checkEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return checkEmail;
    }

    static boolean checkPassword(String password){

        return ((password.length() < 21) && (password.length() > 7)) && isSpecialCharacter(password);
    }

    private static boolean isSpecialCharacter(String string){
        boolean legal = false;
        for(int i = 0; i < string.length(); i++){

            legal = string.substring(i,i+1).matches("[^a-zA-Z0-9<>,;.:_!#¤%&/()=?+*£$€{}]");

           if(legal){
              return !legal;
           }
        }

        return !legal;
    }


}

