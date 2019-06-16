package com.example.matos.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.matos.project1.Users.ForgotPasswordActivity;
import com.example.matos.project1.Users.LoginActivity;

public class AlertDialogBoxes {

    private static AlertDialogBoxes alertDialogBoxes;
    public static Dialog returnDialog;
    public static Dialog dialog;

    public static AlertDialogBoxes getInstance(){
        if(alertDialogBoxes == null){
            alertDialogBoxes = new AlertDialogBoxes();
        }
        return alertDialogBoxes;
    }

    public static void alertDialogOnUI(final String title, final String text, final Activity activity){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(activity)
                        .setTitle(title)
                        .setMessage(text)
                        .setNeutralButton("OK",null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }


    public static void alertDialogOnUIContext(final String title, final String text, final Context context){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(text)
                        .setNeutralButton("OK",null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public static void AlertDialog(final String title, final String text, final String buttonText,final Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton(buttonText,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void resetDialogOnUI(final Context context, final String title, final String email){

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogview_passreset);
                dialog.setTitle(title);
                TextView emailView = dialog.findViewById(R.id.textViewEmail);
                emailView.setText(email);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                ForgotPasswordActivity.dialog = dialog;


                dialog.show();

            }
        });
    }

    public static void passwordResetOnUI(final Context context,final String title,final String text){

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(text)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public static void cancelPasswordReset(final Context context,final String title, final String text){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void finnishactivity(final Activity activity){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        });
    }

}
