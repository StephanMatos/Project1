package com.example.matos.project1;

public class SavedValues {
    private static SavedValues instance = null;
    public static String email,password;

    public static SavedValues getInstance() {
        if (instance == null) {
            instance = new SavedValues();
        }
        return(instance);
    }

    public void saveEmail(String input){
        email = input;
    }

    public String getEmail(){
        return email;
        //hel
    }

    public void savePassword(String input){
        password = input;
    }

    public String getPassword(){
        return password;
    }

}
