package com.imtuandung.viettivi.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.imtuandung.viettivi.R;

/**
 * Created by Dung Nguyen on 12/25/15.
 */
public class PlayerFragment extends AppCompatActivity {

    String title = "";
    String description = "";
    String link = "";
    VideoView vdvPlayer;
    RelativeLayout rlDetails;
    TextView tvTitle, tvDesciption;
    RelativeLayout.LayoutParams paramsNormal, paramsFullscreen;
    AdView mAdView;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player);

        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vdvPlayer = (VideoView) findViewById(R.id.vdvPlayer);
        rlDetails = (RelativeLayout) findViewById(R.id.rlDetails);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesciption = (TextView) findViewById(R.id.tvDescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("link");
            title = extras.getString("title");
            description = extras.getString("description");
        }

        if (link != "" && link != null) {

            Uri uri = Uri.parse(link); // Declare your url here.
            vdvPlayer.setMediaController(new MediaController(this));
            vdvPlayer.setVideoURI(uri);
            vdvPlayer.requestFocus();
            vdvPlayer.start();
        }

        if (title != "" && title != null & description != "" && description != null) {
            tvDesciption.setText(description);
            tvTitle.setText(title);
        }

        paramsNormal = (RelativeLayout.LayoutParams) vdvPlayer.getLayoutParams();

        paramsFullscreen = new LayoutParams(paramsNormal);
        paramsFullscreen.setMargins(0, 0, 0, 0);
        paramsFullscreen.height = ViewGroup.LayoutParams.MATCH_PARENT;
        paramsFullscreen.width = ViewGroup.LayoutParams.MATCH_PARENT;
        paramsFullscreen.addRule(RelativeLayout.CENTER_IN_PARENT);

        //Admob ads

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    /**
     * Catch rotate screen event
     */
    @SuppressLint("NewApi")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            getSupportActionBar().hide();

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);


            vdvPlayer.setLayoutParams(paramsFullscreen);
            rlDetails.setVisibility(View.GONE);
            mAdView.setVisibility(ViewGroup.GONE);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            getSupportActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

            vdvPlayer.setLayoutParams(paramsNormal);
            rlDetails.setVisibility(View.VISIBLE);
            mAdView.setVisibility(ViewGroup.VISIBLE);
        }
    }


}
