package com.example.matos.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CostumGridAdapter extends BaseAdapter {

    private int[] icons;

    private Context context;

    private LayoutInflater inflater;



    public CostumGridAdapter(Context context, int[] icons) {
        this.context = context;
        this.icons = icons;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View gridView = convertView;

        if (convertView == null) {

            gridView = inflater.inflate(R.layout.costum_dialog_profile_pictures, null);

        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);

        icon.setImageResource(icons[position]);




        return gridView;
    }
}
