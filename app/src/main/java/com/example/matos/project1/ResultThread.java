package com.example.matos.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.Users.ForgotPasswordActivity;
import com.example.matos.project1.Users.LoginActivity;
import com.example.matos.project1.Users.ResetPassword;
import com.example.matos.project1.Users.TabLoginFragment;
import com.example.matos.project1.Users.TabSignupFragment;

public class ResultThread {

    public static boolean successLogin, failureLogin,failureSignUp, successRequest, failureRequest, successSignUp,successReset,failureReset, successVerification, failureVerification, network, unknown, exist;

    public static void setBooleans(){
        // Login
        successLogin = false;
        failureLogin = false;

        //Sign Up
        failureSignUp = false;
        successSignUp = false;

        //Request Reset
        successRequest = false;
        failureRequest = false;

        //Verification
        successVerification = false;
        failureVerification = false;

        // Password Reset
        successReset = false;
        failureReset = false;

        // Common
        network = false;
        unknown = false;
        exist = false;

    }



    public static void waitForResults(final boolean signup, final Activity activity, final Context context, final String email,final String password,final boolean auto) {
        setBooleans();
        new Thread(new Runnable() {
            public void run() {

                boolean active = true;

                while(active){
                    try {
                        if(successLogin){
                            if(signup){
                                TabSignupFragment.progressDialog.dismiss();
                                Intent intent = new Intent(activity,HomeActivity.class);
                                activity.startActivity(intent);
                                AlertDialogBoxes.finnishactivity(activity);
                            }else if (auto){
                                Intent intent = new Intent(context,HomeActivity.class);
                                context.startActivity(intent);
                            }else{
                                TabLoginFragment.progressDialog.dismiss();
                                AlertDialogBoxes.finnishactivity(activity);
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                            }
                            active = false;
                        } else if (failureLogin) {
                            if(TabLoginFragment.progressDialog != null){
                                TabLoginFragment.progressDialog.dismiss();
                                AlertDialogBoxes.alertDialogOnUI("Fejl","Forkert email og/eller adgangskode. Prøv igen eller gå til reset password",activity);
                            }else{
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }

                            active = false;

                        }else if (successSignUp) {

                            System.out.println("signup succes");
                            TabLoginFragment tabLoginFragment = new TabLoginFragment();
                            tabLoginFragment.attempt_login(email,password,true,activity);
                            active = false;

                        } else if (failureSignUp) {
                            TabSignupFragment.progressDialog.dismiss();
                            active = false;
                        }else if(successRequest) {

                            active = false;

                        }else if(failureRequest) {

                            AlertDialogBoxes.alertDialogOnUIContext("Fejl", "Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email", context);
                            ForgotPasswordActivity.dialog.dismiss();
                            active = false;

                        }else if(successVerification) {
                            ForgotPasswordActivity.progressDialog.dismiss();
                            Intent New_password = new Intent(context, ResetPassword.class);
                            New_password.putExtra("email",email);
                            context.startActivity(New_password);
                            active = false;
                        }else if(failureVerification) {
                            ForgotPasswordActivity.progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl", "Den indstastede kode stemmer ikke over ens, prøv igen", context);
                            active = false;

                        }else if(successReset){
                            AlertDialogBoxes.passwordResetOnUI(context,"Success","Du kan nu logge ind med dit nye kodeord");
                            ResetPassword.progressDialog.dismiss();
                            active = false;
                        }else if(failureReset){
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Du kan nu logge ind med dit nye kodeord",context);
                            ResetPassword.progressDialog.dismiss();
                            active = false;
                        }else if(network){
                            TabLoginFragment.progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Fejl","Kontroller at telefonen har forbindelse til internettet",activity);
                            active = false;

                        }else if(unknown){
                            TabLoginFragment.progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Ukendt fejl","Prøv igen eller kontakt support",activity);
                            active = false;
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();
    }
}
