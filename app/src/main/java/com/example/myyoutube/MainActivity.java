package com.example.myyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lvMusicMenu = findViewById(R.id.lvMusicMenu);
        List<String> menuList = new ArrayList<>();
        /** （１）各音楽のアクテビティを作成 **/
        menuList.add("全曲シャッフル");
        menuList.add("春の音楽");
        menuList.add("サザンオールスターズ");
        menuList.add("あいみょん");
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,menuList);
        lvMusicMenu.setAdapter(adapter);
        lvMusicMenu.setOnItemClickListener(new ItemClickListener());
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String item = (String)adapterView.getItemAtPosition(i);
            Intent intent;
            switch(item){
                /** （２）音楽のデータを登録する **/
                case "全曲シャッフル":
                    intent = new Intent(MainActivity.this,MusicListAll.class);
                    intent.putExtra("music1","ヒカリへ");
                    intent.putExtra("artist1","miwa");
                    intent.putExtra("music2","春よ来い");
                    intent.putExtra("artist2","松任谷由実");
                    intent.putExtra("music3","唐人物語");
                    intent.putExtra("artist3","サザンオールスターズ");
                    intent.putExtra("music4","桜");
                    intent.putExtra("artist4","コブクロ");
                    startActivity(intent);
                    break;
                case "春の音楽":
                    intent = new Intent(MainActivity.this,MusicListSpring.class);
                    intent.putExtra("music1","ヒカリへ");
                    intent.putExtra("artist1","miwa");
                    intent.putExtra("music2","春よ来い");
                    intent.putExtra("artist2","松任谷由実");
                    intent.putExtra("music3","唐人物語");
                    intent.putExtra("artist3","サザンオールスターズ");
                    intent.putExtra("music4","さよならまたな");
                    intent.putExtra("artist4","フィッシャーズ");
                    startActivity(intent);
                    break;
                case "サザンオールスターズ":
                    intent = new Intent(MainActivity.this,MusicListSouth.class);
                    intent.putExtra("music1","希望の轍");
                    intent.putExtra("artist1","サザンオールスターズ");
                    intent.putExtra("music2","太陽は罪な奴");
                    intent.putExtra("artist2","サザンオールスターズ");
                    intent.putExtra("music3","夕日に別れを告げて");
                    intent.putExtra("artist3","サザンオールスターズ");
                    intent.putExtra("music4","Aja〜彩〜");
                    intent.putExtra("artist4","サザンオールスターズ");
                    startActivity(intent);
                    break;
                case "あいみょん":
                    intent = new Intent(MainActivity.this,MusicListAimyon.class);
                    intent.putExtra("music1","マリーゴールド");
                    intent.putExtra("artist1","あいみょん");
                    intent.putExtra("music2","ハルノヒ");
                    intent.putExtra("artist2","あいみょん");
                    intent.putExtra("music3","さよなら今日の日に");
                    intent.putExtra("artist3","あいみょん");
                    intent.putExtra("music4","What if");
                    intent.putExtra("artist4","あいみょん");
                    startActivity(intent);
                    break;
            }
        }
    }
}