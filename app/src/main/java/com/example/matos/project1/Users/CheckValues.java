package com.example.matos.project1.Users;

import android.util.Patterns;

class CheckValues {

    private static boolean checkEmail = false;

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

