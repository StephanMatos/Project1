package com.example.matos.project1.Menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.SearchListActivity;

public class FragmentSearch extends Fragment {

    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {

        final ConstraintLayout advancedLayoutView = view.findViewById(R.id.advancedSearchView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setFocusable(false);

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchtext = searchView.getQuery().toString();

                String username = SavedValues.getInstance().getEmail();
                searchtext = searchtext.replace(" ", "%25");
                String address = "products.php?username=" + username + "&searchtext=" + searchtext;
                startSearchActivity(address);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


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
            @SuppressLint("SetTextI18n")
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

            @SuppressLint("SetTextI18n")
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
                String username = SavedValues.getInstance().getEmail();
                String address = "products.php?username=" + username + "&toprated=1";
                startSearchActivity(address);
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
                String username = SavedValues.getInstance().getEmail();
                String address = "products.php?username=" + username + "&topfavoritized=1";
                startSearchActivity(address);
            }
        });


    }

    private void startSearchActivity(String address){
        Intent intent = new Intent(getActivity(), SearchListActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("searchtext", searchView.getQuery().toString());

        Pair p1 = Pair.create(searchView, ViewCompat.getTransitionName(searchView));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1);

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onResume() {

        super.onResume();
        searchView.setFocusable(false);
    }


}
