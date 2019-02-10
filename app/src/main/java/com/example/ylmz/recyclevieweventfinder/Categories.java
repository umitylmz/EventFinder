package com.example.ylmz.recyclevieweventfinder;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Categories extends AppCompatActivity implements ListItemClickListener {

    private catAdapter adapter;
    private RecyclerView rw;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.categories:


                    break;
                case R.id.events:
                    Intent intent=new Intent(Categories.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searching:

                    Intent intent2=new Intent(Categories.this,search.class);
                    startActivity(intent2);
                    break;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);







        BottomNavigationView navig=(BottomNavigationView)findViewById(R.id.navigation);

        navig.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu=navig.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);


        Object[][] data = new Object[7][3];

        data[0][0] = R.drawable.music;
        data[1][0] = R.drawable.business;
        data[2][0] = R.drawable.art;
        data[3][0] = R.drawable.parties;
        data[4][0] = R.drawable.sport;
        data[5][0] = R.drawable.education;
        data[6][0] = R.drawable.food;



        data[0][1] = "deneme";
        data[0][2] = "deneme2";
        data[1][1] = "deneme";
        data[1][2] = "deneme2";
        data[2][1] = "deneme";
        data[2][2] = "deneme2";
        data[3][1] = "deneme";
        data[3][2] = "deneme2";
        data[4][1] = "deneme";
        data[4][2] = "deneme2";
        data[5][1] = "deneme";
        data[5][2] = "deneme2";
        data[6][1] = "deneme";
        data[6][2] = "deneme2";



        rw = (RecyclerView) findViewById(R.id.rv_forecast);



        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rw.setLayoutManager(layoutManager);
        rw.setHasFixedSize(true);
        adapter = new catAdapter(data,this);
        rw.setAdapter(adapter);



    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            rw.setLayoutManager(layoutManager);
            rw.setHasFixedSize(true);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            rw.setLayoutManager(layoutManager);
            rw.setHasFixedSize(true);
        }
    }

        @Override
        public void onListItemClick(int clickedItemIndex) {
            Log.v("onClick","Item#"+Integer.toString(clickedItemIndex));

            String value;

            if(clickedItemIndex==0){
                value="music";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==1){
                value="business";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==2){
                value="art";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==3){
                value="festivals";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==4){
                value="sports";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==5){
                value="education";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }
            else if(clickedItemIndex==6){
                value="food";
                Intent appInfo = new Intent(Categories.this, MainActivity.class);
                appInfo.putExtra("startcat",value);
                startActivity(appInfo);
            }









        }


}

