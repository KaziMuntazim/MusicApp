package com.example.enmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class PlaySong extends AppCompatActivity {
    ImageView img_music , img_per , img_start , img_next;
    SeekBar seek_bar;
    TextView text_show;
    ArrayList<File> song;
    MediaPlayer player;
    int pos;
    String sName;
    Thread thread;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        thread.interrupt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_song);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img_music = findViewById(R.id.img_music);
        img_per = findViewById(R.id.img_per);
        img_start = findViewById(R.id.img_start);
        img_next = findViewById(R.id.img_next);
        seek_bar = findViewById(R.id.seek_bar);
        text_show = findViewById(R.id.show_text);
        ImagAnimation();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        song = (ArrayList) bundle.getParcelableArrayList("SNAME");
        String textCon = intent.getStringExtra("CSong");
        pos = bundle.getInt("POSITION" , 0);
        text_show.setSelected(true);
        Uri uri = Uri.parse(song.get(pos).toString());
        sName = song.get(pos).getName();
        text_show.setText(sName);
        player = MediaPlayer.create(this , uri);
        player.start();
        seek_bar.setMax(player.getDuration());

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seek_bar.getProgress());
            }
        });
        thread = new Thread(){
            @Override
            public void run() {
                int Cpos = 0;
                try {
                    while (Cpos < player.getDuration()){
                        Cpos = player.getCurrentPosition();
                        seek_bar.setProgress(Cpos);
                        sleep(800);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }





    public void ClickPer(View view){
        player.stop();
        player.release();
        if (pos!=0){
            pos = pos - 1;
        }else {
            pos = song.size() - 1;
        }
        Uri uri = Uri.parse(song.get(pos).toString());
        sName = song.get(pos).getName();
        text_show.setText(sName);
        player = MediaPlayer.create(this , uri);
        player.start();
        seek_bar.setMax(player.getDuration());
        img_start.setImageResource(R.drawable.paus);
        ImagAnimation();
    }
    public void ClickStart(View view){

        if (player.isPlaying()){
            img_start.setImageResource(R.drawable.play);
            player.pause();
            stopAnimation();
        }else {
            img_start.setImageResource(R.drawable.paus);
            player.start();
            ImagAnimation();
        }
    }
    public void ClickNext(View view){
        player.stop();
        player.release();
        if (pos!=song.size() - 1){
            pos = pos + 1;
        }else {
            pos = 0;
        }
        Uri uri = Uri.parse(song.get(pos).toString());
        sName = song.get(pos).getName();
        text_show.setText(sName);
        player = MediaPlayer.create(this , uri);
        player.start();
        seek_bar.setMax(player.getDuration());
        img_start.setImageResource(R.drawable.paus);
        ImagAnimation();
    }

    public void ImagAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this , R.anim.img_roted);
        img_music.startAnimation(animation);
    }
    public void stopAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this , R.anim.stop);
        img_music.startAnimation(animation);

    }
}