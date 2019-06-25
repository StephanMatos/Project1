package com.example.matos.project1.Users;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.Menu.HomeActivity;

public class ResultThread {

    public static boolean successLogin, failureLogin,failureSignUp, successSignUp,successReset,failureReset, successVerification, failureVerification, network, unknown, exist;


    public static void setBooleans(){
        // Login
        successLogin = false;
        failureLogin = false;

        //Sign Up
        failureSignUp = false;
        successSignUp = false;

        //Request Reset
        successReset = false;
        failureReset = false;

        //Verification
        successVerification = false;
        failureVerification = false;

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
                    System.out.println("running in results");
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
                        }else if(successReset) {

                            active = false;

                        }else if(failureReset) {

                            AlertDialogBoxes.alertDialogOnUIContext("Fejl", "Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email", context);
                            active = false;

                        }else if(successVerification) {
                            ForgotPasswordActivity.progressDialog.dismiss();
                            Intent New_password = new Intent(context, ResetPassword.class);
                            New_password.putExtra("email",email);
                            context.startActivity(New_password);
                            active = false;
                        }else if(failureVerification){
                            ForgotPasswordActivity.progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Den indstastede kode stemmer ikke over ens, prøv igen",context);
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
                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();
    }
}
