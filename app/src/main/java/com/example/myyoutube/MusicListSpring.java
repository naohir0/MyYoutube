package com.example.myyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicListSpring extends AppCompatActivity {

    /** UIのオブジェクト **/
    protected MediaPlayer _player;
    protected Button btPlay;
    protected Button btBack;
    protected Button btForward;
    protected Button btMix;
    protected TextView tvSelectedMusic;

    protected String musicId;
    protected int musicPosition;  /** 現在再生中の音楽を示す **/
    protected Activity ac;  /** 現在のコンテキスト **/
    protected List<Map<String,String>> musicListBox; /** 音楽情報のオブジェクト **/
    protected int listLength;  /** ジャンルの音楽数 **/
    protected double musicPlayNum;
    protected int testNum=0;
    protected boolean _isMix = false; /** シャッフルかを識別 **/
    protected boolean _isLoop; /** ループ再生かを識別 **/
    /** 各音楽のURI **/
    protected String mediaURI1;
    protected String mediaURI2;
    protected String mediaURI3;
    protected String mediaURI4;
    protected String mediaURI5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_spring);
        /** （３）現在のコンテキストを登録する **/
        ac = MusicListSpring.this;
        ListView lvMusicItemSpring = findViewById(R.id.lvMusicItemSpring);
        btPlay = findViewById(R.id.btPlaySp);
        btBack = findViewById(R.id.btBackSp);
        btForward = findViewById(R.id.btFrowardSp);
        btMix = findViewById(R.id.btMixSp);
        tvSelectedMusic = findViewById(R.id.tvSelectedMusicSp);

        btnSetEnabled(btPlay,btBack,btForward);
        crateList(MusicListSpring.this,lvMusicItemSpring);

        lvMusicItemSpring.setOnItemClickListener(new prepareStartListener());
        btPlay.setOnClickListener(new onPlayMusicListener());
        btBack.setOnClickListener(new onBackMusicListener());
        btForward.setOnClickListener(new onNextMusicListener());
        btMix.setOnClickListener(new onMusicMixListener());

        /** 音楽のURIを設定 **/
        mediaURI1 = "android.resource://" + getPackageName() + "/" + R.raw.abcd;
        mediaURI2 = "android.resource://" + getPackageName() + "/" + R.raw.aabb;
        mediaURI3 = "android.resource://" + getPackageName() + "/" + R.raw.aacc;
        mediaURI4 = "android.resource://" + getPackageName() + "/" + R.raw.abcd;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_player.isPlaying()){
            _player.stop();
        }
        _player.release();
        _player = null;
    }

    /** _playerを戻す **/
    public void setMediaSeekToZero(){
        _player.seekTo(0);
    }

    /** リスト作成メゾット **/
    public void crateList(Activity context,ListView lvMenu){
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String music1 = data.getString("music1");
        String artist1 = data.getString("artist1");
        String music2 = data.getString("music2");
        String artist2 = data.getString("artist2");
        String music3 = data.getString("music3");
        String artist3 = data.getString("artist3");
        String music4 = data.getString("music4");
        String artist4 = data.getString("artist4");
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> listItem = new HashMap<>();
        listItem.put("name",music1);
        listItem.put("artist",artist1);
        list.add(listItem);
        listItem = new HashMap<>();
        listItem.put("name",music2);
        listItem.put("artist",artist2);
        list.add(listItem);
        listItem = new HashMap<>();
        listItem.put("name",music3);
        listItem.put("artist",artist3);
        list.add(listItem);
        listItem = new HashMap<>();
        listItem.put("name",music4);
        listItem.put("artist",artist4);
        list.add(listItem);
        musicListBox = list;
        String[] from = {"name","artist"};
        listLength = list.size();
        int[] to = {android.R.id.text1,android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(context,list, android.R.layout.simple_list_item_2,from,to);
        lvMenu.setAdapter(adapter);
    }
    /** ボタンを押せなくする **/
    public void btnSetEnabled(Button btPlay,Button btBack,Button btForward){
        btPlay.setEnabled(false);
        btBack.setEnabled(false);
        btForward.setEnabled(false);
    }

    /** リストのItemクリック => 再生準備開始 **/
    protected class prepareStartListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(_player!=null){
                if(_player.isPlaying()){
                    _player.stop();
                }
                _player.release();
                _player = null;
                btnSetEnabled(btPlay,btBack,btForward);
                btPlay.setText(R.string.bt_play_pause);
                String toastMusicAl = "再生中の音楽を停止しました";
                Toast.makeText(MusicListSpring.this,toastMusicAl,Toast.LENGTH_SHORT).show();
            } else {
                Map<String, String> data = (Map<String, String>) adapterView.getItemAtPosition(i);
                String title = data.get("name");
                musicId = title;
                musicPosition = i;
                if(_isMix!=false){
                    _isMix=false;
                }
                if(_isLoop!=false){
                    _isLoop = false;
                }
                prepareMusic(musicId);
            }
        }

    }

    /** 音楽を設定、再生準備開始（音楽再生の核となるメゾット） **/
    protected void prepareMusic(String title){
        _player = new MediaPlayer();
        Uri mediaParsedUri;
        String musicName;
        /** （４）以下のURIで 音楽を設定する（リストの並び順) **/
        switch (musicPosition) {
            case 0:
                mediaParsedUri = Uri.parse(mediaURI1);
                musicName = musicListBox.get(0).get("name");
                tvSelectedMusic.setText("No 0 : "+ musicName);
                break;
            case 1:
                mediaParsedUri = Uri.parse(mediaURI2);
                musicName = musicListBox.get(1).get("name");
                tvSelectedMusic.setText("No 1 : "+ musicName);
                break;
            case 2:
                mediaParsedUri = Uri.parse(mediaURI3);
                musicName = musicListBox.get(2).get("name");
                tvSelectedMusic.setText("No 2 : " + musicName);
                break;
            default:
                mediaParsedUri = Uri.parse(mediaURI4);
                musicName = musicListBox.get(3).get("name");
                tvSelectedMusic.setText("No 3 : " + musicName);
                break;
        }
        try {
            _player.setDataSource(ac, mediaParsedUri);
            _player.setOnPreparedListener(new preparedListener());
            _player.setOnCompletionListener(new completionListener());
            _player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(title=="自動再生") {
                String al = "自動再生完了" + musicPosition;
                Toast.makeText(MusicListSpring.this, al, Toast.LENGTH_SHORT).show();
                btPlay.setText(R.string.bt_play_pause);
            } else {
                String playNum = String.valueOf(musicPlayNum);
                String musicNum = String.valueOf(musicPosition);
                btPlay.setText(R.string.bt_play_play);
                String toastText = "「 " + title + " 」" + "の再生準備ができました "+ playNum + "/ "+musicNum+ "/ "+testNum;
                Toast.makeText(MusicListSpring.this, toastText, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** UIのボタンごとの処理 **/
    /** 戻るボタンの処理 **/
    protected class onBackMusicListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(_player!=null) {
                _player.seekTo(0);
            }
        }
    }

    /** 再生＆一時停止ボタンの処理 **/
    public class onPlayMusicListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(_player.isPlaying()){
                _player.pause();
                btPlay.setText(R.string.bt_play_play);
            } else {
                _player.start();
                btPlay.setText(R.string.bt_play_pause);
            }
        }
    }

    /** 進むボタンの処理 **/
    protected class onNextMusicListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(_player!=null){
                if(_player.isPlaying()){
                    _player.stop();
                }
                _player.release();
                _player = null;
            }
            musicPosition+=1;
            String title = musicListBox.get(musicPosition).get("name");
            musicId = title;
            if(_isLoop!=false) {
                _isLoop = false;
            }
            prepareMusic(musicId);
        }
    }
    /** シャッフルボタンの処理 **/
    public class onMusicMixListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            musicPlayNum = Math.random()*listLength;
            musicPosition = (int)musicPlayNum;
            Log.i("Mix Number",String.valueOf(musicPlayNum));
            if(_player!=null) {
                if (_player.isPlaying()) {
                    _player.stop();
                }
                _player.release();
                _player = null;
            }
            String t = "シャッフル";
            if(_isMix!=true) {
                _isMix = true;
            }
            if(_isLoop!=false) {
                _isLoop = false;
            }
            prepareMusic(t);
        }
    }
    /** UIのボタンごとの処理 END**/


    /** MediaPlayerの状態ごとの動作 **/
    /** MediaPlayerが準備完了したときの処理 **/
    protected class preparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            if(_isLoop==true){
                _player.start();
                btPlay.setText(R.string.bt_play_pause);
            } else {
                _player.seekTo(0);
                btPlay.setEnabled(true);
                btBack.setEnabled(true);
                btForward.setEnabled(true);
            }
        }
    }

    /** 音楽が終了したときの処理 **/
    public class completionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if(_player!=null){
                if(_player.isPlaying()){
                    _player.stop();
                }
                _player.release();
                _player = null;
            }
            String t = "自動再生";
            if(_isMix == false){
                if(musicPosition < listLength){
                    musicPosition=musicPosition+1;
                }
            } else {
                musicPlayNum = Math.random() * listLength;
                musicPosition = (int)musicPlayNum;
            }
            if(_isLoop!=true) {
                _isLoop = true;
            }
            prepareMusic(t);
        }
    }
    /** MediaPlayerの状態ごとの動作 END **/
}