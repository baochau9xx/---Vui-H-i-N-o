package com.chau.dovui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.chau.dovui.R.drawable.ic_launcher_background;

public class PlayGame extends AppCompatActivity {

    ArrayList<CauHoi> dsCauHoi = new ArrayList<CauHoi>();
    ArrayList<CauHoi> dsCauHoiPhu = new ArrayList<CauHoi>();

    String dapAnChon;
    Integer txtCurrentCore = 0, txtMaxScore = 0;
    Integer txtCurrentHP = 3;
    Integer txtCurrentQuest = 1;
    Integer intMute = 0;
    Integer intVaoGame;
    Integer sizeCauHoi;
    Random random;

    TextView txtDiem, txtDiemCao;
    TextView txtCauHoi;
    TextView txtQuestNumber;

    ImageView imgTym01;
    ImageView imgTym02;
    ImageView imgTym03;

    int radCauHoi;

    Button btnCauA;
    Button btnCauB;
    Button btnCauC;
    Button btnCauD;
    Button btnBack, btnMenu;

    Animation animation_btnDapAn, animation_all, animation_btnBack;

    MediaPlayer mediaCorrect, mediaIncorrect, mediaWin;

    Vibrator vibrator;

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

//        Database
        data = new Data(this, "dataDiem", null, 1);

        String getMute = "Select SoundEffect from DiemCao";
        Cursor cursor = data.GetData(getMute);
        while (cursor.moveToNext())
        {
            intMute = cursor.getInt(0);
        }

        txtMaxScore = GetMaxScore();

//        ANH XA
        AnhXa();

//        NHAN DIEU KIEN AM THANH
        Intent getIntMute = getIntent();
        intMute = getIntMute.getIntExtra("key_3", 0);

//        KHOI TAO CAU HOI
        KhoiTaoCauHoi();

//        RANDOM CAU HOI
        random = new Random();
        sizeCauHoi = dsCauHoi.size();
        radCauHoi = random.nextInt(sizeCauHoi);

//        XU LY TRUNG
        CauHoi cauHoiPhu = dsCauHoi.get(radCauHoi);
        dsCauHoiPhu.add(dsCauHoi.get(radCauHoi));

//        ANIMATION START
        animation_all = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_all);
        txtCauHoi.startAnimation(animation_all);

//        GAN ARRAYLIST SANG GIAO DIEN
        GanCauHoi();

//        CHON AM THANH
        mediaCorrect = MediaPlayer.create(this, R.raw.sound_correct_02);
        mediaIncorrect = MediaPlayer.create(this, R.raw.sound_incorrect_02);
        mediaWin = MediaPlayer.create(this, R.raw.sound_win_01);

//        XU LY CHON DAP AN
        SuKienButton();

    }

    public void AnhXa()
    {

        txtCauHoi = (TextView) findViewById(R.id.txtCauHoi);
        txtDiem = (TextView) findViewById(R.id.txtDiem);
        txtDiemCao = (TextView) findViewById(R.id.txtDiemCao);
        txtQuestNumber = (TextView) findViewById(R.id.txtCauHoiThu);

        btnCauA = (Button) findViewById(R.id.DapAnA);
        btnCauB = (Button) findViewById(R.id.dapAnB);
        btnCauC = (Button) findViewById(R.id.DapAnC);
        btnCauD = (Button) findViewById(R.id.dapAnD);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnMenu = (Button) findViewById(R.id.btnMenu);

        imgTym01 = (ImageView) findViewById(R.id.traiTym01);
        imgTym02 = (ImageView) findViewById(R.id.traiTym02);
        imgTym03 = (ImageView) findViewById(R.id.tym3);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    public void GanCauHoi()
    {

        txtCauHoi.setText(dsCauHoi.get(radCauHoi).cauHoi);
        btnCauA.setText(dsCauHoi.get(radCauHoi).dapAnA);
        btnCauB.setText(dsCauHoi.get(radCauHoi).dapAnB);
        btnCauC.setText(dsCauHoi.get(radCauHoi).dapAnC);
        btnCauD.setText(dsCauHoi.get(radCauHoi).dapAnD);
        txtDiem.setText(txtCurrentCore.toString());
        txtQuestNumber.setText(txtCurrentQuest.toString());
        txtDiemCao.setText(txtMaxScore.toString());

    }

    public void SuKienButton()
    {

//        BUTTON BACK
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnBack = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnBack.startAnimation(animation_btnBack);

                vibrator.vibrate(200);

                Intent intent = new Intent(PlayGame.this, Hello.class)
                        .putExtra("key_9", intMute);
                startActivity(intent);

            }
        });

//        BUTTON MENU
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnBack = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnMenu.startAnimation(animation_btnBack);

                vibrator.vibrate(200);

                final Dialog dialog = new Dialog(PlayGame.this);
                dialog.setContentView(R.layout.dialog_menu);

                final Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
                SwitchCompat swNhacNen = (SwitchCompat) dialog.findViewById(R.id.switch_nhacnen);
                final SwitchCompat swHieuUng = (SwitchCompat) dialog.findViewById(R.id.switch_hieuung);

                dialog.show();

                swHieuUng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vibrator.vibrate(200);
                    }
                });

                if (intMute == 0)
                {
                    swHieuUng.setChecked(true);
                }
                else
                {
                    swHieuUng.setChecked(false);
                }

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSave.startAnimation(animation_btnBack);

                        if (swHieuUng.isChecked())
                        {
                            intMute = 0;
                            SetMute(intMute);
                        }
                        else
                        {
                            intMute = 1;
                            SetMute(intMute);
                        }

                        dialog.hide();
                    }
                });


            }
        });

//        CAU A
        btnCauA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnDapAn = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnCauA.startAnimation(animation_btnDapAn);

                intVaoGame = 1;

                dapAnChon = "A";
                if (dapAnChon.equalsIgnoreCase(dsCauHoi.get(radCauHoi).dapAnDung))
                {
//                    Toast.makeText(PlayGame.this, "Đúng rồi", Toast.LENGTH_SHORT).show();

                    if (intMute == 0)
                    {
                        mediaCorrect.start();
                    }

                    txtCurrentCore = txtCurrentCore + 10;
                    txtDiem.setText(txtCurrentCore.toString());

                    // MAX SCORE
                    if (txtMaxScore < txtCurrentCore)
                    {
                        txtMaxScore = txtCurrentCore;
                        SetMaxScore(txtMaxScore);
                    }

                    txtCurrentQuest = txtCurrentQuest + 1;
                    txtQuestNumber.setText(txtCurrentQuest.toString());

//                    CHIEN THANG
                    if (dsCauHoi.size() == dsCauHoiPhu.size())
                    {
                        Intent intent = new Intent(view.getContext(), Win.class);
                        intent.putExtra("key_4", intMute);
                        startActivity(intent);
                    }
                    else
                    {
//                       DOI CAU HOI
                        NextCauHoi();
                    }

                }
                else
                {

                    vibrator.vibrate(200);

                    if (intMute == 0)
                    {
                        mediaIncorrect.start();
                    }

                    if (txtCurrentHP == 3)
                    {

                        imgTym01.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if (txtCurrentHP == 2)
                        {
                            imgTym02.setBackgroundResource(R.mipmap.heart_02);
                            txtCurrentHP = txtCurrentHP - 1;

                            Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PlayGame.this, "Thua rồi", Toast.LENGTH_SHORT).show();
                            ThuaRoi(view);
                        }
                }

            }
        });

//        CAU B
        btnCauB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnDapAn = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnCauB.startAnimation(animation_btnDapAn);

                intVaoGame = 1;

                dapAnChon = "B";
                if (dapAnChon.equalsIgnoreCase(dsCauHoi.get(radCauHoi).dapAnDung))
                {
//                    Toast.makeText(PlayGame.this, "Đúng rồi", Toast.LENGTH_SHORT).show();

                    if (intMute == 0)
                    {
                        mediaCorrect.start();
                    }

                    txtCurrentCore = txtCurrentCore + 10;
                    txtDiem.setText(txtCurrentCore.toString());

                    // MAX SCORE
                    if (txtMaxScore < txtCurrentCore)
                    {
                        txtMaxScore = txtCurrentCore;
                        SetMaxScore(txtMaxScore);
                    }

                    txtCurrentQuest = txtCurrentQuest + 1;
                    txtQuestNumber.setText(txtCurrentQuest.toString());

//                    CHIEN THANG
                    if (dsCauHoi.size() == dsCauHoiPhu.size())
                    {
                        Intent intent = new Intent(view.getContext(), Win.class);
                        intent.putExtra("key_4", intMute);
                        startActivity(intent);
                    }
                    else
                    {
//                       DOI CAU HOI
                        NextCauHoi();
                    }
                }
                else
                {

                    vibrator.vibrate(200);

                    if (intMute == 0)
                    {
                        mediaIncorrect.start();
                    }

                    if (txtCurrentHP == 3)
                    {
                        imgTym01.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (txtCurrentHP == 2)
                    {
                        imgTym02.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Thua rồi", Toast.LENGTH_SHORT).show();
                        ThuaRoi(view);
                    }
                }

            }
        });

//        CAU C
        btnCauC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnDapAn = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnCauC.startAnimation(animation_btnDapAn);

                intVaoGame = 1;

                dapAnChon = "C";
                if (dapAnChon.equalsIgnoreCase(dsCauHoi.get(radCauHoi).dapAnDung))
                {
//                    Toast.makeText(PlayGame.this, "Đúng rồi", Toast.LENGTH_SHORT).show();

                    if (intMute == 0)
                    {
                        mediaCorrect.start();
                    }

                    txtCurrentCore = txtCurrentCore + 10;
                    txtDiem.setText(txtCurrentCore.toString());

                    // MAX SCORE
                    if (txtMaxScore < txtCurrentCore)
                    {
                        txtMaxScore = txtCurrentCore;
                        SetMaxScore(txtMaxScore);
                    }

                    txtCurrentQuest = txtCurrentQuest + 1;
                    txtQuestNumber.setText(txtCurrentQuest.toString());

//                    CHIEN THANG
                    if (dsCauHoi.size() == dsCauHoiPhu.size())
                    {
                        Intent intent = new Intent(view.getContext(), Win.class);
                        intent.putExtra("key_4", intMute);
                        startActivity(intent);
                    }
                    else
                    {
//                       DOI CAU HOI
                        NextCauHoi();
                    }
                }
                else
                {

                    vibrator.vibrate(200);

                    if (intMute == 0)
                    {
                        mediaIncorrect.start();
                    }

                    if (txtCurrentHP == 3)
                    {
                        imgTym01.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (txtCurrentHP == 2)
                    {
                        imgTym02.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Thua rồi", Toast.LENGTH_SHORT).show();
                        ThuaRoi(view);
                    }
                }

            }
        });

//        CAU D
        btnCauD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation_btnDapAn = AnimationUtils.loadAnimation(PlayGame.this, R.anim.anim_playgame_button_dapan);
                btnCauD.startAnimation(animation_btnDapAn);

                intVaoGame = 1;

                dapAnChon = "D";
                if (dapAnChon.equalsIgnoreCase(dsCauHoi.get(radCauHoi).dapAnDung))
                {
//                    Toast.makeText(PlayGame.this, "Đúng rồi", Toast.LENGTH_SHORT).show();

                    if (intMute == 0)
                    {
                        mediaCorrect.start();
                    }

                    txtCurrentCore = txtCurrentCore + 10;
                    txtDiem.setText(txtCurrentCore.toString());

                    // MAX SCORE
                    if (txtMaxScore < txtCurrentCore)
                    {
                        txtMaxScore = txtCurrentCore;
                        SetMaxScore(txtMaxScore);
                    }

                    txtCurrentQuest = txtCurrentQuest + 1;
                    txtQuestNumber.setText(txtCurrentQuest.toString());

//                    CHIEN THANG
                    if (dsCauHoi.size() == dsCauHoiPhu.size())
                    {
                        Intent intent = new Intent(view.getContext(), Win.class);
                        intent.putExtra("key_4", intMute);
                        startActivity(intent);
                    }
                    else
                    {
//                       DOI CAU HOI
                        NextCauHoi();
                    }

                }
                else
                {

                    vibrator.vibrate(200);

                    if (intMute == 0)
                    {
                        mediaIncorrect.start();
                    }

                    if (txtCurrentHP == 3)
                    {
                        imgTym01.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (txtCurrentHP == 2)
                    {
                        imgTym02.setBackgroundResource(R.mipmap.heart_02);
                        txtCurrentHP = txtCurrentHP - 1;

                        Toast.makeText(PlayGame.this, "Sai mẹ rồi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Thua rồi", Toast.LENGTH_SHORT).show();
                        ThuaRoi(view);
                    }
                }

            }
        });
    }

    public void ThuaRoi(View view)
    {

        if (txtCurrentCore > GetMaxScore())
        {
            SetMaxScore(txtCurrentCore);
        }

        Intent intent = new Intent(view.getContext(), Close.class);
        intent.putExtra("key_1", txtCurrentCore).putExtra("key_2", intMute);

        startActivity(intent);

    }

    public void KhoiTaoCauHoi()
    {
        CauHoi cau001 = new CauHoi(001, "Chơi gì càng chơi càng ra nước?", "Làm chuyện ấy", "Tát nước", "Đánh cờ", "Tắm sông", "C");
        CauHoi cau002 = new CauHoi(002, "Ai là người tạo ra trò này?", "Tao", "Phòng Tôm", "Bảo Châu", "Minh Dự", "C");
        CauHoi cau003 = new CauHoi(003, "Nước nào lớn nhất?", "Anh", "Pháp", "Ấn Độ", "Út", "A");
        CauHoi cau004 = new CauHoi(004, "Động vật nào thông minh nhất?", "Con người", "Con bò", "Con kiến", "Con heo", "C");
        CauHoi cau005 = new CauHoi(005, "Cái gì càng cất càng thấy?", "Cái ví", "Cái lược", "Cái nhà", "Xà phòng", "C");
        CauHoi cau006 = new CauHoi(006, "Người gì đi bằng 3 chân?", "Người nhện", "Người già", "Ngời ta", "Người tuyết", "B");
        CauHoi cau007 = new CauHoi(007, "Cây gì dài nhất?", "Ôỉ", "Tre trăm đót", "Sắt", "Số", "D");
        CauHoi cau008 = new CauHoi(022, "Con gì giống con vượng nhất?", "Con mèo", "Con khỉ", "Vịt con", "Vượng con", "D");
        CauHoi cau009 = new CauHoi(021, "Con gì đầu dê mình ốc?", "Con cốc", "Con tôm", "Con dốc", "Con chó", "C");
        CauHoi cau010 = new CauHoi(010, "Cái gì càng đút vào, càng ra nước?", "Cái ấy ấy", "Cây mía", "Viên phấn", "Cái tay", "B");
        CauHoi cau011 = new CauHoi(011, "Loài chim nào có khả năng bay?", "Sếu", "Đại bàng", "Tất cả ý trên", "Diều hâu", "C");
        CauHoi cau012 = new CauHoi(012, "Cà gì dài nhất?", "Cà lăm", "Cà pháo", "Cà tím", "Cà Mau", "D");
        CauHoi cau013 = new CauHoi(013, "Sợi dây gì dài nhất?", "Dây cáp", "Thần kinh", "Dây điện", "Dây dù", "B");
        CauHoi cau014 = new CauHoi(014, "Bà đó bả chết. Hỏi tại sao bả chết?", "Bò đá", "Té xe", "Bị bệnh", "Muốn chồng", "A");
        CauHoi cau015 = new CauHoi(015, "Uống nước nhớ...?", "Nguồn", "Tiểu", "Người yêu", "Không biết", "B");
        CauHoi cau016 = new CauHoi(016, "Đua gì càng thua càng thắng?", "Đua xe F1", "Đua ngựa", "Đua rùa", "Đua xe đạp chậm", "D");
        CauHoi cau017 = new CauHoi(017, "Con gì chân dài tới nách?", "NGƯỜI MẪU", "Con chó", "HOT girl", "Tinh tinh", "B");
        CauHoi cau018 = new CauHoi(023, "Cái gì càng đẩy càng căng?", "Bơm xe", "Nghĩ bậy", "Ấy ấy", "THUA", "A");
        CauHoi cau019 = new CauHoi(024, "Hoa gì đẹp nhất?", "Hoa hồng", "Tualip", "Hoa dại", "Hoa hậu", "D");
        CauHoi cau020 = new CauHoi(020, "Mật khẩu WIFI khó nhớ nhất là gì?", "conkienchuaolahi", "caigiday", "khongnho", "tatcaytren", "C");
        CauHoi cau021 = new CauHoi(025, "Bỏ ngoài nướng trong, ăn ngoài bỏ trong là gì?", "Trái ngô", "Trứng cá", "Con cá", "Cá trê", "A");
        CauHoi cau022 = new CauHoi(026, "Bệnh gì bác sĩ bó tay?", "Ung thư", "HIV/AIDS", "Nang y", "Gãy tay", "D");
        CauHoi cau023 = new CauHoi(027, "Có 1 đàn chim đậu trên cành, người thợ săn bắn cái rằm. Hỏi chết mấy con?", "MỘT CON", "MƯỜI LĂM", "CHÍN", "3", "B");
        CauHoi cau024 = new CauHoi(030, "Con gì ăn lửa với nước than?", "CON KHÙNG", "Con chó", "Con tàu", "Con bướm xinh", "C");
        CauHoi cau025 = new CauHoi(031, "Con kiến bò lên tai con voi nói gì với con voi mà ngay tức khắc con voi nằm lăn ra chết!!", "Ba mày chết", "Em có thai với anh", "Con voi khùng", "Đồ chó", "B");
        CauHoi cau026 = new CauHoi(032, "Nắng ba năm tôi không bỏ bạn, mưa 1 ngày sao bạn lại bỏ tôi. Là cái gì?", "Cái dù", "Cái bóng", "XE ĐẠP", "Ô tô", "B");
        CauHoi cau027 = new CauHoi(033, "Trên nhấp dưới giật là đang làm gì?", "Làm chuyện ấy", "Bậy quá", "Câu cá", "Đạp xe", "C");
        CauHoi cau028 = new CauHoi(034, "Con gấu trúc ao ước gì mà không bao giờ được?", "Ngủ 3 năm", "Chụp hình màu", "Ăn 3kg trúc", "Xem xiếc", "B");
        CauHoi cau029 = new CauHoi(035, "Tay cầm cục thịt nắn nắn, tay vỗ mông là đang làm gì?", "Ấy ấy", "Bậy bạ", "Coi phim", "Cho con bú", "D");
        CauHoi cau030 = new CauHoi(036, "Cái gì bằng cái vung, vùng xuống ao. Đào chẳng thấy, lấy chẳng được?", "Trăng", "Cái rỗ", "Cái xuồng", "Nắm nồi", "A");
        CauHoi cau031 = new CauHoi(037, "Cái gì trong trắng ngoài xanh trồng đậu trồng hành rồi thả heo vào?", "Cái nồi", "Bánh da lợn", "Bánh chưng", "Cái lồng", "C");
        CauHoi cau032 = new CauHoi(040, "Con gì mang được miếng gỗ lớn nhưng không mang được hòn sỏi?", "Vịt", "MÈO MÉO MEO", "Voi con", "Sông", "D");
        CauHoi cau033 = new CauHoi(041, "Ở Việt Nam, rồng bay ở đâu?", "Long An", "Thăng Long", "Hà Giang", "Đà Nẵng", "B");
        CauHoi cau034 = new CauHoi(042, "Ở Việt Nam, ở đâu con rồng không chết?", "Long An", "Thăng Long", "Hạ Long", "Phan Xích Long", "A");
        CauHoi cau035 = new CauHoi(043, "Cái gì ở giữa 'trời' và 'đất'?", "Mây", "Và", "Núi", "Vietnam Airlines", "B");
        CauHoi cau036 = new CauHoi(044, "Quần rộng nhất là quần gì?", "Quần Jeans", "Quần vải", "Thun", "Đảo", "D");
        CauHoi cau037 = new CauHoi(045, "Con trai có gì quý nhất?", "Cái ấy :))", "Ngọc", "Cái ví", "Cái xe", "B");
        CauHoi cau038 = new CauHoi(046, "Cái gì mà đi thì nằm, đứng cũng nằm, nhưng nằm lại đứng?", "Bàn tay", "Bàn chân", "Bàn tròn", "Tủ giày", "B");
        CauHoi cau039 = new CauHoi(047, "Con đường nào dài nhất?", "Vạn Lý Trường Thành", "Đường đời", "Quốc lộ 62", "Đường mòn HCM", "B");
        CauHoi cau040 = new CauHoi(050, "Một ly đựng đầy nước, làm sao lấy nước dưới đáy mà không đổ nước ra ngoài?", "Đổ ra bát", "Thua", "Dùng ống hút", "Cầm lên", "C");
        CauHoi cau041 = new CauHoi(051, "Cái gì người mua biết, người bán biết, người xài không bao giờ biết?", "Tủ lạnh", "Cái nồi", "CÁI QUẦN", "Quan tài", "D");
        CauHoi cau042 = new CauHoi(052, "Cơ quan quan trọng nhất của phụ nữ là gì?", "Vếu :))", "Hội phụ nữ", "Chổ ấy", "Bậy bạ", "B");
        CauHoi cau043 = new CauHoi(053, "Lịch nào dài nhất?", "Vạn niên", "Lịch sử", "Hõng biết", "Lịch tây", "B");
        CauHoi cau044 = new CauHoi(054, "Tại sao khi bắn súng người ta lại nhắm một mắt?", "Vậy mới trúng", "Nhắm 2 mắt sao thấy?", "Thầy dạy vậy", "Tại quen rồi", "B");
        CauHoi cau045 = new CauHoi(055, "Bên trái đường là nhà xanh, bên phải đường là nhà đỏ. Vậy, nhà Trắng ở đâu?", "Ở giữa", "Mỹ", "Trái", "Phải", "B");
        CauHoi cau046 = new CauHoi(056, "Khi Công Phượng đá phạt đền, anh ta sút vào đâu?", "Thủ môn", "Khung thành", "Góc chữ A", "Trái bóng", "D");
        CauHoi cau047 = new CauHoi(057, "Xã nào đông nhất?", "Tân Đông", "Long An", "Xã hội", "Xã giao", "C");
        CauHoi cau048 = new CauHoi(060, "2 đào trong 2 giờ thì được 1 cái hố. Hỏi 1 người đào trong 1 giờ thì được mấy cái hố?", "Một phần 2", "Một", "Một rưỡi", "1 / 4", "B");
        CauHoi cau049 = new CauHoi(061, "Tại sao con chó không cắn được đuôi của mình.", "Nó khôn", "Đau sao", "Ngắn quá", "Cụt đuôi", "C");
        CauHoi cau050 = new CauHoi(062, "Con gì không gáy ò ó o mà người ta vẫn gọi là gà?", "Gà trống", "Gà con", "Gà cồ", "Vịt lai gà", "B");
        CauHoi cau051 = new CauHoi(063, "Làm sao để cái cân tự cân chính nó?", "Lấy cái cân khác", "Lật nó lại", "Sao mà được", "Leo lên", "B");
        CauHoi cau052 = new CauHoi(064, "Từ gì mà 100% nguời dân Việt Nam đều phát âm sai?", "Từ 'xai'", "Từ 'rên'", "Từ 'nhảy'", "Từ 'sai'", "D");
        CauHoi cau053 = new CauHoi(065, "Có bao nhiêu chữ C trong câu: 'Cơm, canh, cháo gì tớ cũng thích ăn!'", "1", "5", "2", "4", "A");
        CauHoi cau054 = new CauHoi(066, "Cái gì tay trái cầm được còn tay phải cầm không được?", "Cây viết", "Cây chổi", "Chân phải", "Tay phải", "D");
        CauHoi cau055 = new CauHoi(067, "Vịt nào đi bằng hai chân?", "Vịt Donal", "Vịt con", "Vịt Mickey", "Tất cả vịt", "D");
        CauHoi cau056 = new CauHoi(070, "Núi nào bị chặt ra từng khúc?", "Bà Đen", "Thái Sơn", "Thái Nguyệt", "Everest", "B");
        CauHoi cau057 = new CauHoi(071, "Trước khi đỉnh Everest được khám phá, đỉnh núi nào cao nhất thế giới?", "Phú Sĩ", "Núi Đá", "Everest", "Hoàng Liên Sơn", "C");
        CauHoi cau058 = new CauHoi(072, "Con cua đỏ dài 10cm chạy đua với con cua xanh dài 15cm. Con nào về đích trước?", "Đỏ", "Xanh", "Hòa", "Không biết", "B");
        CauHoi cau059 = new CauHoi(073, "Cái gì đen khi bạn mua nó, đỏ khi dùng nó và xám xịt khi vứt nó đi?", "Tivi", "Than", "Bếp gas", "Lò vi sóng", "B");
        CauHoi cau060 = new CauHoi(100, "Cái gì của người con gái lúc nào cũng ẩm ướt?", "Bậy bạ", "Cái ấy", "Lưỡi", "Bàn tay", "C");
        CauHoi cau061 = new CauHoi(101, "Con gì càng to càng nhỏ?", "Chó", "Cua", "Heo", "Gà tre", "B");
        CauHoi cau062 = new CauHoi(102, "Nếu có điều ước, bạn sẽ ước gì?", "Nhiều tiền", "Qua câu này", "Xe mô tô", "Học giỏi", "B");
        CauHoi cau063 = new CauHoi(103, "2 con vịt đi trước 2 con vịt, 2 con vịt đi sau 2 con vịt, 2 con vịt đi giữa 2 con vịt. Hỏi có bao nhiêu con vịt?", "1", "Hai", "Ba", "4", "D");
        CauHoi cau064 = new CauHoi(104, "Buổi tối tôi được nói sẽ làm gì. Buổi sáng tôi làm những gì được nói. Tôi là gì?", "Trợ lý", "Đồng hồ báo thức", "Danh hài", "Mẹ", "B");
        CauHoi cau065 = new CauHoi(105, "Chuột nào đi bằng 2 chân?", "Chuột chuỗi", "Cống", "Mickey", "Donal", "C");
        CauHoi cau066 = new CauHoi(106, "Người đàn ông duy nhất trên thế giới có sữa là ai?", "Ông hai lúa", "Mecer Hamash", "Ông Thọ", "Làm gì có", "C");
        CauHoi cau067 = new CauHoi(107, "Tháng nào ngắn nhất?", "Hai", "Ba", "Năm", "Chín", "B");
        CauHoi cau068 = new CauHoi(108, "Tại sao sư tử ăn thịt sống?", "Không biết nấu", "Loài ăn thịt", "Thèm máu", "Hun dữ", "A");
        CauHoi cau069 = new CauHoi(109, "Có cổ nhưng không có miệng là gì?", "Cái lu", "Cái túi", "Xe đạp", "Cái áo", "D");
        CauHoi cau070 = new CauHoi(110, "Trong 1 cuộc thi chạy, nếu bạn vượt qua người thứ 2 bạn sẽ đứng thứ mấy?", "Thứ 1", "Thứ 2", "Thứ 3", "Chủ nhật", "B");
        CauHoi cau071 = new CauHoi(111, "Sở thú bị cháy, con gì chạy ra đầu tiên?", "Con hổ", "Con nai", "Con voi", "Con người", "D");
        CauHoi cau072 = new CauHoi(112, "5 chia 3 bằng 2 khi nào?", "Học hết 12", "Làm sai", "Bấm máy tính", "Thua", "B");
        CauHoi cau073 = new CauHoi(113, "Vua gọi hoàng hậu bằng gì?", "Hoàng hậu", "Nàng", "Thê tử", "Miệng", "D");
        CauHoi cau074 = new CauHoi(114, "Lúc nào lý tưởng để ăn trưa?", "Sau bữa sáng", "11 giờ", "11giờ rưỡi", "12 giờ", "A");
        CauHoi cau075 = new CauHoi(115, "Ba thằng què đi trước 1 thằng què. Hỏi có mấy thằng què?", "1", "2", "Ba", "4", "A");
        CauHoi cau076 = new CauHoi(116, "Đố em cái gì khi xài thì quăng đi, không xài thì lấy lại?", "Cái ví", "Cái dao", "Mỏ neo", "Tất cả", "C");
        CauHoi cau077 = new CauHoi(117, "1 kg bông gòn và 1 kg sắt, kg nào nặng hơn?", "Bông gòn", "Sắt", "Bằng nhau", "Thua", "C");
        CauHoi cau078 = new CauHoi(118, "Tại sao có những người đi taxi nhưng sao họ lại không trả tiền?", "Quên mang ví", "Quẹt thẻ", "Người già", "Tài xế", "D");
        CauHoi cau079 = new CauHoi(119, "Quả gì có đủ năm châu?", "Ngũ Châu", "Châu Ngũ", "Quả Dưa", "Quả Đất", "D");
        CauHoi cau080 = new CauHoi(120, "Quả gì gang sắt đúc nên, hễ nghe tiếng rú người liền núp mau?", "Bôm", "Bơm", "Bom", "Boom", "D");
        CauHoi cau081 = new CauHoi(121, "Đâu là tên một loại chợ?", "Cốc", "Nháy", "Thằn lằn", "Ếch", "A");
        CauHoi cau082 = new CauHoi(122, "Đâu là tên một bãi biển ở Quảng Bình?", "Đá Lượn", "Đá Bay", "Đá Nhảy", "Đá Chòng", "C");
        CauHoi cau083 = new CauHoi(123, "Đâu là tên một loại bánh ở Huế?", "Sướng", "Khoái", "Mê", "Vui", "B");
        CauHoi cau084 = new CauHoi(124, "Doraemon sợ con gì nhất?", "Chuột", "Mèo", "Chó", "Khủng long", "A");
        CauHoi cau085 = new CauHoi(125, "Điền vào chổ trống: Mạnh vì..., bạo vì tiền", "Gái", "Bạn", "Cơm", "Gạo", "D");
        CauHoi cau086 = new CauHoi(126, "Điền vào chổ trống: Cây ngay không sợ...", "Chết khô", "Bị đốn", "Chết cháy", "Chết đứng", "D");
        CauHoi cau087 = new CauHoi(127, "Ai là người Việt Nam đầu tiên bay vào vũ trụ?", "BẢO CHÂU", "Hồ Chí Minh", "Phạm Tuân", "Đường Tăng", "C");
        CauHoi cau088 = new CauHoi(128, "Ngọn núi nào cao nhất Nhật Bản?", "Phan-xi-păng", "Fansipan", "Phan-xi-măng", "Phú Sĩ", "D");
        CauHoi cau089 = new CauHoi(120, "Đâu là loại hình nghệ thuật của Huế?", "Cải Lương", "Nhã Nhạc Cung Đình", "Ca Trù", "Rap FreeStyle", "B");
        CauHoi cau090 = new CauHoi(120, "Loại hình âm nhạc nổi tiếng Nam Bộ là?", "Cải Lương", "Nhã Nhạc Cung Đình", "Ca Trù", "Rap FreeStyle", "A");



        dsCauHoi.add(cau001);
        dsCauHoi.add(cau002);
        dsCauHoi.add(cau003);
        dsCauHoi.add(cau004);
        dsCauHoi.add(cau005);
        dsCauHoi.add(cau006);
        dsCauHoi.add(cau007);
        dsCauHoi.add(cau008);
        dsCauHoi.add(cau009);
        dsCauHoi.add(cau010);
        dsCauHoi.add(cau011);
        dsCauHoi.add(cau012);
        dsCauHoi.add(cau013);
        dsCauHoi.add(cau014);
        dsCauHoi.add(cau015);
        dsCauHoi.add(cau016);
        dsCauHoi.add(cau017);
        dsCauHoi.add(cau018);
        dsCauHoi.add(cau019);
        dsCauHoi.add(cau020);
        dsCauHoi.add(cau021);
        dsCauHoi.add(cau022);
        dsCauHoi.add(cau023);
        dsCauHoi.add(cau024);
        dsCauHoi.add(cau025);
        dsCauHoi.add(cau026);
        dsCauHoi.add(cau027);
        dsCauHoi.add(cau028);
        dsCauHoi.add(cau029);
        dsCauHoi.add(cau030);
        dsCauHoi.add(cau031);
        dsCauHoi.add(cau032);
        dsCauHoi.add(cau033);
        dsCauHoi.add(cau034);
        dsCauHoi.add(cau035);
        dsCauHoi.add(cau036);
        dsCauHoi.add(cau037);
        dsCauHoi.add(cau038);
        dsCauHoi.add(cau039);
        dsCauHoi.add(cau040);
        dsCauHoi.add(cau041);
        dsCauHoi.add(cau042);
        dsCauHoi.add(cau043);
        dsCauHoi.add(cau044);
        dsCauHoi.add(cau045);
        dsCauHoi.add(cau046);
        dsCauHoi.add(cau047);
        dsCauHoi.add(cau048);
        dsCauHoi.add(cau049);
        dsCauHoi.add(cau050);
        dsCauHoi.add(cau051);
        dsCauHoi.add(cau052);
        dsCauHoi.add(cau053);
        dsCauHoi.add(cau054);
        dsCauHoi.add(cau055);
        dsCauHoi.add(cau056);
        dsCauHoi.add(cau057);
        dsCauHoi.add(cau058);
        dsCauHoi.add(cau059);
        dsCauHoi.add(cau060);
        dsCauHoi.add(cau061);
        dsCauHoi.add(cau062);
        dsCauHoi.add(cau063);
        dsCauHoi.add(cau064);
        dsCauHoi.add(cau065);
        dsCauHoi.add(cau066);
        dsCauHoi.add(cau067);
        dsCauHoi.add(cau068);
        dsCauHoi.add(cau069);
        dsCauHoi.add(cau070);
        dsCauHoi.add(cau071);
        dsCauHoi.add(cau072);
        dsCauHoi.add(cau073);
        dsCauHoi.add(cau074);
        dsCauHoi.add(cau075);
        dsCauHoi.add(cau076);
        dsCauHoi.add(cau077);
        dsCauHoi.add(cau078);
        dsCauHoi.add(cau079);
        dsCauHoi.add(cau080);
        dsCauHoi.add(cau081);
        dsCauHoi.add(cau082);
        dsCauHoi.add(cau083);
        dsCauHoi.add(cau084);
        dsCauHoi.add(cau085);
        dsCauHoi.add(cau086);
        dsCauHoi.add(cau087);
        dsCauHoi.add(cau088);
        dsCauHoi.add(cau089);
        dsCauHoi.add(cau090);


    }

    public void NextCauHoi()
    {
        radCauHoi = random.nextInt(sizeCauHoi);
        int dem = 0;
        for (int i = 0; i < dsCauHoiPhu.size(); i++)
        {
            if (dsCauHoi.get(radCauHoi).id == dsCauHoiPhu.get(i).id)
            {
                dem++;
            }
        }

        if (dem == 0)
        {
            GanCauHoi();
            dsCauHoiPhu.add(dsCauHoi.get(radCauHoi));
        }
        else
        {
            dem = 0;
            NextCauHoi();
        }

    }

//    DATA
    public void SetMute(Integer i)
    {
        String query = "Update DiemCao set SoundEffect = " + i;
        data.AddData(query);
    }

    public void SetMaxScore(Integer i)
    {
        String query = "Update DiemCao set DiemCao = " + i;
        data.AddData(query);
    }

    public Integer GetMaxScore()
    {
        String query = "Select MAX(DiemCao) from DiemCao";
        Integer out = 0;
        Cursor cursor = data.GetData(query);

        while (cursor.moveToNext())
        {
            out = cursor.getInt(0);
        }

        return  out;
    }
}