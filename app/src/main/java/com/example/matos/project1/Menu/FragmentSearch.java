package com.example.matos.project1.Menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matos.project1.R;

public class FragmentSearch extends Fragment {

    SearchView searchViev;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        final ConstraintLayout advancedLayoutView = view.findViewById(R.id.advancedSearchView);
        searchViev = view.findViewById(R.id.searchView);
        searchViev.setFocusable(false);

        final TextView advancedTextView = view.findViewById(R.id.advancedsearchTextView);
        final TextView ratingNumberTextView = view.findViewById(R.id.ratingNumberTextView);
        final TextView sortingtypeTextView = view.findViewById(R.id.sortingtypeTextView);
        final TextView minmaxTextView = view.findViewById(R.id.minmaxTextView);

        final SeekBar minRatingseekBar = view.findViewById(R.id.minRatingseekBar);
        final SeekBar sortingseekBar = view.findViewById(R.id.sortingseekBar);
        final SeekBar minmaxseekBar = view.findViewById(R.id.minmaxseekBar);

        final CardView top_rated = view.findViewById(R.id.top_rated);
        final CardView top_state = view.findViewById(R.id.top_state);
        final CardView top_scanned = view.findViewById(R.id.top_scanned);
        final CardView top_favorized = view.findViewById(R.id.top_favorized);


        // dummy method
        advancedLayoutView.setVisibility(View.GONE);
        // Advanced search view is visible when the user click on the "advanced"-TextView
        advancedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(advancedLayoutView.isShown()){
                    advancedLayoutView.setVisibility(View.GONE);
                }else {
                    advancedLayoutView.setVisibility(View.VISIBLE);
                }

            }
        });

        // The minimum-rating seekBar starting values
        minRatingseekBar.setProgress(0);
        minRatingseekBar.setMax(6);

        minRatingseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    if (progress >= 0 && progress <= minRatingseekBar.getMax()) {
                        String progressString = String.valueOf(progress);
                        ratingNumberTextView.setText(progressString); // the TextView Reference
                        //seekBar.setSecondaryProgress(progress);
                    }
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // The sorting-type seekBar starting values
        sortingseekBar.setProgress(1);
        sortingseekBar.setMax(2);

        sortingseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    if (progress == 0) {
                        sortingtypeTextView.setText("By rating"); // the TextView Reference
                        minmaxseekBar.setEnabled(true);
                        minmaxTextView.setEnabled(true);
                    } else if (progress == 2) {
                        sortingtypeTextView.setText("By price"); // the TextView Reference
                        minmaxseekBar.setEnabled(true);
                        minmaxTextView.setEnabled(true);
                    }else {
                        sortingtypeTextView.setText("Random"); // the TextView Reference
                        minmaxseekBar.setEnabled(false);
                        minmaxTextView.setEnabled(false);
                    }
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // the min-max seekBar starting values
        minmaxseekBar.setProgress(0);
        minmaxseekBar.setMax(1);
        minmaxseekBar.setEnabled(false);

        minmaxseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser){

                    if (progress == 1){
                        minmaxTextView.setText("Max/min");
                    }else {
                        minmaxTextView.setText("Min/max");
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        top_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        top_scanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        top_favorized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    @Override
    public void onResume() {

        super.onResume();
        searchViev.setFocusable(false);
    }




}
