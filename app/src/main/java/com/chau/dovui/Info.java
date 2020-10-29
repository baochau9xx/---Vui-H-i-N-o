package com.chau.dovui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    ImageView imgInfo;
    TextView txtNameDev, txtMailDev, txtVersion;
    Button btnFacebook, btnYoutube, btnCHPlay;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnFacebook = (Button) findViewById(R.id.btnFace);
        btnYoutube = (Button) findViewById(R.id.btnYTB);
        btnCHPlay = (Button) findViewById(R.id.btnCHPlay);
        imgInfo = (ImageView) findViewById(R.id.info_img);
        txtNameDev = (TextView) findViewById(R.id.info_name_dev);
        txtMailDev = (TextView) findViewById(R.id.info_maildev);
        txtVersion = (TextView) findViewById(R.id.info_ver);


        Animation animation_info_img = AnimationUtils.loadAnimation(Info.this, R.anim.anim_info_img);
        Animation animation_info_namedev = AnimationUtils.loadAnimation(Info.this, R.anim.amin_hello_btnplay);
        Animation animation_info_maildev = AnimationUtils.loadAnimation(Info.this, R.anim.anim_hello_btninfo);
        Animation animation_info_linkme = AnimationUtils.loadAnimation(Info.this, R.anim.anim_info_linkme);
        Animation animation_info_version = AnimationUtils.loadAnimation(Info.this, R.anim.anim_alpha);


        imgInfo.startAnimation(animation_info_img);
        txtNameDev.startAnimation(animation_info_namedev);
        txtMailDev.startAnimation(animation_info_maildev);
        btnFacebook.startAnimation(animation_info_linkme);
        btnYoutube.startAnimation(animation_info_linkme);
        btnCHPlay.startAnimation(animation_info_linkme);
        txtVersion.startAnimation(animation_info_version);


        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation = AnimationUtils.loadAnimation(Info.this, R.anim.amin_hello_btnplay);

                btnFacebook.startAnimation(animation);

                Uri uri = Uri.parse("http://fb.com/chau.sliver");

                Intent openFace = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(openFace);

            }
        });

        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation = AnimationUtils.loadAnimation(Info.this, R.anim.amin_hello_btnplay);

                btnYoutube.startAnimation(animation);

                Uri uri = Uri.parse("https://www.youtube.com/c/nhemusic/videos");

                Intent openYoutube = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(openYoutube);

            }
        });

    }
}