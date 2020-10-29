package com.chau.dovui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Hello extends AppCompatActivity {

    Data data;

    Button btnInfo;
    ImageView imgPikaHello;

    Integer intMute = 0;
    Integer flag = 0;

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


//        NHAN DIEU KIEN AM THANH
        Intent getIntMute = getIntent();
        intMute = getIntMute.getIntExtra("key_9", 0);

//        DATABASE
        data = new Data(this, "dataDiem", null, 1);

        String queryCreateTable = "Create Table if not exists DiemCao (ID Integer primary key UNIQUE, DiemCao integer, SoundEffect integer, SoundBack integer)";
        data.AddData(queryCreateTable);

        String queryKhoiTao = "Insert Into DiemCao values (1, 0, 0, 0)";

        String getSoLan = "Select MAX(ID) from DiemCao";
        Cursor curSoLan = data.GetData(getSoLan);

        while (curSoLan.moveToNext())
        {
            if (curSoLan.getInt(0) != 1)
            {
                data.AddData(queryKhoiTao);
            }
        }

        String getMute = "Select SoundEffect from DiemCao";
        Cursor cursor = data.GetData(getMute);
        while (cursor.moveToNext())
        {
            Integer ahi = cursor.getInt(0);
            intMute = ahi;
        }

//        END DATABASE

        btnInfo = (Button) findViewById(R.id.btnAbout);
        imgPikaHello = (ImageView) findViewById(R.id.pika_hello);

        final Button btnPlayGame = (Button) findViewById(R.id.btnPlay);

        Animation animation_hello = AnimationUtils.loadAnimation(Hello.this, R.anim.anim_hello_activity);
        Animation animation_hello_btnplay = AnimationUtils.loadAnimation(Hello.this, R.anim.amin_hello_btnplay);
        Animation animation_hello_btninfo = AnimationUtils.loadAnimation(Hello.this, R.anim.anim_hello_btninfo);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click_sound_02);

        imgPikaHello.startAnimation(animation_hello);

        btnPlayGame.startAnimation(animation_hello_btnplay);
        btnInfo.startAnimation(animation_hello_btninfo);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent openAbout = new Intent(view.getContext(), Info.class);
                openAbout.putExtra("key_3", intMute);

                Animation animation = AnimationUtils.loadAnimation(Hello.this, R.anim.anim_click_button);

                if (intMute == 0)
                {
                    mediaPlayer.start();
                }

                btnInfo.startAnimation(animation);

                startActivityForResult(openAbout, 0);

            }
        });


        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 1;

                Intent playGame = new Intent(view.getContext(), PlayGame.class);
                playGame.putExtra("key_3", intMute);

                Animation animation = AnimationUtils.loadAnimation(Hello.this, R.anim.anim_click_button);

                if (intMute == 0)
                {
                    mediaPlayer.start();
                }

                btnPlayGame.startAnimation(animation);

                startActivity(playGame);

            }
        });


    }

}