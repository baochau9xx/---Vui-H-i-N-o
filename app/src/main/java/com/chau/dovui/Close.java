package com.chau.dovui;

    /*

    Nguyen Phuc Bao Chau - 08/09/20

    */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Close extends AppCompatActivity {

    Button btnRestart;
    TextView txtDiemTong;

    Integer intMute = 0;

    MediaPlayer mediaSoundClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);

//        ANH XA
        btnRestart = (Button) findViewById(R.id.restart);
        txtDiemTong = (TextView) findViewById(R.id.txtDiemCuoiCung);

//        NHAN DIEU KIEN AM THANH
        Intent intentMute = getIntent();
        intMute = intentMute.getIntExtra("key_2", 0);

//        PHAT AM THANH THUA
        if (intMute == 0) {
            mediaSoundClose = MediaPlayer.create(this, R.raw.sound_gameover_01);
            mediaSoundClose.start();
        }

//        NHAN DIEM TU BEN PLAY GAME
        Intent intent = getIntent();
        Integer score = intent.getIntExtra("key_1", 0);

//        HIEN DIEM
        txtDiemTong.setText(score.toString());

//        NHAN NUT CHOI LAI
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PlayGame.class);
                intent.putExtra("key_3", intMute);
                startActivity(intent);

            }
        });

    }

}