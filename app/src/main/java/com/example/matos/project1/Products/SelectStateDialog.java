package com.example.matos.project1.Products;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.matos.project1.R;

import java.util.List;

public class SelectStateDialog extends Dialog {

    int productID;
    List<String> strings;
    Spinner spinner;
    Boolean b = false;
    Activity a;

    public SelectStateDialog(Activity a, List<String> strings, int productID) {
        super(a);
        strings.add(0, "Select method");
        this.a = a;
        this.strings = strings;
        this.productID = productID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_state);

        spinner = findViewById(R.id.spinner);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(b){
                    RateDialog dialog = new RateDialog(a ,productID);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    cancel();
                }
                b = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }



}
