package com.chau.dovui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Win extends AppCompatActivity {

    Button btnVeMenu;
    ImageView imgWin;
    TextView txtChucMung, txtComeback;

    Integer intMute = 0;

    Animation animation_back_meno, ani_image, ani_chucmung, ani_comeback, ani_button_menu;

    MediaPlayer mediaSoundWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        btnVeMenu = (Button) findViewById(R.id.veMenu);
        imgWin = (ImageView) findViewById(R.id.winImage);
        txtChucMung = (TextView) findViewById(R.id.chucmung);
        txtComeback = (TextView) findViewById(R.id.comeback);

//        NHAN GIA TRI INT MUTE
        Intent getIntMute = new Intent();
        intMute = getIntMute.getIntExtra("key_4", 0);

//        PHAT AM THANH THUA
        if (intMute == 0) {
            mediaSoundWin = MediaPlayer.create(this, R.raw.sound_win_01);
            mediaSoundWin.start();
        }

        ani_image = AnimationUtils.loadAnimation(Win.this, R.anim.anim_info_img);
        imgWin.startAnimation(ani_image);

        ani_chucmung = AnimationUtils.loadAnimation(Win.this, R.anim.amin_hello_btnplay);
        txtChucMung.startAnimation(ani_chucmung);

        ani_comeback = AnimationUtils.loadAnimation(Win.this, R.anim.anim_hello_btninfo);
        txtComeback.startAnimation(ani_comeback);

        ani_button_menu = AnimationUtils.loadAnimation(Win.this, R.anim.anim_info_linkme);
        btnVeMenu.startAnimation(ani_button_menu);


        btnVeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_back_meno = AnimationUtils.loadAnimation(Win.this, R.anim.anim_click_button);

                btnVeMenu.startAnimation(animation_back_meno);

                Intent intent = new Intent(view.getContext(), Hello.class);

                startActivity(intent);

            }
        });

    }
}